package com.blackgear.geologicexpansion.common.worldgen.placements;

import com.blackgear.geologicexpansion.common.registries.GEBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class TravertineFeatures {
    public static void bootstrap() {}

    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> LIMESTONE_PATCH = FeatureUtils.register(
            "limestone_patch",
            Feature.VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    BlockStateProvider.simple(GEBlocks.LIMESTONE.get()),
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    CaveSurface.FLOOR,
                    ConstantInt.of(3),
                    0.8F,
                    2,
                    0.0F,
                    UniformInt.of(4, 7),
                    0.7F
            )
    );

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_LIMESTONE = FeatureUtils.register(
            "ore_limestone",
            Feature.ORE,
            new OreConfiguration(
                    new TagMatchTest(BlockTags.LUSH_GROUND_REPLACEABLE),
                    GEBlocks.LIMESTONE.get().defaultBlockState(),
                    64
            )
    );

    public static final Holder<ConfiguredFeature<VegetationPatchConfiguration, ?>> TRAVERTINE_POOL = FeatureUtils.register(
            "travertine_pool",
            Feature.WATERLOGGED_VEGETATION_PATCH,
            new VegetationPatchConfiguration(
                    // REPLACEABLE
                    BlockTags.LUSH_GROUND_REPLACEABLE,
                    // GROUND STATE
                    BlockStateProvider.simple(GEBlocks.LIMESTONE.get()),
                    // VEGETATION FEATURE
                    PlacementUtils.inlinePlaced(CaveFeatures.DRIPLEAF),
                    // SURFACE
                    CaveSurface.FLOOR,
                    // DEPTH
                    ConstantInt.of(3),
                    // EXTRA BOTTOM BLOCK CHANCE
                    0.8F,
                    // VERTICAL RANGE
                    5,
                    // VEGETATION CHANCE
                    0.0F,
                    // XZ RADIUS
                    UniformInt.of(4, 7),
                    // EXTRA EDGE COLUMN CHANCE
                    0.7F
            )
    );

    public static final Holder<ConfiguredFeature<DiskConfiguration, ?>> DISK_LIMESTONE = FeatureUtils.register(
            "disk_limestone",
            Feature.DISK,
            new DiskConfiguration(
//                    new RuleBasedBlockStateProvider(
//                            BlockStateProvider.simple(GEBlocks.LIMESTONE.get())
//                            ,
//                            List.of(
//                                    new RuleBasedBlockStateProvider.Rule(BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), Blocks.AIR), BlockStateProvider.simple(Blocks.SANDSTONE))
//                            )
//                    ),
                    RuleBasedBlockStateProvider.simple(GEBlocks.LIMESTONE.get()),
                    BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.GRASS_BLOCK)),
                    UniformInt.of(4, 7),
                    3
            )
    );

    public static final Holder<ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> TRAVERTINE_FLOOR = FeatureUtils.register(
            "travertine_floor",
            Feature.RANDOM_BOOLEAN_SELECTOR,
            new RandomBooleanFeatureConfiguration(
//                    PlacementUtils.inlinePlaced(LIMESTONE_PATCH),
                    PlacementUtils.inlinePlaced(DISK_LIMESTONE),
                    PlacementUtils.inlinePlaced(TRAVERTINE_POOL)
            )
    );
}