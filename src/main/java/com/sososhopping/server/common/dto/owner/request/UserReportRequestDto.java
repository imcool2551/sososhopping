package com.sososhopping.server.common.dto.owner.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReportRequestDto {
    private Long userId;
    private String userName;
    private String content;
}
