package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class GESounds {
    public static final CoreRegistry<SoundEvent> SOUNDS = CoreRegistry.create(BuiltInRegistries.SOUND_EVENT, GeologicExpansion.MOD_ID);

    // ========== BLOCK SOUNDS =========================================================================================
    public static final Supplier<SoundEvent> GEYSER_ERUPT = create("block.geyser.erupt");

    // ========== ENTITY SOUNDS ========================================================================================
    public static final Supplier<SoundEvent> DUCK_AMBIENT = create("entity.duck.ambient");
    public static final Supplier<SoundEvent> DUCK_HURT = create("entity.duck.hurt");
    public static final Supplier<SoundEvent> DUCK_DEATH = create("entity.duck.death");
    public static final Supplier<SoundEvent> DUCK_STEP = create("entity.duck.step");

    private static Supplier<SoundEvent> create(String key) {
        return SOUNDS.register(key, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GeologicExpansion.MOD_ID, key)));
    }
}