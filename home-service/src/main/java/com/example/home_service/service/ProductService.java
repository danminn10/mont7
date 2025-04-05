package com.example.home_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.home_service.entity.Product;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;

    @Value("${admin-service.base-url}")
    private String adminServiceBaseUrl;

    public ProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(
        Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
    }

    public List<Product> getProductsFromAdminService() {
        try {
            String fullUrl = adminServiceBaseUrl + "/prapi/list-product";
            System.out.println("Calling admin service at: " + fullUrl);
            
            // Thử lấy phản hồi dưới dạng String trước để debug
            ResponseEntity<String> responseRaw = restTemplate.getForEntity(fullUrl, String.class);
            System.out.println("Status code: " + responseRaw.getStatusCode());
            System.out.println("Content type: " + responseRaw.getHeaders().getContentType());
            System.out.println("Response body (first 100 chars): " + 
                (responseRaw.getBody() != null ? responseRaw.getBody().substring(0, Math.min(100, responseRaw.getBody().length())) : "null"));
            
            // Sau đó thử tiếp với list product
            ResponseEntity<List<Product>> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Product>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error calling admin service: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Trả về danh sách rỗng để tránh lỗi
        }
    }
    public Product getProductById(Long id) {
        try {
            String fullUrl = adminServiceBaseUrl + "/prapi/product/" + id;
            System.out.println("Calling admin service at: " + fullUrl);
            
            // Thử lấy phản hồi dưới dạng String trước để debug
            ResponseEntity<String> responseRaw = restTemplate.getForEntity(fullUrl, String.class);
            System.out.println("Status code: " + responseRaw.getStatusCode());
            System.out.println("Content type: " + responseRaw.getHeaders().getContentType());
            System.out.println("Response body: " + responseRaw.getBody());
            
            // Sau đó thử lấy đối tượng Product
            ResponseEntity<Product> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Product>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error fetching product by ID: " + e.getMessage());
            e.printStackTrace();
            return null; // Hoặc có thể trả về Product rỗng tùy theo yêu cầu
        }
    }
    
}
