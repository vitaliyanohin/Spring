package com.boraji.tutorial.springboot.service;



import com.boraji.tutorial.springboot.model.Product;
import com.boraji.tutorial.springboot.repository.ProductJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductJpaRepository productJpaRepository;

  public ProductServiceImpl(ProductJpaRepository productJpaRepository) {
    this.productJpaRepository = productJpaRepository;
  }

  @Transactional
  @Override
  public Optional<Product> getProductById(long id) {
    return Optional.ofNullable(productJpaRepository.findOne(id));
  }

  @Transactional(readOnly = true)
  @Override
  public List<Product> getAllProducts() {
    return productJpaRepository.findAll();
  }

  @Transactional
  @Override
  public void saveOrUpdateProduct(Product name) {
    productJpaRepository.save(name);
  }

  @Override
  public double orderTotalPrice(List<Product> productList) {
    return productList.stream().flatMapToDouble(x -> DoubleStream.of(x.getPrice())).sum();
  }

  @Transactional
  @Override
  public void deleteProduct(long id) {
    productJpaRepository.delete(id);
  }
}
