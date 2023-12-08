package com.blackgear.geologicexpansion.common.worldgen;

import com.blackgear.geologicexpansion.common.worldgen.surface.GESurfaceRules;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.Environment;
import net.minecraft.world.level.levelgen.SurfaceRules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TerrablenderCompat {
    public static void registerSurfaceRules() {
        if (!Environment.isModLoaded("terrablender")) return;

        try {
            Class<?> surfaceRuleManager = Class.forName("terrablender.api.SurfaceRuleManager");
            Class<?> ruleCategory = Class.forName("terrablender.api.SurfaceRuleManager$RuleCategory");

            Method addSurfaceRules = surfaceRuleManager.getMethod("addSurfaceRules", ruleCategory, String.class, SurfaceRules.RuleSource.class);

            for (Object constant : ruleCategory.getEnumConstants()) {
                Method name = constant.getClass().getMethod("name");

                if (name.invoke(constant).equals("OVERWORLD")) {
                    addSurfaceRules.invoke(surfaceRuleManager, constant, GeologicExpansion.MOD_ID, GESurfaceRules.makeRules());
                }
            }

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
            throw new RuntimeException(exception);
        }
    }
}