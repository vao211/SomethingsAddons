package vao211.somethingsaddons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.Somethingsaddons;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(LivingEntity.class)
public abstract class PetCampfireMixin {

    @Unique
    private int somethingsaddons$petCampfireHealTimer = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void somethingsaddons$petCampfireHealTick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        World world = entity.getWorld();

        if (world.isClient() || !entity.isAlive()) return;

        if (!(entity instanceof TameableEntity tameable) || !tameable.isTamed()) return;

        int combatTimer = Somethingsaddons.COMBAT_TIMERS.getOrDefault(entity, 0);
        if (combatTimer > 0) {
            combatTimer--;
            if (combatTimer <= 0) {
                Somethingsaddons.COMBAT_TIMERS.remove(entity);
            } else {
                Somethingsaddons.COMBAT_TIMERS.put(entity, combatTimer);
            }
        }

        if (!SomethingsAddonsConfig.enableCampFireHealingPet) return;

        if (combatTimer > 0 || entity.getHealth() >= entity.getMaxHealth()) {
            this.somethingsaddons$petCampfireHealTimer = 0;
            return;
        }

        this.somethingsaddons$petCampfireHealTimer++;
        int healIntervalTicks = SomethingsAddonsConfig.campfireHealInterval * 20;

        if (this.somethingsaddons$petCampfireHealTimer >= healIntervalTicks) {
            this.somethingsaddons$petCampfireHealTimer = 0;

            BlockPos pos = entity.getBlockPos();
            Iterable<BlockPos> scanArea = BlockPos.iterate(
                    pos.add(-2, -1, -2),
                    pos.add(2, 1, 2)
            );

            for (BlockPos checkPos : scanArea) {
                BlockState state = world.getBlockState(checkPos);

                if (state.isIn(BlockTags.CAMPFIRES) && state.contains(Properties.LIT) && state.get(Properties.LIT)) {
                    entity.heal((float) SomethingsAddonsConfig.campfireHealAmount);

                    if (world instanceof ServerWorld serverWorld) {
                        serverWorld.spawnParticles(
                                ParticleTypes.HEART,
                                entity.getX(), entity.getY() + 0.8, entity.getZ(),
                                3, 0.25, 0.25, 0.25, 0.0
                        );
                    }
                    break;
                }
            }
        }
    }
}