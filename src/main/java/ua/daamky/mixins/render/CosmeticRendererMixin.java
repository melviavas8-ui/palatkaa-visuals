package ua.daamky.mixins.render;

import java.util.List;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntityRenderer.class})
public abstract class CosmeticRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
   @Shadow
   protected List<FeatureRenderer<S, M>> features;

   @Inject(
      method = {"<init>"},
      at = {@At("RETURN")}
   )
   private void daamky$addCosmeticRenderer(Context var1, M var2, float var3, CallbackInfo var4) {
   }
}
