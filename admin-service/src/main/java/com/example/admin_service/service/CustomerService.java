package com.example.admin_service.service;

import java.util.List;

import com.example.admin_service.entity.Customer;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);
    long count();

    // Thêm hàm phục vụ tìm kiếm
    List<Customer> searchByKeyword(String keyword);
}
