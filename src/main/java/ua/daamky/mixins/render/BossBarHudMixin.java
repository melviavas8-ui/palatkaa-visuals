package ua.daamky.mixins.render;

import ua.daamky.features.render.Interface;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BossBarHud.class})
public class BossBarHudMixin {
   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$cancelDefaultBossBar(DrawContext var1, CallbackInfo var2) {
      if (Interface.m687()) {
         var2.cancel();
      }
   }
}
