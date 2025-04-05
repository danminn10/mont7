package com.example.home_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import com.example.home_service.entity.Booking;

@Service
public class BookingService {
    private final RestTemplate restTemplate;
    
    @Value("${admin-service.base-url}")
    private String adminServiceBaseUrl;

    public BookingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
        Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
    }

    public Booking createBooking(Booking booking) {
        try {
            String url = adminServiceBaseUrl + "/bookingapi/create";
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
    
            HttpEntity<Booking> requestEntity = new HttpEntity<>(booking, headers);
    
            ResponseEntity<Booking> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Booking.class
            );
    
            System.out.println("Status: " + response.getStatusCode());
            return response.getBody(); // <-- CHỖ NÀY CÓ THỂ ĐANG TRẢ VỀ NULL
    
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi hệ thống khi tạo booking");
        }
    }
    

    public Booking getBookingById(Long id) {
        try {
            String url = adminServiceBaseUrl + "/bookingapi/" + id;
            System.out.println("Calling booking API to get booking by ID: " + url);
            
            // Set headers
            System.out.println("Calling admin service at: " + url);
            
            // Thử lấy phản hồi dưới dạng String trước để debug
            ResponseEntity<String> responseRaw = restTemplate.getForEntity(url, String.class);
            System.out.println("Status code: " + responseRaw.getStatusCode());
            System.out.println("Content type: " + responseRaw.getHeaders().getContentType());
            System.out.println("Response body: " + responseRaw.getBody());
            
            // Gửi request GET
            ResponseEntity<Booking> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Booking.class
            );
            
            System.out.println("Response status: " + response.getStatusCode());
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new RuntimeException("Không tìm thấy booking với ID: " + id);
            }
            
        } catch (RestClientResponseException e) {
            throw new RuntimeException("Lỗi từ admin service: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new RuntimeException("Lỗi hệ thống khi lấy thông tin booking");
        }
    }
}