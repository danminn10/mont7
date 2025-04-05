package com.example.admin_service.controller;

import com.example.admin_service.entity.Authority;
import com.example.admin_service.service.AuthorityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/authorities")
public class AuthorityController {

    private AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("/list")
    public String listAuthorities(Model model) {
        List<Authority> authorities = authorityService.findAll();
        model.addAttribute("authorities", authorities);
        return "admin/system/authorities/list";
    }

    @GetMapping("/add")
    public String addAuthorityForm(Model model) {
        model.addAttribute("authority", new Authority());
        return "admin/system/authorities/add";
    }

    @PostMapping("/save")
    public String saveAuthority(@Valid @ModelAttribute("authority") Authority authority, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/system/authorities/add";
        }
        authorityService.save(authority);
        return "redirect:/system/authorities/list";
    }

    @GetMapping("/edit/{id}")
    public String editAuthorityForm(@PathVariable Long id, Model model) {
        Authority authority = authorityService.findById(id);
        model.addAttribute("authority", authority);
        return "admin/system/authorities/edit";
    }

    @PostMapping("/update")
    public String updateAuthority(@Valid @ModelAttribute("authority") Authority authority, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/system/authorities/edit";
        }
        authorityService.save(authority);
        return "redirect:/system/authorities/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthority(@PathVariable Long id) {
        authorityService.deleteById(id);
        return "redirect:/system/authorities/list";
    }
}