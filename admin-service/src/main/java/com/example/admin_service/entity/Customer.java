package com.example.admin_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers") // Sử dụng số nhiều để tránh xung đột với từ khóa SQL
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên công ty không được để trống.")
    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;

    @NotBlank(message = "Địa chỉ không được để trống.")
    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @NotBlank(message = "Thành phố không được để trống.")
    // @Pattern(regexp = "[a-zA-Z\\s]+", message = "Thành phố phải chỉ chứa chữ cái và khoảng trắng.")
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @NotBlank(message = "Quốc gia không được để trống.")
    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Size(min = 1, max = 8, message = "Mã bưu điện phải có từ 1 đến 8 ký tự.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$", message = "Mã bưu điện phải chứa cả chữ cái và số, không có ký tự đặc biệt.")
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @NotNull(message = "Năm sinh không được để trống.")
    @Column(name = "year_of_birth", nullable = false)
    private Integer namSinh;

    @Column(name = "region", length = 100)
    private String region;
}
