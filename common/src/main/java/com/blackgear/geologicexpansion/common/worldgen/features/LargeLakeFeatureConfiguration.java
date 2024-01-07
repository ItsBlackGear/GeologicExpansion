package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.ArrayList;
import java.util.List;

public class LargeLakeFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<LargeLakeFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            Codec.INT.fieldOf("min_radius").forGetter(config -> config.minRadius),
            Codec.INT.fieldOf("max_radius").forGetter(config -> config.maxRadius),
            Codec.INT.fieldOf("min_depth").forGetter(config -> config.minDepth),
            Codec.INT.fieldOf("max_depth").forGetter(config -> config.maxDepth),
            BlockStateProvider.CODEC.fieldOf("lake_floor_state").forGetter(config -> config.lakeFloorState),
            BlockStateProvider.CODEC.fieldOf("border_state").forGetter(config -> config.borderState),
            PlacedFeature.LIST_CODEC.fieldOf("lake_surface_features").forGetter(config -> config.lakeSurfaceFeatures),
            PlacedFeature.LIST_CODEC.fieldOf("lake_edge_features").forGetter(config -> config.lakeEdgeFeatures),
            PlacementModifier.CODEC.listOf().fieldOf("modifiers").forGetter(config -> config.modifiers)
        ).apply(instance, LargeLakeFeatureConfiguration::new);
    });

    public final int minRadius;
    public final int maxRadius;
    public final int minDepth;
    public final int maxDepth;
    public final BlockStateProvider lakeFloorState;
    public final BlockStateProvider borderState;
    public final HolderSet<PlacedFeature> lakeSurfaceFeatures;
    public final HolderSet<PlacedFeature> lakeEdgeFeatures;
    public final List<PlacementModifier> modifiers;

    public LargeLakeFeatureConfiguration(int minRadius, int maxRadius, int minDepth, int maxDepth, BlockStateProvider lakeFloorState, BlockStateProvider borderState, HolderSet<PlacedFeature> lakeSurfaceFeatures, HolderSet<PlacedFeature> lakeEdgeFeatures, List<PlacementModifier> modifiers) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.minDepth = minDepth;
        this.maxDepth = maxDepth;
        this.lakeFloorState = lakeFloorState;
        this.borderState = borderState;
        this.lakeSurfaceFeatures = lakeSurfaceFeatures;
        this.lakeEdgeFeatures = lakeEdgeFeatures;
        this.modifiers = modifiers;
    }

    public int getRandomRadius(RandomSource random) {
        return random.nextInt(Math.max(1, this.maxRadius - this.minRadius)) + this.minRadius;
    }

    public int getRandomDepth(RandomSource random) {
        return random.nextInt(Math.max(1, this.maxDepth - this.minDepth)) + this.minRadius;
    }

    public static Holder<PlacedFeature> createDripLeavesPlacedFeature(int rarity, PlacementModifier... modifiers) {
        ArrayList<PlacementModifier> placementModifiers = new ArrayList<>();
        placementModifiers.add(RarityFilter.onAverageOnceEvery(rarity));
        placementModifiers.addAll(List.of(modifiers));
        placementModifiers.addAll(List.of(BlockPredicateFilter.forPredicate(BlockPredicate.anyOf(BlockPredicate.wouldSurvive(Blocks.BIG_DRIPLEAF.defaultBlockState(), BlockPos.ZERO), BlockPredicate.wouldSurvive(Blocks.SMALL_DRIPLEAF.defaultBlockState(), BlockPos.ZERO), BlockPredicate.wouldSurvive(Blocks.SMALL_DRIPLEAF.defaultBlockState(), BlockPos.ZERO)))));
        return Holder.direct(new PlacedFeature(Holder.hackyErase(CaveFeatures.DRIPLEAF), List.copyOf(placementModifiers)));
    }
}