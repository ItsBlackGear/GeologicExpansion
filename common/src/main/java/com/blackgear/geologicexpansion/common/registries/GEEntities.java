package com.blackgear.geologicexpansion.common.registries;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.blackgear.geologicexpansion.common.entity.bear.GrizzlyBear;
import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.common.entity.projectile.ThrownDuckEgg;
import com.blackgear.geologicexpansion.core.GeologicExpansion;
import com.blackgear.geologicexpansion.core.platform.CoreRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class GEEntities {
    public static final CoreRegistry<EntityType<?>> ENTITIES = CoreRegistry.create(Registry.ENTITY_TYPE, GeologicExpansion.MOD_ID);

    // ========== GRIZZLY BEAR =========================================================================================
    public static final Supplier<EntityType<GrizzlyBear>> GRIZZLY_BEAR = create(
        "grizzly_bear",
        EntityType.Builder.of(GrizzlyBear::new, MobCategory.CREATURE)
            .sized(1.44F, 1.44F)
            .clientTrackingRange(10)
    );
    public static final Supplier<EntityType<Grizzly>> GRIZZLY = create(
        "grizzly",
        EntityType.Builder.of(Grizzly::new, MobCategory.CREATURE)
            .sized(1.75F, 1.75F)
            .clientTrackingRange(10)
    );

    // ========== DUCKS ================================================================================================

    public static final Supplier<EntityType<Duck>> DUCK = create(
        "duck",
        EntityType.Builder.of(Duck::new, MobCategory.CREATURE)
            .sized(0.4F, 0.7F)
            .clientTrackingRange(10)
    );
    public static final Supplier<EntityType<ThrownDuckEgg>> DUCK_EGG = create(
        "duck_egg",
        EntityType.Builder.<ThrownDuckEgg>of(ThrownDuckEgg::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .updateInterval(10)
    );

    private static <T extends Entity> Supplier<EntityType<T>> create(String key, EntityType.Builder<T> builder) {
        return ENTITIES.register(key, () -> builder.build(key));
    }
}