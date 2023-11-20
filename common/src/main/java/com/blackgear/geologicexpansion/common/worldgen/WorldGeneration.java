package com.blackgear.geologicexpansion.common.worldgen;

import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfacePlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.UndergroundPlacements;
import com.blackgear.geologicexpansion.core.data.GEBiomeTags;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WorldGeneration {
    public static void bootstrap() {
        BiomeManager.add((writer, context) -> {
            if (context.is(BiomeTags.IS_OVERWORLD)) {
                writer.feature(GenerationStep.Decoration.UNDERGROUND_ORES, UndergroundPlacements.ORE_LIMESTONE);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.OVERGROWTH_PATCH);
            }

            if (context.is(GEBiomes.PRISMATIC_CALDERA)) {
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_LAKE);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_YELLOW);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_ORANGE);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_RED);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_BROWN);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_PURPLE);

                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, CalderaPlacements.GEYSER_PATCH);
            }

            if (context.is(GEBiomeTags.CAN_DUCKS_SPAWN)) {
                writer.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(GEEntities.DUCK.get(), 60, 4, 6));
            }
        });
    }
}