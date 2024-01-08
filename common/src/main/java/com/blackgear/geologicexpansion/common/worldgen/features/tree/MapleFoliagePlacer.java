package com.blackgear.geologicexpansion.common.worldgen.features.tree;

import com.blackgear.geologicexpansion.common.registries.worldgen.GEFoliagePlacer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.function.BiConsumer;

public class MapleFoliagePlacer extends FoliagePlacer {
    public static final Codec<MapleFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> {
        return foliagePlacerParts(instance).and(instance.group(
            IntProvider.codec(4, 16).fieldOf("height").forGetter(placer -> placer.height),
            Codec.floatRange(0.0F, 1.0F).fieldOf("wide_bottom_layer_hole_chance").forGetter(placer -> placer.wideBottomLayerHoleChance),
            Codec.floatRange(0.0F, 1.0F).fieldOf("corner_hole_chance").forGetter(placer -> placer.cornerHoleChance),
            Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter(placer -> placer.hangingLeavesChance),
            Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_extension_chance").forGetter(placer -> placer.hangingLeavesExtensionChance)
        )).apply(instance, MapleFoliagePlacer::new);
    });

    private final IntProvider height;
    private final float wideBottomLayerHoleChance;
    private final float cornerHoleChance;
    private final float hangingLeavesChance;
    private final float hangingLeavesExtensionChance;

    public MapleFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider height, float wideBottomLayerHoleChance, float cornerHoleChance, float hangingLeavesChance, float hangingLeavesExtensionChance) {
        super(radius, offset);
        this.height = height;
        this.wideBottomLayerHoleChance = wideBottomLayerHoleChance;
        this.cornerHoleChance = cornerHoleChance;
        this.hangingLeavesChance = hangingLeavesChance;
        this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return GEFoliagePlacer.MAPLE_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        boolean large = attachment.doubleTrunk();
        BlockPos pos = attachment.pos().above(offset);
        int range = foliageRadius + attachment.radiusOffset() - 1;
        this.placeLeavesRow(level, blockSetter, random, config, pos, range - 2, foliageHeight - 3, large);
        this.placeLeavesRow(level, blockSetter, random, config, pos, range - 1, foliageHeight - 4, large);
        for (int yOffset = foliageHeight - 5; yOffset >= 0; yOffset--) {
            this.placeLeavesRow(level, blockSetter, random, config, pos, range, yOffset, large);
        }
        this.placeLeavesRowWithHangingLeavesBelow(level, blockSetter, random, config, pos, range, -1, large, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
        this.placeLeavesRowWithHangingLeavesBelow(level, blockSetter, random, config, pos, range - 1, -2, large, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
    }

    private void placeLeavesRowWithHangingLeavesBelow(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, BlockPos pos, int range, int offset, boolean large, float hangingLeavesChance, float hangingLeavesExtensionChance) {
        this.placeLeavesRow(level, blockSetter, random, config, pos, range, offset, large);
        int k = large ? 1 : 0;
        BlockPos blockPos2 = pos.below();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            Direction direction2 = direction.getClockWise();
            int l = direction2.getAxisDirection() == Direction.AxisDirection.POSITIVE ? range + k : range;
            mutable.setWithOffset(pos, 0, offset - 1, 0).move(direction, l).move(direction, -range);
            for (int m = -range; m < range + k; m++) {
                boolean bl2 = level.isStateAtPosition(mutable.move(Direction.UP), state -> state.is(BlockTags.LEAVES));
                mutable.move(Direction.DOWN);
                if (bl2 && this.tryPlaceExtension(level, blockSetter, random, config, hangingLeavesChance, blockPos2, mutable)) {
                    mutable.move(Direction.DOWN);
                    this.tryPlaceExtension(level, blockSetter, random, config, hangingLeavesExtensionChance, blockPos2, mutable);
                    mutable.move(Direction.UP);
                }

                mutable.move(direction);
            }
        }
    }

    private boolean tryPlaceExtension(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, TreeConfiguration config, float chance, BlockPos pos, BlockPos.MutableBlockPos mutable) {
        if (mutable.distManhattan(pos) < 7 && random.nextFloat() <= chance && TreeFeature.validTreePos(level, mutable)) {
            FoliagePlacer.tryPlaceLeaf(level, blockSetter, random, config, mutable);
            return true;
        }

        return false;
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return this.height.sample(random);
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int i, int j, int k, int l, boolean large) {
        if (j == -1 && (i == l || k == l) && random.nextFloat() < this.wideBottomLayerHoleChance) {
            return true;
        }

        boolean bl2 = i == l && k == l;
        boolean bl3 = l > 2;
        if (bl3) {
            return bl2 || i + k > l * 2 - 2 && random.nextFloat() < this.cornerHoleChance;
        }

        return bl2;
    }
}