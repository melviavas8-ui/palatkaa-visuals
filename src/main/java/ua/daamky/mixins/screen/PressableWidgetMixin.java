package ua.daamky.mixins.screen;

import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PressableWidget.class})
public class PressableWidgetMixin {
   @Inject(
      method = {"drawButton(Lnet/minecraft/client/gui/DrawContext;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$drawLiquidGlassButton(DrawContext var1, CallbackInfo var2) {
      if (MinecraftClient.getInstance().currentScreen instanceof TitleScreen) {
         ClickableWidget var3 = (ClickableWidget)(Object)this;
         float var4 = var3.getAlpha();
         this.daamky$drawGlassButton(var3, var4);
         var2.cancel();
      }
   }

   private void daamky$drawGlassButton(ClickableWidget var1, float var2) {
      float var10 = Render2DUtil.m192();
      float var3 = (float)var1.getX() * var10;
      float var4 = (float)var1.getY() * var10;
      float var5 = (float)var1.getWidth() * var10;
      float var6 = (float)var1.getHeight() * var10;
      boolean var7 = var1.isHovered();
      float var8 = 4.0F * var10;
      Color var9 = var7 ? new Color(255, 255, 255, Math.round(130.0F * var2)) : new Color(210, 218, 225, Math.round(92.0F * var2));
      Render2DUtil.m217(var3 + 0.5F, var4 + 1.2F, var5 - 1.0F, var6 - 0.4F, var8, 9.0F, 0.28F * var2, 2.2F, new Color(0, 0, 0, 170));
      Render2DUtil.m195(var3, var4, var5, var6, var8, new Color(18, 22, 28, Math.round((var7 ? 128.0F : 96.0F) * var2)));
      Render2DUtil.m200(var3, var4, var5, var6, var8, var7 ? 1.0F : 0.9F, var1.active ? 0.94F * var2 : 0.52F * var2, var9);
      Render2DUtil.m202(
         var3 + 0.45F,
         var4 + 0.45F,
         var5 - 0.9F,
         var6 - 0.9F,
         var8,
         0.85F,
         new Color(255, 255, 255, Math.round((var7 ? 128.0F : 76.0F) * var2)),
         new Color(255, 255, 255, Math.round(32.0F * var2))
      );
      Render2DUtil.m195(
         var3 + 6.0F, var4 + 2.0F, var5 - 12.0F, 1.05F, 0.5F, new Color(255, 255, 255, Math.round((var7 ? 74.0F : 48.0F) * var2)), new Color(255, 255, 255, 0)
      );
   }
}
