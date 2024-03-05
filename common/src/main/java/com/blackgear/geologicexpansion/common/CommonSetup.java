package com.blackgear.geologicexpansion.common;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.blackgear.geologicexpansion.common.entity.duck.ODuck;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.GEItems;
import com.blackgear.geologicexpansion.common.registries.entities.GEEntityDataSerializers;
import com.blackgear.geologicexpansion.common.registries.entities.GEProfessions;
import com.blackgear.geologicexpansion.common.worldgen.TerrablenderCompat;
import com.blackgear.geologicexpansion.common.worldgen.WorldGeneration;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.MapleForestFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.MapleForestPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SavannaFeatures;
import com.blackgear.geologicexpansion.common.worldgen.placements.SavannaPlacements;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;

public class CommonSetup {
    public static void onInstance() {
        // ========== ENTITY ATTRIBUTE REGISTRY ========================================================================
        EntityRegistry.attributes(GEEntities.DUCK, Duck::createAttributes);
        EntityRegistry.attributes(GEEntities.O_DUCK, ODuck::createAttributes);
        EntityRegistry.attributes(GEEntities.GRIZZLY, Grizzly::createAttributes);
    }

    public static void postInstance() {
        // ========== FEATURE REGISTRY =================================================================================
        CalderaFeatures.FEATURES.register();
        CalderaPlacements.FEATURES.register();
        MapleForestFeatures.FEATURES.register();
        MapleForestPlacements.FEATURES.register();
        SavannaFeatures.FEATURES.register();
        SavannaPlacements.FEATURES.register();
        SurfaceFeatures.FEATURES.register();
        SurfacePlacements.FEATURES.register();
        UndergroundFeatures.FEATURES.register();
        UndergroundPlacements.FEATURES.register();

        // ========== INTEGRATION REGISTRY =============================================================================
        IntegrationRegistry.compostable(GEBlocks.OVERGROWTH.get(), 0.3F);
        IntegrationRegistry.strippable(GEBlocks.MAPLE_LOG.get(), GEBlocks.STRIPPED_MAPLE_LOG.get());
        IntegrationRegistry.strippable(GEBlocks.MAPLE_WOOD.get(), GEBlocks.STRIPPED_MAPLE_WOOD.get());

        // ========== VILLAGER TRADE REGISTRY ==========================================================================
        TradeRegistry.registerTrade(
                GEProfessions.GEOLOGIST.get(),
                TradeRegistry.Level.NOVICE,
                new VillagerTrades.EmeraldForItems(Items.AMETHYST_SHARD, 16, 16, 2),
                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2F),
                new VillagerTrades.EmeraldForItems(Items.COAL, 10, 16, 2),
                new VillagerTrades.ItemsForEmeralds(Items.TORCH, 1, 16, 8, 4)
        );

        TradeRegistry.registerTrade(
                GEProfessions.GEOLOGIST.get(),
                TradeRegistry.Level.APPRENTICE,
                new VillagerTrades.EmeraldForItems(Items.RAW_IRON, 4, 12, 10),
                new VillagerTrades.EmeraldForItems(Items.RAW_GOLD, 2, 12, 10),
                new VillagerTrades.EmeraldForItems(Items.RAW_COPPER, 6, 12, 10),
                new VillagerTrades.EmeraldForItems(Items.STONE, 20, 16, 10)
        );

        TradeRegistry.registerTrade(
                GEProfessions.GEOLOGIST.get(),
                TradeRegistry.Level.JOURNEYMAN,
                new VillagerTrades.ItemsForEmeralds(new ItemStack(GEBlocks.PRISMATIC_STONE.get()), 4, 16, 12, 10),
                new VillagerTrades.EmeraldForItems(Items.DIAMOND, 1, 12, 20),
                new VillagerTrades.ItemsForEmeralds(Items.LANTERN, 1, 1, 5),
                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.BONE_BLOCK), 4, 8, 12, 15)
        );

        TradeRegistry.registerTrade(
                GEProfessions.GEOLOGIST.get(),
                TradeRegistry.Level.EXPERT,
                new VillagerTrades.ItemsForEmeralds(Items.LAPIS_LAZULI, 1, 1, 5)
        );

        TradeRegistry.registerTrade(
                GEProfessions.GEOLOGIST.get(),
                TradeRegistry.Level.MASTER,
                new VillagerTrades.ItemsForEmeralds(new ItemStack(GEItems.ROCK_HAMMER.get()), 20, 1, 8, 16),
                new VillagerTrades.ItemsForEmeralds(new ItemStack(Items.DIAMOND_PICKAXE), 13, 1, 3, 30, 0.2F)
        );

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
        SpawnPlacementsAccessor.register(GEEntities.O_DUCK.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ODuck::checkDuckSpawnRules
        );
        SpawnPlacementsAccessor.register(GEEntities.GRIZZLY.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Grizzly::checkGrizzlyBearSpawnRules);
    }
}