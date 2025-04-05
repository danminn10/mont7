package com.example.admin_service.controller;

import com.example.admin_service.entity.Product;
import com.example.admin_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prapi")
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list-product")
    public List<Product> listProductsForHomeService() {
        return productService.findAll();
    }
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    // Thêm endpoint tìm kiếm
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam("keyword") String keyword) {
        return productService.searchByKeyword(keyword);
    }

}