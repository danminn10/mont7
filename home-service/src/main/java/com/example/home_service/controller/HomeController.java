package com.example.home_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.home_service.entity.Product;
import com.example.home_service.service.ProductService;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;
    
    public HomeController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello World");
        return "index"; // Trả về file home.html trong thư mục templates
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("message", "day la trang dang nhap");
        return "login";
    }
    
    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productService.getProductsFromAdminService();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/tour/{id}")
    public String showTourDetail(@PathVariable Long id, Model model) {
        Product tour = productService.getProductById(id);
        if (tour == null) {
            return "redirect:/"; // hoặc trang lỗi
        }
        model.addAttribute("tour", tour);
        return "tour-detail";
    }
     // Xử lý lỗi 404
    @GetMapping("/error")
    public String handleError() {
        return "error"; // Tạo file error.html trong templates/
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about"; // Tên file about.html trong /templates
    }
    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact"; // Tên file about.html trong /templates
    }

    @GetMapping("/privacy")
    public String privacy(Model model) {
        return "privacy";
    }

}