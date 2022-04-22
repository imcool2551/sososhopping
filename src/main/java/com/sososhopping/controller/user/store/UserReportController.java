package com.sososhopping.controller.user.store;

import com.sososhopping.common.dto.user.request.store.ReportCreateDto;
import com.sososhopping.service.user.store.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @PostMapping("/api/v1/users/stores/{storeId}/reports")
    public ResponseEntity createStoreReport(
            Authentication authentication,
            @PathVariable Long storeId,
            @RequestBody @Valid ReportCreateDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        userReportService.createReport(userId, storeId, dto.getContent());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(null);
    }
}
