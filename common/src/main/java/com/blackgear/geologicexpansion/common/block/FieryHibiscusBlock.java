package com.blackgear.geologicexpansion.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.ToIntFunction;

public class FieryHibiscusBlock extends Block {
    public static final BooleanProperty HAS_FILAMENT = BooleanProperty.create("has_filament");
    private static final int REGULAR_GROWTH_TIME_TICKS = 24000;
    private static final int BOOSTED_GROWTH_TIME_TICKS = 12000;
    private static final int RANDOM_GROWTH_OFFSET_TICKS = 300;
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);
    public static final ToIntFunction<BlockState> LIGHT_VALUE = state -> state.getValue(HAS_FILAMENT) ? 7 : 0;

    public FieryHibiscusBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(HAS_FILAMENT, true)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_FILAMENT);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.below(), Direction.UP) && !level.isWaterAt(pos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (direction != Direction.DOWN && !this.canSurvive(state, level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

//    @Override
//    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
//        if (!state.getValue(HAS_FILAMENT)) {
//            level.setBlock(pos, state.setValue(HAS_FILAMENT, true), 2);
//        }
//    }

//    @Override
//    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        ItemStack stack = player.getItemInHand(hand);
//        if (stack.is(Items.SHEARS) && state.getValue(HAS_FILAMENT)) {
//            if (!level.isClientSide) {
//                level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
//                level.setBlock(pos, state.setValue(HAS_FILAMENT, false), 3);
//                popResource(level, pos, new ItemStack(GEItems.ROCK_HAMMER.get(), 1));
//                stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(hand));
//                level.gameEvent(player, GameEvent.SHEAR, pos);
//                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
//            }
//
//            return InteractionResult.sidedSuccess(level.isClientSide);
//        }
//
//        return super.use(state, level, pos, player, hand, hit);
//    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(HAS_FILAMENT)) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            double xOffset = (double)x + random.nextDouble();
            double yOffset = (double)y + 0.3;
            double zOffset = (double)z + random.nextDouble();
            level.addParticle(ParticleTypes.SMALL_FLAME, xOffset, yOffset, zOffset, 0.0, 0.025, 0.0);
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for(int i = 0; i < 14; ++i) {
                mutable.set(x + Mth.nextInt(random, -5, 5), y + random.nextInt(10), z + Mth.nextInt(random, -5, 5));
                BlockState blockState = level.getBlockState(mutable);
                if (!blockState.isCollisionShapeFullBlock(level, mutable)) {
                    level.addParticle(ParticleTypes.CRIMSON_SPORE, (double)mutable.getX() + random.nextDouble(), (double)mutable.getY() + random.nextDouble(), (double)mutable.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

//    @Override
//    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
//        int ticks = growthBoost(level, pos) ? BOOSTED_GROWTH_TIME_TICKS : REGULAR_GROWTH_TIME_TICKS;
//        int growthTicks = ticks / 3;
//        level.gameEvent(GameEvent.ENTITY_PLACE, pos, GameEvent.Context.of(state));
//        level.scheduleTick(pos, this, growthTicks + level.random.nextInt(RANDOM_GROWTH_OFFSET_TICKS));
//    }

//    public static boolean growthBoost(BlockGetter level, BlockPos pos) {
//        return level.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK);
//    }
}