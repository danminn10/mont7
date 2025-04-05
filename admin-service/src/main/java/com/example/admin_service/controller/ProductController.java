package com.example.admin_service.controller;

import com.example.admin_service.entity.Product;
import com.example.admin_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list-product")
    public String listProducts(@RequestParam(name="keyword", required=false) String keyword, Model model) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByKeyword(keyword);
        } else {
            products = productService.findAll();
        }
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "admin/list-product";
    }

    @GetMapping("/product-form-insert")
    public String formInsertProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "admin/product-form-insert"; // Assuming your insert form template is in the admin folder
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/product-form-insert"; // Trả về form nếu có lỗi
        }
        productService.save(product);
        return "redirect:/products/list-product";
    }

    @GetMapping("/product-form-update")
    public String formUpdateProduct(@RequestParam("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/product-form-update"; // Assuming your update form template is in the admin folder
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products/list-product";
    }
}