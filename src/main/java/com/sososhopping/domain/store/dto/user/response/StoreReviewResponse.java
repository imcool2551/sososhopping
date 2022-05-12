package com.sososhopping.domain.store.dto.user.response;

import com.sososhopping.entity.user.Review;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StoreReviewResponse {

    private Long storeId;
    private Long userId;
    private String nickname;
    private String content;
    private String imgUrl;
    private BigDecimal score;
    private LocalDateTime createdAt;

    public StoreReviewResponse(Review review) {
        storeId = review.getStore().getId();
        userId = review.getUser().getId();
        nickname = review.getUser().getNickname();
        content = review.getContent();
        imgUrl = review.getImgUrl();
        score = review.getScore();
        createdAt = review.getCreatedAt();
    }
}
