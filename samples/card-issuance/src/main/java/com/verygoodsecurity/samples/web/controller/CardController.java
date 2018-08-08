package com.verygoodsecurity.samples.web.controller;

import com.verygoodsecurity.samples.web.model.CreateCardResponse;
import com.verygoodsecurity.samples.web.model.UserForm;
import com.verygoodsecurity.samples.domain.Card;
import com.verygoodsecurity.samples.domain.User;
import com.verygoodsecurity.samples.service.CardService;
import com.verygoodsecurity.samples.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

  private final CardService cardService;
  private final UserService userService;

  public CardController(CardService cardService, UserService userService) {
    this.cardService = cardService;
    this.userService = userService;
  }

  @PostMapping("/cards")
  public CreateCardResponse createCard(@RequestBody UserForm userForm) {
    final User user =
        userService
            .getUserByEmail(userForm.getEmail())
            .orElseGet(() -> userService.createUser(userForm));

    final Card card = cardService.createCard(user.getToken());

    return CreateCardResponse.fromCardEntity(card);
  }
}
