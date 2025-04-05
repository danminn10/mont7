package com.example.admin_service.dao;

import com.example.admin_service.entity.User;
import java.util.List;

public interface UserDAO {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
    User findByUsername(String username);
    long count();
    boolean existsByUsername(String username);
    void addAuthorityToUser(Long userId, Long authorityId);
    void clearAuthoritiesForUser(Long userId);


}