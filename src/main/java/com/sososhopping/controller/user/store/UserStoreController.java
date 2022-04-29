package com.sososhopping.controller.user.store;

import com.sososhopping.common.dto.ApiListResponse;
import com.sososhopping.common.dto.user.request.store.GetLocalCurrencyStoreDto;
import com.sososhopping.common.dto.user.request.store.GetStoreByCategoryDto;
import com.sososhopping.common.dto.user.request.store.GetStoreBySearchDto;
import com.sososhopping.common.dto.user.response.store.StoreInfoDto;
import com.sososhopping.common.dto.user.response.store.StoreListDto;
import com.sososhopping.domain.store.repository.InterestStoreRepository;
import com.sososhopping.service.user.store.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;


//@RestController
@RequiredArgsConstructor
public class UserStoreController {

    private final UserStoreService userStoreService;
    private final InterestStoreRepository interestStoreRepository;

    @GetMapping("/api/v1/users/stores")
    public ApiListResponse<StoreListDto> getStoresByCategory(
            Authentication authentication,
            @ModelAttribute @Valid GetStoreByCategoryDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        List<StoreListDto> dtos = userStoreService
                .getStoresByCategory(userId, dto);

        return new ApiListResponse<>(dtos);
    }

    @GetMapping("/api/v1/users/stores/page")
    public Slice<StoreListDto> getStoresByCategoryPageable(
            Authentication authentication,
            @ModelAttribute @Valid GetStoreByCategoryDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        return userStoreService
                .getStoresByCategoryPageable(userId, dto);
    }

    @GetMapping("/api/v1/users/stores/local")
    public Slice<StoreListDto> getLocalCurrencyStores(
            Authentication authentication,
            @ModelAttribute @Valid GetLocalCurrencyStoreDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        return userStoreService
                .getLocalCurrencyStores(userId, dto);
    }

    @GetMapping("/api/v1/users/search")
    public ApiListResponse<StoreListDto> getStoresBySearch(
            Authentication authentication,
            @ModelAttribute @Valid GetStoreBySearchDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        List<StoreListDto> dtos = userStoreService
                .getStoreBySearch(userId, dto);

        return new ApiListResponse<>(dtos);
    }

    @GetMapping("/api/v1/users/search/page")
    public Slice<StoreListDto> getStoresBySearchPageable(
            Authentication authentication,
            @ModelAttribute @Valid GetStoreBySearchDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        return userStoreService
                .getStoreBySearchPageable(userId, dto);
    }

    @GetMapping("/api/v1/users/stores/{storeId}")
    public StoreInfoDto getStoreInfo(
            Authentication authentication,
            @PathVariable Long storeId
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        return userStoreService.getStoreInfo(userId, storeId);
    }


}
