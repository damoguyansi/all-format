package com.damoguyansi.all.format.cache;

public enum CacheName {
    ON_TOP("onTop"),
    NEW_LINE("newLine"),
    SELECT_INDEX("selectIndex"),
    LOCATION_X("locationX"),
    LOCATION_Y("locationY"),
    SIZE_WDITH("sizeWidth"),
    SIZE_HEIGHT("sizeHeight");

    private CacheName(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
