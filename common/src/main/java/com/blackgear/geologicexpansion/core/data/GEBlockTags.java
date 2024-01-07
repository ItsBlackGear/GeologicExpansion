package com.blackgear.geologicexpansion.core.data;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.TagRegistry;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class GEBlockTags {
    public static final TagRegistry<Block> TAGS = TagRegistry.of(Registry.BLOCK_REGISTRY, GeologicExpansion.MOD_ID);

    public static final TagKey<Block> OVERGROWTH_GROWABLE_BLOCKS = TAGS.create("overgrowth_growable_blocks");
    public static final TagKey<Block> CAN_FALLEN_TREES_GENERATE_ON = TAGS.create("can_fallen_trees_generate_on");
    public static final TagKey<Block> SAVANNA_TERRACOTTA = TAGS.create("savanna_terracotta");
    public static final TagKey<Block> MAPLE_LOGS = TAGS.create("maple_logs");
}