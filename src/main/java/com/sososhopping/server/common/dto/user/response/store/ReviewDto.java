package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.member.Review;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReviewDto {

    private Long storeId;
    private Long userId;
    private String nickname;
    private String content;
    private String imgUrl;
    private BigDecimal score;

    public ReviewDto(Review review) {
        storeId = review.getStore().getId();
        userId = review.getUser().getId();
        nickname = review.getUser().getNickname();
        content = review.getContent();
        imgUrl = review.getImgUrl();
        score = review.getScore();
    }
}
