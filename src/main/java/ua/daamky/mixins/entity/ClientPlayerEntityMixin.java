package ua.daamky.mixins.entity;

import ua.daamky.events.PlayerTickEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.events.SlowdownEvent;
import ua.daamky.features.player.CLockSlot;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.events.EventBus;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientPlayerEntity.class})
public class ClientPlayerEntityMixin {
   @Inject(
      method = {"dropSelectedItem(Z)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$lockSlot(boolean var1, CallbackInfoReturnable<Boolean> var2) {
      CLockSlot var3 = ModuleManager.getModule(CLockSlot.class);
      if (var3 != null && var3.isEnabled()) {
         ClientPlayerEntity var4 = (ClientPlayerEntity)(Object)this;
         if (var3.m10(var4.getInventory().getSelectedSlot())) {
            var2.setReturnValue(false);
         }
      }
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("HEAD")}
   )
   private void update(CallbackInfo var1) {
      RotationUtil.m63();
      EventBus.post(new PostMotionEvent());
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("TAIL")}
   )
   private void postMotion(CallbackInfo var1) {
      EventBus.post(new PlayerTickEvent());
   }

   @Redirect(
      method = {"applyMovementSpeedFactors(Lnet/minecraft/util/math/Vec2f;)Lnet/minecraft/util/math/Vec2f;"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
      )
   )
   private boolean applyMovementSpeedFactors(ClientPlayerEntity var1) {
      SlowdownEvent var2 = new SlowdownEvent();
      EventBus.post(var2);
      return var2.isCancelled() ? false : var1.isUsingItem();
   }
}
