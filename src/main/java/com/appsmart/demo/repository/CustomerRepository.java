package com.appsmart.demo.repository;

import com.appsmart.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByIdAndIsDeleted(Long id, boolean isDeleted);

    Page<Customer> findAllByIsDeleted(boolean isDeleted, Pageable pageable);
}
