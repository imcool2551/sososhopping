package com.sososhopping.domain.point.controller;

import com.sososhopping.domain.point.dto.response.MyPointLogResponse;
import com.sososhopping.domain.point.dto.response.MyPointsResponse;
import com.sososhopping.domain.point.service.UserPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserPointController {

    private final UserPointService userPointService;

    @GetMapping("/users/my/points/store/{storeId}")
    public MyPointLogResponse findMonthlyPointLogs(Authentication authentication,
                                                   @PathVariable Long storeId,
                                                   @RequestParam LocalDate yearMonth) {

        Long userId = Long.parseLong(authentication.getName());
        return userPointService.findMonthlyPointLogs(userId, storeId, yearMonth);
    }

    @GetMapping("/users/my/points")
    public MyPointsResponse findMyPoints(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return userPointService.findMyPoints(userId);
    }
}
