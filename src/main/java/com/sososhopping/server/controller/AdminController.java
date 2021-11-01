package com.sososhopping.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AdminController {

    @GetMapping("/admin")
    private String main() {
        return "admin/main";
    }

    @GetMapping("/admin/login")
    private String loginForm() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    private String login() {
        return "/admin/login";
    }

    @GetMapping("/admin/store-register")
    private String storeRegister() {
        return "/admin/store-register";
    }

    @GetMapping("/admin/user-report")
    private String userReport() {
        return "/admin/user-report";
    }

    @GetMapping("/admin/store-report")
    private String storeReport() {
        return "/admin/store-report";
    }

    @PostMapping("/admin/logout")
    private String logout() {
        return "/admin/login";
    }
}
