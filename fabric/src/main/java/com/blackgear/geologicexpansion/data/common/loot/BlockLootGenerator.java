package com.blackgear.geologicexpansion.data.common.loot;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLoot;

public class BlockLootGenerator extends FabricBlockLootTableProvider {
    public BlockLootGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateBlockLootTables() {
        GEBlockFamilies.getStoneFamilies()
            .forEach(this::familyDropsSelf);

        this.add(GEBlocks.OVERGROWTH.get(), BlockLoot::createShearsOnlyDrop);
        this.add(GEBlocks.GEYSER.get(), BlockLoot::createDoorTable);

        this.dropSelf(GEBlocks.GEOLOGIST_TABLE.get());
        this.dropSelf(GEBlocks.FIERY_HIBISCUS.get());
    }

    private void familyDropsSelf(BlockFamily family) {
        this.dropSelf(family.getBaseBlock());
        family.getVariants().forEach((variant, block) -> this.dropSelf(block));
    }
}