package com.sososhopping.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminController {

    @GetMapping("/admin")
    private String main() {
        return "admin/main";
    }

    @GetMapping("/admin/login")
    private String login() {
        return "admin/login";
    }
}
