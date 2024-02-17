package com.blackgear.geologicexpansion.common.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Arrays;
import java.util.List;

public record StructurePieceConfiguration(
    List<ResourceLocation> templates,
    boolean mirror,
    boolean rotate,
    boolean spawnEntities,
    boolean requireGround,
    Holder<StructureProcessorList> processors
) implements FeatureConfiguration {
    public static final Codec<StructurePieceConfiguration> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(
            ResourceLocation.CODEC.listOf().fieldOf("templates").forGetter(config -> config.templates),
            Codec.BOOL.fieldOf("mirror").forGetter(config -> config.mirror),
            Codec.BOOL.fieldOf("rotate").forGetter(config -> config.rotate),
            Codec.BOOL.fieldOf("spawn_entities").forGetter(config -> config.spawnEntities),
            Codec.BOOL.fieldOf("require_ground").forGetter(config -> config.requireGround),
            StructureProcessorType.LIST_CODEC.fieldOf("processors").forGetter(config -> config.processors)
        ).apply(instance, StructurePieceConfiguration::new);
    });

    public static class Builder {
        private final List<ResourceLocation> templates;
        private boolean mirror, rotate, spawnEntities, requireGround = true;
        private final List<StructureProcessor> processors = new ObjectArrayList<>(1);

        public Builder(ResourceLocation... templates) {
            this.templates = Arrays.asList(templates);
        }

        public Builder(List<ResourceLocation> templates) {
            this.templates = templates;
        }

        public Builder dontMirror() {
            this.mirror = false;
            return this;
        }

        public Builder dontRotate() {
            this.rotate = false;
            return this;
        }

        public Builder ignoreEntities() {
            this.spawnEntities = false;
            return this;
        }

        public Builder spawnInMidair() {
            this.requireGround = false;
            return this;
        }

        public Builder withProcessors(StructureProcessor... processors) {
            this.processors.addAll(Arrays.asList(processors));
            return this;
        }

        public Builder withProcessors(StructureProcessorList processorList) {
            this.processors.addAll(processorList.list());
            return this;
        }

        public StructurePieceConfiguration build() {
            return new StructurePieceConfiguration(templates, mirror, rotate, spawnEntities, requireGround, Holder.direct(new StructureProcessorList(this.processors)));
        }
    }

    public StructureTemplate getTemplate(WorldGenLevel level, RandomSource rand) {
        ResourceLocation templatePath = templates.get(rand.nextInt(templates.size()));
        return level.getLevel().getServer().getStructureManager().getOrCreate(templatePath);
    }

    public StructurePlaceSettings getPlacementSettings(RandomSource rand) {
        StructurePlaceSettings settings = new StructurePlaceSettings();

        if (mirror) settings.setMirror(Util.getRandom(Mirror.values(), rand));

        if (rotate) settings.setRotation(Rotation.getRandom(rand));

        settings.setIgnoreEntities(!spawnEntities);
        settings.setFinalizeEntities(spawnEntities);

        for (StructureProcessor processor : this.processors.value().list()) {
            settings.addProcessor(processor);
        }

        return settings;
    }
}