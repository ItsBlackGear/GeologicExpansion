package com.blackgear.geologicexpansion.core.platform.client.forge;

import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.client.ParticleRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = GeologicExpansion.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistryImpl {
    private static final Set<Consumer<RegisterParticleProvidersEvent>> FACTORIES = ConcurrentHashMap.newKeySet();

    @SubscribeEvent
    public static void event(RegisterParticleProvidersEvent event) {
        FACTORIES.forEach(consumer -> consumer.accept(event));
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void create(Supplier<P> type, ParticleProvider<T> provider) {
        FACTORIES.add(event -> event.registerSpecial(type.get(), provider));
    }

    public static <T extends ParticleOptions, P extends ParticleType<T>> void create(Supplier<P> type, ParticleRegistry.Factory<T> factory) {
        FACTORIES.add(event -> event.registerSpriteSet(type.get(), factory::create));
    }
}