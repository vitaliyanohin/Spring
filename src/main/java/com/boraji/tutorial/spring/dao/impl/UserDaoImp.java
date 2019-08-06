package com.boraji.tutorial.spring.dao.impl;

import com.boraji.tutorial.spring.dao.UserDao;
import model.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

  private final SessionFactory sessionFactory;
  private JdbcTemplate jdbcTemplate;

  @Autowired
  public UserDaoImp(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Optional<User> getUserByLogin(String login) {
    User user = jdbcTemplate.queryForObject("SELECT * FROM user_hibernate WHERE email = ?", new Object[]{login}, ROW_MAPPER);
    return Optional.ofNullable(user);
  }

  @Override
  public List<User> getAllUsers() {
    @SuppressWarnings("unchecked") TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
    return query.getResultList();
  }

  @Override
  public void saveOrUpdateUser(User user) {
    sessionFactory.getCurrentSession().saveOrUpdate(user);
  }

  @Override
  public void deleteUser(Long id) {
    sessionFactory.getCurrentSession().delete(getUserById(id).get());
  }

  @Override
  public Optional<User> getUserById(Long id) {
    return Optional.ofNullable(sessionFactory.getCurrentSession().get(User.class, id));
  }
}
