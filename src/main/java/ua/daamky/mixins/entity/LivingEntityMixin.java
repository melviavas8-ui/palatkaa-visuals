package ua.daamky.mixins.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import ua.daamky.features.movement.NoPush;
import ua.daamky.features.render.BeautifulHands;
import ua.daamky.features.render.SwingAnimations;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public class LivingEntityMixin {
   private boolean daamky_modifiedElytraRotations = false;
   private float daamky_prevElytraYaw;
   private float daamky_prevElytraPitch;

   @Inject(
      method = {"isPushable()Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void isPushable(CallbackInfoReturnable<Boolean> var1) {
      if (NoPush.f1.isEnabled() && NoPush.f2.m6()) {
         var1.setReturnValue(false);
      }
   }

   @Inject(
      method = {"getHandSwingDuration()I"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void modifyHandSwingDuration(CallbackInfoReturnable<Integer> var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if ((Object)this == var2.player) {
         SwingAnimations var3 = ModuleManager.getModule(SwingAnimations.class);
         LivingEntity var4 = (LivingEntity)(Object)this;
         if (var3 != null && var3.m687()) {
            var1.setReturnValue(var3.m1267((Integer)var1.getReturnValue(), var4.handSwinging, var4.handSwingTicks));
         } else {
            BeautifulHands var5 = ModuleManager.getModule(BeautifulHands.class);
            if (var5 != null && var5.isEnabled()) {
               var1.setReturnValue(var5.m1009((Integer)var1.getReturnValue()));
            }
         }
      }
   }

   @ModifyExpressionValue(
      method = {"jump"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"
      )}
   )
   private float correctJumpYaw(float var1) {
      return (Object)this == MinecraftClient.getInstance().player && RotationUtil.m31() ? RotationUtil.m411(var1) : var1;
   }

   @Inject(
      method = {"travel(Lnet/minecraft/util/math/Vec3d;)V"},
      at = {@At("HEAD")}
   )
   private void onTravelHead(Vec3d var1, CallbackInfo var2) {
      if ((Object)this instanceof ClientPlayerEntity var3 && RotationUtil.m31() && var3.isGliding()) {
         this.daamky_prevElytraYaw = var3.getYaw();
         this.daamky_prevElytraPitch = var3.getPitch();
         var3.setYaw(RotationUtil.m411(this.daamky_prevElytraYaw));
         var3.setPitch(RotationUtil.m412(this.daamky_prevElytraPitch));
         this.daamky_modifiedElytraRotations = true;
      }
   }

   @Inject(
      method = {"travel(Lnet/minecraft/util/math/Vec3d;)V"},
      at = {@At("RETURN")}
   )
   private void onTravelReturn(Vec3d var1, CallbackInfo var2) {
      if (this.daamky_modifiedElytraRotations && (Object)this instanceof ClientPlayerEntity var3) {
         var3.setYaw(this.daamky_prevElytraYaw);
         var3.setPitch(this.daamky_prevElytraPitch);
         this.daamky_modifiedElytraRotations = false;
      }
   }
}
