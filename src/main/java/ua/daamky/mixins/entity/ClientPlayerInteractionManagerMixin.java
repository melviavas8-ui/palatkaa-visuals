package ua.daamky.mixins.entity;

import ua.daamky.events.InteractEvent;
import ua.daamky.features.player.NoInteract;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.events.EventBus;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientPlayerInteractionManager.class})
public class ClientPlayerInteractionManagerMixin {
   @Inject(
      method = {"attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void noInteractAttackEntity(PlayerEntity var1, Entity var2, CallbackInfo var3) {
      NoInteract var4 = ModuleManager.getModule(NoInteract.class);
      if (var4 != null && var4.m1040(var2)) {
         var3.cancel();
      }
   }

   @Inject(
      method = {"attackEntity"},
      at = {@At("TAIL")}
   )
   private void attackEntity(PlayerEntity var1, Entity var2, CallbackInfo var3) {
      EventBus.post(new InteractEvent(var1, var2));
   }

   @Inject(
      method = {"interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void interactBlock(ClientPlayerEntity var1, Hand var2, BlockHitResult var3, CallbackInfoReturnable<ActionResult> var4) {
      NoInteract var5 = ModuleManager.getModule(NoInteract.class);
      if (var5 != null && var5.m678(var3.getBlockPos())) {
         var4.setReturnValue(ActionResult.FAIL);
      }
   }

   @Inject(
      method = {"interactEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void interactEntity(PlayerEntity var1, Entity var2, Hand var3, CallbackInfoReturnable<ActionResult> var4) {
      if (RotationUtil.m6()) {
         var4.setReturnValue(ActionResult.PASS);
      } else {
         NoInteract var5 = ModuleManager.getModule(NoInteract.class);
         if (var5 != null && var5.m1040(var2)) {
            var4.setReturnValue(ActionResult.FAIL);
         }
      }
   }

   @Inject(
      method = {"interactEntityAtLocation(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/hit/EntityHitResult;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void interactEntityAtLocation(PlayerEntity var1, Entity var2, EntityHitResult var3, Hand var4, CallbackInfoReturnable<ActionResult> var5) {
      if (RotationUtil.m6()) {
         var5.setReturnValue(ActionResult.PASS);
      } else {
         NoInteract var6 = ModuleManager.getModule(NoInteract.class);
         if (var6 != null && var6.m1040(var2)) {
            var5.setReturnValue(ActionResult.FAIL);
         }
      }
   }
}
