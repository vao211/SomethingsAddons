package vao211.somethingsaddons.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(World.class)
public abstract class WorldMixin {

    @Inject(method = "breakBlock(Lnet/minecraft/util/math/BlockPos;ZLnet/minecraft/entity/Entity;I)Z", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$globallyProtectBlocks(BlockPos pos, boolean drop, Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        World world = (World) (Object) this;
        BlockState targetState = world.getBlockState(pos);

        boolean shouldProtect = false;

        if (SomethingsAddonsConfig.globalBedrockProtection) {
            if (targetState.getHardness(world, pos) < 0.0F || targetState.isIn(BlockTags.WITHER_IMMUNE)) {
                shouldProtect = true;
            }
        }
        if (!shouldProtect && SomethingsAddonsConfig.enableCustomBlockProtection) {
            String blockIdStr = Registries.BLOCK.getId(targetState.getBlock()).toString();
            if (SomethingsAddonsConfig.protectedBlocks.contains(blockIdStr)) {
                shouldProtect = true;
            }
        }

        if (shouldProtect) {
            if (breakingEntity instanceof PlayerEntity player && player.isCreative()) {
                return;
            }
            cir.setReturnValue(false);
        }
    }
}