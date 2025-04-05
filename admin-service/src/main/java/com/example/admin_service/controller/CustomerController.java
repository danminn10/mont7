package com.example.admin_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.admin_service.entity.Customer;
import com.example.admin_service.service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list-customer")
    public String list(@RequestParam(name="keyword", required=false) String keyword, Model model) {
        List<Customer> customers;
        if (keyword != null && !keyword.isEmpty()) {
            customers = customerService.searchByKeyword(keyword);
        } else {
            customers = customerService.findAll();
        }
        model.addAttribute("customers", customers);
        // Để hiển thị lại keyword trên form tìm kiếm
        model.addAttribute("keyword", keyword);
        return "admin/list-customer";
    }

    @GetMapping("/customer-form-insert")
    public String formInsert(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "admin/customer-form-insert";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/customer-form-insert";
        }
        customerService.save(customer);
        return "redirect:/customers/list-customer";
    }

    @GetMapping("/customer-form-update")
    public String formUpdate(@RequestParam("id") Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        return "admin/customer-form-update";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        customerService.deleteById(id);
        return "redirect:/customers/list-customer";
    }

}
