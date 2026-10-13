package ua.daamky.mixins.screen;

import ua.daamky.gui.AccountOverlay;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({DrawContext.class})
public class DrawContextMixin {
   @Inject(
      method = {"drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$hideTitleAccountString(TextRenderer var1, String var2, int var3, int var4, int var5, CallbackInfo var6) {
      if (AccountOverlay.m20(var2)) {
         var6.cancel();
      }
   }

   @Inject(
      method = {"drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$hideTitleAccountText(TextRenderer var1, Text var2, int var3, int var4, int var5, CallbackInfo var6) {
      if (AccountOverlay.m20(var2.getString())) {
         var6.cancel();
      }
   }
}
