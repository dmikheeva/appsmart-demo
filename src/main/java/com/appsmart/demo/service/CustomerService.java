package com.appsmart.demo.service;

import com.appsmart.demo.exception.NoCustomerFoundException;
import com.appsmart.demo.model.Customer;
import com.appsmart.demo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository
                .findAll(pageable)
                .getContent();
    }

    public Customer addCustomer(String title) {
        ZonedDateTime now = ZonedDateTime.now();
        Customer customer = Customer.builder()
                .title(title)
                .createdAt(now)
                .build();
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public Customer getCustomer(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new NoCustomerFoundException(customerId));
    }

    public Customer updateCustomer(Long customerId, String title) {
        Customer customer = customerRepository
                .findById(customerId)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new NoCustomerFoundException(customerId));
        customer.setTitle(title);
        customer.setModifiedAt(ZonedDateTime.now());
        return customerRepository.save(customer);
    }


}
