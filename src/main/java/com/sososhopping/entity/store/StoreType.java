package com.sososhopping.entity.store;

import java.util.Arrays;

public enum StoreType {
    GROCERY("마트"),
    MEAT("정육점"),
    FISH("수산물"),
    BAKERY("베이커리"),
    CAFE("카페"),
    HAIRSHOP("헤어숍"),
    LAUNDRY("세탁소"),
    BOOK("서점"),
    OFFICE("사무용품"),
    ACADEMY("학원"),
    PHOTO("사진관"),
    FLOWER("꽃집"),
    CLOTH("의류")
    ;

    private final String krType;

    StoreType(String krType) { this.krType = krType; }

    public String getKrType() { return krType; }

    static public StoreType nameOf(String krType) {
        return Arrays.stream(StoreType.values())
                .filter(storeType -> storeType.getKrType().equals(krType))
                .findFirst()
                .orElse(null);
    }
}
