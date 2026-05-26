package vao211.somethingsaddons.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vao211.somethingsaddons.config.SomethingsAddonsConfig;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilMenuMixin {

    @Inject(method = "updateResult", at = @At("RETURN"))
    private void somethingsaddons$limitAnvilEnchantments(CallbackInfo ci) {
        if (!SomethingsAddonsConfig.enableEnchantmentLimit) return;

        AnvilScreenHandler anvil = (AnvilScreenHandler) (Object) this;
        ItemStack resultStack = anvil.getSlot(2).getStack();

        if (!resultStack.isEmpty()) {
            int enchantCount = EnchantmentHelper.getEnchantments(resultStack).getEnchantments().size();
            if (enchantCount > SomethingsAddonsConfig.maxEnchantmentsPerItem) {
                anvil.getSlot(2).setStack(ItemStack.EMPTY);
            }
        }
    }
}