package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.member.Review;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class StoreReviewDto {

    private final Long storeId;
    private final Long userId;
    private final String nickname;
    private final String content;
    private final String imgUrl;
    private final BigDecimal score;
    private final LocalDateTime createdAt;

    public StoreReviewDto(Review review) {
        storeId = review.getStore().getId();
        userId = review.getUser().getId();
        nickname = review.getUser().getNickname();
        content = review.getContent();
        imgUrl = review.getImgUrl();
        score = review.getScore();
        createdAt = review.getCreatedAt();
    }
}
