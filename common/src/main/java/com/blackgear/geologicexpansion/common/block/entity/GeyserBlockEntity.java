package com.blackgear.geologicexpansion.common.block.entity;

import com.blackgear.geologicexpansion.client.registries.GEParticleTypes;
import com.blackgear.geologicexpansion.common.block.GeyserBlock;
import com.blackgear.geologicexpansion.common.registries.GEBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class GeyserBlockEntity extends BlockEntity {
    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(GEBlockEntities.GEYSER.get(), pos, state);
    }

    @SuppressWarnings("unused")
    public static void clientTicker(Level level, BlockPos pos, BlockState state, GeyserBlockEntity geyser) {
        RandomSource random = level.random;

        if (random.nextFloat() < 0.11F && !state.getValue(BlockStateProperties.WATERLOGGED) && state.getValue(GeyserBlock.HALF) == DoubleBlockHalf.UPPER) {
            if (state.getValue(GeyserBlock.STAGE) == GeyserBlock.Stage.AWAKE) {
                for (int i = 0; i < random.nextInt(2) + 2; i++) {
                    level.addAlwaysVisibleParticle(
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            true,
                            pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1),
                            pos.getY() + 0.5 + random.nextDouble() + random.nextDouble(),
                            pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1),
                            0.0,
                            0.07,
                            0.0
                    );
                }
            } else if (state.getValue(GeyserBlock.STAGE) == GeyserBlock.Stage.COOLING_OFF) {
                for (int i = 0; i < random.nextInt(2) + 2; i++) {
                    level.addParticle(
                            ParticleTypes.SMOKE,
                            pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1),
                            pos.getY() + 0.5 + random.nextDouble() + random.nextDouble(),
                            pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1),
                            0.0,
                            0.07,
                            0.0
                    );
                }
            }
        }

        if (state.getValue(GeyserBlock.STAGE) == GeyserBlock.Stage.ERUPTING) {
            for (int i = 0; i < random.nextInt(1) + 1; i++) {
                level.addParticle(
                        ParticleTypes.LAVA,
                        (double)pos.getX() + 0.5,
                        (double)pos.getY() + 0.5,
                        (double)pos.getZ() + 0.5,
                        random.nextFloat() / 2.0F,
                        5.0E-5,
                        random.nextFloat() / 2.0F
                );
                level.addParticle(
                        GEParticleTypes.GEYSER_ERUPTION.get(),
                        (double)pos.getX() + 0.5,
                        (double)pos.getY() + 1,
                        (double)pos.getZ() + 0.5,
                        random.nextFloat() / 2.0F,
                        5.0E-5,
                        random.nextFloat() / 2.0F
                );
            }
        }
    }

    @SuppressWarnings("unused")
    public static void serverTicker(Level level, BlockPos pos, BlockState state, GeyserBlockEntity geyser) {
        if (state.getValue(GeyserBlock.STAGE) == GeyserBlock.Stage.ERUPTING) {
            double velocity = 0.25;

            // Checks for entities in a vertical range of 15 blocks
            List<Entity> entities = new ArrayList<>();
            for (int i = 1; i < 15; i++) {
                BlockPos scanPos = pos.above(i);

                // stops if there's a solid block that prevents its movement upwards
                if (level.getBlockState(scanPos).getMaterial().isSolidBlocking()) {
                    break;
                }

                AABB boundingBox = new AABB(
                        scanPos.getX(),
                        scanPos.getY(),
                        scanPos.getZ(),
                        scanPos.getX() + 1.0,
                        scanPos.getY() + 1.0,
                        scanPos.getZ() + 1.0
                );
                entities.addAll(level.getEntitiesOfClass(LivingEntity.class, boundingBox));
            }

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity living) {
                    int protLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_PROTECTION, living);
                    float damage = 1.0F - (float)protLevel * 0.15F;

                    if (!living.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                        living.hurt(DamageSource.HOT_FLOOR, damage);
                    }
                }

                entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, velocity, 0.0));
                entity.hurtMarked = true;
            }
        }
    }

    private int getGeyserPower(BlockState state) {
        return 0;
    }
}
