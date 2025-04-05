package com.example.admin_service.controller;

import com.example.admin_service.entity.Authority;
import com.example.admin_service.entity.User;
import com.example.admin_service.service.AuthorityService;
import com.example.admin_service.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/system/users")
public class UserController {

    private UserService userService;
    private  AuthorityService authorityService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/system/users/list";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/system/users/add";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           @RequestParam(value = "authorities", required = false) Set<Long> authorityIds,
                           Model model) {
        if (result.hasErrors()){
            model.addAttribute("user", user);
            return "admin/system/users/add";
        }
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Nếu không chọn quyền, mặc định là ROLE_USER
        if (authorityIds == null || authorityIds.isEmpty()) {
            Authority defaultAuthority = authorityService.findByName("ROLE_USER");
            Set<Authority> defaultAuthorities = new HashSet<>();
            defaultAuthorities.add(defaultAuthority);
            user.setAuthorities(defaultAuthorities);
        } else {
            Set<Authority> authorities = authorityService.findByIds(authorityIds);
            user.setAuthorities(authorities);
        }
        userService.save(user); // Cần xử lý password encoding ở service
        return "redirect:/system/users/list";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
                // Lấy tất cả quyền (Authorities) từ cơ sở dữ liệu
        List<Authority> authorities = authorityService.findAll();

        // Thêm người dùng và danh sách quyền vào model
        model.addAttribute("user", user);
        model.addAttribute("authorities", authorities);
        return "admin/system/users/edit";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             @RequestParam(value = "authorities", required = false) Set<Long> authorityIds,
                             Model model) {
        model.addAttribute("authorities", authorityService.findAll());
        if (result.hasErrors()) {
            model.addAttribute("authorities", authorityService.findAll());
            return "admin/system/users/edit";
        }

        User existingUser = userService.findById(user.getId());

        existingUser.setUsername(user.getUsername());
        existingUser.setEnabled(user.isEnabled());

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            existingUser.setPassword(existingUser.getPassword());
        } else {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // ✅ Thay vì addAuthorityToUser → set lại authorities và để Hibernate tự insert
        if (authorityIds != null && !authorityIds.isEmpty()) {
            Set<Authority> authorities = authorityService.findByIds(authorityIds);
            existingUser.setAuthorities(authorities);
            System.out.println("Authorities selected: " + authorities);
        } else {
            existingUser.setAuthorities(new HashSet<>());
            System.out.println("No authorities selected.");
        }

        userService.save(existingUser);

        return "redirect:/system/users/list";
    }



    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/system/users/list";
    }
}