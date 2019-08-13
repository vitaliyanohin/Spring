package com.boraji.tutorial.springboot.service;



import com.boraji.tutorial.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {

  Optional<User> getUserByLogin(String login);

  List<User> getAllUsers();

  void saveOrUpdateUser(User user);

  void addUser(User name);

  void deleteUser(Long id);

  Optional<User> getUserById(long id);
}
