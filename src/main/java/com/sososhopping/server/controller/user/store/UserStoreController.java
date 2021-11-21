package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.user.request.store.GetStoreByCategoryDto;
import com.sososhopping.server.common.dto.user.request.store.GetStoreBySearchDto;
import com.sososhopping.server.common.dto.user.request.store.ToggleStoreLikeDto;
import com.sososhopping.server.common.dto.user.response.store.StoreListDto;
import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.response.store.StoreInfoDto;
import com.sososhopping.server.repository.store.InterestStoreRepository;
import com.sososhopping.server.service.user.store.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class UserStoreController {

    private final UserStoreService userStoreService;
    private final InterestStoreRepository interestStoreRepository;

    @GetMapping("/api/v1/users/stores")
    public ApiResponse<StoreListDto> getStoresByCategory(
            Authentication authentication,
            @ModelAttribute @Valid GetStoreByCategoryDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        List<StoreListDto> dtos = userStoreService
                .getStoresByCategory(userId, dto);

        return new ApiResponse<>(dtos);
    }

    @GetMapping("/api/v1/users/search")
    public ApiResponse<StoreListDto> getStoresBySearch(
            Authentication authentication,
            @ModelAttribute @Valid GetStoreBySearchDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        List<StoreListDto> dtos = userStoreService
                .getStoreBySearch(userId, dto);

        return new ApiResponse<>(dtos);
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

    @PostMapping("/api/v1/users/my/interest_store")
    public ResponseEntity toggleStoreLike(
            Authentication authentication,
            @RequestBody @Valid ToggleStoreLikeDto dto
    ) {

        Long userId = Long.parseLong(authentication.getName());
        Long storeId = dto.getStoreId();

        userStoreService.toggleStoreLike(userId, storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping("/api/v1/users/my/interest_store")
    public ApiResponse<StoreListDto> getInterestStores(Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());

        List<StoreListDto> dtos = interestStoreRepository.findAllByUserId(userId)
                .stream()
                .map((interestStore) -> new StoreListDto(interestStore))
                .collect(Collectors.toList());

        return new ApiResponse<StoreListDto>(dtos);
    }

}
