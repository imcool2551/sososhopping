package com.sososhopping.server.common.dto.owner.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMetaDataRequestDto {
    private String businessNumber;
    private String representativeName;
    private String businessName;
    private String openingDate;
}
