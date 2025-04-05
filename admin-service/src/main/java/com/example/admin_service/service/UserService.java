package com.example.admin_service.service;

import com.example.admin_service.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    User save(User user);
    void deleteById(Long id);
    long count();
    boolean existsByUsername(String username);
    void addAuthorityToUser(Long userId, Long authorityId);
    void clearAuthoritiesForUser(Long userId);


}