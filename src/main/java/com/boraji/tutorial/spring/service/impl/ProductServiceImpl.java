package com.boraji.tutorial.spring.service.impl;


import com.boraji.tutorial.spring.dao.ProductDao;
import com.boraji.tutorial.spring.service.ProductService;
import model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductDao productDao;

  public ProductServiceImpl(ProductDao productDao) {
    this.productDao = productDao;
  }

  @Transactional
  @Override
  public Optional<Product> getProductById(long id) {
    return productDao.getProductById(id);
  }

  @Transactional(readOnly = true)
  @Override
  public List<Product> getAllProducts() {
    return productDao.getAllProducts();
  }

  @Transactional
  @Override
  public void saveOrUpdateProduct(Product name) {
    productDao.saveOrUpdateProduct(name);
  }

  @Transactional
  @Override
  public double orderTotalPrice(List<Product> productList) {
    return productList.stream().flatMapToDouble(x -> DoubleStream.of(x.getPrice())).sum();
  }

  @Transactional
  @Override
  public void deleteProduct(long id) {
    productDao.deleteProduct(id);
  }
}
