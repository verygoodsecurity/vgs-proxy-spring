package com.verygoodsecurity.samples.service.impl;

import com.verygoodsecurity.samples.component.marqeta.MarqetaClient;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserRequest;
import com.verygoodsecurity.samples.component.marqeta.model.user.CreateUserResponse;
import com.verygoodsecurity.samples.domain.User;
import com.verygoodsecurity.samples.domain.repository.UserRepository;
import com.verygoodsecurity.samples.service.UserService;
import com.verygoodsecurity.samples.web.model.UserForm;
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

    final CreateUserResponse response = marqetaClient.createUser(request);

    final User user = new User();
    user.setToken(response.getToken());
    user.setFirstName(response.getFirstName());
    user.setLastName(response.getLastName());
    user.setEmail(response.getEmail());
    user.setBirthDate(response.getBirthDate());
    user.setSsn(response.getSsn());

    return userRepository.save(user);
  }
}
