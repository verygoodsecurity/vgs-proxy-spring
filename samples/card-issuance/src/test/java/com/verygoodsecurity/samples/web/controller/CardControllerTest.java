package com.verygoodsecurity.samples.web.controller;

import com.verygoodsecurity.samples.domain.Card;
import com.verygoodsecurity.samples.domain.User;
import com.verygoodsecurity.samples.service.CardService;
import com.verygoodsecurity.samples.service.UserService;
import com.verygoodsecurity.samples.web.model.UserForm;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CardController.class, secure = false)
public class CardControllerTest {

  private static final String USER_EMAIL = "john.doe@gmail.com";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CardService cardService;

  @MockBean
  private UserService userService;

  @Test
  public void shouldReturnCreatedOnSuccess() throws Exception {
    when(userService.getUserByEmail(any())).thenReturn(Optional.empty());
    when(userService.createUser(any())).then(invocation -> {
      final UserForm userForm = invocation.getArgument(0);
      final User user = new User();
      user.setId(RandomUtils.nextLong());
      user.setToken(userForm.getEmail());
      user.setFirstName(userForm.getFirstName());
      user.setLastName(userForm.getLastName());
      user.setEmail(userForm.getEmail());
      user.setBirthDate(userForm.getBirthDate());
      user.setSsn(userForm.getSsn());
      return user;
    });

    final Card card = new Card();
    card.setId(RandomUtils.nextLong());
    card.setToken(UUID.randomUUID().toString());
    card.setCardProductToken(UUID.randomUUID().toString());
    card.setPan("1111222233334444");
    card.setCvvNumber("123");
    card.setExpirationTime(LocalDateTime.of(2025, Month.JANUARY, 1, 12, 30));

    when(cardService.createCard(any())).then(invocation -> {
      final String userToken = invocation.getArgument(0);
      card.setUserToken(userToken);
      return card;
    });

    mockMvc
        .perform(createCard(new ClassPathResource("CardControllerTest/UserForm.json")))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").value(equalTo(card.getToken())))
        .andExpect(jsonPath("$.card_product_token").value(equalTo(card.getCardProductToken())))
        .andExpect(jsonPath("$.user_token").value(USER_EMAIL))
        .andExpect(jsonPath("$.pan").value(equalTo(card.getPan())))
        .andExpect(jsonPath("$.cvv_number").value(equalTo(card.getCvvNumber())))
        .andExpect(jsonPath("$.expiration_time").value(equalTo("2025-01-01T12:30:00")));

    verify(userService).getUserByEmail(USER_EMAIL);
    verify(cardService).createCard(USER_EMAIL);
  }

  /**
   * @param resource Where to read the request body from
   */
  private RequestBuilder createCard(Resource resource) throws Exception {
    final String payload = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
    return post("/cards")
        .contentType(MediaType.APPLICATION_JSON)
        .content(payload);
  }
}
