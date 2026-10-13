package ua.daamky.utils;

import ua.daamky.features.render.CapeManager;
import ua.daamky.utils.render.MaskUtil;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;

public final class CustomFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
   private static Immediate f1;

   public CustomFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> var1) {
      super(var1);
   }

   @Override
   public void render(MatrixStack var1, OrderedRenderCommandQueue var2, int var3, PlayerEntityRenderState var4, float var5, float var6) {
   }

   // Рисует выбранную косметику на локальном игроке сразу (immediate), в пространстве модели игрока.
   public static void m500(MatrixStack var0, Immediate var1, PlayerEntityModel var2, PlayerEntityRenderState var3, int var4, float var5) {
      MinecraftClient var6 = MinecraftClient.getInstance();
      if (var6.player != null && var3.id == var6.player.getId()) {
         List<Integer> var7 = CapeManager.m24();
         if (!var7.isEmpty()) {
            if (var1 == null) {
               if (f1 == null) {
                  f1 = VertexConsumerProvider.immediate(new BufferAllocator(786432));
               }

               var1 = f1;
            }

            var2.setAngles(var3);
            boolean var8 = false;

            for (int var10 : var7) {
               CosmeticModelItem var11 = CapeManager.m478(var10);
               if (var11 != null && var11.m560() != null) {
                  MaskUtil.m569().m570(var11, var6.player, var0, var1, var4, var2, var5);
                  var8 = true;
               }
            }

            if (var8) {
               var1.draw();
            }
         }
      }
   }
}
