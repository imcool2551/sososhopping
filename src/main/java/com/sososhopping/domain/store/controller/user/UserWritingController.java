package com.sososhopping.domain.store.controller.user;

import com.sososhopping.common.dto.OffsetBasedPageRequest;
import com.sososhopping.domain.store.dto.user.response.WritingResponse;
import com.sososhopping.domain.store.service.user.UserWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserWritingController {

    private final UserWritingService userWritingService;

    @GetMapping("/store/{storeId}/writing/{writingId}")
    public WritingResponse findStoreWriting(@PathVariable Long storeId, @PathVariable Long writingId) {
        return userWritingService.findStoreWriting(storeId, writingId);
    }

    @GetMapping("/store/{storeId}/writing")
    public Slice<WritingResponse> findStoreWritings(@PathVariable Long storeId,
                                                    @RequestParam(defaultValue = "0") int offset,
                                                    @RequestParam(defaultValue = "5") int limit) {

        return userWritingService.findStoreWritings(storeId, new OffsetBasedPageRequest(offset, limit));
    }
}
