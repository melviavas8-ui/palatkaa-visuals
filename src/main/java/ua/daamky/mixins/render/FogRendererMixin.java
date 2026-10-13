package ua.daamky.mixins.render;

import ua.daamky.features.render.CustomFog;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({FogRenderer.class})
public class FogRendererMixin {
   @Inject(
      method = {"getFogColor(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/world/ClientWorld;IF)Lorg/joml/Vector4f;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void applyFogColor(Camera var1, float var2, ClientWorld var3, int var4, float var5, CallbackInfoReturnable<Vector4f> var6) {
      var6.setReturnValue(CustomFog.m1030(var1, (Vector4f)var6.getReturnValue()));
   }
}
