package com.visionary.crofting.repository;

import com.visionary.crofting.entity.Client;
import com.visionary.crofting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Product findProductByReference(String reference);
}
