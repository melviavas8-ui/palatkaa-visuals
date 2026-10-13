package ua.daamky.mixins.render;

import ua.daamky.features.render.ChinaHat;
import ua.daamky.features.render.EntityESP;
import ua.daamky.features.render.SeeInvisibles;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.utils.CustomFeatureRenderer;
import ua.daamky.utils.RenderQueueBridge;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntityRenderer.class})
public abstract class LivingEntityRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
   @Unique
   private static Immediate daamky$chamsConsumers;
   @Shadow
   protected M model;
   @Shadow
   protected List<FeatureRenderer<S, M>> features;

   @Shadow
   public abstract Identifier getTexture(S var1);

   @Shadow
   protected abstract boolean shouldRenderFeatures(S var1);

   @Inject(
      method = {"getRenderLayer(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/render/RenderLayer;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$hideModelLayer(S var1, boolean var2, boolean var3, boolean var4, CallbackInfoReturnable<RenderLayer> var5) {
      EntityESP var6 = ModuleManager.getModule(EntityESP.class);
      if (var6 != null && var6.m1037(var1)) {
         var5.setReturnValue(null);
      }
   }

   @Inject(
      method = {"isVisible(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$seeInvisibles(S var1, CallbackInfoReturnable<Boolean> var2) {
      SeeInvisibles var3 = ModuleManager.getModule(SeeInvisibles.class);
      if (var3 != null && var3.isEnabled() && var1.invisible && var1 instanceof PlayerEntityRenderState) {
         var2.setReturnValue(true);
      }
   }

   @Inject(
      method = {"getMixColor(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;)I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$seeInvisiblesColor(S var1, CallbackInfoReturnable<Integer> var2) {
      SeeInvisibles var3 = ModuleManager.getModule(SeeInvisibles.class);
      if (var3 != null && var3.isEnabled() && var1.invisible && var1 instanceof PlayerEntityRenderState) {
         int var4 = MathHelper.clamp((int)(var3.m2() * 255.0F), 0, 255);
         var2.setReturnValue(var4 << 24 | 16777215);
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"
      )}
   )
   private void daamky$renderEntityChams(S var1, MatrixStack var2, OrderedRenderCommandQueue var3, CameraRenderState var4, CallbackInfo var5) {
      EntityESP var6 = ModuleManager.getModule(EntityESP.class);
      if (var6 != null && var6.m1036(var1)) {
         var6.m1035(() -> {
            this.model.setAngles(var1);
            if (var6.m665() && var1.entityType == EntityType.PLAYER) {
               if (daamky$chamsConsumers == null) {
                  daamky$chamsConsumers = VertexConsumerProvider.immediate(new BufferAllocator(786432));
               }

               var2.push();
               float var4x = 1.01F;
               var2.scale(var4x, var4x, var4x);
               var2.translate(0.0F, -0.01F, 0.0F);
               VertexConsumer var5x = daamky$chamsConsumers.getBuffer(this.model.getLayer(this.getTexture((S)var1)));
               this.model.render(var2, var5x, var1.light, LivingEntityRenderer.getOverlay(var1, 0.0F), -1);
               daamky$chamsConsumers.draw();
               var2.pop();
            }
         }, var1);
         if (var6.m1()) {
            if (this.model instanceof BipedEntityModel var7) {
               if (EntityESP.f2 == null) {
                  EntityESP.f2 = VertexConsumerProvider.immediate(new BufferAllocator(786432));
               }

               var6.m1044(var2, EntityESP.f2, var7, var1);
            } else {
               if (EntityESP.f4 == null) {
                  EntityESP.f4 = VertexConsumerProvider.immediate(new BufferAllocator(786432));
               }

               VertexConsumer var13 = EntityESP.f4.getBuffer(RenderLayers.entityTranslucentEmissive(this.getTexture((S)var1), false));
               this.model.render(var2, var13, var1.light, LivingEntityRenderer.getOverlay(var1, 0.0F), -16777216);
               if (this.shouldRenderFeatures((S)var1) && !this.features.isEmpty()) {
                  RenderQueueBridge var9 = new RenderQueueBridge(EntityESP.f4);

                  for (FeatureRenderer var11 : this.features) {
                     var11.render(var2, var9, var1.light, var1, var1.relativeHeadYaw, var1.pitch);
                  }
               }
            }
         }

         if (var6 != null && var6.m585() && this.model instanceof BipedEntityModel var12) {
            if (EntityESP.f2 == null) {
               EntityESP.f2 = VertexConsumerProvider.immediate(new BufferAllocator(786432));
            }

            var6.m1043(var2, EntityESP.f2, var12, var1);
         }
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"
      )}
   )
   private void daamky$renderChinaHat(S var1, MatrixStack var2, OrderedRenderCommandQueue var3, CameraRenderState var4, CallbackInfo var5) {
      ChinaHat var6 = ModuleManager.getModule(ChinaHat.class);
      if (var6 != null && var6.isEnabled() && var1 instanceof PlayerEntityRenderState var7) {
         MinecraftClient var8 = MinecraftClient.getInstance();
         if (var8.player != null
            && var7.id == var8.player.getId()
            && !var8.options.getPerspective().isFirstPerson()
            && this.model instanceof BipedEntityModel var9) {
            var6.m1022(var2, var9);
         }
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"
      )}
   )
   private void daamky$renderCosmetics(S var1, MatrixStack var2, OrderedRenderCommandQueue var3, CameraRenderState var4, CallbackInfo var5) {
      if (var1 instanceof PlayerEntityRenderState var6 && this.model instanceof PlayerEntityModel var7) {
         MinecraftClient var8 = MinecraftClient.getInstance();
         if (var8.player != null && var6.id == var8.player.getId() && !var8.options.getPerspective().isFirstPerson()) {
            CustomFeatureRenderer.m500(var2, null, var7, var6, var6.light, var8.getRenderTickCounter().getTickProgress(false));
         }
      }
   }
}
