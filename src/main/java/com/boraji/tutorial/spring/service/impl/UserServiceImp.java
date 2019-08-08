package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.UserDao;
import com.boraji.tutorial.spring.service.UserService;
import model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

  private final UserDao userDao;

  public UserServiceImp(UserDao userDao) {
    this.userDao = userDao;
  }

  @Transactional
  @Override
  public void getUserByLogin(User user) {
    userDao.saveOrUpdateUser(user);
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> listUsers() {
    return userDao.getAllUsers();
  }
}
