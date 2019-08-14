package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private UserDao userDao;

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.getUserByLogin(username).get();
    if (user != null) {
     return user;
    } else {
      throw new UsernameNotFoundException("User not found.");
    }
  }
}
