package com.boraji.tutorial.springboot.repository;


import com.boraji.tutorial.springboot.model.Product;
import com.boraji.tutorial.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductJpaRepository extends CrudRepository<Product, Long>, JpaRepository<Product, Long> {

  Optional<User> findByName(String name);

}