package ua.daamky.mixins.render;

import ua.daamky.features.movement.Speed;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PlayerEntityRenderer.class})
public class SilentRotationRenderMixin {
   @Inject(
      method = {"updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V"},
      at = {@At("TAIL")}
   )
   private void updateSilentRenderState(PlayerLikeEntity var1, PlayerEntityRenderState var2, float var3, CallbackInfo var4) {
      if (var1 == MinecraftClient.getInstance().player) {
         Speed var5 = Speed.m1280();
         if (var5 != null && var5.m665()) {
            Vec3d var6 = var5.m1281(var3);
            var2.x = var6.x;
            var2.y = var6.y;
            var2.z = var6.z;
         }

         if (RotationUtil.m6()) {
            float var7 = RotationUtil.m413(var2.bodyYaw);
            var2.bodyYaw = var7;
            var2.relativeHeadYaw = 0.0F;
            var2.pitch = RotationUtil.m414(var2.pitch);
         }
      }
   }
}
