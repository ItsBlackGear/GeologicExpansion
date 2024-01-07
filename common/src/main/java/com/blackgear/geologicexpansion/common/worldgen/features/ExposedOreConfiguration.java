package com.blackgear.geologicexpansion.common.worldgen.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class ExposedOreConfiguration implements FeatureConfiguration {
    public static final Codec<ExposedOreConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
                Codec.list(ExposedOreConfiguration.TargetBlockState.CODEC).fieldOf("targets").forGetter(config -> config.targetStates),
                Codec.intRange(0, 64).fieldOf("size").forGetter(config -> config.size)
        ).apply(instance, ExposedOreConfiguration::new);
    });
    public final List<ExposedOreConfiguration.TargetBlockState> targetStates;
    public final int size;

    public ExposedOreConfiguration(List<ExposedOreConfiguration.TargetBlockState> targetBlock, int size) {
        this.targetStates = targetBlock;
        this.size = size;
    }

    public ExposedOreConfiguration(RuleTest target, BlockStateProvider state, int size) {
        this(ImmutableList.of(new ExposedOreConfiguration.TargetBlockState(target, state)), size);
    }

    public ExposedOreConfiguration(RuleTest target, BlockState state, int size) {
        this(ImmutableList.of(new ExposedOreConfiguration.TargetBlockState(target, BlockStateProvider.simple(state))), size);
    }

    public static ExposedOreConfiguration.TargetBlockState target(RuleTest target, BlockStateProvider state) {
        return new ExposedOreConfiguration.TargetBlockState(target, state);
    }

    public static ExposedOreConfiguration.TargetBlockState target(RuleTest target, BlockState state) {
        return new ExposedOreConfiguration.TargetBlockState(target, BlockStateProvider.simple(state));
    }

    public static class TargetBlockState {
        public static final Codec<ExposedOreConfiguration.TargetBlockState> CODEC = RecordCodecBuilder.create(instance -> {
            return instance.group(
                    RuleTest.CODEC.fieldOf("target").forGetter(targetState -> targetState.target),
                    BlockStateProvider.CODEC.fieldOf("state").forGetter(targetState -> targetState.state)
            ).apply(instance, ExposedOreConfiguration.TargetBlockState::new);
        });
        public final RuleTest target;
        public final BlockStateProvider state;

        TargetBlockState(RuleTest target, BlockStateProvider state) {
            this.target = target;
            this.state = state;
        }
    }
}