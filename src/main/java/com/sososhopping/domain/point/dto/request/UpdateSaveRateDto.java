package com.sososhopping.domain.point.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class UpdateSaveRateDto {

    @NotNull(message = "포인트 정책 여부 필수")
    private Boolean pointPolicyStatus;

    @Min(value = 0, message = "적립율은 0% 이상")
    private BigDecimal saveRate;
}
