package com.kelonstudios.wms.location.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

public enum LocationType {

    EUR1(1200, 800),
    EUR2(1200, 1000),
    EUR3(1000, 1200),
    EUR6(800, 600),

    ISO1(1219, 1016),
    ISO2(1067, 1067),
    ISO3(1219, 1219),

    HALF(600, 800),
    CUSTOM(0, 0);

    private final int length;
    private final int width;

    LocationType(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}