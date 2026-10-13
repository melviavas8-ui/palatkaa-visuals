package ua.daamky.mixins.entity;

import ua.daamky.events.EntityPushEvent;
import ua.daamky.features.combat.HitBoxes;
import ua.daamky.features.movement.NoPush;
import ua.daamky.system.events.EventBus;
import ua.daamky.utils.player.RaytraceUtil;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public class EntityMixin {
   @Inject(
      method = {"getBoundingBox()Lnet/minecraft/util/math/Box;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void daamky$hitBoxes(CallbackInfoReturnable<Box> var1) {
      HitBoxes var2 = HitBoxes.f1;
      if (var2 != null && var2.isEnabled()) {
         Entity var3 = (Entity)(Object)this;
         if (var3 instanceof PlayerEntity) {
            ClientPlayerEntity var4 = MinecraftClient.getInstance().player;
            if (var4 != null && var3.getId() != var4.getId()) {
               double var5 = var2.m724() / 2.0;
               double var7 = var2.m725();
               if (var5 != 0.0 || var7 != 0.0) {
                  if (!RaytraceUtil.m80(var3.getName().getString())) {
                     Box var9 = (Box)var1.getReturnValue();
                     var1.setReturnValue(new Box(var9.minX - var5, var9.minY, var9.minZ - var5, var9.maxX + var5, var9.maxY + var7, var9.maxZ + var5));
                  }
               }
            }
         }
      }
   }

   @Redirect(
      method = {"updateMovementInFluid(Lnet/minecraft/registry/tag/TagKey;D)Z"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/fluid/FluidState;getVelocity(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/Vec3d;"
      )
   )
   private Vec3d stopWaterVelocity(FluidState var1, BlockView var2, BlockPos var3) {
      return NoPush.f1.isEnabled() && NoPush.f3.m6() ? Vec3d.ZERO : var1.getVelocity(var2, var3);
   }

   @Redirect(
      method = {"updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/Entity;getYaw()F"
      )
   )
   private float correctMovementYaw(Entity var1) {
      return RotationUtil.m31() && var1 instanceof ClientPlayerEntity ? RotationUtil.m411(var1.getYaw()) : var1.getYaw();
   }

   @Inject(
      method = {"updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"},
      at = {@At("TAIL")}
   )
   private void onUpdateVelocityPost(float var1, Vec3d var2, CallbackInfo var3) {
      Entity var4 = (Entity)(Object)this;
      ClientPlayerEntity var5 = MinecraftClient.getInstance().player;
      if (var5 != null && var4.getId() == var5.getId()) {
         EventBus.post(new EntityPushEvent(var1, var2));
      }
   }
}
