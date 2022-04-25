package com.sososhopping.common.dto.owner.request;

import com.sososhopping.entity.owner.WritingType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreWritingRequestDto {
    private String title;
    private String content;
    private WritingType writingType;
}
