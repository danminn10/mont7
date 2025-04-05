package com.example.admin_service.service;

import com.example.admin_service.dao.BookingDAO;
import com.example.admin_service.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO;

    @Autowired
    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public List<Booking> findAll() {
        return bookingDAO.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingDAO.findById(id);
    }

    @Override
    @Transactional
    public Booking save(Booking booking) {
        return bookingDAO.save(booking);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookingDAO.deleteById(id);
    }

    @Override
    public List<Booking> findByTourId(Long tourId) {
        return bookingDAO.findByTourId(tourId);
    }

    @Override
    public long count() {
        return bookingDAO.count();
    }
}