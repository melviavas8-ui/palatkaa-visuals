package ua.daamky.mixins.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Text.class})
public class TextButtonWidgetMixin {
   @Inject(
      method = {"drawIcon(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$drawBoldTitleButton(DrawContext var1, int var2, int var3, float var4, CallbackInfo var5) {
      if (MinecraftClient.getInstance().currentScreen instanceof TitleScreen) {
         var5.cancel();
      }
   }
}
