package com.sososhopping.controller.admin;

import com.sososhopping.common.dto.auth.request.AdminAuthRequestDto;
import com.sososhopping.entity.report.StoreReport;
import com.sososhopping.entity.report.UserReport;
import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.StoreStatus;
import com.sososhopping.repository.report.UserReportRepository;
import com.sososhopping.repository.store.StoreReportRepository;
import com.sososhopping.repository.store.StoreRepository;
import com.sososhopping.service.admin.AdminService;
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
    private final UserReportRepository userReportRepository;
    private final StoreReportRepository storeReportRepository;

    @GetMapping("/admin/login")
    private String loginForm(@ModelAttribute("adminVO") AdminAuthRequestDto adminVO) {
        return "admin/login";
    }

    @GetMapping("/admin/main")
    private String main() {
        return "admin/main";
    }

    @GetMapping("/admin/storeRegister")
    private String storeRegisterPage(Model model) {
        List<Store> stores = storeRepository.findByStoreStatus(StoreStatus.PENDING);
        model.addAttribute("stores", stores);
        return "admin/storeRegister";
    }

    @PostMapping("/admin/storeRegister")
    private String handleStoreRegister(
            @RequestParam Long storeId,
            @RequestParam String action,
            RedirectAttributes redirectAttributes
    ) {
        adminService.updateStoreStatus(storeId, action);
        return "redirect:/admin/storeRegister";
    }

    @GetMapping("/admin/userReport")
    private String userReportPage(Model model) {
        List<UserReport> reports = userReportRepository.findByHandled(false);
        model.addAttribute("reports", reports);
        return "admin/userReport";
    }

    @PostMapping("/admin/userReport")
    private String handleUserReport(
            @RequestParam Long reportId,
            @RequestParam Long userId,
            @RequestParam String description,
            @RequestParam String action,
            RedirectAttributes redirectAttributes
    ) {
        adminService.handleUserReport(reportId, userId, action, description);
        return "redirect:/admin/userReport";
    }

    @GetMapping("/admin/storeReport")
    private String storeReportPage(Model model) {
        List<StoreReport> reports = storeReportRepository.findByHandled(false);
        model.addAttribute("reports", reports);
        return "admin/storeReport";
    }

    @PostMapping("/admin/storeReport")
    private String handleStoreReport(
            @RequestParam Long reportId,
            @RequestParam Long storeId,
            @RequestParam String action,
            @RequestParam String description,
            RedirectAttributes redirectAttributes
    ) {
        adminService.handleStoreReport(reportId, storeId, action, description);
        return "redirect:/admin/storeReport";
    }

    @PostMapping("/admin/logout")
    private String logout() {
        return "admin/login";
    }
}
