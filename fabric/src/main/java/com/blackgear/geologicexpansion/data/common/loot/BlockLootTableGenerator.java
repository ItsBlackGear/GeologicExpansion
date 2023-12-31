package com.blackgear.geologicexpansion.data.common.loot;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLoot;

public class BlockLootTableGenerator extends FabricBlockLootTableProvider {
    public BlockLootTableGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        GEBlockFamilies.getStoneFamilies().forEach(this::familyDropsSelf);

        this.add(GEBlocks.OVERGROWTH.get(), BlockLoot::createShearsOnlyDrop);
        this.add(GEBlocks.GEYSER.get(), BlockLoot::createDoorTable);
    }

    private void familyDropsSelf(BlockFamily family) {
        this.dropSelf(family.getBaseBlock());
        family.getVariants().forEach((variant, block) -> this.dropSelf(block));
    }
}