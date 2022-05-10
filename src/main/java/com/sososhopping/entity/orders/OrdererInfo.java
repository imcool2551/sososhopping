package com.sososhopping.entity.orders;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdererInfo {

    private String ordererName;

    @Column(columnDefinition = "char", length = 11)
    private String ordererPhone;

    public OrdererInfo(String ordererName, String ordererPhone) {
        this.ordererName = ordererName;
        this.ordererPhone = ordererPhone;
    }
}
