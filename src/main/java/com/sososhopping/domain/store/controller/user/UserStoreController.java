package com.sososhopping.domain.store.controller.user;

import com.sososhopping.a.dto.ApiResponse;
import com.sososhopping.domain.store.dto.user.request.StoreSearchByCategoryDto;
import com.sososhopping.domain.store.dto.user.request.StoreSearchByKeywordDto;
import com.sososhopping.domain.store.dto.user.response.InterestStoreResponse;
import com.sososhopping.domain.store.dto.user.response.StoreResponse;
import com.sososhopping.domain.store.dto.user.response.StoresResponse;
import com.sososhopping.domain.store.service.user.StoreSearchService;
import com.sososhopping.domain.store.service.user.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserStoreController {

    private final UserStoreService userStoreService;
    private final StoreSearchService storeSearchService;

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

    @GetMapping("/store/{storeId}")
    public StoreResponse findStore(Authentication authentication, @PathVariable Long storeId) {
        Long userId = authentication != null
                ? Long.parseLong(authentication.getName())
                : null;

        return userStoreService.findStore(userId, storeId);
    }

    @GetMapping("/store")
    public Slice<StoresResponse> findStoresByCategory(Authentication authentication,
                                                      @ModelAttribute @Valid StoreSearchByCategoryDto dto) {

        Long userId = authentication != null
                ? Long.parseLong(authentication.getName())
                : null;

        return storeSearchService.findStoresByCategory(userId, dto);
    }

    @GetMapping("/search")
    public Slice<StoresResponse> findStoresBySearch(Authentication authentication,
                                                    @ModelAttribute @Valid StoreSearchByKeywordDto dto) {

        Long userId = authentication != null
                ? Long.parseLong(authentication.getName())
                : null;

        return storeSearchService.findStoresBySearch(userId, dto);
    }
}
