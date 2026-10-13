package org.palatkaa.mixin.render;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.palatkaa.context.RotationContext;
import org.palatkaa.feature.impl.player.FreeLookFeature;
import org.palatkaa.feature.impl.visual.RemovalsFeature;

@Mixin(Camera.class)
public abstract class CameraMixin {
   @Shadow
   protected abstract void setPosition(double var1, double var3, double var5);

   @Inject(method = "extractRenderState", at = @At("TAIL"))
   private void onExtractRenderState(CameraRenderState cameraState, float cameraEntityPartialTick, CallbackInfo ci) {
      if (RemovalsFeature.shouldRemoveBadEffectsVisuals()) {
         cameraState.entityRenderState.doesMobEffectBlockSky = false;
      }
   }

   @Inject(method = "alignWithEntity", at = @At("HEAD"))
   private void onAlignWithEntity(float partialTick, CallbackInfo ci) {
      Camera camera = (Camera)(Object)this;
      Entity entity = camera.entity();
      if (entity != null) {
         RotationContext.applyRenderInterpolation();
         RotationContext.syncFreeLook(entity.getViewYRot(partialTick), entity.getViewXRot(partialTick));
      }
   }

   @ModifyExpressionValue(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewYRot(F)F"))
   private float palatkaa$freeLookYaw(float original) {
      Float yaw = FreeLookFeature.cameraYaw();
      return yaw != null ? yaw : original;
   }

   @ModifyExpressionValue(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewXRot(F)F"))
   private float palatkaa$freeLookPitch(float original) {
      Float pitch = FreeLookFeature.cameraPitch();
      return pitch != null ? pitch : original;
   }
}

