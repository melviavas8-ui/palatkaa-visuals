package ua.daamky.mixins.render;

import ua.daamky.features.render.CustomFog;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.AtmosphericFogModifier;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AtmosphericFogModifier.class})
public class AtmosphericFogModifierMixin {
   @Inject(
      method = {"applyStartEndModifier(Lnet/minecraft/client/render/fog/FogData;Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/world/ClientWorld;FLnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("RETURN")}
   )
   private void applyFogSettings(FogData var1, Camera var2, ClientWorld var3, float var4, RenderTickCounter var5, CallbackInfo var6) {
      CustomFog.m1029(var1);
   }
}
