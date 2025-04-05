package com.example.admin_service.controller;

import com.example.admin_service.service.BookingService;
import com.example.admin_service.service.CustomerService;
import com.example.admin_service.service.ProductService;
import com.example.admin_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final BookingService bookingService;

    @Autowired
    public AdminController(UserService userService,
                           CustomerService customerService,
                           ProductService productService,
                           BookingService bookingService) {
        this.userService = userService;
        this.customerService = customerService;
        this.productService = productService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("title", "Admin Dashboard");
        model.addAttribute("pageTitle", "Dashboard");

        long userCount = userService.count();
        long customerCount = customerService.count();
        long productCount = productService.count();
        long orderCount = bookingService.count();

        double monthlySales = bookingService.findAll().stream()
                .filter(b -> b.getStatus() != null && b.getStatus().equalsIgnoreCase("CONFIRMED"))
                .mapToDouble(b -> b.getTour().getPrice() * b.getNumberOfPeople())
                .sum();

        model.addAttribute("userCount", userCount);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("monthlySales", monthlySales);

        model.addAttribute("revenueLabels", List.of("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6"));
        model.addAttribute("revenueData", List.of(12000000, 19000000, 30000000, 25000000, 32000000, 40000000));

        int adminCount = (int) userService.findAll().stream()
                .filter(u -> u.getAuthorities().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN")))
                .count();
        int customerUserCount = (int) userService.findAll().stream()
                .filter(u -> u.getAuthorities().stream().anyMatch(a -> a.getName().equals("ROLE_USER")))
                .count();

        model.addAttribute("userTypes", List.of(adminCount, customerUserCount));
        model.addAttribute("managerStats", List.of((int) userCount, (int) customerCount, (int) productCount));

        return "admin/dashboard";
    }
}
