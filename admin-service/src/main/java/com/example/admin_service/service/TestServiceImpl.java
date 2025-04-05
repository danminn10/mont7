package com.example.admin_service.service;

import com.example.admin_service.dao.TestDao;
import com.example.admin_service.entity.Product;
import com.example.admin_service.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private TestDao testDao;
    @Autowired
    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }

    @Override
    public List<Test> findAll() {
        return testDao.findAll();
    }


    @Override
    public Test save(Test test) {
        return testDao.save(test);
    }
}
