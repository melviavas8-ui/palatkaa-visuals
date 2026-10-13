package ua.daamky.mixins.render;

import ua.daamky.features.render.EntityESP;
import ua.daamky.system.api.ModuleManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ArmorFeatureRenderer.class})
public class ArmorFeatureRendererMixin {
   @Inject(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/BipedEntityRenderState;FF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$hideArmorWithChams(
      MatrixStack var1, OrderedRenderCommandQueue var2, int var3, BipedEntityRenderState var4, float var5, float var6, CallbackInfo var7
   ) {
      EntityESP var8 = ModuleManager.getModule(EntityESP.class);
      if (var8 != null && var4 instanceof LivingEntityRenderState && var8.m1037(var4)) {
         var7.cancel();
      }
   }
}
