package com.sososhopping.entity.orders;

public enum OrderType {
    ONSITE("픽업"),
    DELIVERY("배송");

    private final String krOrderType;

    OrderType(String krOrderType) {
        this.krOrderType = krOrderType;
    }

    public String getKrOrderType() {
        return krOrderType;
    }
}
