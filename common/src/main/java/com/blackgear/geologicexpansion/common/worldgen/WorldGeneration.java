package com.blackgear.geologicexpansion.common.worldgen;

import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
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
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, SurfacePlacements.BOULDER);

                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, UndergroundPlacements.MOSSY_PATCH_CEILING);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, UndergroundPlacements.MOSSY_PATCH_FLOOR);
            }

            if (context.is(GEBiomes.SAVANNA_STRATA)) {
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, SavannaPlacements.SAVANNA_SURFACE);
            }

            if (context.is(GEBiomes.PRISMATIC_CALDERA)) {
                BiomeModdedFeatures.addPrismaticLakeDecorations(writer);
                BiomeModdedFeatures.addPrismaticStoneBorder(writer);

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
            }

            if (context.is(Biomes.PLAINS) || context.is(Biomes.MEADOW) || context.is(Biomes.SUNFLOWER_PLAINS)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_OAK_TREE_PLAINS);
            }

            if (context.is(BiomeTags.IS_TAIGA) || context.is(GEBiomes.PRISMATIC_CALDERA)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_SPRUCE_TREE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.SPRUCE_BUSH);
            }

            if (context.is(BiomeTags.IS_SAVANNA) || context.is(GEBiomes.SAVANNA_STRATA)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.FALLEN_ACACIA_TREE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, TreeVegetationPlacements.ACACIA_BUSH);
            }

            if (context.is(GEBiomes.MAPLE_FOREST)) {
                BiomeModdedFeatures.addMapleForestVegetation(writer);
            }
        });
    }
}