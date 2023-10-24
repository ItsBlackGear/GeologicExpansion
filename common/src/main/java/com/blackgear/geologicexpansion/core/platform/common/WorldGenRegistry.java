package com.blackgear.geologicexpansion.core.platform.common;

import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class WorldGenRegistry {
    protected final String modId;

    private WorldGenRegistry(String modId) {
        this.modId = modId;
    }

    public static WorldGenRegistry of(String modId) {
        return new WorldGenRegistry(modId);
    }

    public <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> create(String key, F feature, FC configuration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, this.modId + ":" + key, new ConfiguredFeature<>(feature, configuration));
    }

    public Holder<PlacedFeature> create(String key, Holder<? extends ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placements) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.PLACED_FEATURE, this.modId + ":" + key, new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placements)));
    }

    public Holder<PlacedFeature> create(String key, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... placements) {
        return create(key, feature, List.of(placements));
    }

    public void register() {
    }
}