package com.sososhopping.domain.store.dto.owner.response;

import com.sososhopping.domain.store.dto.user.response.StoreReviewResponse;
import lombok.Data;

import java.util.List;

@Data
public class StoreReviewsResponse {

    private int size;
    private double averageScore;
    private List<StoreReviewResponse> reviews;

    public StoreReviewsResponse(int size, double averageScore, List<StoreReviewResponse> reviews) {
        this.size = size;
        this.averageScore = averageScore;
        this.reviews = reviews;
    }
}
