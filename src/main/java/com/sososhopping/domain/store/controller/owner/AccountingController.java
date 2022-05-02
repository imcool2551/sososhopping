package com.sososhopping.domain.store.controller.owner;


import com.sososhopping.common.dto.ApiResponse;
import com.sososhopping.domain.store.dto.owner.request.CreateAccountingDto;
import com.sososhopping.domain.store.dto.owner.response.StoreAccountingResponse;
import com.sososhopping.domain.store.service.owner.AccountingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountingController {

    private final AccountingService accountingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/owner/my/store/{storeId}/accounting")
    public ApiResponse createAccounting(Authentication authentication,
                                        @PathVariable Long storeId,
                                        @RequestBody @Valid CreateAccountingDto dto) {

        Long ownerId = Long.parseLong(authentication.getName());
        Long accountingId = accountingService.createAccounting(ownerId, storeId, dto);
        return new ApiResponse(accountingId);
    }

    @GetMapping("/owner/my/store/{storeId}/accounting")
    public ApiResponse findAccountings(Authentication authentication,
                                       @PathVariable Long storeId,
                                       @RequestParam LocalDate yearMonth) {

        Long ownerId = Long.parseLong(authentication.getName());
        List<StoreAccountingResponse> accountings = accountingService.findAccountings(ownerId, storeId, yearMonth);
        return new ApiResponse(accountings);
    }

    @GetMapping("/owner/my/store/{storeId}/accounting/{accountingId}")
    public ApiResponse findAccounting(Authentication authentication,
                                      @PathVariable Long storeId,
                                      @PathVariable Long accountingId) {

        Long ownerId = Long.parseLong(authentication.getName());
        StoreAccountingResponse accounting = accountingService.findAccounting(ownerId, storeId, accountingId);
        return new ApiResponse(accounting);
    }

    @DeleteMapping("/owner/my/store/{storeId}/accounting/{accountingId}")
    public void deleteAccounting(Authentication authentication,
                                      @PathVariable Long storeId,
                                      @PathVariable Long accountingId) {

        Long ownerId = Long.parseLong(authentication.getName());
        accountingService.deleteAccounting(ownerId, storeId, accountingId);
    }
}
