package com.blackgear.geologicexpansion.data.common.tag;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.ItemTags;
import org.jetbrains.annotations.Nullable;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator(FabricDataGenerator generator, @Nullable BlockTagProvider provider) {
        super(generator, provider);
    }

    @Override
    protected void generateTags() {
        GEBlockFamilies.getAllFamilies().forEach(this::populateTags);

        this.tag(ItemTags.STONE_TOOL_MATERIALS).add(GEBlocks.PRISMATIC_STONE.get().asItem());
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(GEBlocks.PRISMATIC_STONE.get().asItem());
    }

    private void populateTags(BlockFamily family) {
        if (family.getVariants().containsKey(BlockFamily.Variant.WALL)) {
            this.tag(ItemTags.WALLS).add(family.get(BlockFamily.Variant.WALL).asItem());
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
            this.tag(ItemTags.SLABS).add(family.get(BlockFamily.Variant.SLAB).asItem());
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.STAIRS)) {
            this.tag(ItemTags.STAIRS).add(family.get(BlockFamily.Variant.STAIRS).asItem());
        }
        if (family.getVariants().containsKey(BlockFamily.Variant.FENCE)) {
            this.tag(ItemTags.FENCES).add(family.get(BlockFamily.Variant.FENCE).asItem());
        }
    }
}