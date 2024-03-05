package com.blackgear.geologicexpansion.common.entity.bear.behavior;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BearSearchForItems extends Behavior<Grizzly> {
    private final float speedModifier;

    public BearSearchForItems(float speedModifier) {
        super(
            ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
                MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED
            )
        );
        this.speedModifier = speedModifier;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Grizzly bear) {
        if (!bear.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else if (bear.getTarget() != null || bear.getLastHurtByMob() != null) {
            return false;
        } else if (bear.isSleeping()) {
            return false;
        } else if (bear.getRandom().nextInt(Mth.positiveCeilDiv(10, 2)) != 0) {
            return false;
        } else {
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, bear.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Grizzly.GRIZZLY_ITEMS);
            return !items.isEmpty();
        }
    }

    @Override
    protected void tick(ServerLevel level, Grizzly bear, long gameTime) {
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, bear.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Grizzly.GRIZZLY_ITEMS);
        ItemStack stack = bear.getItemBySlot(EquipmentSlot.MAINHAND);
        if (stack.isEmpty() && !items.isEmpty()) {
            bear.getNavigation().moveTo(items.get(0), this.speedModifier);
        }
    }

    @Override
    protected void start(ServerLevel level, Grizzly bear, long gameTime) {
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, bear.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Grizzly.GRIZZLY_ITEMS);
        if (!items.isEmpty()) {
            bear.getNavigation().moveTo(items.get(0), this.speedModifier);
        }
    }
}