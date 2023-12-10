package com.blackgear.geologicexpansion.common;

import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.worldgen.TerrablenderCompat;
import com.blackgear.geologicexpansion.common.worldgen.WorldGeneration;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfaceFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfacePlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundPlacements;
import com.blackgear.geologicexpansion.core.mixin.access.SpawnPlacementsAccessor;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import com.blackgear.geologicexpansion.core.platform.common.EntityRegistry;
import com.blackgear.geologicexpansion.core.platform.common.IntegrationRegistry;
import com.blackgear.geologicexpansion.core.platform.common.TradeRegistry;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.levelgen.Heightmap;

public class CommonSetup {
    public static void onInstance() {
        // ========== ENTITY ATTRIBUTE REGISTRY ========================================================================
        EntityRegistry.attributes(GEEntities.DUCK, Duck::createAttributes);
    }

    public static void postInstance() {
        // ========== FEATURE REGISTRY =================================================================================
        CalderaFeatures.FEATURES.register();
        CalderaPlacements.FEATURES.register();
        SurfaceFeatures.FEATURES.register();
        SurfacePlacements.FEATURES.register();
        UndergroundFeatures.FEATURES.register();
        UndergroundPlacements.FEATURES.register();

        // ========== INTEGRATION REGISTRY =============================================================================
        IntegrationRegistry.compostable(GEBlocks.OVERGROWTH.get(), 0.3F);

        // ========== VILLAGER TRADE REGISTRY ==========================================================================
        TradeRegistry.registerTrade(
                VillagerProfession.MASON,
                TradeRegistry.Level.JOURNEYMAN,
                new VillagerTrades.EmeraldForItems(GEBlocks.LIMESTONE.get(), 16, 16, 20),
                new VillagerTrades.ItemsForEmeralds(GEBlocks.PRISMATIC_STONE.get(), 1, 4, 16, 10)
        );

        TradeRegistry.registerWanderingTrade(false, new VillagerTrades.ItemsForEmeralds(GEBlocks.OVERGROWTH.get(), 1, 2, 5, 1));

        // ========== WORLD GEN INITIALIZATION =========================================================================
        BiomeManager.bootstrap();
        WorldGeneration.bootstrap();
        TerrablenderCompat.registerSurfaceRules();

        // ========== ENTITY SPAWN REGISTRY ============================================================================
        SpawnPlacementsAccessor.register(GEEntities.DUCK.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Duck::checkDuckSpawnRules
        );
    }
}