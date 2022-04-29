package com.sososhopping.controller.user.store;

import com.sososhopping.common.dto.user.request.store.GetStoreBySearchDto;
import com.sososhopping.common.dto.user.response.store.StoreListDto;
import com.sososhopping.service.user.store.UserStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;


//@RestController
@RequiredArgsConstructor
public class UserStoreController {

    private final UserStoreService userStoreService;

    @GetMapping("/api/v1/users/search/page")
    public Slice<StoreListDto> getStoresBySearchPageable(
            Authentication authentication,
            @ModelAttribute @Valid GetStoreBySearchDto dto
    ) {
        Long userId = null;

        if (authentication != null) userId = Long.parseLong(authentication.getName());

        return userStoreService
                .getStoreBySearchPageable(userId, dto);
    }

}
