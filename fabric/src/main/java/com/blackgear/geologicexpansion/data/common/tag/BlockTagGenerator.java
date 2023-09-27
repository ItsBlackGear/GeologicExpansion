package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.BlockTags;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateTags() {
        GEBlockFamilies.getAllFamilies().forEach(this::populateTags);

        GEBlockFamilies.getStoneFamilies().forEach(family -> {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(family.getBaseBlock());
            family.getVariants().forEach((variant, block) -> this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block));
        });
    }

    private void populateTags(BlockFamily family) {
        if (family.getVariants().containsKey(BlockFamily.Variant.WALL)) {
            this.tag(BlockTags.WALLS).add(family.get(BlockFamily.Variant.WALL));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
            this.tag(BlockTags.SLABS).add(family.get(BlockFamily.Variant.SLAB));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.STAIRS)) {
            this.tag(BlockTags.STAIRS).add(family.get(BlockFamily.Variant.STAIRS));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.FENCE)) {
            this.tag(BlockTags.FENCES).add(family.get(BlockFamily.Variant.FENCE));
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.FENCE_GATE)) {
            this.tag(BlockTags.FENCE_GATES).add(family.get(BlockFamily.Variant.FENCE_GATE));
        }
    }
}