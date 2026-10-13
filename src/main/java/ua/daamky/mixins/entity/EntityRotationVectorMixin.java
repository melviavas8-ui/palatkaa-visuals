package ua.daamky.mixins.entity;

import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public class EntityRotationVectorMixin {
   @Inject(
      method = {"getRotationVector()Lnet/minecraft/util/math/Vec3d;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void redirectRotationVector(CallbackInfoReturnable<Vec3d> var1) {
      if ((Object)this instanceof ClientPlayerEntity var2 && RotationUtil.m31() && var2.isGliding()) {
         float var3 = RotationUtil.m412(var2.getPitch());
         float var4 = RotationUtil.m411(var2.getYaw());
         float var5 = var3 * (float) (Math.PI / 180.0);
         float var6 = -var4 * (float) (Math.PI / 180.0);
         float var7 = MathHelper.cos((double)var6);
         float var8 = MathHelper.sin((double)var6);
         float var9 = MathHelper.cos((double)var5);
         float var10 = MathHelper.sin((double)var5);
         var1.setReturnValue(new Vec3d((double)(var8 * var9), (double)(-var10), (double)(var7 * var9)));
      }
   }
}
