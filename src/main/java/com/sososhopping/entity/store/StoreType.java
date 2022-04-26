package com.sososhopping.entity.store;

import java.util.Arrays;
import java.util.NoSuchElementException;

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

    private final String krName;

    StoreType(String krName) {
        this.krName = krName;
    }

    public static StoreType ofKrName(String krName) {
        return Arrays.stream(StoreType.values())
                .filter(storeType -> storeType.getKrName().equals(krName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("can't find store type of " + krName));
    }

    public String getKrName() {
        return krName;
    }
}
