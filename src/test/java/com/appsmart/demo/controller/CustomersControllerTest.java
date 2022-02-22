package com.appsmart.demo.controller;

import com.appsmart.demo.config.DefaultTestConfig;
import com.appsmart.demo.model.Customer;
import com.appsmart.demo.repository.CustomerRepository;
import com.appsmart.demo.service.impl.ProductService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomersController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(DefaultTestConfig.class)
public class CustomersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    ProductService productService;

    @Test
    public void givenCustomer_whenGet_thenStatusOkAndCustomerReturned() throws Exception {
        Customer testCustomer = Customer.builder()
                .id(1L)
                .title("Test")
                .build();
        Mockito.when(customerRepository.findByIdAndIsDeleted(1L, false))
                .thenReturn(Optional.of(testCustomer));
        this.mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.title").value("Test"));
    }

    @Test
    public void whenGetNonExistentCustomer_thenStatusNotFound() throws Exception {
        Mockito.when(customerRepository.findByIdAndIsDeleted(1L, false))
                .thenReturn(Optional.empty());
        this.mockMvc.perform(get("/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Disabled
    public void givenCustomer_whenUpdate_thenStatusOkAndCustomerUpdated() throws Exception {
        Customer testCustomer = Customer.builder()
                .id(1L)
                .title("Test")
                .isDeleted(false)
                .build();
        Mockito.when(customerRepository.findByIdAndIsDeleted(1L, false))
                .thenReturn(Optional.of(testCustomer));
        Mockito.when(customerRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArguments()[0]);
        this.mockMvc.perform(put("/customers/1")
                .param("title", "Updated test"))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(1L),
                        jsonPath("$.title").value("Updated test"),
                        jsonPath("$.modifiedAt").isNotEmpty()
                );
    }
}