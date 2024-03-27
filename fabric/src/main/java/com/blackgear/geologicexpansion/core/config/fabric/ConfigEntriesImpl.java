package com.blackgear.geologicexpansion.core.config.fabric;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.fabric.GeologicExpansionFabric;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = GeologicExpansion.MOD_ID)
public class ConfigEntriesImpl implements ConfigData {
    // ========== TOGGLEABLE FEATURES ==================================================================================
    public static boolean includeGeysers() {
        return GeologicExpansionFabric.CONFIG.toggleableFeatures.includeGeysers;
    }

    public static boolean includeLimestone() {
        return GeologicExpansionFabric.CONFIG.toggleableFeatures.includeLimestone;
    }

    public static boolean includeOvergrowth() {
        return GeologicExpansionFabric.CONFIG.toggleableFeatures.includeOvergrowth;
    }

    public static boolean includeDucks() {
        return GeologicExpansionFabric.CONFIG.toggleableFeatures.includeDucks;
    }

    public static boolean includePrismaticCaldera() {
        return GeologicExpansionFabric.CONFIG.toggleableFeatures.includePrismaticCaldera;
    }

    // ========== ENTITY BEHAVIOR ======================================================================================
    public static boolean canDucksFish() {
        return GeologicExpansionFabric.CONFIG.entityBehavior.canDucksFish;
    }

    // ========== FABRIC CONFIGURATION =================================================================================
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public ToggleableFeatures toggleableFeatures = new ToggleableFeatures();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public EntityBehavior entityBehavior = new EntityBehavior();

    public static class ToggleableFeatures {
        @ConfigEntry.Gui.Tooltip
        public boolean includeGeysers = true;

        @ConfigEntry.Gui.Tooltip
        public boolean includeLimestone = true;

        @ConfigEntry.Gui.Tooltip
        public boolean includeOvergrowth = true;

        @ConfigEntry.Gui.Tooltip
        public boolean includeDucks = true;

        @ConfigEntry.Gui.Tooltip
        public boolean includePrismaticCaldera = true;
    }

    public static class EntityBehavior {
        @ConfigEntry.Gui.Tooltip
        public boolean canDucksFish = true;
    }
}