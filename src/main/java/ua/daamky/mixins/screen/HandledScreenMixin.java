package ua.daamky.mixins.screen;

import ua.daamky.features.misc.ServerAssistant;
import ua.daamky.features.render.ShulkerPreview;
import ua.daamky.system.api.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HandledScreen.class})
public class HandledScreenMixin {
   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("TAIL")}
   )
   private void daamky$shulkerPreview(DrawContext var1, int var2, int var3, float var4, CallbackInfo var5) {
      ShulkerPreview var6 = ModuleManager.getModule(ShulkerPreview.class);
      if (var6 != null && var6.isEnabled()) {
         var6.m1261(var1, (HandledScreen<?>)(Object)this, var2, var3);
      }

      ServerAssistant var7 = ModuleManager.getModule(ServerAssistant.class);
      if (var7 != null && var7.isEnabled()) {
         var7.m878(var1, (HandledScreen<?>)(Object)this);
      }
   }
}
