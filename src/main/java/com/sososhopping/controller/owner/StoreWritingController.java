package com.sososhopping.controller.owner;

import com.sososhopping.common.dto.owner.response.StoreWritingListResponseDto;
import com.sososhopping.common.dto.owner.response.StoreWritingResponseDto;
import com.sososhopping.entity.store.Writing;
import com.sososhopping.service.owner.StoreWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StoreWritingController {

    private final StoreWritingService storeWritingService;

    @GetMapping(value = "/api/v1/owner/store/{storeId}/writing")
    public ResponseEntity readWritingList(@PathVariable(value = "storeId") Long storeId) {
        List<StoreWritingListResponseDto> writings = storeWritingService.readWritingList(storeId)
                .stream()
                .map(writing -> new StoreWritingListResponseDto(writing, storeId))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(writings);
    }


    @GetMapping(value = "/api/v1/owner/store/{storeId}/writing/{writingId}")
    public ResponseEntity readWriting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "writingId") Long writingId) {
        Writing writing = storeWritingService.readWriting(storeId, writingId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreWritingResponseDto(writing));
    }


    @DeleteMapping(value = "/api/v1/owner/store/{storeId}/writing/{writingId}")
    public ResponseEntity deleteWriting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "writingId") Long writingId) {
        storeWritingService.deleteWriting(storeId, writingId);

        return new ResponseEntity(HttpStatus.OK);
    }
}