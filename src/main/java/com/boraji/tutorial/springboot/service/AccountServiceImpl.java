package com.boraji.tutorial.springboot.service;



import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.repository.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

  private final UserJpaRepository userJpaRepository;

  public AccountServiceImpl(UserJpaRepository userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  @Transactional
  @Override
  public Optional<User> getUserByLogin(String login) {
    return userJpaRepository.findByEmail(login);
  }

  @Transactional
  @Override
  public Optional<User> getUserById(long id) {
    return Optional.ofNullable(userJpaRepository.getOne(id));
  }

  @Transactional
  @Override
  public List<User> getAllUsers() {
    return userJpaRepository.findAll();
  }

  @Transactional
  @Override
  public void saveOrUpdateUser(User user) {
    userJpaRepository.save(user);
  }

  @Transactional
  @Override
  public void addUser(User name) {
    userJpaRepository.save(name);
  }

  @Transactional
  @Override
  public void deleteUser(Long id) {
    userJpaRepository.delete(id);
  }
}
