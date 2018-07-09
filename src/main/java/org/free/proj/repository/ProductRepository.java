package org.free.proj.repository;

import org.free.proj.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ProductRepository extends JpaRepository<Product, Long> {

    ArrayList<Product> findAll();
    Product findById(long id);
    Product findByName(String name);
}
