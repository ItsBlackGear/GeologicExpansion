package com.blackgear.geologicexpansion.common.entity.projectile;

import com.blackgear.geologicexpansion.common.entity.duck.Duck;
import com.blackgear.geologicexpansion.common.registries.GEEntities;
import com.blackgear.geologicexpansion.common.registries.GEItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownDuckEgg extends ThrowableItemProjectile {
    public ThrownDuckEgg(EntityType<? extends ThrownDuckEgg> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownDuckEgg(Level level, LivingEntity owner) {
        super(GEEntities.DUCK_EGG.get(), owner, level);
    }

    //TODO add dispenser behavior
    public ThrownDuckEgg(Level level, double x, double y, double z) {
        super(GEEntities.DUCK_EGG.get(), x, y, z, level);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08
                );
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int amount = 1;
                if (this.random.nextInt(32) == 0) {
                    amount = 4;
                }

                for(int i = 0; i < amount; ++i) {
                    Duck duck = GEEntities.DUCK.get().create(this.level);
                    if (duck != null) {
                        duck.setAge(-24000);
                        duck.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        this.level.addFreshEntity(duck);
                    }
                }
            }

            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return GEItems.DUCK_EGG.get();
    }
}