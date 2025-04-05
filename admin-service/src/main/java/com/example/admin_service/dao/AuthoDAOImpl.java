package com.example.admin_service.dao;

import com.example.admin_service.entity.Authority;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Repository
public class AuthoDAOImpl implements AuthorityDAO {

    private EntityManager em;

    @Autowired
    public AuthoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Authority> findAll() {
        TypedQuery<Authority> query = em.createQuery("from Authority", Authority.class);
        return query.getResultList();
    }

    @Override
    public Authority findById(Long id) {
        return em.find(Authority.class, id);
    }

    @Override
    @Transactional
    public Authority save(Authority authority) {
        if (authority.getId() == null) {
            em.persist(authority);
            return authority;
        } else {
            return em.merge(authority);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Authority authority = em.find(Authority.class, id);
        if (authority != null) {
            em.remove(authority);
        }
    }

    @Override
    public Authority findByName(String name) {
        TypedQuery<Authority> query = em.createQuery("from Authority a where a.name = :name", Authority.class);
        query.setParameter("name", name);
        List<Authority> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(u) FROM Authority u", Long.class).getSingleResult();
    }

    @Override
    public Set<Authority> findByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }

        // Dùng List thay vì Set cho setParameter (tránh lỗi không map được)
        List<Long> idList = new ArrayList<>(ids);

        TypedQuery<Authority> query = em.createQuery("SELECT a FROM Authority a WHERE a.id IN :ids", Authority.class);
        query.setParameter("ids", idList);

        return new HashSet<>(query.getResultList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority that = (Authority) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}