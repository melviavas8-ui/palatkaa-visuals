package ua.daamky.mixins.render;

import ua.daamky.features.render.ItemPhysic;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.utils.IItemEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemEntityRenderer.class})
public class ItemEntityRendererMixin {
   @Inject(
      method = {"updateRenderState(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;F)V"},
      at = {@At("TAIL")}
   )
   private void daamky$captureGround(ItemEntity var1, ItemEntityRenderState var2, float var3, CallbackInfo var4) {
      ((IItemEntityRenderState)var2).daamky$setOnGround(var1.isOnGround());
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V"
      )
   )
   private void daamky$translate(MatrixStack var1, float var2, float var3, float var4) {
      if (this.daamky$active()) {
         var3 = 0.0F;
      }

      var1.translate(var2, var3, var4);
   }

   @Redirect(
      method = {"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionfc;)V"
      )
   )
   private void daamky$cancelHover(MatrixStack var1, Quaternionfc var2) {
      if (!this.daamky$active()) {
         var1.multiply(var2);
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/ItemEntityRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/ItemStackEntityRenderState;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/Box;)V",
         shift = Shift.BEFORE
      )}
   )
   private void daamky$applyPhysics(ItemEntityRenderState var1, MatrixStack var2, OrderedRenderCommandQueue var3, CameraRenderState var4, CallbackInfo var5) {
      ItemPhysic var6 = ModuleManager.getModule(ItemPhysic.class);
      if (var6 != null && var6.isEnabled()) {
         if (var6.m687()) {
            var2.scale(0.5F, 0.5F, 0.5F);
         }

         if (((IItemEntityRenderState)var1).daamky$isOnGround()) {
            var2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
         } else {
            float var7 = ItemEntity.getRotation(var1.age, var1.uniqueOffset) * 300.0F;
            var2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var7));
         }
      }
   }

   @Unique
   private boolean daamky$active() {
      ItemPhysic var1 = ModuleManager.getModule(ItemPhysic.class);
      return var1 != null && var1.isEnabled();
   }
}
