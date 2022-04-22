package com.sososhopping.common.dto.user.request.store;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class ReviewCreateDto {

    @NotNull
    private BigDecimal score;
    @NotNull
    private String content;
    private String imgUrl;
}
