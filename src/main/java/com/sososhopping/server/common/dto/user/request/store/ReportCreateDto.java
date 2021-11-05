package com.sososhopping.server.common.dto.user.request.store;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class ReportCreateDto {

    @NotEmpty
    private String content;
}
