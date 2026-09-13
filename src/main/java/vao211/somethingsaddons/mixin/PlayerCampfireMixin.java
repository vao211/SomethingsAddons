package vao211.somethingsaddons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

@Mixin(PlayerEntity.class)
public abstract class PlayerCampfireMixin extends LivingEntity {

    protected PlayerCampfireMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private int somethingsaddons$campfireHealTimer = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void somethingsaddons$campfireSurvivalTick(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        World world = player.getWorld();

        if (world.isClient() || !player.isAlive() || player.isSpectator()) return;

        int combatTimer = Somethingsaddons.PLAYER_COMBAT_TIMERS.getOrDefault(player, 0);
        if (combatTimer > 0) {
            combatTimer--;
            if (combatTimer <= 0) {
                Somethingsaddons.PLAYER_COMBAT_TIMERS.remove(player);
            } else {
                Somethingsaddons.PLAYER_COMBAT_TIMERS.put(player, combatTimer);
            }
        }

        if (!SomethingsAddonsConfig.enableCampfireHealing) return;


        if (combatTimer > 0 || player.getHealth() >= player.getMaxHealth()) {
            this.somethingsaddons$campfireHealTimer = 0;
            return;
        }

        this.somethingsaddons$campfireHealTimer++;
        int healIntervalTicks = SomethingsAddonsConfig.campfireHealInterval * 20;

        if (this.somethingsaddons$campfireHealTimer >= healIntervalTicks) {
            this.somethingsaddons$campfireHealTimer = 0;
            BlockPos pos = player.getBlockPos();
            Iterable<BlockPos> scanArea = BlockPos.iterate(
                    pos.add(-2, -1, -2),
                    pos.add(2, 1, 2)
            );

            for (BlockPos checkPos : scanArea) {
                BlockState state = world.getBlockState(checkPos);

                if (state.isIn(BlockTags.CAMPFIRES) && state.contains(Properties.LIT) && state.get(Properties.LIT)) {
                    player.heal((float) SomethingsAddonsConfig.campfireHealAmount);
                    ((ServerWorld) world).spawnParticles(
                            ParticleTypes.HEART,
                            player.getX(), player.getY() + 1.0, player.getZ(),
                            3, 0.3, 0.3, 0.3, 0.0
                    );
                    break;
                }
            }
        }
    }
}