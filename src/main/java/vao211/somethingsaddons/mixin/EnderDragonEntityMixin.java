package vao211.somethingsaddons.mixin;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType; // Đổi thành PhaseType
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory; // Đổi thành SoundCategory
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;
import vao211.somethingsaddons.entity.CustomDragonFireballEntity;

import java.util.List;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin {
    @Unique private int somethingsaddons$fireballTimer = 0;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void somethingsaddons$dangerDragonAttack(CallbackInfo ci) {
        EnderDragonEntity dragon = (EnderDragonEntity) (Object) this;
        if (dragon.getWorld().isClient()) return;
        if (SomethingsAddonsConfig.enableDangerDragon) {
            somethingsaddons$fireballTimer++;
            if (somethingsaddons$fireballTimer >= 120) {
                somethingsaddons$fireballTimer = 0;

                if (dragon.getPhaseManager().getCurrent() != null) {
                    var currentPhase = dragon.getPhaseManager().getCurrent().getType();
                    if (currentPhase != PhaseType.SITTING_FLAMING &&
                            currentPhase != PhaseType.SITTING_SCANNING &&
                            currentPhase != PhaseType.SITTING_ATTACKING) {

                        List<ServerPlayerEntity> targets = dragon.getWorld().getEntitiesByClass(
                                ServerPlayerEntity.class,
                                dragon.getBoundingBox().expand(50.0D),
                                p -> p.isAlive() && !p.isCreative() && !p.isSpectator()
                        );

                        for (ServerPlayerEntity player : targets) {
                            if (dragon.getRandom().nextFloat() <= 0.20f) {
                                somethingsaddons$shootFireballAt(dragon, player);
                            }
                        }
                    }
                }
            }
        }
    }

    @Unique
    private void somethingsaddons$shootFireballAt(EnderDragonEntity dragon, ServerPlayerEntity target) {
        double spawnX = dragon.getX();
        double spawnY = dragon.getY() - 0.5D;
        double spawnZ = dragon.getZ();

        double vecX = target.getX() - spawnX;
        double vecY = target.getBodyY(0.5D) - spawnY;
        double vecZ = target.getZ() - spawnZ;

        CustomDragonFireballEntity fireball = new CustomDragonFireballEntity(dragon.getWorld(), dragon, vecX, vecY, vecZ);
        fireball.refreshPositionAndAngles(spawnX, spawnY, spawnZ, dragon.getYaw(), dragon.getPitch());
        dragon.getWorld().spawnEntity(fireball);

        dragon.getWorld().playSound(null, spawnX, spawnY, spawnZ,
                SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, SoundCategory.HOSTILE, 1.5F, 0.8F);
    }
}