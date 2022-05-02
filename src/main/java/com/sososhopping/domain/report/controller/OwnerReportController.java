package com.sososhopping.domain.report.controller;

import com.sososhopping.domain.report.dto.request.CreateUserReportDto;
import com.sososhopping.domain.report.service.OwnerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OwnerReportController {

    private final OwnerReportService ownerReportService;

    @PostMapping("/owner/my/store/{storeId}/user/{userId}/reports")
    public void createUserReport(Authentication authentication,
                                 @PathVariable Long storeId,
                                 @PathVariable Long userId,
                                 @RequestBody @Valid CreateUserReportDto dto) {

        Long ownerId = Long.parseLong(authentication.getName());
        ownerReportService.createUserReport(ownerId, storeId, userId, dto.getContent());
    }
}
