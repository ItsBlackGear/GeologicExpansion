package com.blackgear.geologicexpansion.core.mixin.common;

import com.blackgear.geologicexpansion.common.worldgen.surface.GESurfaceRules;
import com.blackgear.geologicexpansion.core.mixin.access.NoiseBasedChunkGeneratorAccessor;
import com.blackgear.geologicexpansion.core.mixin.access.NoiseGeneratorSettingsAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow public abstract WorldData getWorldData();

    @Inject(method = "createLevels", at = @At("TAIL"))
    private void addSurfaceRules(ChunkProgressListener listener, CallbackInfo ci) {
        if (this.getWorldData() == null) throw new NullPointerException("WorldData is null!");

        LevelStem stem = this.getWorldData().worldGenSettings().dimensions().get(LevelStem.OVERWORLD);

        if (stem == null) throw new NullPointerException("LevelStem is null!");

        if (stem.generator() instanceof NoiseBasedChunkGenerator) {
            NoiseGeneratorSettings settings = ((NoiseBasedChunkGeneratorAccessor)stem.generator()).getSettings().value();

            if (settings.surfaceRule() instanceof SurfaceRules.SequenceRuleSource sequence) {
                List<SurfaceRules.RuleSource> rules = new ArrayList<>(sequence.sequence());
                rules.add(0, GESurfaceRules.makeRules());
                ((NoiseGeneratorSettingsAccessor)(Object)settings).setSurfaceRule(new SurfaceRules.SequenceRuleSource(rules));
            }
        }
    }
}