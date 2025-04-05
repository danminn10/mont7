package com.example.admin_service.service;

import com.example.admin_service.dao.AuthorityDAO;
import com.example.admin_service.entity.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityDAO authorityDAO;

    @Autowired
    public AuthorityServiceImpl(AuthorityDAO authorityDAO) {
        this.authorityDAO = authorityDAO;
    }

    @Override
    public List<Authority> findAll() {
        return authorityDAO.findAll();
    }



    @Override
    public Authority findByName(String name) {
        return authorityDAO.findByName(name);
    }

    @Transactional
    @Override
    public Authority save(Authority authority) {
        return authorityDAO.save(authority);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        authorityDAO.deleteById(id);
    }

    @Override
    public long count() {
        return authorityDAO.count();
    }

    @Override
    public Set<Authority> findByIds(Set<Long> authorityIds) {
        return authorityDAO.findByIds(authorityIds).stream().collect(Collectors.toSet());
    }

    @Override
    public Authority findById(long id) {
        return authorityDAO.findById(id);
    }
}