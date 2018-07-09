package org.free.proj.service;

import org.free.proj.entity.User;
import org.free.proj.repository.ShoppingRepository;
import org.free.proj.entity.Product;
import org.free.proj.entity.Shopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    public ArrayList<Shopping> findByUser(User id) {
        return this.shoppingRepository.findAllByUser(id);
    }

    public Shopping findByUserAndProduct(User user, Product product) {
        return this.shoppingRepository.findByUserAndProduct(user, product);
    }

    public void delete(Shopping shopping){
        this.shoppingRepository.delete(shopping);
    }
    public void save(Shopping shopping) {
        this.shoppingRepository.save(shopping);
    }

}
