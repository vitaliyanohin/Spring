package com.boraji.tutorial.spring.service;

import model.User;

import java.util.List;

public interface UserService {

    void getUserByLogin(User user);

    List<User> listUsers();
}
