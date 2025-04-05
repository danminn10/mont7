package com.example.admin_service.dao;

import java.util.List;

import com.example.admin_service.entity.Customer;

public interface CustomerDAO {
    List<Customer> findAll();
    Customer findById(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);
    long count();

    // Thêm hàm mới phục vụ tìm kiếm
    List<Customer> searchByKeyword(String keyword);
}
