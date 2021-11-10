package com.sososhopping.server.controller.admin;

import com.sososhopping.server.common.dto.auth.request.AdminAuthRequestDto;
import com.sososhopping.server.entity.store.Store;
import com.sososhopping.server.entity.store.StoreStatus;
import com.sososhopping.server.repository.store.StoreRepository;
import com.sososhopping.server.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final StoreRepository storeRepository;

    @GetMapping("/admin")
    private String main() {
        return "admin/main";
    }

    @GetMapping("/admin/login")
    private String loginForm(@ModelAttribute("adminVO") AdminAuthRequestDto adminVO) {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    private String login() {
        return "/admin/login";
    }

    @GetMapping("/admin/store-register")
    private String storeRegisterPage(Model model) {
        List<Store> stores = storeRepository.findByStoreStatus(StoreStatus.PENDING);
        model.addAttribute("stores", stores);
        return "/admin/store-register";
    }

    @PostMapping("/admin/store-register")
    private String storeRegister(
            @RequestParam Long storeId,
            @RequestParam String action,
            RedirectAttributes redirectAttributes
    ) {
        adminService.updateStoreStatus(storeId, action);
        return "redirect:/admin/store-register";
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
