package com.example.admin_service.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.admin_service.entity.Product;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    private EntityManager entityManager; // Changed variable name to 'entityManager'

    @Autowired
    public ProductDAOImpl(EntityManager entityManager) { // Changed parameter name to 'entityManager'
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> query = entityManager.createQuery("from Product", Product.class); // Changed variable name to 'query'
        return query.getResultList();
    }

    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    @Transactional
    public Product save(Product product) { // Changed parameter name to 'product'
        return entityManager.merge(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = entityManager.find(Product.class, id); // Changed variable name to 'product'
        if (product != null) {
            entityManager.remove(product);
        }
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(p) FROM Product p", Long.class)
                .getSingleResult();
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        String jpql = "SELECT p FROM Product p " +
        "WHERE p.name LIKE :kw " +
        "   OR p.description LIKE :kw";

        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("kw", "%" + keyword + "%");
        return query.getResultList();
    }
}