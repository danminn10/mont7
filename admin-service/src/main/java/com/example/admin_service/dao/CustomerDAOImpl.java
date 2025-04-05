package com.example.admin_service.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.admin_service.entity.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
    private EntityManager em;

    @Autowired
    public CustomerDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Customer> findAll() {
        TypedQuery<Customer> query = em.createQuery("from Customer", Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        return em.merge(customer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(u) FROM Customer u", Long.class).getSingleResult();
    }

    // Triển khai hàm tìm kiếm
    @Override
    public List<Customer> searchByKeyword(String keyword) {
        // Tuỳ vào yêu cầu, bạn có thể thêm các trường khác để tìm kiếm
        // ví dụ: tên công ty, địa chỉ, thành phố...
        // Ở đây minh hoạ so khớp trên một vài cột
        String jpql = "SELECT c FROM Customer c " +
                      "WHERE c.companyName LIKE :kw " +
                      "   OR c.address LIKE :kw " +
                      "   OR c.city LIKE :kw " +
                      "   OR c.country LIKE :kw " +
                      "   OR c.region LIKE :kw " +
                      "   OR c.postalCode LIKE :kw";

        TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
        query.setParameter("kw", "%" + keyword + "%");
        return query.getResultList();
    }
}
