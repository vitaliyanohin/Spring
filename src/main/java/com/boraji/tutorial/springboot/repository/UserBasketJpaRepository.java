package com.boraji.tutorial.springboot.repository;


import com.boraji.tutorial.springboot.model.Basket;
import com.boraji.tutorial.springboot.model.Product;
import com.boraji.tutorial.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBasketJpaRepository extends CrudRepository<Basket, Long>, JpaRepository<Basket, Long> {

  @Query("SELECT b FROM Basket b WHERE b.user = :id AND b.available= 'true'")
  public Optional<Basket> getBasket(@Param("id") User id);
 // Optional<Basket> findBasketWhereUser

}