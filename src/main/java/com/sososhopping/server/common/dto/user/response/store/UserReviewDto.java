package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.member.Review;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class UserReviewDto {

    private final Long storeId;
    private final Long userId;
    private final String storeName;
    private final String content;
    private final String imgUrl;
    private final BigDecimal score;
    private final String createdAt;

    public UserReviewDto(Review review) {
        storeId = review.getStore().getId();
        userId = review.getUser().getId();
        storeName = review.getStore().getName();
        content = review.getContent();
        imgUrl = review.getImgUrl();
        score = review.getScore();
        createdAt = review.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}