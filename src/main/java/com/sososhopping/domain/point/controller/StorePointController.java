package com.sososhopping.domain.point.controller;

import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.common.exception.BindingException;
import com.sososhopping.domain.point.dto.request.UpdateSaveRateDto;
import com.sososhopping.domain.point.dto.request.UpdateUserPointDto;
import com.sososhopping.domain.point.dto.response.UserPointResponse;
import com.sososhopping.domain.point.service.StorePointService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StorePointController {

    private final StorePointService storePointService;

    @GetMapping("/owner/my/store/{storeId}/saveRate")
    public ApiResponse findSaveRate(Authentication authentication, @PathVariable Long storeId) {
        Long ownerId = Long.parseLong(authentication.getName());
        return new ApiResponse(storePointService.findSaveRate(ownerId, storeId));
    }

    @PatchMapping("/owner/my/store/{storeId}/saveRate")
    public void updateSaveRate(Authentication authentication,
                               @PathVariable Long storeId,
                               @RequestBody @Valid UpdateSaveRateDto dto) {

        validateUpdateSaveRateDto(dto);
        Long ownerId = Long.parseLong(authentication.getName());
        storePointService.updateSaveRate(ownerId, storeId, dto);
    }

    private void validateUpdateSaveRateDto(UpdateSaveRateDto dto) {
        if (dto.getPointPolicyStatus() && (dto.getSaveRate() == null)) {
            throw new BindingException("적립율을 입력해주세요");
        }
    }

    @GetMapping("/owner/my/store/{storeId}/point")
    public UserPointResponse findUserPoint(Authentication authentication,
                                           @PathVariable Long storeId,
                                           @RequestParam String userPhone) {

        Long ownerId = Long.parseLong(authentication.getName());
        return storePointService.findUserPoint(ownerId, storeId, userPhone);
    }

    @PostMapping("/owner/my/store/{storeId}/point")
    public void updateUserPoint(Authentication authentication,
                                @PathVariable Long storeId,
                                @RequestBody @Valid UpdateUserPointDto dto) {

        Long ownerId = Long.parseLong(authentication.getName());
        storePointService.updateUserPoint(ownerId, storeId, dto);
    }
}
