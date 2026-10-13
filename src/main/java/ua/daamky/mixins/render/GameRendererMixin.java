package ua.daamky.mixins.render;

import ua.daamky.events.RenderEvent;
import ua.daamky.features.render.Hands;
import ua.daamky.features.render.NoRender;
import ua.daamky.features.render.Zoom;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.events.EventBus;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({GameRenderer.class})
public abstract class GameRendererMixin {
   @Unique
   private boolean daamky$capturingChamsHand;

   @Shadow
   private void renderHand(float var1, boolean var2, Matrix4f var3) {
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/RenderTickCounter;Z)V"},
      at = {@At("RETURN")}
   )
   private void render(RenderTickCounter var1, boolean var2, CallbackInfo var3) {
      RotationUtil.m348(var1.getTickProgress(true));
      EventBus.post(new RenderEvent(var1));
   }

   @Inject(
      method = {"getFov(Lnet/minecraft/client/render/Camera;FZ)F"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void daamky$zoomFov(Camera var1, float var2, boolean var3, CallbackInfoReturnable<Float> var4) {
      Zoom var5 = ModuleManager.getModule(Zoom.class);
      if (var5 != null && var5.isEnabled()) {
         float var6 = var5.m2();
         if (var6 != 1.0F) {
            var4.setReturnValue((Float)var4.getReturnValue() * var6);
         }
      }
   }

   @Inject(
      method = {"renderHand(FZLorg/joml/Matrix4f;)V"},
      at = {@At("RETURN")}
   )
   private void daamky$drawChamsHandPost(float var1, boolean var2, Matrix4f var3, CallbackInfo var4) {
      Hands var5 = ModuleManager.getModule(Hands.class);
      if (var5 != null && var5.m1() && !this.daamky$capturingChamsHand) {
         this.daamky$capturingChamsHand = true;

         try {
            var5.m5(() -> this.renderHand(var1, var2, var3));
         } finally {
            this.daamky$capturingChamsHand = false;
         }

         var5.m115();
      }
   }

   @Inject(
      method = {"tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$noRenderHurtCamera(MatrixStack var1, float var2, CallbackInfo var3) {
      if (NoRender.m665()) {
         var3.cancel();
      }
   }

   @Redirect(
      method = {"renderWorld(Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;getEffectFadeFactor(Lnet/minecraft/registry/entry/RegistryEntry;F)F"
      )
   )
   private float daamky$noRenderNauseaProjection(ClientPlayerEntity var1, RegistryEntry<StatusEffect> var2, float var3) {
      return NoRender.m1() && var2 == StatusEffects.NAUSEA ? 0.0F : var1.getEffectFadeFactor(var2, var3);
   }
}
