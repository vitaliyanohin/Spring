package com.boraji.tutorial.spring.service;

import model.Basket;
import model.Order;
import model.Product;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {

  void addOrderToDb(Order order);

  void addProductToBasket(User user, Long id);

  void addUserBasket(User user);

  Optional<Basket> getBasket(User user);

  List<Product> getProductsFromUserBasket(User user);
}
