package vao211.somethingsaddons.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;
import java.util.Collections;
import java.util.UUID;

@Mixin(MobEntity.class)
public abstract class PetTeleportMixin extends LivingEntity {

    protected PetTeleportMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private int somethingsaddons$lostTimer = 0;

    @Inject(method = "mobTick", at = @At("HEAD"))
    private void somethingsaddons$checkLostPet(CallbackInfo ci) {
        if (!SomethingsAddonsConfig.enablePetReturnToSpawn) return;

        if (!((Object) this instanceof TameableEntity pet)) return;

        if (pet.getWorld().isClient() || !pet.isTamed()) return;

        if (pet.isSitting()) {
            this.somethingsaddons$lostTimer = 0;
            return;
        }

        if (pet.age % 20 != 0) return; //1 per sec

        UUID ownerUuid = pet.getOwnerUuid();
        if (ownerUuid == null) return;

        MinecraftServer server = pet.getServer();
        if (server == null) return;

        ServerPlayerEntity owner = server.getPlayerManager().getPlayer(ownerUuid);
        boolean isLost = false;

        if (owner == null) {
            isLost = true;
        } else {
            double radius = SomethingsAddonsConfig.petLostRadius;
            if (owner.getWorld() != pet.getWorld() || pet.squaredDistanceTo(owner) > (radius * radius)) {
                isLost = true;
            }
        }

        if (isLost) {
            this.somethingsaddons$lostTimer++;
            int maxTicks = SomethingsAddonsConfig.petReturnLostTimer;

            if (this.somethingsaddons$lostTimer >= maxTicks) {
                this.somethingsaddons$lostTimer = 0; // Reset bộ đếm

                ServerWorld targetWorld = null;
                BlockPos targetPos = null;

                if (owner != null) {
                    targetPos = owner.getSpawnPointPosition();
                    if (targetPos != null) {
                        targetWorld = server.getWorld(owner.getSpawnPointDimension());
                    }
                }

                if (targetPos == null || targetWorld == null) {
                    targetWorld = server.getWorld(World.OVERWORLD);
                    targetPos = targetWorld.getSpawnPos();
                }

                if (targetWorld != null && targetPos != null) {
                    pet.teleport(targetWorld, targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5, Collections.emptySet(), pet.getYaw(), pet.getPitch());

                    pet.setVelocity(0, 0, 0);
                    pet.fallDistance = 0.0f;
                }
            }
        } else {
            this.somethingsaddons$lostTimer = 0;
        }
    }
}