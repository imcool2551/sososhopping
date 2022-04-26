package com.sososhopping.domain.store.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.exception.BindingException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.store.dto.request.CreateStoreDto;
import com.sososhopping.domain.store.dto.response.StoreResponse;
import com.sososhopping.domain.store.dto.response.StoresResponse;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.store.service.StoreService;
import com.sososhopping.entity.owner.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreRepository storeRepository;

    @PostMapping("/owner/my/store/upload")
    public ResponseEntity<ApiResponse> upload(Authentication authentication,
                                              @RequestParam MultipartFile file) throws IOException {

        Long ownerId = Long.parseLong(authentication.getName());
        String imgUrl = storeService.upload(ownerId, file);
        return new ResponseEntity<>(new ApiResponse(imgUrl), CREATED);
    }

    @PostMapping("/owner/my/store")
    public ResponseEntity<ApiResponse> createStore(Authentication authentication,
                                                   @RequestBody @Valid CreateStoreDto dto,
                                                   BindingResult bindingResult) {

        validateCreateStoreDto(dto, bindingResult);
        Long ownerId = Long.parseLong(authentication.getName());
        Long storeId = storeService.createStore(ownerId, dto);
        return new ResponseEntity<>(new ApiResponse(storeId), CREATED);
    }

    private void validateCreateStoreDto(CreateStoreDto dto, BindingResult bindingResult) {
        if (dto.getDeliveryStatus() && dto.getDeliveryCharge() == null) {
            bindingResult.reject("deliveryCharge", "배송 가격을 입력해 주세요.");
        }

        if (dto.getDeliveryStatus() && dto.getMinimumOrderPrice() == null) {
            bindingResult.reject("minimumOrderPrice", "최소 주문금액을 입력해 주세요.");
        }

        if (dto.getPointPolicyStatus() && dto.getSaveRate() == null) {
            bindingResult.reject("saveRate", "적립율을 입력해 주세요.");
        }

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .get(0)
                    .getDefaultMessage();

            throw new BindingException(errorMessage);
        }
    }

    @GetMapping("/owner/my/store")
    public ApiResponse findStores(Authentication authentication) {
        Long ownerId = Long.parseLong(authentication.getName());
        List<StoresResponse> stores = storeService.findStores(ownerId);
        return new ApiResponse(stores);
    }

    @GetMapping("/owner/my/store/{storeId}")
    public StoreResponse findStoreById(Authentication authentication, @PathVariable Long storeId) {
        Long ownerId = Long.parseLong(authentication.getName());
        return storeService.findStore(ownerId, storeId);
    }

    @PatchMapping("/owner/my/store/{storeId}/open")
    public ApiResponse setOpen(Authentication authentication,
                               @PathVariable Long storeId,
                               @RequestParam boolean open) {

        Long ownerId = Long.parseLong(authentication.getName());
        boolean isOpen = storeService.setOpen(ownerId, storeId, open);
        return new ApiResponse(isOpen);
    }

    @GetMapping("/owner/store/{storeId}/open")
    public ApiResponse isOpen(@PathVariable Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("Store not found with id " + storeId));

        return new ApiResponse(store.isOpen());
    }
}
