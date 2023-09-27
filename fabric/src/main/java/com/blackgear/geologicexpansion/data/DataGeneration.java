package com.blackgear.geologicexpansion.data;

import com.blackgear.geologicexpansion.data.client.ModelGenerator;
import com.blackgear.geologicexpansion.data.common.BlockLootTableGenerator;
import com.blackgear.geologicexpansion.data.common.RecipeGenerator;
import com.blackgear.geologicexpansion.data.common.tag.BlockTagGenerator;
import com.blackgear.geologicexpansion.data.common.tag.ItemTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(ModelGenerator::new);

        FabricTagProvider.BlockTagProvider blockTags = generator.addProvider(BlockTagGenerator::new);
        generator.addProvider(gen -> new ItemTagGenerator(gen, blockTags));

        generator.addProvider(BlockLootTableGenerator::new);
        generator.addProvider(RecipeGenerator::new);
    }
}