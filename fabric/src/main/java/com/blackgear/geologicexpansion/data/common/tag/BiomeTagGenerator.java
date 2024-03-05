package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEBiomes;
import com.blackgear.geologicexpansion.core.data.GEBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeTagGenerator extends FabricTagProvider.DynamicRegistryTagProvider<Biome> {
    public BiomeTagGenerator(FabricDataGenerator generator) {
        super(generator, Registry.BIOME_REGISTRY);
    }

    @Override
    protected void generateTags() {
        this.tag(BiomeTags.IS_OVERWORLD)
            .add(GEBiomes.PRISMATIC_CALDERA)
            .add(GEBiomes.MAPLE_FOREST)
            .add(GEBiomes.SAVANNA_STRATA);

        // ========== ENTITY SPAWNING ==================================================================================

        this.tag(GEBiomeTags.CAN_DUCKS_SPAWN)
                .add(Biomes.SWAMP)
                .add(Biomes.MANGROVE_SWAMP)
                .add(Biomes.MEADOW)
                .add(Biomes.RIVER)
                .add(Biomes.TAIGA)
                .add(Biomes.OLD_GROWTH_PINE_TAIGA)
                .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
                .add(GEBiomes.PRISMATIC_CALDERA)
                .add(GEBiomes.MAPLE_FOREST);
    }
}