package com.boraji.tutorial.spring.service;

import model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

  Optional<Product> getProductById(long id);

  List<Product> getAllProducts();

  double orderTotalPrice(List<Product> productList);

  void deleteProduct(long id);

  void saveOrUpdateProduct(Product name);
}
