package vao211.somethingsaddons.mixin;

import fuzs.enchantinginfuser.world.inventory.InfuserMenu;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

import java.util.function.IntUnaryOperator;

@Mixin(value = InfuserMenu.class, remap = false)
public abstract class InfuserMenuMixin {
    @Shadow @Final private PlayerEntity player;
    @Shadow public abstract ItemEnchantmentsComponent getItemEnchantments();

    @Inject(method = "clickEnchantmentLevelButton", at = @At("HEAD"), cancellable = true)
    private void somethingsaddons$limitInfuserEnchantments(RegistryEntry<Enchantment> enchantment, IntUnaryOperator operation, CallbackInfoReturnable<Integer> cir) {
        if (!SomethingsAddonsConfig.enableEnchantmentLimit) return;

        ItemEnchantmentsComponent currentEnchants = this.getItemEnchantments();
        int currentLevel = currentEnchants.getLevel(enchantment);
        int newLevel = operation.applyAsInt(currentLevel);

        if (currentLevel == 0 && newLevel > 0) {
            int currentCount = (int) currentEnchants.getEnchantments().stream()
                    .filter(e -> currentEnchants.getLevel(e) > 0)
                    .count();

            if (currentCount >= SomethingsAddonsConfig.maxEnchantmentsPerItem) {
                this.player.sendMessage(
                        Text.translatable("somethingsaddons.message.enchantment_limit", SomethingsAddonsConfig.maxEnchantmentsPerItem)
                                .formatted(Formatting.RED),
                        true
                );

                cir.setReturnValue(0);
            }
        }
    }
}