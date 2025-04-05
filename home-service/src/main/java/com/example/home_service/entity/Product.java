package com.example.home_service.entity;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Integer duration; // Số ngày tour
    private LocalDate createdAt = LocalDate.now(); // Ngày tạo
    private LocalDate updatedAt; 
    private String imageUrl;
}   
