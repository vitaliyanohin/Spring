package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.UserDao;
import com.boraji.tutorial.spring.service.AccountService;
import model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

  private final UserDao userDao;

  public AccountServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Transactional
  @Override
  public Optional<User> getUserByLogin(String login) {
    return userDao.getUserByLogin(login);
  }

  @Transactional
  @Override
  public Optional<User> getUserById(long id) {
    return userDao.getUserById(id);
  }

  @Transactional
  @Override
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Transactional
  @Override
  public void saveOrUpdateUser(User user) {
    userDao.saveOrUpdateUser(user);
  }

  @Transactional
  @Override
  public void addUser(User name) {
    userDao.saveOrUpdateUser(name);
  }

  @Transactional
  @Override
  public void deleteUser(Long id) {
    userDao.deleteUser(id);
  }
}
