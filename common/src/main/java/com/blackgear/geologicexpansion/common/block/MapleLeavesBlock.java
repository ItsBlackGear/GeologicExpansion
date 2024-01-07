package com.blackgear.geologicexpansion.common.block;

import com.blackgear.geologicexpansion.client.registries.GEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MapleLeavesBlock extends LeavesBlock {
    private final Type type;

    public MapleLeavesBlock(Properties properties, Type type) {
        super(properties);
        this.type = type;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextInt(10) == 0) {
            BlockPos blockPos = pos.below();
            BlockState blockState = level.getBlockState(blockPos);
            if (!isFaceFull(blockState.getCollisionShape(level, blockPos), Direction.UP)) {
                spawnParticleBelow(level, pos, random, type.particle);
            }
        }
    }

    public static void spawnParticleBelow(Level level, BlockPos pos, RandomSource random, ParticleOptions particle) {
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() - 0.05;
        double z = (double)pos.getZ() + random.nextDouble();
        level.addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
    }

    public enum Type {
        RED(GEParticleTypes.RED_MAPLE_LEAVES.get()),
        BROWN(GEParticleTypes.BROWN_MAPLE_LEAVES.get());

        public final ParticleOptions particle;

        Type(ParticleOptions particle) {
            this.particle = particle;
        }
    }
}