package com.example.admin_service.dao;

import com.example.admin_service.entity.Authority;
import java.util.List;
import java.util.Set;

public interface AuthorityDAO {
    List<Authority> findAll();
    Authority findById(Long id);
    Authority save(Authority authority);
    void deleteById(Long id);
    Authority findByName(String name);
    long count();
    boolean equals(Object o);
    int hashCode();
        // Tìm các quyền dựa trên danh sách ID
    Set<Authority> findByIds(Set<Long> ids);
}