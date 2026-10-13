package org.palatkaa.mixin.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.palatkaa.event.EventManager;
import org.palatkaa.event.events.input.PlayerVelocityStrafeEvent;
import org.palatkaa.feature.impl.combat.HitBoxesFeature;
import org.palatkaa.feature.impl.movement.NoPushFeature;
import org.palatkaa.utils.combat.aura.AngleConnection;

@Mixin(Entity.class)
public abstract class EntityMixin {
   @Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
   private void cancelEntityPush(Entity entity, CallbackInfo ci) {
      if (NoPushFeature.shouldCancelEntityPush((Entity)(Object)this, entity)) {
         ci.cancel();
      }
   }

   @Inject(method = "getPickRadius", at = @At("RETURN"), cancellable = true)
   private void expandPickRadius(CallbackInfoReturnable<Float> cir) {
      HitBoxesFeature hitBoxes = HitBoxesFeature.getEnabled();
      if (hitBoxes != null && hitBoxes.appliesTo((Entity)(Object)this)) {
         cir.setReturnValue((Float)cir.getReturnValue() + (float)hitBoxes.getHorizontalExpansion());
      }
   }

   @Redirect(
      method = "moveRelative",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/Entity;getInputVector(Lnet/minecraft/world/phys/Vec3;FF)Lnet/minecraft/world/phys/Vec3;"
      ),
      require = 0
   )
   private Vec3 palatkaa$fixMoveRelative(Vec3 movementInput, float speed, float yaw) {
      if (this.palatkaa$isLocalPlayer()) {
         PlayerVelocityStrafeEvent event = EventManager.call(
            new PlayerVelocityStrafeEvent(movementInput, speed, yaw, palatkaa$getInputVector(movementInput, speed, yaw))
         );
         return event.getVelocity();
      } else {
         return palatkaa$getInputVector(movementInput, speed, yaw);
      }
   }

   @ModifyVariable(method = "calculateViewVector(FF)Lnet/minecraft/world/phys/Vec3;", at = @At("HEAD"), ordinal = 0, argsOnly = true)
   private float palatkaa$packetPitch(float pitch) {
      return this.palatkaa$isLocalPlayer() && AngleConnection.INSTANCE.shouldApplyPacketRotation() ? AngleConnection.INSTANCE.getPacketPitch() : pitch;
   }

   @ModifyVariable(method = "calculateViewVector(FF)Lnet/minecraft/world/phys/Vec3;", at = @At("HEAD"), ordinal = 1, argsOnly = true)
   private float palatkaa$packetYaw(float yaw) {
      return this.palatkaa$isLocalPlayer() && AngleConnection.INSTANCE.shouldApplyPacketRotation() ? AngleConnection.INSTANCE.getPacketYaw() : yaw;
   }

   @Unique
   private boolean palatkaa$isLocalPlayer() {
      LocalPlayer player = Minecraft.getInstance().player;
      return player != null && (Object)this == player;
   }

   @Unique
   private static Vec3 palatkaa$getInputVector(Vec3 movementInput, float speed, float yaw) {
      double length = movementInput.lengthSqr();
      if (length < 1.0E-7) {
         return Vec3.ZERO;
      }

      Vec3 scaled = (length > 1.0 ? movementInput.normalize() : movementInput).scale(speed);
      float sin = Mth.sin(yaw * (float) (Math.PI / 180.0));
      float cos = Mth.cos(yaw * (float) (Math.PI / 180.0));
      return new Vec3(scaled.x * cos - scaled.z * sin, scaled.y, scaled.z * cos + scaled.x * sin);
   }
}

