package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record OvergrowthPatchConfiguration(TagKey<Block> replaceable, boolean shouldReplace, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance) implements FeatureConfiguration {
    public static final Codec<OvergrowthPatchConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                TagKey.hashedCodec(Registry.BLOCK_REGISTRY).fieldOf("replaceable").forGetter(OvergrowthPatchConfiguration::replaceable),
                Codec.BOOL.fieldOf("should_replace").forGetter(OvergrowthPatchConfiguration::shouldReplace),
                PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(OvergrowthPatchConfiguration::vegetationFeature),
                CaveSurface.CODEC.fieldOf("surface").forGetter(OvergrowthPatchConfiguration::surface),
                IntProvider.codec(1, 128).fieldOf("depth").forGetter(OvergrowthPatchConfiguration::depth),
                Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(OvergrowthPatchConfiguration::extraBottomBlockChance),
                Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(OvergrowthPatchConfiguration::verticalRange),
                Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(OvergrowthPatchConfiguration::vegetationChance),
                IntProvider.CODEC.fieldOf("xz_radius").forGetter(OvergrowthPatchConfiguration::xzRadius),
                Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(OvergrowthPatchConfiguration::extraEdgeColumnChance)
        ).apply(instance, OvergrowthPatchConfiguration::new);
    });
}