package com.sososhopping.server.common.dto.user.request.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@ToString
public class ReviewCreateDto {

    @NotNull
    private BigDecimal score;
    @NotNull
    private String content;
    private String imgUrl;
}
