package com.appsmart.demo.controller;

import com.appsmart.demo.model.Customer;
import com.appsmart.demo.model.Product;
import com.appsmart.demo.model.dto.CustomerDto;
import com.appsmart.demo.model.dto.ProductDto;
import com.appsmart.demo.service.CustomerService;
import com.appsmart.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomersController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;


    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return customerService.getAllCustomers(PageRequest.of(page, size));
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto addCustomer(@RequestParam String title) {
        return customerService.addCustomer(title);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @GetMapping(value = "/customers/{customerId}")
    public CustomerDto getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDto updateCustomer(@PathVariable Long customerId,
                                   @RequestParam String title
    ) {
        return customerService.updateCustomer(customerId, title);
    }

    @GetMapping(value = "/customers/{customerId}/products")
    public List<ProductDto> getProducts(@PathVariable Long customerId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return productService.getProducts(customerId, PageRequest.of(page, size));
    }

    @PostMapping("/customers/{customerId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@PathVariable Long customerId,
                              @RequestParam String title,
                              @RequestParam(required = false) String description,
                              @RequestParam BigDecimal price) {
        return productService.addProduct(customerId, title, description, price);
    }
}
