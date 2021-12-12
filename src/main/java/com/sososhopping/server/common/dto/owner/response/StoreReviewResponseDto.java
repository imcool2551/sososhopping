package com.sososhopping.server.common.dto.owner.response;

import com.sososhopping.server.entity.member.Review;
import lombok.*;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreReviewResponseDto {
    private Long storeId;
    private Long userId;
    private String userName;
    private BigDecimal score;
    private String content;
    private String createdAt;

    public StoreReviewResponseDto(Review review, Long storeId) {
        this.storeId = storeId;
        this.userId = review.getUser().getId();
        this.userName = review.getUser().getNickname();
        this.score = review.getScore();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
    }
}
