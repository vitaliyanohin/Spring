package com.boraji.tutorial.springboot.service;



import com.boraji.tutorial.springboot.model.Basket;
import com.boraji.tutorial.springboot.model.Order;
import com.boraji.tutorial.springboot.model.Product;
import com.boraji.tutorial.springboot.model.User;
import com.boraji.tutorial.springboot.repository.OrderJpaRepository;
import com.boraji.tutorial.springboot.repository.ProductJpaRepository;
import com.boraji.tutorial.springboot.repository.UserBasketJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserOrderServiceImpl implements UserOrderService {

  private final UserBasketJpaRepository userBasketJpaRepository;
  private final OrderJpaRepository orderJpaRepository;
  private final ProductJpaRepository productJpaRepository;

  public UserOrderServiceImpl(UserBasketJpaRepository userBasketJpaRepository,
                              OrderJpaRepository orderJpaRepository,
                              ProductJpaRepository productJpaRepository) {
    this.userBasketJpaRepository = userBasketJpaRepository;
    this.orderJpaRepository = orderJpaRepository;
    this.productJpaRepository = productJpaRepository;
  }

  @Transactional
  public void addOrderToDb(Order order) {
    orderJpaRepository.save(order);
    order.getBoxId().setAvailable("false");
    userBasketJpaRepository.save(order.getBoxId());
  }

  @Transactional
  @Override
  public void addProductToBasket(User user, Long id) {
    Product product = productJpaRepository.getOne(id);
    Basket userBasket = userBasketJpaRepository.getBasket(user).get();
    userBasket.addProducts(product);
    userBasketJpaRepository.save(userBasket);
  }

  @Transactional
  @Override
  public void addUserBasket(User user) {
    Basket newBasket = new Basket(new ArrayList<>(), user, "true");
    userBasketJpaRepository.save(newBasket);
    user.setBasket(newBasket);
  }

  @Transactional
  @Override
  public List<Product> getProductsFromUserBasket(User user) {
    Basket basket = userBasketJpaRepository.getBasket(user).get();
    return basket.getProducts();
  }

  @Transactional
  @Override
  public Optional<Basket> getBasket(User user) {
    return userBasketJpaRepository.getBasket(user);
  }
}
