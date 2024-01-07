package com.blackgear.geologicexpansion.common.worldgen.features.tree;

import com.blackgear.geologicexpansion.common.registries.worldgen.GETrunkPlacers;
import com.blackgear.geologicexpansion.core.utils.CodecUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class MapleTrunkPlacer extends TrunkPlacer {
    private static final Codec<UniformInt> BRANCH_START_CODEC = CodecUtils.validate(UniformInt.CODEC, provider -> {
        if (provider.getMaxValue() - provider.getMinValue() < 1) {
            return DataResult.error("Need at least 2 blocks variation for the branch starts to fit both branches");
        }
        return DataResult.success(provider);
    });
    public static final Codec<MapleTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> {
        return MapleTrunkPlacer.trunkPlacerParts(instance)
            .and(
                instance.group(
                    IntProvider.codec(1, 5).fieldOf("branch_count").forGetter(placer -> placer.branchCount),
                    IntProvider.codec(1, 16).fieldOf("branch_horizontal_length").forGetter(placer -> placer.branchHorizontalLength),
                    CodecUtils.codec(-16, 0, BRANCH_START_CODEC).fieldOf("branch_start_offset_from_top").forGetter(placer -> placer.branchStartOffsetFromTop),
                    IntProvider.codec(-16, 16).fieldOf("branch_end_offset_from_top").forGetter(placer -> placer.branchEndOffsetFromTop)
                )
            ).apply(instance, MapleTrunkPlacer::new);
    });
    private final IntProvider branchCount;
    private final IntProvider branchHorizontalLength;
    private final UniformInt branchStartOffsetFromTop;
    private final UniformInt secondBranchStartOffsetFromTop;
    private final IntProvider branchEndOffsetFromTop;

    public MapleTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, IntProvider branchCount, IntProvider branchHorizontalLength, UniformInt branchStartOffsetFromTop, IntProvider branchEndOffsetFromTop) {
        super(baseHeight, heightRandA, heightRandB);
        this.branchCount = branchCount;
        this.branchHorizontalLength = branchHorizontalLength;
        this.branchStartOffsetFromTop = branchStartOffsetFromTop;
        this.secondBranchStartOffsetFromTop = UniformInt.of(branchStartOffsetFromTop.getMinValue(), branchStartOffsetFromTop.getMaxValue() - 1);
        this.branchEndOffsetFromTop = branchEndOffsetFromTop;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return GETrunkPlacers.MAPLE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        // Set the dirt at the base of the tree
        setDirtAt(level, blockSetter, random, pos.below(), config);

        // Calculate the start positions for the branches
        int firstBranchStart = Math.max(0, freeTreeHeight - 1 + this.branchStartOffsetFromTop.sample(random));
        int secondBranchStart = Math.max(0, freeTreeHeight - 1 + this.secondBranchStartOffsetFromTop.sample(random));

        // Adjust the start positions if necessary
        if (secondBranchStart >= firstBranchStart) {
            ++secondBranchStart;
        }

        // Sample the branch count and determine the branch configuration
        int branchCount = this.branchCount.sample(random);
        boolean bl = branchCount >= 3;
        boolean bl2 = branchCount >= 2;
        boolean bl3 = branchCount >= 4;
        boolean bl4 = branchCount == 5;

        // Calculate the height of the trunk
        int trunkHeight = firstBranchStart + 1;
        if (bl) {
            trunkHeight = freeTreeHeight;
        } else if (bl2) {
            trunkHeight = Math.max(firstBranchStart, secondBranchStart) + 1;
        }

        // Place the trunk blocks
        for (int i = 0; i < trunkHeight; i++) {
            this.placeLog(level, blockSetter, random, pos.above(i), config);
        }

        // Prepare the list of foliage attachments
        List<FoliagePlacer.FoliageAttachment> foliageAttachments = new ArrayList<>();
        if (bl) {
            foliageAttachments.add(new FoliagePlacer.FoliageAttachment(pos.above(trunkHeight), 0, false));
        }

        // Prepare for branch generation
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction direction2 = direction.getClockWise(Direction.Axis.Y);
        Function<BlockState, BlockState> trunkState = state -> state.setValue(RotatedPillarBlock.AXIS, direction.getAxis());
        Function<BlockState, BlockState> trunkState2 = state -> state.setValue(RotatedPillarBlock.AXIS, direction2.getAxis());

        // Generate the first branch
        foliageAttachments.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, trunkState, direction, firstBranchStart, firstBranchStart < trunkHeight - 1, mutable));

        // Generate the second branch if necessary
        if (bl2) {
            foliageAttachments.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, trunkState, direction.getOpposite(), secondBranchStart, secondBranchStart < trunkHeight - 1, mutable));
        }
        if (bl3) {
            foliageAttachments.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, trunkState2, direction2, secondBranchStart, secondBranchStart < trunkHeight - 1, mutable));
        }
        if (bl4) {
            foliageAttachments.add(this.generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, trunkState2, direction2.getOpposite(), secondBranchStart, secondBranchStart < trunkHeight - 1, mutable));
        }

        // Return the list of foliage attachments
        return foliageAttachments;
    }

    private FoliagePlacer.FoliageAttachment generateBranch(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> trunkState, Direction direction, int branchStart, boolean bl, BlockPos.MutableBlockPos mutable) {
        // Set the start position of the branch
        mutable.set(pos).move(Direction.UP, branchStart);

        int branchEndOffset = freeTreeHeight - 1 + this.branchEndOffsetFromTop.sample(random);
        boolean branchEndOffsetLessThanStart = bl || branchEndOffset < branchStart;
        int branchHorizontalLength = this.branchHorizontalLength.sample(random) + (branchEndOffsetLessThanStart ? 1 : 0);
        BlockPos branchEndPos = pos.relative(direction, branchHorizontalLength).above(branchEndOffset);
        int branchLength = branchEndOffsetLessThanStart ? 2 : 1;

        // Place the log blocks for the branch
        for(int i = 0; i < branchLength; ++i) {
            this.placeLog(level, blockSetter, random, mutable.move(direction), config, trunkState);
        }

        // Determine the direction of the branch
        Direction branchDirection = branchEndPos.getY() > mutable.getY() ? Direction.UP : Direction.DOWN;

        while(true) {
            int distanceToBranchEnd = mutable.distManhattan(branchEndPos);
            if (distanceToBranchEnd == 0) {
                return new FoliagePlacer.FoliageAttachment(branchEndPos.above(), 0, false);
            }

            float branchSlope = (float)Math.abs(branchEndPos.getY() - mutable.getY()) / (float)distanceToBranchEnd;
            boolean isBranchSlopeGreater = random.nextFloat() < branchSlope;
            mutable.move(isBranchSlopeGreater ? branchDirection : direction);
            this.placeLog(level, blockSetter, random, mutable, config, isBranchSlopeGreater ? Function.identity() : trunkState);
        }
    }
}