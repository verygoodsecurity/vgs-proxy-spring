package com.verygoodsecurity.samples.service.impl;

import com.verygoodsecurity.samples.component.marqeta.MarqetaClient;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserRequest;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserResponse;
import com.verygoodsecurity.samples.domain.User;
import com.verygoodsecurity.samples.domain.repository.UserRepository;
import com.verygoodsecurity.samples.web.model.UserForm;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private static final String USER_EMAIL = "john.doe@gmail.com";

  private UserServiceImpl target;

  @Mock
  private MarqetaClient marqetaClient;

  @Mock
  private UserRepository userRepository;

  @Before
  public void setUp() {
    target = new UserServiceImpl(marqetaClient, userRepository);

    when(userRepository.save(any())).then(invocation -> {
      final User entity = invocation.getArgument(0);
      entity.setId(RandomUtils.nextLong());
      return entity;
    });
  }

  @Test
  public void shouldReturnUserByEmail() {
    final User entity = new User();
    when(userRepository.findByEmail(any())).thenReturn(Optional.of(entity));

    assertThat(target.getUserByEmail(USER_EMAIL), is(optionalWithValue(sameInstance(entity))));

    verify(userRepository).findByEmail(USER_EMAIL);
  }

  @Test
  public void shouldCallMarqetaApiAndSaveResponse() {
    final UserForm userForm = new UserForm();
    userForm.setFirstName("John");
    userForm.setLastName("Doe");
    userForm.setEmail(USER_EMAIL);
    userForm.setBirthDate(LocalDate.now().minusYears(25));
    userForm.setSsn("111223333");

    final CreateUserResponse response = new CreateUserResponse();
    response.setToken(userForm.getEmail());
    response.setFirstName(userForm.getFirstName());
    response.setLastName(userForm.getLastName());
    response.setEmail(userForm.getEmail());
    response.setBirthDate(userForm.getBirthDate());
    response.setSsn(userForm.getSsn());

    when(marqetaClient.createUser(any())).thenReturn(response);

    final User entity = target.createUser(userForm);

    assertThat(entity, is(notNullValue()));
    assertThat(entity.getToken(), is(equalTo(response.getToken())));
    assertThat(entity.getFirstName(), is(equalTo(response.getFirstName())));
    assertThat(entity.getLastName(), is(equalTo(response.getLastName())));
    assertThat(entity.getEmail(), is(equalTo(response.getEmail())));
    assertThat(entity.getBirthDate(), is(equalTo(response.getBirthDate())));
    assertThat(entity.getSsn(), is(equalTo(response.getSsn())));

    final ArgumentCaptor<CreateUserRequest> arg = ArgumentCaptor.forClass(CreateUserRequest.class);
    verify(marqetaClient).createUser(arg.capture());

    final CreateUserRequest request = arg.getValue();

    assertThat(request, is(notNullValue()));
    assertThat(request.getToken(), is(userForm.getEmail()));
    assertThat(request.getFirstName(), is(equalTo(userForm.getFirstName())));
    assertThat(request.getLastName(), is(equalTo(userForm.getLastName())));
    assertThat(request.getEmail(), is(equalTo(userForm.getEmail())));
    assertThat(request.getBirthDate(), is(equalTo(userForm.getBirthDate())));
    assertThat(request.getSsn(), is(equalTo(userForm.getSsn())));
  }
}
