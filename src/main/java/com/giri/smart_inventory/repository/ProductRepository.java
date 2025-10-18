package com.giri.smart_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giri.smart_inventory.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
