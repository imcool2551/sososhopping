package com.sososhopping.server.controller.owner;

import com.sososhopping.server.common.dto.owner.request.StoreWritingRequestDto;
import com.sososhopping.server.common.dto.owner.response.StoreWritingListResponseDto;
import com.sososhopping.server.common.dto.owner.response.StoreWritingResponseDto;
import com.sososhopping.server.entity.store.Writing;
import com.sososhopping.server.service.owner.StoreWritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/api/v1/owner/store/{storeId}/writing")
    public ResponseEntity createWriting(
            @PathVariable(value = "storeId") Long storeId
            , @RequestPart(value = "dto") StoreWritingRequestDto dto
            , @RequestPart(value = "img", required = false) MultipartFile image) {
        storeWritingService.createWriting(storeId, dto, image);

        return new ResponseEntity(HttpStatus.CREATED);
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

    @PatchMapping(value = "/api/v1/owner/store/{storeId}/writing/{writingId}")
    public ResponseEntity updateWriting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "writingId") Long writingId
            , @RequestPart(value = "dto") StoreWritingRequestDto dto
            , @RequestPart(value = "img", required = false) MultipartFile image) {
        storeWritingService.updateWriting(storeId, writingId, dto, image);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/v1/owner/store/{storeId}/writing/{writingId}")
    public ResponseEntity deleteWriting(
            @PathVariable(value = "storeId") Long storeId
            , @PathVariable(value = "writingId") Long writingId) {
        storeWritingService.deleteWriting(storeId, writingId);

        return new ResponseEntity(HttpStatus.OK);
    }
}