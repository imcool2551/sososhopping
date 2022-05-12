package com.sososhopping.domain.store.dto.user.response;

import com.sososhopping.entity.store.Writing;
import com.sososhopping.entity.store.WritingType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WritingResponse {

    private Long writingId;
    private String title;
    private String content;
    private WritingType writingType;
    private String imgUrl;
    private LocalDateTime createdAt;

    public WritingResponse(Writing writing) {
        writingId = writing.getId();
        title = writing.getTitle();
        content = writing.getContent();
        writingType = writing.getWritingType();
        imgUrl = writing.getImgUrl();
        createdAt = writing.getCreatedAt();
    }
}
