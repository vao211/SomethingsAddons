package vao211.somethingsaddons.mixin;

import net.minecraft.entity.projectile.WitherSkullEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(WitherSkullEntity.class)
public abstract class WitherSkullEntityMixin {

    //Damage
    @ModifyArg(
            method = "onEntityHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"),
            index = 1
    )
    private float somethingsaddons$buffSkullDamage(float originalDamage) {
        if (!SomethingsAddonsConfig.enableDangerBoss) return originalDamage;

        WitherSkullEntity skull = (WitherSkullEntity) (Object) this;
        float newDmg = (float) SomethingsAddonsConfig.witherSkullDirectDamage;

        return skull.isCharged() ? newDmg * 1.5f : newDmg;
    }

    //Explode
    @ModifyArg(
            method = "onCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"),
            index = 4
    )
    private float somethingsaddons$buffSkullExplosion(float originalPower) {
        if (!SomethingsAddonsConfig.enableDangerBoss) return originalPower;

        WitherSkullEntity skull = (WitherSkullEntity) (Object) this;
        float newPower = (float) SomethingsAddonsConfig.witherSkullExplosionPower;

        // Sọ xanh nổ to gấp đôi
        return skull.isCharged() ? newPower * 2.0f : newPower;
    }
}