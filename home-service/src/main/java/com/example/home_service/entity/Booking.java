package com.example.home_service.entity;

import java.time.LocalDate;

import org.hibernate.annotations.processing.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Booking {
    private Long id;
    
    private Product tour;
    
    @NotBlank(message = "Tên khách hàng không được để trống")
    @Size(max = 100, message = "Tên khách hàng không quá 100 ký tự")
    private String customerName;
    
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không quá 100 ký tự")
    private String customerEmail;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 10, max = 11, message = "Số điện thoại phải từ 10-11 số")
    private String customerPhone;
    
    @NotNull(message = "Số người không được để trống")
    @Min(value = 1, message = "Số người phải lớn hơn hoặc bằng 1")
    @Max(value = 50, message = "Số người tối đa là 50")
    private Integer numberOfPeople;
    
    @NotNull(message = "Ngày đặt không được để trống")
    @FutureOrPresent(message = "Ngày đặt phải là hiện tại hoặc tương lai")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    
    @Size(max = 500, message = "Yêu cầu đặc biệt không quá 500 ký tự")
    private String specialRequest;
    
    private String status; // PENDING, CONFIRMED, CANCELLED
}
