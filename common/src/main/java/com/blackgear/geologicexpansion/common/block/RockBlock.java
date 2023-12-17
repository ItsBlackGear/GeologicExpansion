package com.blackgear.geologicexpansion.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RockBlock extends Block implements SimpleWaterloggedBlock {
    private static final VoxelShape ONE_ROCK_AABB = Block.box(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
    private static final VoxelShape MULTIPLE_ROCKS_AABB = Block.box(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty ROCKS = IntegerProperty.create("rocks", 1, 4);

    public RockBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(ROCKS, 1)
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        if (state.is(this)) {
            return state.setValue(ROCKS, Math.min(4, state.getValue(ROCKS) + 1));
        } else {
            boolean isInWater = context.getLevel().getFluidState(context.getClickedPos()).getType().isSame(Fluids.WATER);
            return super.getStateForPlacement(context).setValue(WATERLOGGED, isInWater);
        }
    }

    private boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return !state.getCollisionShape(level, pos).getFaceShape(Direction.UP).isEmpty() || state.isFaceSturdy(level, pos, Direction.UP);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && state.getValue(ROCKS) < 4 || super.canBeReplaced(state, useContext);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }

        return super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = state.getValue(ROCKS) > 1 ? MULTIPLE_ROCKS_AABB : ONE_ROCK_AABB;
        Vec3 offset = state.getOffset(level, pos);
        return shape.move(offset.x, offset.y, offset.z);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROCKS, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.125F;
    }
}