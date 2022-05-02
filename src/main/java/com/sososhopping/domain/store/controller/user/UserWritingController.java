package com.sososhopping.domain.store.controller.user;

import com.sososhopping.common.dto.user.response.store.WritingDto;
import com.sososhopping.domain.store.service.user.UserWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserWritingController {

    private final UserWritingService userWritingService;

    @GetMapping("/store/{storeId}/writing/{writingId}")
    public WritingDto findStoreWriting(@PathVariable Long storeId, @PathVariable Long writingId) {
        return userWritingService.findStoreWriting(storeId, writingId);
    }
}
