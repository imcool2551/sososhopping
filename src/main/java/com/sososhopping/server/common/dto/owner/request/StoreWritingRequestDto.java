package com.sososhopping.server.common.dto.owner.request;

import com.sososhopping.server.entity.store.WritingType;
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
