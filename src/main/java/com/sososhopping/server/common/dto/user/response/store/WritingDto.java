package com.sososhopping.server.common.dto.user.response.store;

import com.sososhopping.server.entity.store.Writing;
import com.sososhopping.server.entity.store.WritingType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class WritingDto {

    private Long writingId;
    private String title;
    private String content;
    private WritingType writingType;
    private List<String> imgUrl = new ArrayList<>();
    private final LocalDateTime createdAt;

    public WritingDto(Writing writing) {
        writingId = writing.getId();
        title = writing.getTitle();
        content = writing.getContent();
        writingType = writing.getWritingType();
        imgUrl.add(writing.getImgUrl());
        createdAt = writing.getCreatedAt();
    }
}
