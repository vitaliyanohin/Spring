package com.boraji.tutorial.spring.dao;

import model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface UserDao {

   RowMapper<User> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
      return new User(resultSet.getLong("id"),
              resultSet.getString("email"),
              resultSet.getString("password"),
              resultSet.getString("role"));
   };

   Optional<User> getUserByLogin(String login);

   List<User> getAllUsers();

   void saveOrUpdateUser(User user);

   void deleteUser(Long id);

   Optional<User> getUserById(Long id);

}
