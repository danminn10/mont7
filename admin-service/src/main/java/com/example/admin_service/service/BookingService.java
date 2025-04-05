package com.example.admin_service.service;

import com.example.admin_service.entity.Booking;
import java.util.List;

public interface BookingService {
    List<Booking> findAll();
    Booking findById(Long id);
    Booking save(Booking booking);
    void deleteById(Long id);
    List<Booking> findByTourId(Long tourId);
    long count();
}