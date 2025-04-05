package com.example.admin_service.dao;


import java.util.List;

import com.example.admin_service.entity.Product;

public interface ProductDAO {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product); // Changed parameter name to 'product'
    void deleteById(Long id);
    long count();

    // Thêm hàm tìm kiếm
    List<Product> searchByKeyword(String keyword);
}
