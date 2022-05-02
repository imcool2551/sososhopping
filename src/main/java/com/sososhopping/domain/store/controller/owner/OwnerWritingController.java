package com.sososhopping.domain.store.controller.owner;

import com.sososhopping.domain.store.dto.owner.request.CreateWritingDto;
import com.sososhopping.domain.store.service.owner.OwnerWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OwnerWritingController {

    private final OwnerWritingService ownerWritingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/owner/my/store/{storeId}/writings")
    public void createWriting(Authentication authentication,
                              @PathVariable Long storeId,
                              @ModelAttribute @Valid CreateWritingDto dto) {

        Long ownerId = Long.parseLong(authentication.getName());
        ownerWritingService.createWriting(ownerId, storeId, dto);
    }
}
