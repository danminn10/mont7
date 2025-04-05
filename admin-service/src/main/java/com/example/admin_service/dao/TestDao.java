package com.example.admin_service.dao;

import com.example.admin_service.entity.Authority;
import com.example.admin_service.entity.Test;

import java.util.List;

public interface TestDao {
    List<Test> findAll();
    Test findById(Long id);
    Test save(Test test);
    void deleteById(Long id);
}
