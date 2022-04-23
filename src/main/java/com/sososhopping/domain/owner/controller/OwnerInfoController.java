package com.sososhopping.domain.owner.controller;

import com.sososhopping.common.exception.UnAuthorizedException;
import com.sososhopping.domain.owner.dto.request.OwnerInfoUpdateDto;
import com.sososhopping.domain.owner.dto.response.OwnerInfoResponse;
import com.sososhopping.domain.owner.repository.OwnerRepository;
import com.sososhopping.domain.owner.service.OwnerInfoService;
import com.sososhopping.entity.member.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OwnerInfoController {

    private final OwnerInfoService ownerInfoService;
    private final OwnerRepository ownerRepository;

    @GetMapping("/owner/info")
    public OwnerInfoResponse ownerInfo(Authentication authentication) {
        Long ownerId = Long.parseLong(authentication.getName());
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UnAuthorizedException::new);
        return new OwnerInfoResponse(owner);
    }

    @PatchMapping("/owner/info")
    public void updateOwnerInfo(Authentication authentication,
                                @RequestBody @Valid OwnerInfoUpdateDto dto) {

        Long ownerId = Long.parseLong(authentication.getName());
        ownerInfoService.updateOwnerInfo(ownerId, dto);
    }
}
