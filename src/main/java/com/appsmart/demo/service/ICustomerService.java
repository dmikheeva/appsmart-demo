package com.appsmart.demo.service;

import com.appsmart.demo.model.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    Page<CustomerDto> getAllCustomers(Pageable pageable);

    CustomerDto addCustomer(String title);

    void deleteCustomer(Long customerId);

    CustomerDto getCustomer(Long customerId);

    CustomerDto updateCustomer(Long customerId, String title);
}
