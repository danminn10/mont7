package com.example.admin_service.service;

import com.example.admin_service.entity.Authority;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorityService {
    List<Authority> findAll();
    Authority findById(long id);
    Authority findByName(String name);
    Authority save(Authority authority);
    void deleteById(Long id);
    long count();
    // Lấy quyền theo danh sách ID (Set<Long>)
    Set<Authority> findByIds(Set<Long> authorityIds);
}