package com.sososhopping.domain.store.dto.user.request;

import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.user.Review;
import com.sososhopping.entity.user.User;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreateReviewDto {

    @NotNull(message = "평점 필수")
    @Min(1)
    private BigDecimal score;

    @NotNull(message = "후기 필수")
    @NotBlank(message = "후기 필수")
    private String content;

    private String imgUrl;

    public Review toEntity(Store store, User user) {
       return  Review.builder()
                .store(store)
                .user(user)
                .content(content)
                .score(score)
                .imgUrl(imgUrl)
                .build();
    }
}
