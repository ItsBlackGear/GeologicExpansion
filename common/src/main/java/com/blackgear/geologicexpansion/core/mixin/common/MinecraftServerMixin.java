package com.blackgear.geologicexpansion.core.mixin.common;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow public abstract WorldData getWorldData();

    @Inject(method = "createLevels", at = @At("TAIL"))
    private void addSurfaceRules(ChunkProgressListener listener, CallbackInfo ci) {
//        if (this.getWorldData() == null) throw new NullPointerException("WorldData is null!");
//
//        LevelStem stem = this.getWorldData().worldGenSettings().dimensions().get(LevelStem.OVERWORLD);
//
//        if (stem == null) throw new NullPointerException("LevelStem is null!");
//
//        ChunkGenerator generator = stem.generator();
//
//        boolean isGexBiome = generator.getBiomeSource().possibleBiomes().stream().anyMatch(holder -> holder.unwrapKey().orElseThrow().location().getNamespace().equals(GeologicExpansion.MOD_ID));
//        if (isGexBiome) {
//            if (generator instanceof NoiseBasedChunkGenerator noise) {
//                NoiseGeneratorSettings settings = noise.generatorSettings().value();
//                ((NoiseGeneratorSettingsAccessor)(Object)settings).setSurfaceRule(SurfaceRules.sequence(GESurfaceRules.makeRules(), settings.surfaceRule()));
//            }
//        }
    }
}