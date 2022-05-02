package com.sososhopping.domain.store.dto.owner.request;

import com.sososhopping.entity.store.Store;
import com.sososhopping.entity.store.Writing;
import com.sososhopping.entity.store.WritingType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateWritingDto {

    @NotNull(message = "제목 필수")
    @NotBlank(message = "제목 필수")
    private String title;

    @NotNull(message = "내용 필수")
    @NotBlank(message = "내용 필수")
    private String content;

    @NotNull(message = "글 종류 필수")
    private WritingType writingType;

    private String imgUrl;

    public Writing toEntity(Store store) {
        return Writing.builder()
                .store(store)
                .title(title)
                .content(content)
                .writingType(writingType)
                .imgUrl(imgUrl)
                .build();
    }
}
