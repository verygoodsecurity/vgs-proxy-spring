package com.verygoodsecurity.samples.service;

import com.verygoodsecurity.samples.controller.model.UserForm;
import com.verygoodsecurity.samples.domain.User;

import java.util.Optional;

public interface UserService {

  Optional<User> getUserByEmail(String email);

  User createUser(UserForm userForm);
}
