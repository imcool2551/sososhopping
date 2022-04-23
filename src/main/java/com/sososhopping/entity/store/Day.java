package com.sososhopping.entity.store;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Day {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private String krDay;

    Day(String krDay) {
        this.krDay = krDay;
    }

    public static Day krDayOf(String krDay) {
        return Arrays.stream(Day.values())
                .filter(day -> day.getKrDay().equals(krDay))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

    }

    public String getKrDay() {
        return krDay;
    }
}