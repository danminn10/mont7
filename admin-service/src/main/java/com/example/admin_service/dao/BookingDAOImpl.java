package com.example.admin_service.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.admin_service.entity.Booking;

import java.util.List;

@Repository
public class BookingDAOImpl implements BookingDAO {

    private final EntityManager entityManager;

    @Autowired
    public BookingDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Booking> findAll() {
        TypedQuery<Booking> query = entityManager.createQuery("FROM Booking", Booking.class);
        return query.getResultList();
    }

    @Override
    public Booking findById(Long id) {
        return entityManager.find(Booking.class, id);
    }

    @Override
    @Transactional
    public Booking save(Booking booking) {
       return entityManager.merge(booking);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Booking booking = entityManager.find(Booking.class, id);
        if (booking != null) {
            entityManager.remove(booking);
        }
    }

    @Override
    public List<Booking> findByTourId(Long tourId) {
        TypedQuery<Booking> query = entityManager.createQuery(
            "SELECT b FROM Booking b WHERE b.tour.id = :tourId", Booking.class);
        query.setParameter("tourId", tourId);
        return query.getResultList();
    }

    @Override
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(b) FROM Booking b", Long.class);
        return query.getSingleResult();
    }
}