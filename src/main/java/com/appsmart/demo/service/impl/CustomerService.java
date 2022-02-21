package com.appsmart.demo.service.impl;

import com.appsmart.demo.exception.NoCustomerFoundException;
import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.converter.CustomerToDtoConverter;
import com.appsmart.demo.model.dto.CustomerDto;
import com.appsmart.demo.repository.CustomerRepository;
import com.appsmart.demo.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService implements ICustomerService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-dd-yyyy HH:mm:ss");

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerToDtoConverter converter;

    public List<CustomerDto> getAllCustomers(Pageable pageable) {
        return customerRepository
                .findAll(pageable)
                .getContent()
                .stream().map(t -> converter.convert(t))
                .collect(Collectors.toList());
    }

    public CustomerDto addCustomer(String title) {
        ZonedDateTime now = ZonedDateTime.now();
        Customer customer = Customer.builder()
                .title(title)
                .createdAt(now)
                .build();
        return converter.convert(customerRepository.save(customer));
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new NoCustomerFoundException(customerId));
        customer.setIsDeleted(true);
        customerRepository.save(customer);
    }

    public CustomerDto getCustomer(Long customerId) {
        return converter.convert(customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new NoCustomerFoundException(customerId)));
    }

    public CustomerDto updateCustomer(Long customerId, String title) {
        Customer customer = customerRepository
                .findByIdAndIsDeleted(customerId, false)
                .orElseThrow(() -> new NoCustomerFoundException(customerId));
        customer.setTitle(title);
        customer.setModifiedAt(ZonedDateTime.now());
        return converter.convert(customerRepository.save(customer));
    }

}
