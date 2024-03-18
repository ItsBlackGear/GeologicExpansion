package com.blackgear.geologicexpansion.common.entity.bear.goals;

import com.blackgear.geologicexpansion.common.entity.bear.Grizzly;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GrizzlyEatBerriesGoal extends MoveToBlockGoal {
    private final Grizzly grizzly;
    private int ticksWaited;
    
    public GrizzlyEatBerriesGoal(Grizzly grizzly, double speedModifier, int horizontalSearchRange, int verticalSearchRange) {
        super(grizzly, speedModifier, horizontalSearchRange, verticalSearchRange);
        this.grizzly = grizzly;
    }

    @Override
    public double acceptedDistance() {
        return 2.0D;
    }

    @Override
    public boolean shouldRecalculatePath() {
        return this.tryTicks % 100 == 0;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(Blocks.SWEET_BERRY_BUSH) && state.getValue(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasGlowBerries(state);
    }

    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            if (this.ticksWaited >= 40) {
                this.onReachedTarget();
            } else {
                ++this.ticksWaited;
            }
        } else if (!this.isReachedTarget() && this.grizzly.getRandom().nextFloat() < 0.05F) {
            this.grizzly.playSound(SoundEvents.FOX_SNIFF, 1.0F, 1.0F);
        }
        
        super.tick();
    }

    private void onReachedTarget() {
        if (this.grizzly.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            BlockState state = this.grizzly.level.getBlockState(this.blockPos);
            if (state.is(Blocks.SWEET_BERRY_BUSH)) {
                this.pickSweetBerries(state);
            } else if (CaveVines.hasGlowBerries(state)) {
                this.pickGlowBerries(state);
            }
        }
    }

    private void pickGlowBerries(BlockState state) {
        CaveVines.use(state, this.grizzly.level, this.blockPos);
    }

    private void pickSweetBerries(BlockState state) {
        int i = state.getValue(SweetBerryBushBlock.AGE);
        state.setValue(SweetBerryBushBlock.AGE, 1);
        int size = 1 + this.grizzly.level.random.nextInt(2) + (i == 3 ? 1 : 0);
        ItemStack stack = this.grizzly.getItemBySlot(EquipmentSlot.MAINHAND);
        if (stack.isEmpty()) {
            this.grizzly.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SWEET_BERRIES, size));
            --size;
        }

        if (size > 0) {
            Block.popResource(this.grizzly.level, this.blockPos, new ItemStack(Items.SWEET_BERRIES, size));
        }

        this.grizzly.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        this.grizzly.level.setBlock(this.blockPos, state.setValue(SweetBerryBushBlock.AGE, 1), 2);
    }

    @Override
    public boolean canUse() {
        return !this.grizzly.isSleeping() && this.grizzly.isBaby() && super.canUse();
    }

    @Override
    public void start() {
        this.ticksWaited = 0;
        super.start();
    }
}