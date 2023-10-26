package com.blackgear.geologicexpansion.core.platform.common.resource;

public class TimeValue {
    public static int seconds(int seconds) {
        return seconds * 20;
    }

    public static int minutes(int minutes) {
        return seconds(minutes * 60);
    }
}