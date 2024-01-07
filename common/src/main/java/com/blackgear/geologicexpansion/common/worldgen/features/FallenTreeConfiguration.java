package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FallenTreeConfiguration(BlockStateProvider trunkState, IntProvider trunkLength, IntProvider distanceFromStump, float vegetationChance, BlockStateProvider vegetationState) implements FeatureConfiguration {
    public static final Codec<FallenTreeConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                BlockStateProvider.CODEC.fieldOf("trunk_state").forGetter(config -> config.trunkState),
                IntProvider.CODEC.fieldOf("trunk_length").forGetter(config -> config.trunkLength),
                IntProvider.CODEC.fieldOf("distance_from_stump").forGetter(config -> config.distanceFromStump),
                Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(config -> config.vegetationChance),
                BlockStateProvider.CODEC.fieldOf("vegetationState").forGetter(config -> config.vegetationState)
        ).apply(instance, FallenTreeConfiguration::new);
    });
}