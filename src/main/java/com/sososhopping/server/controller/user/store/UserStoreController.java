package com.sososhopping.server.controller.user.store;

import com.sososhopping.server.common.dto.user.request.store.ToggleStoreLikeDto;
import com.sososhopping.server.common.dto.user.response.store.StoreListDto;
import com.sososhopping.server.common.dto.ApiResponse;
import com.sososhopping.server.common.dto.user.response.store.StoreInfoDto;
import com.sososhopping.server.entity.store.StoreType;
import com.sososhopping.server.repository.store.InterestStoreRepository;
import com.sososhopping.server.repository.store.StoreRepository;
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
    private final StoreRepository storeRepository;
    private final InterestStoreRepository interestStoreRepository;

    @GetMapping("/api/v1/stores")
    public ApiResponse<StoreListDto> getStoresByCategory(
            Authentication authentication,
            @RequestParam StoreType type
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        List<StoreListDto> dtos = userStoreService
                .getStoresByCategory(userId, type);

        return new ApiResponse<StoreListDto>(dtos);
    }

    @GetMapping("/api/v1/stores/{storeId}")
    public ApiResponse<StoreInfoDto> getStoreInfo(
            Authentication authentication,
            @PathVariable Long storeId
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        StoreInfoDto storeInfoDto = userStoreService.getStoreInfo(userId, storeId);

        return new ApiResponse<StoreInfoDto>(storeInfoDto);
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
