package com.boraji.tutorial.spring.dao.impl;


import com.boraji.tutorial.spring.dao.ProductDao;
import model.Product;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

  private final SessionFactory sessionFactory;

  public ProductDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<Product> getProductById(long id) {
    return Optional.ofNullable(sessionFactory.getCurrentSession()
            .get(Product.class, id));
  }

  @Override
  public List<Product> getAllProducts() {
    TypedQuery<Product> query = sessionFactory.getCurrentSession().createQuery("FROM Product");
    return query.getResultList();
  }

  @Override
  public void saveOrUpdateProduct(Product product) {
    sessionFactory.getCurrentSession().saveOrUpdate(product);
  }

  @Override
  public void deleteProduct(long id) {
    sessionFactory.getCurrentSession().delete(getProductById(id).get());
  }
}
