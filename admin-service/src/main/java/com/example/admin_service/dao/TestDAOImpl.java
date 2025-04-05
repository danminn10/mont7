package com.example.admin_service.dao;

import com.example.admin_service.entity.Product;
import com.example.admin_service.entity.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDAOImpl implements TestDao {

    private EntityManager em;

    @Autowired
    public TestDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public List<Test> findAll() {
        TypedQuery<Test > query = em.createQuery("from Test", Test.class);
        return query.getResultList();
    }

    @Override
    public Test findById(Long id) {
        return null;
    }

    @Override
    public Test save(Test test) {
        return em.merge(test);
    }

    @Override
    public void deleteById(Long id) {

    }
}
