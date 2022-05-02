package com.sososhopping.domain.store.controller.owner;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.exception.BindingException;
import com.sososhopping.common.exception.NotFoundException;
import com.sososhopping.domain.store.dto.owner.request.CreateStoreDto;
import com.sososhopping.domain.store.dto.owner.response.StoreResponse;
import com.sososhopping.domain.store.dto.owner.response.StoresResponse;
import com.sososhopping.domain.store.repository.StoreRepository;
import com.sososhopping.domain.store.service.owner.OwnerStoreService;
import com.sososhopping.entity.store.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OwnerStoreController {

    private final OwnerStoreService ownerStoreService;
    private final StoreRepository storeRepository;

    @PostMapping("/owner/my/store")
    public ResponseEntity<ApiResponse> createStore(Authentication authentication,
                                                   @RequestBody @Valid CreateStoreDto dto,
                                                   BindingResult bindingResult) {

        validateCreateStoreDto(dto, bindingResult);
        Long ownerId = Long.parseLong(authentication.getName());
        Long storeId = ownerStoreService.createStore(ownerId, dto);
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
        List<StoresResponse> stores = ownerStoreService.findStores(ownerId);
        return new ApiResponse(stores);
    }

    @GetMapping("/owner/my/store/{storeId}")
    public StoreResponse findStoreById(Authentication authentication, @PathVariable Long storeId) {
        Long ownerId = Long.parseLong(authentication.getName());
        return ownerStoreService.findStore(ownerId, storeId);
    }

    @PatchMapping("/owner/my/store/{storeId}/open")
    public ApiResponse setOpen(Authentication authentication,
                               @PathVariable Long storeId,
                               @RequestParam boolean open) {

        Long ownerId = Long.parseLong(authentication.getName());
        boolean isOpen = ownerStoreService.setOpen(ownerId, storeId, open);
        return new ApiResponse(isOpen);
    }

    @GetMapping("/store/{storeId}/open")
    public ApiResponse isOpen(@PathVariable Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("Store not found with id " + storeId));

        return new ApiResponse(store.isOpen());
    }
}
