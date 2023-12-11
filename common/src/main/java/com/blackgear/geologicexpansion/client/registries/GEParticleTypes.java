package com.blackgear.geologicexpansion.client.registries;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.mixin.access.SimpleParticleTypeAccessor;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class GEParticleTypes {
    public static final CoreRegistry<ParticleType<?>> PARTICLES = CoreRegistry.create(BuiltInRegistries.PARTICLE_TYPE, GeologicExpansion.MOD_ID);

    public static final Supplier<SimpleParticleType> GEYSER_ERUPTION = create("geyser_eruption", false);

    private static Supplier<SimpleParticleType> create(String key, boolean overrideLimiter) {
        return PARTICLES.register(key, () -> SimpleParticleTypeAccessor.create(overrideLimiter));
    }

    private static <T extends ParticleOptions> Supplier<ParticleType<T>> create(String key, boolean overrideLimiter, ParticleOptions.Deserializer<T> deserializer, Function<ParticleType<T>, Codec<T>> factory) {
        return PARTICLES.register(key, () -> new ParticleType<>(overrideLimiter, deserializer) {
            @Override
            public Codec<T> codec() {
                return factory.apply(this);
            }
        });
    }
}