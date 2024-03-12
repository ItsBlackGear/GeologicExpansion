package com.blackgear.geologicexpansion.common.entity.duck.goals;

import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.common.entity.resource.EntityState;
import com.blackgear.geologicexpansion.common.entity.resource.OpenWaterType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class DuckFishingGoal extends MoveToBlockGoal {
    private final Duck duck;
    protected int ticksWaited;

    public DuckFishingGoal(Duck duck, double speedModifier, int searchRange, int verticalSearchRange) {
        super(duck, speedModifier, searchRange, verticalSearchRange);
        this.duck = duck;
    }

    @Override
    public void start() {
        this.ticksWaited = 0;
        super.start();
    }

    @Override
    public boolean canUse() {
        return (!this.duck.ateRecently() || this.duck.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) && !this.duck.isBaby() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.duck.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 100 == 0;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return OpenWaterType.calculateOpenWater(level, pos);
    }

    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            if (this.ticksWaited == 40) {
                this.duck.getNavigation().stop();
                this.duck.setState(EntityState.FISHING);
            } else if (this.ticksWaited == 50) {
                this.duck.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F);
            } else if (this.ticksWaited == 65) {
                this.duck.setState(EntityState.IDLE);
                this.startFishing();
            }

            ++this.ticksWaited;
        }

        super.tick();
    }

    private void startFishing() {
        ItemStack stack = this.duck.getItemBySlot(EquipmentSlot.MAINHAND);
        if (stack.isEmpty()) {
            this.retrieveItemFromFishing();
        }
    }

    private void retrieveItemFromFishing() {
        MinecraftServer server = this.duck.level.getServer();
        if (!this.duck.level.isClientSide && server != null) {
            // Get an item from the fishing loot table
            LootContext.Builder builder = new LootContext.Builder((ServerLevel) this.duck.level)
                .withParameter(LootContextParams.ORIGIN, this.duck.position())
                .withParameter(LootContextParams.TOOL, new ItemStack(Items.FISHING_ROD))
                .withParameter(LootContextParams.THIS_ENTITY, this.duck)
                .withRandom(this.duck.getRandom())
                .withLuck((float) this.duck.getAttributeValue(Attributes.LUCK));
            LootTable lootTable = server.getLootTables().get(BuiltInLootTables.FISHING);
            List<ItemStack> loot = lootTable.getRandomItems(builder.create(LootContextParamSets.FISHING));

            // For each item found, hold it in the beak and set it as guaranteed drop
            for (ItemStack stack : loot) {
                this.duck.setGuaranteedDrop(EquipmentSlot.MAINHAND);
                this.duck.setItemSlot(EquipmentSlot.MAINHAND, stack);
                break;
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.duck.setState(EntityState.IDLE);
    }
}