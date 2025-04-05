package com.example.admin_service.service;

import com.example.admin_service.entity.Product;
import com.example.admin_service.entity.Test;

import java.util.List;

public interface TestService {
    List<Test> findAll();
//    Product findById(Long id);
    Test save(Test test); // Changed parameter name to 'product'
//    void deleteById(Long id);
}
