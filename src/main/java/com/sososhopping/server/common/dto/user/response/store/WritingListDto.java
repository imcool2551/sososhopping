package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.store.Writing;
import com.sososhopping.server.entity.store.WritingType;
import lombok.Getter;

@Getter
public class WritingListDto {

    private Long writingId;
    private String title;
    private WritingType writingType;
    private String imgUrl;

    public WritingListDto(Writing writing) {
        writingId = writing.getId();
        title = writing.getTitle();
        writingType = writing.getWritingType();
        imgUrl = writing.getImgUrl();
    }
}
