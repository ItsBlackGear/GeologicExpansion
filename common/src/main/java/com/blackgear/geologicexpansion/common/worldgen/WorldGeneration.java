package com.blackgear.geologicexpansion.common.worldgen;

import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.MapleForestPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SavannaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfacePlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.TreeVegetationPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundPlacements;
import com.blackgear.geologicexpansion.core.config.ConfigEntries;
import com.blackgear.geologicexpansion.core.data.GEBiomeTags;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WorldGeneration {
    public static void bootstrap() {
        BiomeManager.add((writer, context) -> {
            if (context.is(BiomeTags.IS_OVERWORLD)) {
                writer.feature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacements.ORE_LIMESTONE, ConfigEntries.includeLimestone());
//                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.OVERGROWTH_PATCH, ConfigEntries.includeOvergrowth());
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.ROCK_PATCH);

                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, UndergroundPlacements.MOSSY_PATCH_CEILING);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, UndergroundPlacements.MOSSY_PATCH_FLOOR);
            }

            if (context.is(Biomes.SAVANNA) || context.is(Biomes.SAVANNA_PLATEAU) || context.is(Biomes.WINDSWEPT_SAVANNA)) {
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, SavannaPlacements.SAVANNA_SURFACE);
            }

            if (context.is(GEBiomes.PRISMATIC_CALDERA)) {
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_LAKE);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_YELLOW);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_ORANGE);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_RED);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_BROWN);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_PURPLE);

                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, CalderaPlacements.GEYSER_PATCH, ConfigEntries.includeGeysers());
            }

            if (context.is(GEBiomeTags.CAN_DUCKS_SPAWN)) {
                writer.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(GEEntities.DUCK.get(), 30, 2, 4), ConfigEntries.includeDucks());
            }

            // ========== FALLEN TREES =========================================================================================

            if (context.is(Biomes.FOREST) || context.is(Biomes.BIRCH_FOREST) || context.is(Biomes.FLOWER_FOREST) || context.is(Biomes.OLD_GROWTH_BIRCH_FOREST)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_BIRCH_TREE);
            }


            if (context.is(Biomes.FOREST) || context.is(Biomes.FLOWER_FOREST) || context.is(Biomes.DARK_FOREST) || context.is(Biomes.WINDSWEPT_FOREST)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_OAK_TREE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.RED_MAPLE_TREE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.BROWN_MAPLE_TREE);
            }

            if (context.is(Biomes.PLAINS) || context.is(Biomes.MEADOW) || context.is(Biomes.SUNFLOWER_PLAINS)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_OAK_TREE_PLAINS);
            }

            if (context.is(BiomeTags.IS_TAIGA) || context.is(GEBiomes.PRISMATIC_CALDERA)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_SPRUCE_TREE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.SPRUCE_BUSH);
            }

            if (context.is(BiomeTags.IS_SAVANNA)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_ACACIA_TREE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.ACACIA_BUSH);
            }

            if (context.is(GEBiomes.MAPLE_FOREST)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.TREES_MAPLE_FOREST);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.RED_MAPLE_BUSH);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.BROWN_MAPLE_BUSH);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_MAPLE_TREE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, MapleForestPlacements.MAPLE_LEAF_CARPETS);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, MapleForestPlacements.MAPLE_PATCH_PUMPKIN);
                writer.feature(GenerationStep.Decoration.UNDERGROUND_ORES, SurfacePlacements.DISK_PACKED_MUD);
                writer.feature(GenerationStep.Decoration.UNDERGROUND_ORES, SurfacePlacements.DISK_MUD);
            }
        });
    }
}