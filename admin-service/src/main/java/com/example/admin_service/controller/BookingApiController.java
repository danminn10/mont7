package com.example.admin_service.controller;

import com.example.admin_service.entity.Booking;
import com.example.admin_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookingapi")
public class BookingApiController {

    private final BookingService bookingService;

    @Autowired
    public BookingApiController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.save(booking);
    }
    
    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable Long id) {
        return bookingService.findById(id);
    }
}