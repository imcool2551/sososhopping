package com.sososhopping.common.dto.owner.response;

import com.sososhopping.entity.owner.Writing;
import com.sososhopping.entity.owner.WritingType;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreWritingResponseDto {
    private Long id;
    private String title;
    private String content;
    private WritingType writingType;
    private String imgUrl;
    private String createdAt;

    public StoreWritingResponseDto(Writing writing) {
        this.id = writing.getId();
        this.title = writing.getTitle();
        this.content = writing.getContent();
        this.writingType = writing.getWritingType();
        this.imgUrl = writing.getImgUrl();
        this.createdAt = writing.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
    }
}
