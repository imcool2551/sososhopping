package com.sososhopping.domain.store.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.exception.BindingException;
import com.sososhopping.domain.store.dto.request.CreateStoreDto;
import com.sososhopping.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/owner/my/store/upload")
    public ResponseEntity<ApiResponse> upload(
            Authentication authentication,
            @RequestParam MultipartFile file) throws IOException {

        Long ownerId = Long.parseLong(authentication.getName());
        String imgUrl = storeService.upload(ownerId, file);
        return new ResponseEntity<>(new ApiResponse(imgUrl), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/owner/my/store")
    public void createStore(Authentication authentication,
                            @RequestBody @Valid CreateStoreDto dto,
                            BindingResult bindingResult) {

        validateCreateStoreDto(dto, bindingResult);

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
}
