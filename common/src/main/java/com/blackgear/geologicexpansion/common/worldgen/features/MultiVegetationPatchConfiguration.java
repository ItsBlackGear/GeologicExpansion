package com.blackgear.geologicexpansion.common.worldgen.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class MultiVegetationPatchConfiguration implements FeatureConfiguration {
    public static final Codec<MultiVegetationPatchConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                TagKey.hashedCodec(Registry.BLOCK_REGISTRY).fieldOf("replaceable").forGetter(config -> config.replaceable),
                BlockStateProvider.CODEC.fieldOf("ground_state").forGetter(config -> config.groundState),
                PlacedFeature.CODEC.listOf().fieldOf("vegetation_feature").forGetter(config -> config.vegetationFeatures),
                CaveSurface.CODEC.fieldOf("surface").forGetter(config -> config.surface),
                IntProvider.codec(1, 128).fieldOf("depth").forGetter(config -> config.depth),
                Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(config -> config.extraBottomBlockChance),
                Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(config -> config.verticalRange),
                Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(config -> config.vegetationChance),
                IntProvider.CODEC.fieldOf("xz_radius").forGetter(config -> config.xzRadius),
                Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(config -> config.extraEdgeColumnChance),
                Codec.BOOL.fieldOf("only_on_air_exposure").forGetter(config -> config.onlyOnAirExposure)
        ).apply(instance, MultiVegetationPatchConfiguration::new);
    });
    public final TagKey<Block> replaceable;
    public final BlockStateProvider groundState;
    public final List<Holder<PlacedFeature>> vegetationFeatures;
    public final CaveSurface surface;
    public final IntProvider depth;
    public final float extraBottomBlockChance;
    public final int verticalRange;
    public final float vegetationChance;
    public final IntProvider xzRadius;
    public final float extraEdgeColumnChance;
    public final boolean onlyOnAirExposure;

    public MultiVegetationPatchConfiguration(TagKey<Block> replaceable, BlockStateProvider groundState, List<Holder<PlacedFeature>> vegetationFeatures, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance, boolean onlyOnAirExposure) {
        this.replaceable = replaceable;
        this.groundState = groundState;
        this.vegetationFeatures = vegetationFeatures;
        this.surface = surface;
        this.depth = depth;
        this.extraBottomBlockChance = extraBottomBlockChance;
        this.verticalRange = verticalRange;
        this.vegetationChance = vegetationChance;
        this.xzRadius = xzRadius;
        this.extraEdgeColumnChance = extraEdgeColumnChance;
        this.onlyOnAirExposure = onlyOnAirExposure;
    }

    public MultiVegetationPatchConfiguration(TagKey<Block> replaceable, BlockStateProvider groundState, List<Holder<PlacedFeature>> vegetationFeatures, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance) {
        this(replaceable, groundState, vegetationFeatures, surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, xzRadius, extraEdgeColumnChance, false);
    }

    public MultiVegetationPatchConfiguration(TagKey<Block> replaceable, BlockStateProvider groundState, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance, boolean onlyOnAirExposure) {
        this(replaceable, groundState, ImmutableList.of(vegetationFeature), surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, xzRadius, extraEdgeColumnChance, onlyOnAirExposure);
    }

    public MultiVegetationPatchConfiguration(TagKey<Block> replaceable, BlockStateProvider groundState, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance) {
        this(replaceable, groundState, ImmutableList.of(vegetationFeature), surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, xzRadius, extraEdgeColumnChance, false);
    }
}