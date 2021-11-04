package com.sososhopping.server.entity.store;

public enum Day {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일")
    ;

    final private String krDay;

    Day(String krDay) {
        this.krDay = krDay;
    }

    public String getKrDay() { return krDay; }

    public static Day nameOf(String krDay) {
        for (Day day : Day.values()) {
            if (day.getKrDay().equals(krDay)) {
                return day;
            }
        }

        return null;
    }
}