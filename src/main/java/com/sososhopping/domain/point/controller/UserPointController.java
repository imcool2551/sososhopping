package com.sososhopping.domain.point.controller;

import com.sososhopping.common.dto.user.response.point.UserPointListDto;
import com.sososhopping.domain.point.dto.response.UserPointLogResponse;
import com.sososhopping.common.error.Api401Exception;
import com.sososhopping.domain.point.service.UserPointService;
import com.sososhopping.entity.user.User;
import com.sososhopping.entity.point.UserPoint;
import com.sososhopping.entity.store.Store;
import com.sososhopping.domain.point.repository.UserPointRepository;
import com.sososhopping.domain.auth.repository.UserAuthRepository;
import com.sososhopping.repository.store.InterestStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserPointController {

    private final UserPointService userPointService;
    private final UserAuthRepository userRepository;
    private final InterestStoreRepository interestStoreRepository;
    private final UserPointRepository userPointRepository;

    @GetMapping("/api/v1/users/my/points")
    public UserPointListDto getMyPoints(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Api401Exception("Invalid Token"));

        List<UserPoint> userPoints = userPointRepository.findByUser(user);

        List<Store> interestStores = interestStoreRepository.findAllByUserId(userId)
                .stream()
                .map(interestStore -> interestStore.getStore())
                .collect(Collectors.toList());

        return new UserPointListDto(userPoints, interestStores);
    }

    @GetMapping("/users/my/points/store/{storeId}")
    public UserPointLogResponse findMonthlyUserPointLogs(Authentication authentication,
                                                         @PathVariable Long storeId,
                                                         @RequestParam LocalDate yearMonth) {

        Long userId = Long.parseLong(authentication.getName());
        return userPointService.findMonthlyUserPointLogs(userId, storeId, yearMonth);
    }
}
