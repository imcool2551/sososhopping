package com.sososhopping.domain.report.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.report.dto.request.CreateStoreReportDto;
import com.sososhopping.domain.report.service.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/store/{storeId}/reports")
    public ApiResponse createStoreReport(Authentication authentication,
                                         @PathVariable Long storeId,
                                         @RequestBody @Valid CreateStoreReportDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        Long storeReportId = userReportService.createStoreReport(userId, storeId, dto.getContent());
        return new ApiResponse(storeReportId);
    }
}
