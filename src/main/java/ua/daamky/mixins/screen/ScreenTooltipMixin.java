package ua.daamky.mixins.screen;

import ua.daamky.features.misc.ServerAssistant;
import ua.daamky.system.api.ModuleManager;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Screen.class})
public class ScreenTooltipMixin {
   @Inject(
      method = {"getTooltipFromItem(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void daamky$perUnitPrice(MinecraftClient var0, ItemStack var1, CallbackInfoReturnable<List<Text>> var2) {
      ServerAssistant var3 = ModuleManager.getModule(ServerAssistant.class);
      if (var3 != null && var3.isEnabled()) {
         List var4 = var3.m879(var1, (List<Text>)var2.getReturnValue());
         if (var4 != null) {
            var2.setReturnValue(var4);
         }
      }
   }
}
