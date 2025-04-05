package com.example.admin_service.dao;

import com.example.admin_service.entity.Authority;
import com.example.admin_service.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager em;

    @Autowired
    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return em.merge(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("from User u where u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
    }

    @Override
    public boolean existsByUsername(String username) {
        return em.createQuery(
            "SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username", 
            Boolean.class)
            .setParameter("username", username)
            .getSingleResult();
    }

    @Override
    @Transactional
    public void addAuthorityToUser(Long userId, Long authorityId) {
        User user = em.find(User.class, userId);
        Authority authority = em.find(Authority.class, authorityId);

        if (user != null && authority != null) {
            user.getAuthorities().add(authority);
            em.merge(user); // Cập nhật lại user với quyền mới
        }
    }


    @Override
    @Transactional
    public void clearAuthoritiesForUser(Long userId) {
        User user = em.find(User.class, userId);
        if (user != null) {
            user.getAuthorities().clear();
            em.merge(user); // Cập nhật lại user sau khi xóa quyền
        }
    }






}