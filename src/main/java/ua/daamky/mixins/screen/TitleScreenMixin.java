package ua.daamky.mixins.screen;

import java.util.List;

import ua.daamky.gui.AccountOverlay;
import ua.daamky.gui.TitleBackground;
import ua.daamky.utils.render.Render2D;
import ua.daamky.utils.render.Render2DUtil;
import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TitleScreen.class})
public abstract class TitleScreenMixin extends Screen {
   protected TitleScreenMixin(Text var1) {
      super(var1);
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/TitleScreen;renderPanoramaBackground(Lnet/minecraft/client/gui/DrawContext;F)V"
      )
   )
   private void daamky$renderCustomBackground(TitleScreen var1, DrawContext var2, float var3) {
      TitleBackground.m63();
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")}
   )
   private void daamky$captureContext(DrawContext var1, int var2, int var3, float var4, CallbackInfo var5) {
      AccountOverlay.m1386((TitleScreen)(Object)this);
      AccountOverlay.m1400((TitleScreen)(Object)this);
      AccountOverlay.m1063(var1);
   }

   @Inject(
      method = {"init()V"},
      at = {@At("RETURN")}
   )
   private void daamky$layoutButtons(CallbackInfo var1) {
      TitleScreen var2 = (TitleScreen)(Object)this;
      List<ButtonWidget> var3 = new ArrayList<>();
      AccountOverlay.m1386(var2);

      for (Element var5 : var2.children()) {
         if (var5 instanceof TextIconButtonWidget var6) {
            var6.visible = false;
            var6.active = false;
         } else if (var5 instanceof PressableTextWidget var7) {
            var7.visible = false;
            var7.active = false;
         } else if (var5 instanceof ButtonWidget var8) {
            var3.add(var8);
         }
      }

      // Layout is computed in "virtual" units (half a physical pixel), the same space Render2DUtil draws in,
      // then converted to GUI units, so it looks identical at every GUI scale (1..5, auto).
      float var30 = Render2DUtil.m192();
      float var31 = (float)var2.width * var30;
      float var32 = (float)var2.height * var30;
      float var33 = Math.clamp(Math.min(var32 / 300.0F, var31 / 250.0F), 0.6F, 1.3F);
      float var34 = 212.0F * var33;
      float var35 = 30.0F * var33;
      float var36 = 8.0F * var33;
      float var37 = var35 * 4.0F + var36 * 3.0F;
      float var38 = 100.0F * var33;
      float var39 = (var32 - (var38 + var37)) / 2.0F;
      float var40 = var39 + var38;
      float var41 = var31 / 2.0F - var34 / 2.0F;
      float var42 = (var34 - var36) / 2.0F;
      ButtonWidget var11 = ButtonWidget.builder(Text.literal("Открыть альтменеджер"), var1x -> MinecraftClient.getInstance().setScreen(new Render2D(var2)))
         .dimensions(0, 0, 100, 20)
         .build();
      this.addDrawableChild(var11);
      ButtonWidget var12 = null;
      ButtonWidget var13 = null;
      ButtonWidget var14 = null;
      ButtonWidget var15 = null;

      for (ButtonWidget var17 : var3) {
         String var18 = var17.getMessage().getString().replaceAll("§.", "").toLowerCase();
         if (var18.contains("одиноч") || var18.contains("сингл") || var18.contains("singleplayer") || var18.contains("single")) {
            var12 = var17;
         } else if (var18.contains("сетев") || var18.contains("мульти") || var18.contains("multiplayer") || var18.contains("multi")) {
            var13 = var17;
         } else if (var18.contains("настрой") || var18.contains("options")) {
            var14 = var17;
         } else if (var18.contains("выйти") || var18.contains("выход") || var18.contains("quit")) {
            var15 = var17;
         } else if (var18.contains("realm") || var18.contains("реалм")) {
            var17.visible = false;
            var17.active = false;
         }
      }

      if (var12 != null && var13 != null) {
         this.daamky$place(var12, var41, var40, var34, var35, var30);
         this.daamky$place(var13, var41, var40 + var35 + var36, var34, var35, var30);
         if (var14 != null) {
            this.daamky$place(var14, var41, var40 + 2.0F * (var35 + var36), var42, var35, var30);
         }

         this.daamky$place(var11, var41 + var42 + var36, var40 + 2.0F * (var35 + var36), var42, var35, var30);
         if (var15 != null) {
            this.daamky$place(var15, var41, var40 + 3.0F * (var35 + var36), var34, var35, var30);
         }
      } else {
         for (ButtonWidget var25 : var3) {
            var25.visible = true;
            var25.active = true;
         }

         var11.visible = false;
         var11.active = false;
      }
   }

   private void daamky$place(ButtonWidget var1, float var2, float var3, float var4, float var5, float var6) {
      int var7 = Math.max(20, Math.round(var4 / var6));
      int var8 = Math.max(12, Math.round(var5 / var6));
      var1.setDimensionsAndPosition(var7, var8, Math.round(var2 / var6), Math.round(var3 / var6));
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/LogoDrawer;draw(Lnet/minecraft/client/gui/DrawContext;IF)V"
      )
   )
   private void daamky$hideMinecraftLogo(LogoDrawer var1, DrawContext var2, int var3, float var4) {
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/SplashTextRenderer;render(Lnet/minecraft/client/gui/DrawContext;ILnet/minecraft/client/font/TextRenderer;F)V"
      )
   )
   private void daamky$hideSplashText(SplashTextRenderer var1, DrawContext var2, int var3, TextRenderer var4, float var5) {
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"
      )
   )
   private void daamky$hideVersionText(DrawContext var1, TextRenderer var2, String var3, int var4, int var5, int var6) {
   }
}
