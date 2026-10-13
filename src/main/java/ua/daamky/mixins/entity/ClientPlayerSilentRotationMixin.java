package ua.daamky.mixins.entity;

import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ClientPlayerEntity.class})
public class ClientPlayerSilentRotationMixin {
   @Redirect(
      method = {"sendMovementPackets()V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"
      )
   )
   private float getSilentYaw(ClientPlayerEntity var1) {
      return RotationUtil.m411(var1.getYaw());
   }

   @Redirect(
      method = {"sendMovementPackets()V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"
      )
   )
   private float getSilentPitch(ClientPlayerEntity var1) {
      return RotationUtil.m412(var1.getPitch());
   }
}
