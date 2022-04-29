package com.sososhopping.domain.store.controller.user;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.store.dto.user.response.InterestStoreResponse;
import com.sososhopping.domain.store.service.user.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserStoreController {

    private final UserStoreService userStoreService;

    @PostMapping("/users/my/interest_store")
    public void toggleInterest(Authentication authentication, @RequestParam Long storeId) {
        Long userId = Long.parseLong(authentication.getName());
        userStoreService.toggleInterest(userId, storeId);
    }

    @GetMapping("/users/my/interest_store")
    public ApiResponse findInterestStores(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<InterestStoreResponse> stores = userStoreService.findInterestStores(userId);
        return new ApiResponse(stores);
    }
}
