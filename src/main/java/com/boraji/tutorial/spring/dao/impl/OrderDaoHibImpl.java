package com.boraji.tutorial.spring.dao.impl;

import com.boraji.tutorial.spring.dao.OrderDao;
import model.Basket;
import model.Order;
import model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoHibImpl implements OrderDao {

  private final SessionFactory sessionFactory;

  public OrderDaoHibImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public void addOrderToDb(User userId, String address, Basket boxId) {
    Order order = new Order(userId, boxId, address);
    sessionFactory.getCurrentSession().save(order);
  }
}
