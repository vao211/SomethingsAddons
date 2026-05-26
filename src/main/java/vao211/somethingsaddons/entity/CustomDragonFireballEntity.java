package vao211.somethingsaddons.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;
import net.minecraft.world.GameRules;

public class CustomDragonFireballEntity extends ExplosiveProjectileEntity {
    public CustomDragonFireballEntity(EntityType<? extends CustomDragonFireballEntity> entityType, World world) {
        super(entityType, world);
    }
    public CustomDragonFireballEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(ModEntities.CUSTOM_DRAGON_FIREBALL, owner.getX(), owner.getBodyY(0.5D), owner.getZ(), new Vec3d(velocityX, velocityY, velocityZ), world);
        this.setOwner(owner);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.getWorld().isClient()) {
            Entity owner = this.getOwner();
            LivingEntity livingOwner = (owner instanceof LivingEntity) ? (LivingEntity) owner : null;
            DamageSource damageSource = this.getDamageSources().mobProjectile(this, livingOwner);
            entityHitResult.getEntity().damage(damageSource, (float) SomethingsAddonsConfig.dragonFireballDirectDamage);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient()) {
            boolean griefing = this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            this.getWorld().createExplosion(
                    this,
                    this.getX(), this.getY(), this.getZ(),
                    (float) SomethingsAddonsConfig.dragonFireballExplosionPower,
                    griefing ? World.ExplosionSourceType.MOB : World.ExplosionSourceType.NONE
            );
            this.discard();
        }
    }

    @Override
    protected boolean isBurning() {
        return false;
    }
}