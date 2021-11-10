package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.store.Writing;
import com.sososhopping.server.entity.store.WritingType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WritingDto {

    private Long writingId;
    private String title;
    private String content;
    private WritingType writingType;
    private String imgUrl;
    private final LocalDateTime createdAt;

    public WritingDto(Writing writing) {
        writingId = writing.getId();
        title = writing.getTitle();
        content = writing.getContent();
        writingType = writing.getWritingType();
        imgUrl = writing.getImgUrl();
        createdAt = writing.getCreatedAt();
    }
}
