package com.blackgear.geologicexpansion.common.entity.duck;

import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.entities.GEActivities;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class DuckBrain {
    public static final ImmutableList<SensorType<? extends Sensor<? super ODuck>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY
    );
    public static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of();
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    public static Brain<?> makeBrain(Brain<ODuck> brain) {
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(Brain<ODuck> brain) {
        brain.addActivity(
            Activity.CORE,
            0,
            ImmutableList.of(
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<ODuck> brain) {
        brain.addActivity(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60))),
                Pair.of(1, new AnimalMakeLove(GEEntities.DUCK.get(), 0.2F)),
                Pair.of(2, new RunOne<>(
                    ImmutableList.of(
                        Pair.of(new FollowTemptation(DuckBrain::getSpeedModifier), 1),
                        Pair.of(new BabyFollowAdult<>(ADULT_FOLLOW_RANGE, 0.6F), 1)
                    )
                ))
                // start fishing
                // try find water
                // random stroll
            )
        );
    }

    private static void addHuntActivities(Brain<ODuck> brain) {
//        brain.addActivityAndRemoveMemoryWhenStopped(
        // stop fishing
        // erase memory
//        );
    }

    public static void updateActivity(ODuck duck) {
        Brain<?> brain = duck.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        brain.setActiveActivityToFirstValid(
            ImmutableList.of(
                Activity.IDLE,
                GEActivities.HUNT.get()
            )
        );

        // If the ODuck was hunting and is no longer hunting, set a hunting cooldown memory
        if (activity == GEActivities.HUNT.get() && brain.getActiveNonCoreActivity().orElse(null) != GEActivities.HUNT.get()) {
            brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
    }

    private static float getSpeedModifier(LivingEntity entity) {
        return 1.0F;
    }

    private static boolean isBreeding(ODuck duck) {
        return duck.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
    }

    public static Ingredient getTemptations() {
        return Ingredient.of(Items.WHEAT);
    }
}