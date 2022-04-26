package com.sososhopping.common.dto.user.response.store;

import com.sososhopping.entity.store.Writing;
import com.sososhopping.entity.store.WritingType;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class WritingListDto {

    private Long writingId;
    private String title;
    private String content;
    private WritingType writingType;
    private String imgUrl;
    private String createdAt;

    public WritingListDto(Writing writing) {
        writingId = writing.getId();
        title = writing.getTitle();
        content = writing.getContent().length() < 30 ?
                writing.getContent()
                : writing.getContent().substring(0, 30);
        writingType = writing.getWritingType();
        imgUrl = writing.getImgUrl();
        createdAt = writing.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
