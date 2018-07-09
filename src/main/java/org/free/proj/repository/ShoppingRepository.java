package org.free.proj.repository;

import org.free.proj.entity.User;
import org.free.proj.entity.Product;
import org.free.proj.entity.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ShoppingRepository extends JpaRepository<Shopping, Long> {

    ArrayList<Shopping> findAll();
    ArrayList<Shopping> findAllByUser(User id);
    Shopping findByUserAndProduct(User user, Product product);
}
