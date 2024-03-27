package com.blackgear.geologicexpansion.core.config;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ConfigEntries {
    // ========== TOGGLEABLE FEATURES ==================================================================================
    @ExpectPlatform
    public static boolean includePrismaticCaldera() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean includeGeysers() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean includeLimestone() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean includeOvergrowth() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean includeDucks() {
        throw new AssertionError();
    }

    // ========== ENTITY BEHAVIOR ======================================================================================
    @ExpectPlatform
    public static boolean canDucksFish() {
        throw new AssertionError();
    }
}