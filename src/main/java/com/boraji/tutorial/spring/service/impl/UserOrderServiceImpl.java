package com.boraji.tutorial.spring.service.impl;


import com.boraji.tutorial.spring.dao.OrderDao;
import com.boraji.tutorial.spring.dao.ProductDao;
import com.boraji.tutorial.spring.dao.UserBoxDao;
import com.boraji.tutorial.spring.service.UserOrderService;
import model.Basket;
import model.Order;
import model.Product;
import model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserOrderServiceImpl implements UserOrderService {

  private final UserBoxDao userBoxDao;
  private final OrderDao orderDao;
  private final ProductDao productDao;

  public UserOrderServiceImpl(UserBoxDao userBoxDao,
                              OrderDao orderDao,
                              ProductDao productDao) {
    this.userBoxDao = userBoxDao;
    this.orderDao = orderDao;
    this.productDao = productDao;
  }

  @Transactional
  public void addOrderToDb(Order order) {
    orderDao.addOrderToDb(order.getUserId(), order.getAddress(), order.getBoxId());
    order.getBoxId().setAvailable("false");
    userBoxDao.addUserBasketInDb(order.getBoxId());
  }

  @Transactional
  @Override
  public void addProductToBasket(User user, Long id) {
    Product product = productDao.getProductById(id).get();
    Basket userBasket = userBoxDao.getBasket(user).get();
    userBasket.addProducts(product);
    userBoxDao.addUserBasketInDb(userBasket);
  }

  @Transactional
  @Override
  public void addUserBasket(User user) {
    Basket newBasket = new Basket(new ArrayList<>(), user);
    userBoxDao.addUserBasketInDb(newBasket);
    user.setBasket(newBasket);
  }

  @Transactional
  @Override
  public List<Product> getProductsFromUserBasket(User user) {
    return userBoxDao.getProductsFromUserBasket(user);
  }

  @Transactional
  @Override
  public Optional<Basket> getBasket(User user) {
    return userBoxDao.getBasket(user);
  }
}
