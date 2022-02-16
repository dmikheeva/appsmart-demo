package com.appsmart.demo.service;

import com.appsmart.demo.exception.NoCustomerFoundException;
import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.dto.CustomerDto;
import com.appsmart.demo.repository.CustomerRepository;
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
public class CustomerService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-dd-yyyy HH:mm:ss");

    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerDto> getAllCustomers(Pageable pageable) {
        return customerRepository
                .findAll(pageable)
                .getContent()
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CustomerDto addCustomer(String title) {
        ZonedDateTime now = ZonedDateTime.now();
        Customer customer = Customer.builder()
                .title(title)
                .createdAt(now)
                .build();
        return convertToDto(customerRepository.save(customer));
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public CustomerDto getCustomer(Long customerId) {
        return convertToDto(customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new NoCustomerFoundException(customerId)));
    }

    public CustomerDto updateCustomer(Long customerId, String title) {
        Customer customer = customerRepository
                .findById(customerId)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new NoCustomerFoundException(customerId));
        customer.setTitle(title);
        customer.setModifiedAt(ZonedDateTime.now());
        return convertToDto(customerRepository.save(customer));
    }

    private CustomerDto convertToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .title(customer.getTitle())
                .createdAt(customer.getCreatedAt().format(formatter))
                .modifiedAt(customer.getModifiedAt() != null ? customer.getModifiedAt().format(formatter) : "")
                //todo products
                .build();
    }

}
