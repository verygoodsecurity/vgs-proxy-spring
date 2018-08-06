package com.verygoodsecurity.samples.service.impl;

import com.verygoodsecurity.samples.component.marqeta.MarqetaClient;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserRequest;
import com.verygoodsecurity.samples.controller.model.UserForm;
import com.verygoodsecurity.samples.domain.User;
import com.verygoodsecurity.samples.domain.repository.UserRepository;
import com.verygoodsecurity.samples.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final MarqetaClient marqetaClient;
  private final UserRepository userRepository;

  public UserServiceImpl(MarqetaClient marqetaClient, UserRepository userRepository) {
    this.marqetaClient = marqetaClient;
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User createUser(UserForm userForm) {
    final CreateUserRequest request = new CreateUserRequest();
    request.setToken(userForm.getEmail());
    request.setFirstName(userForm.getFirstName());
    request.setLastName(userForm.getLastName());
    request.setEmail(userForm.getEmail());
    request.setBirthDate(userForm.getBirthDate());
    request.setSsn(userForm.getSsn());

    marqetaClient.createUser(request);

    final User user = new User();
    user.setToken(request.getToken());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setBirthDate(request.getBirthDate());
    user.setSsn(request.getSsn());

    return userRepository.save(user);
  }
}
