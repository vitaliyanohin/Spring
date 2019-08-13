package com.boraji.tutorial.springboot.service;

import com.boraji.tutorial.springboot.model.Basket;
import com.boraji.tutorial.springboot.model.Order;
import com.boraji.tutorial.springboot.model.Product;
import com.boraji.tutorial.springboot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {

  void addOrderToDb(Order order);

  void addProductToBasket(User user, Long id);

  void addUserBasket(User user);

  Optional<Basket> getBasket(User user);

  List<Product> getProductsFromUserBasket(User user);
}
