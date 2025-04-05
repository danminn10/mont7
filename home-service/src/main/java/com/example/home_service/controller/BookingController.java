package com.example.home_service.controller;

import com.example.home_service.entity.Booking;
import com.example.home_service.entity.Product;
import com.example.home_service.service.BookingService;
import com.example.home_service.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final ProductService productService;

    public BookingController(BookingService bookingService, ProductService productService) {
        this.bookingService = bookingService;
        this.productService = productService;
    }

    // Hiển thị form đặt tour
    @GetMapping("/confirm/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {
        try {
            Product tour = productService.getProductById(id);
            if (tour == null) {
                return "redirect:/products?error=Tour+không+tồn+tại";
            }
            
            Booking booking = new Booking();
            booking.setTour(tour);
            
            model.addAttribute("tour", tour);
            model.addAttribute("booking", booking);
            return "booking-form";
        } catch (Exception e) {
            return "redirect:/products?error=Lỗi+hệ+thống";
        }
    }

    // Xử lý submit form đặt tour
    @PostMapping("/submit")
    public String submitBooking(
            @Validated @ModelAttribute("booking") Booking booking,
            BindingResult bindingResult,
            Model model) {

        try {
            // Lấy lại thông tin tour từ admin service bằng ID
            Long tourId = booking.getTour() != null ? booking.getTour().getId() : null;
            if (tourId == null) {
                model.addAttribute("error", "Tour không hợp lệ.");
                return "booking-form";
            }

            Product tour = productService.getProductById(tourId);
            if (tour == null) {
                model.addAttribute("error", "Không tìm thấy thông tin tour.");
                return "booking-form";
            }

            // Gán lại vào booking
            booking.setTour(tour);
            model.addAttribute("tour", tour);

            // Nếu có lỗi validation
            if (bindingResult.hasErrors()) {
                return "booking-form";
            }

            Booking savedBooking = bookingService.createBooking(booking);
            return "redirect:/booking/confirmation/" + savedBooking.getId();

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log để debug
            model.addAttribute("error", "Lỗi hệ thống. Vui lòng thử lại sau.");
        }

        return "booking-form";
    }

    // Xem thông tin xác nhận đặt tour
    @GetMapping("/confirmation/{id}")
    public String showConfirmation(@PathVariable Long id, Model model) {
        try {
            Booking booking = bookingService.getBookingById(id);
            
            if (booking == null) {
                return "redirect:/products?error=Đơn+đặt+tour+không+tồn+tại";
            }
            model.addAttribute("booking", booking);
            return "booking-confirmation";
        } catch (Exception e) {
            return "redirect:/products?error=Lỗi+hệ+thống";
        }
    }
}