package com.sososhopping.domain.report.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserReportDto {

    @NotNull(message = "신고 내용 필수")
    @NotBlank(message = "신고 내용 필수")
    private String content;
}
