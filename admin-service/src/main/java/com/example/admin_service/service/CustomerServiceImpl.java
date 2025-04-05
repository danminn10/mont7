package com.example.admin_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin_service.dao.CustomerDAO;
import com.example.admin_service.entity.Customer;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerDAO customerDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerDAO.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerDAO.save(customer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        customerDAO.deleteById(id);
    }

    @Override
    public long count() {
        return customerDAO.count();
    }

    @Override
    public List<Customer> searchByKeyword(String keyword) {
        return customerDAO.searchByKeyword(keyword);
    }
}


