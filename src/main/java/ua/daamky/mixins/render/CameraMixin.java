package ua.daamky.mixins.render;

import ua.daamky.features.misc.FreeCam;
import ua.daamky.features.movement.Speed;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin({Camera.class})
public abstract class CameraMixin {
   @Shadow
   private boolean thirdPerson;

   @ModifyArgs(
      method = {"update(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;ZZF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"
      )
   )
   private void daamky$freeCamRotation(Args var1) {
      FreeCam var2 = FreeCam.m869();
      if (var2 != null && var2.isEnabled()) {
         float var3 = MinecraftClient.getInstance().getRenderTickCounter().getTickProgress(true);
         var1.set(0, var2.m412(var3));
         var1.set(1, var2.m413(var3));
         this.thirdPerson = true;
      }
   }

   @ModifyArgs(
      method = {"update(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;ZZF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"
      )
   )
   private void daamky$freeCamPosition(Args var1) {
      FreeCam var2 = FreeCam.m869();
      MinecraftClient var3 = MinecraftClient.getInstance();
      if (var2 != null && var2.isEnabled()) {
         float var7 = var3.getRenderTickCounter().getTickProgress(true);
         var1.set(0, var2.m871(var7));
         var1.set(1, var2.m872(var7));
         var1.set(2, var2.m873(var7));
      } else {
         Speed var4 = Speed.m1280();
         if (var4 != null && var4.m665()) {
            float var5 = var3.getRenderTickCounter().getTickProgress(true);
            Vec3d var6 = var4.m1048(var5);
            var1.set(0, var6.x);
            var1.set(1, var6.y);
            var1.set(2, var6.z);
         }
      }
   }
}
