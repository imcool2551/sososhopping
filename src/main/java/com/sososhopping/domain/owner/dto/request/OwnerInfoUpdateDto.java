package com.sososhopping.domain.owner.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class OwnerInfoUpdateDto {

    @NotNull(message = "이름 필수")
    @Length(min = 2, max = 10, message = "이름 2자 이상 10자 이하")
    private String name;
}
