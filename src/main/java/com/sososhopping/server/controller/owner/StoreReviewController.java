package com.sososhopping.server.controller.owner;

import com.sososhopping.server.common.dto.owner.response.StoreReviewListResponseDto;
import com.sososhopping.server.common.dto.owner.response.StoreReviewResponseDto;
import com.sososhopping.server.service.owner.StoreReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StoreReviewController {

    private final StoreReviewService storeReviewService;

    @GetMapping(value = "/api/v1/owner/store/{storeId}/review")
    public ResponseEntity readReviewList(@PathVariable(value = "storeId") Long storeId) {
        List<StoreReviewResponseDto> reviews = storeReviewService.readReviewList(storeId)
                .stream()
                .map(review -> new StoreReviewResponseDto(review))
                .collect(Collectors.toList());

        BigDecimal averageScore = new BigDecimal("0");

        if (reviews.size() != 0) {
            for (StoreReviewResponseDto review : reviews) {
                averageScore = averageScore.add(review.getScore());
            }

            averageScore = averageScore.divide(new BigDecimal(String.valueOf(reviews.size())))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new StoreReviewListResponseDto(averageScore, reviews));
    }
}
