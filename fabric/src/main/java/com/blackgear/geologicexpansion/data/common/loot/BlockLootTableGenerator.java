package com.blackgear.geologicexpansion.data.common.loot;

import com.blackgear.geologicexpansion.common.block.RockBlock;
import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import com.blackgear.geologicexpansion.data.resources.GEBlockFamilies;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;

public class BlockLootTableGenerator extends FabricBlockLootTableProvider {
    public BlockLootTableGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        GEBlockFamilies.getStoneFamilies().forEach(this::familyDropsSelf);

        this.add(GEBlocks.OVERGROWTH.get(), BlockLoot::createShearsOnlyDrop);
        this.add(GEBlocks.GEYSER.get(), BlockLoot::createDoorTable);

        this.dropSelf(GEBlocks.GEOLOGIST_TABLE.get());
        this.dropSelf(GEBlocks.FIERY_HIBISCUS.get());
        this.add(GEBlocks.ROCK.get(), block -> {
            return LootTable.lootTable()
                    .withPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(
                                            applyExplosionDecay(
                                                    GEBlocks.ROCK.get(),
                                                    LootItem.lootTableItem(block)
                                                            .apply(
                                                                    List.of(2, 3, 4),
                                                                    integer -> {
                                                                        return SetItemCountFunction.setCount(
                                                                                ConstantValue.exactly(integer)
                                                                        ).when(
                                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                                        .setProperties(
                                                                                                StatePropertiesPredicate.Builder.properties()
                                                                                                        .hasProperty(RockBlock.ROCKS, integer)
                                                                                        )
                                                                        );
                                                                    }
                                                            )
                                            )
                                    )
                    );
        });
    }

    private void familyDropsSelf(BlockFamily family) {
        this.dropSelf(family.getBaseBlock());
        family.getVariants().forEach((variant, block) -> this.dropSelf(block));
    }
}