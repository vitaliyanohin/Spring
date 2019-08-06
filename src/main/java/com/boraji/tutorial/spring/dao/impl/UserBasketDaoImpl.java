package com.boraji.tutorial.spring.dao.impl;

import com.boraji.tutorial.spring.dao.UserBoxDao;
import model.Basket;
import model.Product;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserBasketDaoImpl implements UserBoxDao {

  private final SessionFactory sessionFactory;
  private static final String GET_BASKET = "FROM Basket WHERE user_id = :id AND available= 'true'";

  public UserBasketDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<Product> getProductsFromUserBox(User user) {
    Query query = sessionFactory.getCurrentSession().createQuery(GET_BASKET);
    query.setParameter("id", user);
    Basket basket = (Basket) query.uniqueResult();
    return basket.getProducts();
  }

  @Override
  public void addUserBasketInDb(Basket basket) {
    sessionFactory.getCurrentSession().saveOrUpdate(basket);
  }

  @Override
  public Optional<Basket> getBasket(User user) {
    Query query = sessionFactory.getCurrentSession().createQuery(GET_BASKET);
    query.setParameter("id", user);
    Basket basket = (Basket) query.uniqueResult();
    return Optional.ofNullable(basket);
  }
}
