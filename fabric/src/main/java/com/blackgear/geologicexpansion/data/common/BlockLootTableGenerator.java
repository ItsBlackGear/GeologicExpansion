package com.blackgear.geologicexpansion.data.common;

import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.BlockFamily;

public class BlockLootTableGenerator extends FabricBlockLootTableProvider {
    public BlockLootTableGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        GEBlockFamilies.getStoneFamilies().forEach(this::familyDropsSelf);
    }

    private void familyDropsSelf(BlockFamily family) {
        this.dropSelf(family.getBaseBlock());
        family.getVariants().forEach((variant, block) -> this.dropSelf(block));
    }
}