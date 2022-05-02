package com.sososhopping.domain.store.controller.user;

import com.sososhopping.domain.store.dto.user.request.CreateReportDto;
import com.sososhopping.domain.store.service.user.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/store/{storeId}/reports")
    public void createStoreReport(Authentication authentication,
                                            @PathVariable Long storeId,
                                            @RequestBody @Valid CreateReportDto dto) {

        Long userId = Long.parseLong(authentication.getName());
        userReportService.createReport(userId, storeId, dto.getContent());
    }
}
