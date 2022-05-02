package com.sososhopping.common.dto.user.response.store;

import com.sososhopping.entity.store.Writing;
import com.sososhopping.entity.store.WritingType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WritingDto {

    private Long writingId;
    private String title;
    private String content;
    private WritingType writingType;
    private String imgUrl;
    private LocalDateTime createdAt;

    public WritingDto(Writing writing) {
        writingId = writing.getId();
        title = writing.getTitle();
        content = writing.getContent();
        writingType = writing.getWritingType();
        imgUrl = writing.getImgUrl();
        createdAt = writing.getCreatedAt();
    }
}
