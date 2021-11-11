package com.sososhopping.server.controller.owner;

import com.sososhopping.server.common.dto.owner.request.UserReportRequestDto;
import com.sososhopping.server.service.owner.StoreUserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreUserReportController {
    private final StoreUserReportService storeUserReportService;

    @PostMapping(value = "/api/v1/owner/store/{storeId}/report")
    public ResponseEntity createUserReport(
            @PathVariable(value = "storeId") Long storeId
            , @RequestBody UserReportRequestDto dto) {
        storeUserReportService.createUserReport(storeId, dto);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
