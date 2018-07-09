package org.free.proj.service;

import org.free.proj.repository.ProductRepository;
import org.free.proj.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ArrayList<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findById(long id)  {
        return this.productRepository.findById(id);
    }

    public Product findByName(String name) {
        return this.productRepository.findByName(name);
    }

    public void save(Product product) {
        this.productRepository.save(product);
    }

    public void delete(Product product) { this.productRepository.delete(product);}
}
