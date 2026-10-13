package ua.daamky.features.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.utils.player.RaytraceUtil;
import java.awt.Color;
import java.util.LinkedHashMap;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Wings",
   I00 = "Объёмные драконьи крылья за спиной",
   I000 = Category.RENDER
)
public class Wings extends Module {
   public static Wings f1;
   private static Immediate f2;
   private static final RenderPipeline f3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "wings_solid"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.TRIANGLES)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withCull(false)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderPipeline f4 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "wings_glow"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.TRIANGLES)
         .withBlend(BlendFunction.ADDITIVE)
         .withCull(false)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderLayer f5 = RenderLayer.of(
      "daamky_wings_solid", RenderSetup.builder(f3).translucent().expectedBufferSize(4096).build()
   );
   private static final RenderLayer f6 = RenderLayer.of(
      "daamky_wings_glow", RenderSetup.builder(f4).translucent().expectedBufferSize(4096).build()
   );
   private static final float[][] f7 = new float[][]{
      {0.04F, 0.1F, -0.05F, 1.0F},
      {0.36F, 0.58F, -0.1F, 0.98F},
      {0.73F, 0.42F, 0.02F, 1.0F},
      {1.0F, 0.57F, 0.09F, 0.94F},
      {1.28F, 0.55F, 0.13F, 0.72F},
      {1.02F, 0.33F, 0.1F, 0.94F},
      {1.31F, 0.11F, 0.14F, 0.7F},
      {0.98F, 0.09F, 0.06F, 0.94F},
      {1.19F, -0.3F, 0.08F, 0.68F},
      {0.78F, -0.14F, -0.01F, 0.94F},
      {0.87F, -0.65F, -0.04F, 0.66F},
      {0.2F, -0.42F, -0.08F, 0.84F},
      {1.0F, 0.32F, 0.11F, 0.82F},
      {1.01F, -0.05F, 0.08F, 0.8F},
      {0.82F, -0.35F, 0.0F, 0.78F}
   };
   private static final int[][] f8 = new int[][]{
      {0, 1, 2},
      {2, 3, 4},
      {2, 4, 12},
      {2, 12, 6},
      {2, 6, 5},
      {2, 6, 13},
      {2, 13, 8},
      {2, 8, 7},
      {2, 8, 14},
      {2, 14, 10},
      {2, 10, 9},
      {0, 2, 9},
      {0, 9, 10},
      {0, 10, 11}
   };
   private static final int[] f9 = new int[]{0, 1, 2, 3, 4, 12, 6, 13, 8, 14, 10, 11};
   private static final float[][] f10 = new float[][]{
      {0.0F, 1.0F, 0.052F, 0.044F},
      {1.0F, 2.0F, 0.044F, 0.038F},
      {2.0F, 3.0F, 0.032F, 0.025F},
      {3.0F, 4.0F, 0.025F, 0.012F},
      {2.0F, 5.0F, 0.03F, 0.023F},
      {5.0F, 6.0F, 0.023F, 0.011F},
      {2.0F, 7.0F, 0.028F, 0.021F},
      {7.0F, 8.0F, 0.021F, 0.01F},
      {2.0F, 9.0F, 0.027F, 0.02F},
      {9.0F, 10.0F, 0.02F, 0.01F},
      {0.0F, 11.0F, 0.03F, 0.012F}
   };
   private static final float[][] f11 = new float[][]{
      {0.0F, 0.06F}, {1.0F, 0.055F}, {2.0F, 0.05F}, {3.0F, 0.032F}, {5.0F, 0.03F}, {7.0F, 0.028F}, {9.0F, 0.027F}
   };
   private static final float f12 = 0.012F;
   private static final int f13 = 8;
   private final BooleanSetting f14 = new BooleanSetting("Цвет темы", true);
   private final ColorSetting f15 = new ColorSetting("Цвет", new Color(118, 87, 255));
   private final NumberSetting f16 = new NumberSetting("Прозрачность", 0.72, 0.1, 1.0, 0.05);
   private final NumberSetting f17 = new NumberSetting("Размер", 1.0, 0.75, 1.35, 0.05);
   private final BooleanSetting f18 = new BooleanSetting("На себя", true);
   private final BooleanSetting f19 = new BooleanSetting("На игроков", false);
   private float f20;
   private boolean f21;

   public Wings() {
      f1 = this;
      this.addSettings(new Setting[]{this.f14, this.f15, this.f16, this.f17, this.f18, this.f19});
   }

   @Override
   public void onDisable() {
      this.f21 = false;
      super.onDisable();
   }

   public static void m114(WorldRenderContext var0) {
      if (f1 != null && f1.isEnabled()) {
         f1.m136(var0);
      }
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.player != null && this.mc.world != null) {
         if (f2 == null) {
            LinkedHashMap var2 = new LinkedHashMap();
            var2.put(f5, new BufferAllocator(2097152));
            var2.put(f6, new BufferAllocator(1048576));
            f2 = VertexConsumerProvider.immediate(var2, new BufferAllocator(262144));
         }

         float var8 = this.mc.getRenderTickCounter().getTickProgress(false);
         Vec3d var3 = var1.worldState().cameraRenderState.pos;
         MatrixStack var4 = var1.matrices();
         if (this.f18.m6() && !this.mc.options.getPerspective().isFirstPerson() && this.mc.player.isAlive()) {
            this.m1234(var1, var4, this.mc.player, var8, var3);
         }

         if (this.f19.m6()) {
            for (Entity var6 : this.mc.world.getEntities()) {
               if (var6 instanceof PlayerEntity) {
                  PlayerEntity var7 = (PlayerEntity)var6;
                  if (var7 != this.mc.player && var7.isAlive()) {
                     this.m1234(var1, var4, var7, var8, var3);
                  }
               }
            }
         }

         f2.draw();
      }
   }

   private void m1234(WorldRenderContext var1, MatrixStack var2, PlayerEntity var3, float var4, Vec3d var5) {
      if (!var3.isTouchingWater()) {
         double var6 = MathHelper.lerp((double)var4, var3.lastX, var3.getX()) - var5.x;
         double var8 = MathHelper.lerp((double)var4, var3.lastY, var3.getY()) - var5.y;
         double var10 = MathHelper.lerp((double)var4, var3.lastZ, var3.getZ()) - var5.z;
         float var12 = this.m1243(var3, var4);
         float var13 = MathHelper.clamp(var3.limbAnimator.getSpeed(), 0.0F, 1.0F);
         boolean var14 = var3.isSneaking();
         float var15 = (float)Math.sin((double)(((float)var3.age + var4) * 0.13F)) * (5.0F + var13 * 6.0F);
         float var16 = 22.0F + var15;
         float var17 = (float)this.f17.getValue() * (var14 ? 0.72F : 1.0F);
         int var18 = this.m1242(var3);
         float var19 = (float)this.f16.getValue();
         int var20 = m1244(var18, Math.round(var19 * 255.0F));
         int var21 = m1244(m260(var18, 16777215, 0.42F), Math.round(var19 * 255.0F));
         int var22 = m1244(var18, Math.round(var19 * 68.0F));
         VertexConsumer var23 = f2.getBuffer(f5);
         VertexConsumer var24 = f2.getBuffer(f6);
         var2.push();
         var2.translate(var6, var8, var10);
         var2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - var12));
         var2.translate(0.0F, var14 ? 1.06F : 1.3F, 0.16F);
         var2.scale(var17, var17, var17);
         this.m1235(var2, var23, var24, -1.0F, var16, var20, var21, var22);
         this.m1235(var2, var23, var24, 1.0F, var16, var20, var21, var22);
         var2.pop();
      }
   }

   private void m1235(MatrixStack var1, VertexConsumer var2, VertexConsumer var3, float var4, float var5, int var6, int var7, int var8) {
      var1.push();
      var1.translate(var4 * 0.055F, 0.0F, 0.02F);
      var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var4 * var5));
      var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var4 * -8.0F));
      var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-5.0F));
      Entry var9 = var1.peek();
      this.m1236(var2, var9, var4, var6, var7);
      this.m1239(var2, var9, var4, m1244(var7, Math.round((float)m1009(var7) * 0.9F)), 1.0F);
      this.m1239(var3, var9, var4, var8, 1.55F);
      var1.pop();
   }

   private void m1236(VertexConsumer var1, Entry var2, float var3, int var4, int var5) {
      for (int var6 = 0; var6 < f8.length; var6++) {
         int var7 = m1245(var4, (var6 & 1) == 0 ? 1.0F : 0.88F);
         this.m1237(var1, var2, var3, f8[var6], var7, 0.012F, false);
         this.m1237(var1, var2, var3, f8[var6], m1245(var7, 0.82F), -0.012F, true);
      }

      int var10 = m1245(var4, 0.62F);

      for (int var11 = 0; var11 < f9.length; var11++) {
         float[] var8 = f7[f9[var11]];
         float[] var9 = f7[f9[(var11 + 1) % f9.length]];
         this.m1238(var1, var2, var3, var8, var9, var10);
      }
   }

   private void m1237(VertexConsumer var1, Entry var2, float var3, int[] var4, int var5, float var6, boolean var7) {
      for (int var8 = 0; var8 < 3; var8++) {
         float[] var9 = f7[var4[var7 ? 2 - var8 : var8]];
         m1025(var1, var2, var3 * var9[0], var9[1], var9[2] + var6, var5);
      }
   }

   private void m1238(VertexConsumer var1, Entry var2, float var3, float[] var4, float[] var5, int var6) {
      float var7 = var3 * var4[0];
      float var8 = var4[1];
      float var9 = var4[2] + 0.012F;
      float var10 = var4[2] - 0.012F;
      float var11 = var3 * var5[0];
      float var12 = var5[1];
      float var13 = var5[2] + 0.012F;
      float var14 = var5[2] - 0.012F;
      m1025(var1, var2, var7, var8, var9, var6);
      m1025(var1, var2, var11, var12, var13, var6);
      m1025(var1, var2, var11, var12, var14, var6);
      m1025(var1, var2, var7, var8, var9, var6);
      m1025(var1, var2, var11, var12, var14, var6);
      m1025(var1, var2, var7, var8, var10, var6);
   }

   private void m1239(VertexConsumer var1, Entry var2, float var3, int var4, float var5) {
      for (float[] var9 : f10) {
         this.m1240(var1, var2, var3, f7[(int)var9[0]], f7[(int)var9[1]], var9[2] * var5, var9[3] * var5, var4);
      }

      for (float[] var13 : f11) {
         this.m1241(var1, var2, var3, f7[(int)var13[0]], var13[1] * var5, var4);
      }
   }

   private void m1240(VertexConsumer var1, Entry var2, float var3, float[] var4, float[] var5, float var6, float var7, int var8) {
      float var9 = var3 * var4[0];
      float var10 = var4[1];
      float var11 = var4[2];
      float var12 = var3 * var5[0];
      float var13 = var5[1];
      float var14 = var5[2];
      float var15 = var12 - var9;
      float var16 = var13 - var10;
      float var17 = var14 - var11;
      float var18 = (float)Math.sqrt((double)(var15 * var15 + var16 * var16 + var17 * var17));
      if (!(var18 < 1.0E-4F)) {
         var15 /= var18;
         var16 /= var18;
         var17 /= var18;
         float var20 = -var15;
         float var21 = 0.0F;
         float var22 = (float)Math.sqrt((double)(var16 * var16 + var20 * var20));
         float var19;
         if (var22 < 1.0E-4F) {
            var19 = 1.0F;
            var20 = 0.0F;
         } else {
            var19 = var16 / var22;
            var20 /= var22;
         }

         float var23 = var16 * var21 - var17 * var20;
         float var24 = var17 * var19 - var15 * var21;
         float var25 = var15 * var20 - var16 * var19;

         for (int var26 = 0; var26 < 8; var26++) {
            float var27 = (float)((Math.PI * 2) * (double)var26 / 8.0);
            float var28 = (float)((Math.PI * 2) * (double)(var26 + 1) / 8.0);
            float var29 = (float)Math.cos((double)var27);
            float var30 = (float)Math.sin((double)var27);
            float var31 = (float)Math.cos((double)var28);
            float var32 = (float)Math.sin((double)var28);
            float var33 = var9 + (var19 * var29 + var23 * var30) * var6;
            float var34 = var10 + (var20 * var29 + var24 * var30) * var6;
            float var35 = var11 + (var21 * var29 + var25 * var30) * var6;
            float var36 = var9 + (var19 * var31 + var23 * var32) * var6;
            float var37 = var10 + (var20 * var31 + var24 * var32) * var6;
            float var38 = var11 + (var21 * var31 + var25 * var32) * var6;
            float var39 = var12 + (var19 * var29 + var23 * var30) * var7;
            float var40 = var13 + (var20 * var29 + var24 * var30) * var7;
            float var41 = var14 + (var21 * var29 + var25 * var30) * var7;
            float var42 = var12 + (var19 * var31 + var23 * var32) * var7;
            float var43 = var13 + (var20 * var31 + var24 * var32) * var7;
            float var44 = var14 + (var21 * var31 + var25 * var32) * var7;
            int var45 = m1245(var8, 0.8F + 0.2F * Math.max(0.0F, var29));
            m1025(var1, var2, var33, var34, var35, var45);
            m1025(var1, var2, var39, var40, var41, var45);
            m1025(var1, var2, var42, var43, var44, var45);
            m1025(var1, var2, var33, var34, var35, var45);
            m1025(var1, var2, var42, var43, var44, var45);
            m1025(var1, var2, var36, var37, var38, var45);
         }
      }
   }

   private void m1241(VertexConsumer var1, Entry var2, float var3, float[] var4, float var5, int var6) {
      float var7 = var3 * var4[0];
      float var8 = var4[1];
      float var9 = var4[2];
      float[][] var10 = new float[][]{
         {var7 + var5, var8, var9},
         {var7 - var5, var8, var9},
         {var7, var8 + var5, var9},
         {var7, var8 - var5, var9},
         {var7, var8, var9 + var5},
         {var7, var8, var9 - var5}
      };
      int[][] var11 = new int[][]{{2, 0, 4}, {2, 4, 1}, {2, 1, 5}, {2, 5, 0}, {3, 4, 0}, {3, 1, 4}, {3, 5, 1}, {3, 0, 5}};

      for (int var12 = 0; var12 < var11.length; var12++) {
         int var13 = m1245(var6, var12 < 4 ? 1.0F : 0.78F);

         for (int var17 : var11[var12]) {
            float[] var18 = var10[var17];
            m1025(var1, var2, var18[0], var18[1], var18[2], var13);
         }
      }
   }

   private static void m1025(VertexConsumer var0, Entry var1, float var2, float var3, float var4, int var5) {
      var0.vertex(var1, var2, var3, var4).color(m11(var5), m826(var5), m827(var5), m1009(var5));
   }

   private int m1242(PlayerEntity var1) {
      if (var1 != this.mc.player && RaytraceUtil.m80(var1.getNameForScoreboard())) {
         return -11141121;
      } else {
         Color var2 = this.f14.m6() ? ThemeManager.m1379() : this.f15.m7();
         return 0xFF000000 | var2.getRed() << 16 | var2.getGreen() << 8 | var2.getBlue();
      }
   }

   private float m1243(PlayerEntity var1, float var2) {
      float var3 = MathHelper.lerpAngleDegrees(var2, var1.lastBodyYaw, var1.bodyYaw);
      if (var1 != this.mc.player) {
         return var3;
      } else if (this.f21 && var1.age >= 2) {
         this.f20 = this.f20 + MathHelper.clamp(MathHelper.wrapDegrees(var3 - this.f20), -14.0F, 14.0F);
         return this.f20;
      } else {
         this.f20 = var3;
         this.f21 = true;
         return this.f20;
      }
   }

   private static int m260(int var0, int var1, float var2) {
      int var3 = Math.round((float)MathHelper.lerp(var2, var0 >> 16 & 0xFF, var1 >> 16 & 0xFF));
      int var4 = Math.round((float)MathHelper.lerp(var2, var0 >> 8 & 0xFF, var1 >> 8 & 0xFF));
      int var5 = Math.round((float)MathHelper.lerp(var2, var0 & 0xFF, var1 & 0xFF));
      return var3 << 16 | var4 << 8 | var5;
   }

   private static int m1244(int var0, int var1) {
      return MathHelper.clamp(var1, 0, 255) << 24 | var0 & 16777215;
   }

   private static int m1245(int var0, float var1) {
      int var2 = MathHelper.clamp(Math.round((float)(var0 >> 16 & 0xFF) * var1), 0, 255);
      int var3 = MathHelper.clamp(Math.round((float)(var0 >> 8 & 0xFF) * var1), 0, 255);
      int var4 = MathHelper.clamp(Math.round((float)(var0 & 0xFF) * var1), 0, 255);
      return var0 & 0xFF000000 | var2 << 16 | var3 << 8 | var4;
   }

   private static int m1009(int var0) {
      return var0 >>> 24 & 0xFF;
   }

   private static int m11(int var0) {
      return var0 >>> 16 & 0xFF;
   }

   private static int m826(int var0) {
      return var0 >>> 8 & 0xFF;
   }

   private static int m827(int var0) {
      return var0 & 0xFF;
   }
}
