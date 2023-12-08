package com.blackgear.geologicexpansion.core.platform.common;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.BiConsumer;

public abstract class BiomeWriter {
    public void add(BiConsumer<BiomeWriter, BiomeContext> modifier) {
        modifier.accept(this, this.context());
    }

    public abstract ResourceLocation name();

    public abstract BiomeContext context();

    public abstract void feature(GenerationStep.Decoration decoration, Holder<PlacedFeature> feature);

    public void feature(GenerationStep.Decoration decoration, Holder<PlacedFeature> feature, boolean featureFlag) {
        if (featureFlag) this.feature(decoration, feature);
    }

    public abstract void spawn(MobCategory category, MobSpawnSettings.SpawnerData data);

    public void spawn(MobCategory category, MobSpawnSettings.SpawnerData data, boolean featureFlag) {
        if (featureFlag) this.spawn(category, data);
    }

    public abstract void carver(GenerationStep.Carving carving, Holder<? extends ConfiguredWorldCarver<?>> carver);

    public void carver(GenerationStep.Carving carving, Holder<? extends ConfiguredWorldCarver<?>> carver, boolean featureFlag) {
        if (featureFlag) this.carver(carving, carver);
    }
}