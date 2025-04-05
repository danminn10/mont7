package com.example.admin_service.controller;

import com.example.admin_service.entity.Test;
import com.example.admin_service.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TestController {

    private TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/test/list")
    public String showTestList(Model model) {
        List<Test> testList = testService.findAll();
        model.addAttribute("title", "Danh sách Test");
        model.addAttribute("tests", testList);
        return "test/list";
    }
}
