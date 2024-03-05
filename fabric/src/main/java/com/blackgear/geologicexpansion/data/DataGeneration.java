package com.blackgear.geologicexpansion.data;

import com.blackgear.geologicexpansion.data.client.ModelGenerator;
import com.blackgear.geologicexpansion.data.common.RecipeGenerator;
import com.blackgear.geologicexpansion.data.common.loot.BlockLootGenerator;
import com.blackgear.geologicexpansion.data.common.loot.EntityLootGenerator;
import com.blackgear.geologicexpansion.data.common.tag.BiomeTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.BlockTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.EntityTypeTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.ItemTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.PoiTypeTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        // ========== CLIENT ===========================================================================================

        // ---------- MODELS ----------
        generator.addProvider(ModelGenerator::new);

        // ========== SERVER ===========================================================================================

        // ---------- TAGS ----------
        var tags = generator.addProvider(BlockTagGenerator::new);
        generator.addProvider(gen -> new ItemTagGenerator(gen, tags));
        generator.addProvider(BiomeTagGenerator::new);
        generator.addProvider(EntityTypeTagGenerator::new);
        generator.addProvider(PoiTypeTagGenerator::new);

        // ---------- LOOT TABLES ----------
        generator.addProvider(BlockLootGenerator::new);
        generator.addProvider(EntityLootGenerator::new);

        // ---------- RECIPES ----------
        generator.addProvider(RecipeGenerator::new);
    }
}