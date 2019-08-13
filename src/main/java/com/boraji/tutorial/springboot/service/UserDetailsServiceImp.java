package com.boraji.tutorial.springboot.service;

import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.repository.UserJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userJpaRepository.findByEmail(username).get();
    if (user != null) {
     return user;
    } else {
      throw new UsernameNotFoundException("User not found.");
    }
  }
}
