package com.blackgear.geologicexpansion.common.entity.bear;

import com.blackgear.geologicexpansion.common.entity.bear.behavior.BearMeleeAttack;
import com.blackgear.geologicexpansion.common.entity.bear.behavior.BearPanic;
import com.blackgear.geologicexpansion.common.entity.bear.behavior.BearSearchForItems;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
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
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EraseMemoryIf;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.RunSometimes;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Optional;

public class GrizzlyBrain {
    public static final ImmutableList<SensorType<? extends Sensor<? super GrizzlyBear>>> SENSORS = ImmutableList.of(
        SensorType.NEAREST_LIVING_ENTITIES,
        SensorType.NEAREST_PLAYERS,
        SensorType.NEAREST_ITEMS,
        SensorType.NEAREST_ADULT,
        SensorType.HURT_BY
        // GRIZZLY ATTACKABLES
        // GRIZZLY TEMPTATIONS
    );
    public static final ImmutableList<MemoryModuleType<?>> MEMORIES = ImmutableList.of(
        MemoryModuleType.ATE_RECENTLY,
        MemoryModuleType.BREED_TARGET,
        MemoryModuleType.NEAREST_LIVING_ENTITIES,
        MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
        MemoryModuleType.NEAREST_VISIBLE_PLAYER,
        MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.PATH,
        MemoryModuleType.ATTACK_TARGET,
        MemoryModuleType.ATTACK_COOLING_DOWN,
        MemoryModuleType.NEAREST_VISIBLE_ADULT,
        MemoryModuleType.HURT_BY_ENTITY,
        MemoryModuleType.NEAREST_ATTACKABLE,
        MemoryModuleType.TEMPTING_PLAYER,
        MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
        MemoryModuleType.IS_TEMPTED,
        MemoryModuleType.HAS_HUNTING_COOLDOWN,
        MemoryModuleType.IS_PANICKING
    );

    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    public static Brain<?> makeBrain(Brain<GrizzlyBear> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(Brain<GrizzlyBear> brain) {
        brain.addActivity(
            Activity.CORE,
            0,
            ImmutableList.of(
                new Swim(0.8F),
                new BearPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
            )
        );
    }

    private static void addIdleActivities(Brain<GrizzlyBear> brain) {
        brain.addActivity(
            Activity.IDLE,
            ImmutableList.of(
                Pair.of(0, new RunSometimes<>(new SetEntityLookTarget(EntityType.PLAYER, 6.0F), UniformInt.of(30, 60))),
                Pair.of(0, new AnimalMakeLove(GEEntities.GRIZZLY_BEAR.get(), 0.2F)),
                Pair.of(1, new FollowTemptation(entity -> 0.6F)),
                Pair.of(2, new BabyFollowAdult<>(ADULT_FOLLOW_RANGE, 1.25F)),
                Pair.of(3, new RunOne<>(
                    ImmutableList.of(
                        Pair.of(new RandomStroll(1.0F), 2),
                        Pair.of(new SetWalkTargetFromLookTarget(1.0F, 3), 2),
                        Pair.of(new DoNothing(30, 60), 1)
                    )
                )),
                Pair.of(3, new BearSearchForItems(1.2F)),
                Pair.of(3, new StartAttacking<>(GrizzlyBrain::findNearestValidAttackTarget))
            )
        );
    }

    private static void addFightActivities(Brain<GrizzlyBear> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
            Activity.FIGHT,
            0,
            ImmutableList.of(
                new StopAttackingIfTargetInvalid<>(),
                new SetWalkTargetFromAttackTargetIfTargetOutOfReach(0.6F),
                new BearMeleeAttack(20),
                new EraseMemoryIf<>(GrizzlyBrain::isBreeding, MemoryModuleType.ATTACK_TARGET)
            ),
            MemoryModuleType.ATTACK_TARGET
        );
    }

    public static void updateActivity(GrizzlyBear grizzly) {
        Brain<GrizzlyBear> brain = grizzly.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);

        brain.setActiveActivityToFirstValid(
            ImmutableList.of(
                Activity.FIGHT,
                Activity.IDLE
            )
        );

        if (activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse(null) != Activity.FIGHT) {
            brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
        }
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(GrizzlyBear grizzly) {
        if (isBreeding(grizzly)) {
            return Optional.empty();
        } else {
            return grizzly.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
        }
    }

    private static boolean isBreeding(GrizzlyBear grizzly) {
        return grizzly.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
    }
}