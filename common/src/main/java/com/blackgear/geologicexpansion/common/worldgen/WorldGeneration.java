package com.blackgear.geologicexpansion.common.worldgen;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.common.worldgen.placements.CalderaPlacements;
import com.blackgear.geologicexpansion.common.worldgen.placements.SurfacePlacements;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WorldGeneration {
    public static void bootstrap() {
        BiomeManager.add((writer, context) -> {
            if (context.is(BiomeTags.IS_OVERWORLD)) {
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, SurfacePlacements.OVERGROWTH_PATCH);
            }

            if (context.is(GEBiomes.PRISMATIC_CALDERA)) {
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_LAKE);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_YELLOW);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_ORANGE);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_RED);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_BROWN);
                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.PRISMATIC_BORDER_PURPLE);

                writer.feature(GenerationStep.Decoration.RAW_GENERATION, CalderaPlacements.GEYSER_PATCH);

                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_TAIGA);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA_2);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
                writer.feature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
            }
        });
    }
}