package com.example.admin_service.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Tên sản phẩm không được để trống.")
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @NotNull(message = "Giá sản phẩm không được để trống.")
    @Min(value = 0, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0.")
    @Column(name = "price", nullable = false)
    private Double price;
    
    private String description;

    @NotNull(message = "Thời lượng tour không được để trống.")
    @Min(value = 1, message = "Thời lượng tour ít nhất 1 ngày")
    private Integer duration; // Số ngày tour

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt = LocalDate.now(); // Ngày tạo
    
    @Column(name = "updated_at")
    private LocalDate updatedAt; 

    private String imageUrl;
}
