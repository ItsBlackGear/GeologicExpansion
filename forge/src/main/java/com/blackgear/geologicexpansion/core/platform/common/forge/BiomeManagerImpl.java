package com.blackgear.geologicexpansion.core.platform.common.forge;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.common.BiomeContext;
import com.blackgear.geologicexpansion.core.platform.common.BiomeManager;
import com.blackgear.geologicexpansion.core.platform.common.BiomeWriter;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BiomeManagerImpl {
    @Nullable private static Codec<PlatformBiomeModifier> codec = null;

    public static void bootstrap() {
        FMLJavaModLoadingContext.get().getModEventBus().<RegisterEvent>addListener(event -> {
            event.register(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, registry -> {
                registry.register(new ResourceLocation(GeologicExpansion.MOD_ID, "biome_modifier_codec"),
                        codec = Codec.unit(PlatformBiomeModifier.INSTANCE));
            });
//            event.register(ForgeRegistries.Keys.BIOME_MODIFIERS, registry -> {
//                registry.register(new ResourceLocation(GeologicExpansion.MOD_ID, "biome_modifier"),
//                        PlatformBiomeModifier.INSTANCE);
//            });
        });
    }

    static class PlatformBiomeModifier implements BiomeModifier {
        private static final PlatformBiomeModifier INSTANCE = new PlatformBiomeModifier();

        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD) BiomeManager.INSTANCE.register(new ForgeBiomeWriter(biome.unwrapKey(), builder));
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return codec != null ? codec : Codec.unit(INSTANCE);
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    static class ForgeBiomeWriter extends BiomeWriter {
        private final Optional<ResourceKey<Biome>> biome;
        private final ModifiableBiomeInfo.BiomeInfo.Builder builder;

        ForgeBiomeWriter(Optional<ResourceKey<Biome>> biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            this.biome = biome;
            this.builder = builder;
        }

        @Override
        public ResourceLocation name() {
            return ForgeBiomeWriter.this.biome.get().location();
        }

        @Override
        public BiomeContext context() {
            return new BiomeContext() {
                @Override
                public boolean is(TagKey<Biome> tag) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    if (server != null) {
                        Optional<? extends Registry<Biome>> registry = server.registryAccess().registry(Registries.BIOME);
                        if (registry.isPresent()) {
                            Optional<Holder.Reference<Biome>> holder = registry.get().getHolder(ForgeBiomeWriter.this.biome.get());
                            if (holder.isPresent()) {
                                return holder.get().is(tag);
                            }
                        }
                    }

                    return false;
                }

                @Override
                public boolean is(ResourceKey<Biome> biome) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    if (server != null) {
                        Optional<? extends Registry<Biome>> registry = server.registryAccess().registry(Registries.BIOME);
                        if (registry.isPresent()) {
                            Optional<Holder.Reference<Biome>> holder = registry.get().getHolder(ForgeBiomeWriter.this.biome.get());
                            if (holder.isPresent()) {
                                return holder.get().is(biome);
                            }
                        }
                    }

                    return false;
                }
            };
        }

        @Override
        public void feature(GenerationStep.Decoration decoration, ResourceKey<PlacedFeature> feature) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                Optional<? extends Registry<PlacedFeature>> registry = server.registryAccess().registry(Registries.PLACED_FEATURE);
                if (registry.isPresent()) {
                    Optional<Holder.Reference<PlacedFeature>> holder = registry.get().getHolder(feature);
                    holder.ifPresent(placedFeature -> this.builder.getGenerationSettings().addFeature(decoration, placedFeature));
                }
            }
        }

        @Override
        public void spawn(MobCategory category, MobSpawnSettings.SpawnerData data) {
            this.builder.getMobSpawnSettings().addSpawn(category, data);
        }

        @Override
        public void carver(GenerationStep.Carving carving, ResourceKey<ConfiguredWorldCarver<?>> carver) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                Optional<? extends Registry<ConfiguredWorldCarver<?>>> registry = server.registryAccess().registry(Registries.CONFIGURED_CARVER);
                registry.ifPresent(features -> this.builder.getGenerationSettings().addCarver(carving, features.getHolderOrThrow(carver)));
            }
        }
    }
}