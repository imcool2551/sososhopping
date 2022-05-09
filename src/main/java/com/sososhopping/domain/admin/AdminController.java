package com.sososhopping.domain.admin;

import com.sososhopping.domain.auth.dto.request.AdminLoginDto;
import com.sososhopping.domain.report.repository.StoreReportRepository;
import com.sososhopping.domain.report.repository.UserReportRepository;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.entity.admin.StoreReport;
import com.sososhopping.entity.admin.UserReport;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.sososhopping.entity.store.StoreStatus.PENDING;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final StoreRepository storeRepository;
    private final UserReportRepository userReportRepository;
    private final StoreReportRepository storeReportRepository;

    @GetMapping("/admin/login")
    public String loginForm(@ModelAttribute("adminVO") AdminLoginDto adminVO) {
        return "admin/login";
    }

    @GetMapping("/admin/main")
    public String main() {
        return "admin/main";
    }

    @GetMapping("/admin/storeRegister")
    public String storeRegisterPage(Model model) {
        List<Store> stores = storeRepository.findByStoreStatus(PENDING);
        model.addAttribute("stores", stores);
        return "admin/storeRegister";
    }

    @PostMapping("/admin/storeRegister")
    public String handleStoreRegister(@RequestParam Long storeId,
                                      @RequestParam String action) {

        adminService.updateStoreStatus(storeId, action);
        return "redirect:/admin/storeRegister";
    }

    @GetMapping("/admin/userReport")
    public String userReportPage(Model model) {
        List<UserReport> reports = userReportRepository.findByHandled(false);
        model.addAttribute("reports", reports);
        return "admin/userReport";
    }

    @PostMapping("/admin/userReport")
    public String handleUserReport(@RequestParam Long reportId,
                                   @RequestParam Long userId,
                                   @RequestParam String description,
                                   @RequestParam String action) {

        adminService.handleUserReport(reportId, userId, action, description);
        return "redirect:/admin/userReport";
    }

    @GetMapping("/admin/storeReport")
    public String storeReportPage(Model model) {
        List<StoreReport> reports = storeReportRepository.findByHandled(false);
        model.addAttribute("reports", reports);
        return "admin/storeReport";
    }

    @PostMapping("/admin/storeReport")
    public String handleStoreReport(@RequestParam Long reportId,
                                    @RequestParam Long storeId,
                                    @RequestParam String action) {

        adminService.handleStoreReport(reportId, storeId, action);
        return "redirect:/admin/storeReport";
    }

    @PostMapping("/admin/logout")
    public String logout() {
        return "admin/login";
    }
}
