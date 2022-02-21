package com.appsmart.demo.repository;

import com.appsmart.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCustomerId(Long customerId, Pageable pageable);

    Optional<Product> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
