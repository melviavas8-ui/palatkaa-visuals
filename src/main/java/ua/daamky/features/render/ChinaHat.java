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
import java.awt.Color;
import java.lang.reflect.Field;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@NewFunction(
   I0 = "ChinaHat",
   I00 = "Китайская шляпа на голове",
   I000 = Category.RENDER
)
public class ChinaHat extends Module {
   public static ChinaHat f1;
   public static Immediate f2;
   private static final RenderPipeline f3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "chinahat"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.TRIANGLES)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withCull(false)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderLayer f4 = RenderLayer.of(
      "daamky_chinahat", RenderSetup.builder(f3).translucent().expectedBufferSize(4096).build()
   );
   private final BooleanSetting f5 = new BooleanSetting("Цвет темы", true);
   private final ColorSetting f6 = new ColorSetting("Цвет", new Color(255, 80, 80));
   private final NumberSetting f7 = new NumberSetting("Прозрачность", 0.75, 0.05, 1.0, 0.05);
   private final NumberSetting f8 = new NumberSetting("Радиус", 0.8, 0.3, 1.5, 0.05);
   private final NumberSetting f9 = new NumberSetting("Высота", 0.3, 0.1, 0.8, 0.05);
   private final NumberSetting f10 = new NumberSetting("Позиция Y", 0.0, -0.5, 0.5, 0.05);
   private final BooleanSetting f11 = new BooleanSetting("Ободок", true);
   private static Field f12 = m1028(
      ModelPart.class, "originX", "pivotX", "x"
   );
   private static Field f13 = m1028(
      ModelPart.class, "originY", "pivotY", "y"
   );
   private static Field f14 = m1028(
      ModelPart.class, "originZ", "pivotZ", "z"
   );
   private static Field f15 = m1028(ModelPart.class, "pitch", "xRot");
   private static Field f16 = m1028(ModelPart.class, "yaw", "yRot");
   private static Field f17 = m1028(ModelPart.class, "roll", "zRot");

   public ChinaHat() {
      f1 = this;
      this.addSettings(new Setting[]{this.f5, this.f6, this.f7, this.f8, this.f9, this.f10, this.f11});
      this.f5.m5(this::m115);
      this.m115();
   }

   private void m115() {
      this.f6.setVisible(!this.f5.m6());
   }

   private Color m1021() {
      return this.f5.m6() ? ThemeManager.m1379() : this.f6.m7();
   }

   public void m1022(MatrixStack var1, BipedEntityModel<?> var2) {
      if (f2 == null) {
         f2 = VertexConsumerProvider.immediate(new BufferAllocator(786432));
      }

      int var3 = MathHelper.clamp((int)(this.f7.getValue() * 255.0), 0, 255);
      if (var3 > 0) {
         Color var4 = this.m1021();
         long var5 = System.currentTimeMillis();
         var1.push();
         var1.scale(0.0625F, 0.0625F, 0.0625F);
         this.m1026(var1, var2.head);
         float var7 = -8.0F - (float)(this.f10.getValue() * 16.0);
         float var8 = (float)(this.f9.getValue() * 16.0);
         float var9 = (float)(this.f8.getValue() * 16.0);
         Entry var10 = var1.peek();
         byte var11 = 48;
         VertexConsumer var12 = f2.getBuffer(f4);

         for (int var13 = 0; var13 < var11; var13++) {
            float var14 = (float)((double)var13 * Math.PI * 2.0 / (double)var11);
            float var15 = (float)((double)(var13 + 1) * Math.PI * 2.0 / (double)var11);
            float var16 = (float)Math.cos((double)var14) * var9;
            float var17 = (float)Math.sin((double)var14) * var9;
            float var18 = (float)Math.cos((double)var15) * var9;
            float var19 = (float)Math.sin((double)var15) * var9;
            int var20 = this.m1023(var4, (float)var13 * (360.0F / (float)var11), var5, var3);
            int var21 = this.m1023(var4, (float)(var13 + 1) * (360.0F / (float)var11), var5, var3);
            int var22 = this.m1023(var4, (float)var13 * (360.0F / (float)var11), var5, var3);
            this.m1025(var12, var10, var16, var7, var17, var20);
            this.m1025(var12, var10, var18, var7, var19, var21);
            this.m1025(var12, var10, 0.0F, var7 - var8, 0.0F, var22);
         }

         if (this.f11.m6()) {
            float var26 = var9 * 0.86F;

            for (int var27 = 0; var27 < var11; var27++) {
               float var28 = (float)((double)var27 * Math.PI * 2.0 / (double)var11);
               float var29 = (float)((double)(var27 + 1) * Math.PI * 2.0 / (double)var11);
               float var30 = (float)Math.cos((double)var28) * var9;
               float var31 = (float)Math.sin((double)var28) * var9;
               float var32 = (float)Math.cos((double)var29) * var9;
               float var33 = (float)Math.sin((double)var29) * var9;
               float var34 = (float)Math.cos((double)var28) * var26;
               float var35 = (float)Math.sin((double)var28) * var26;
               float var23 = (float)Math.cos((double)var29) * var26;
               float var24 = (float)Math.sin((double)var29) * var26;
               int var25 = this.m1024(var4, 0.55F, var3);
               this.m1025(var12, var10, var34, var7, var35, var25);
               this.m1025(var12, var10, var30, var7, var31, var25);
               this.m1025(var12, var10, var32, var7, var33, var25);
               this.m1025(var12, var10, var34, var7, var35, var25);
               this.m1025(var12, var10, var32, var7, var33, var25);
               this.m1025(var12, var10, var23, var7, var24, var25);
            }
         }

         var1.pop();
      }
   }

   public static void m114(WorldRenderContext var0) {
      if (f2 != null) {
         f2.draw();
      }
   }

   private int m1023(Color var1, float var2, long var3, int var5) {
      float var6 = (var2 / 360.0F + (float)(var3 % 3000L) / 3000.0F) % 1.0F;
      float var7 = (float)(Math.sin((double)var6 * Math.PI * 2.0) * 0.5 + 0.5);
      return this.m1024(var1, 0.4F * var7, var5);
   }

   private int m1024(Color var1, float var2, int var3) {
      float var4 = 1.0F - MathHelper.clamp(var2, 0.0F, 1.0F);
      int var5 = (int)((float)var1.getRed() * var4);
      int var6 = (int)((float)var1.getGreen() * var4);
      int var7 = (int)((float)var1.getBlue() * var4);
      return var3 << 24 | var5 << 16 | var6 << 8 | var7;
   }

   private void m1025(VertexConsumer var1, Entry var2, float var3, float var4, float var5, int var6) {
      var1.vertex(var2, var3, var4, var5).color(var6 >>> 16 & 0xFF, var6 >>> 8 & 0xFF, var6 & 0xFF, var6 >>> 24 & 0xFF);
   }

   private void m1026(MatrixStack var1, ModelPart var2) {
      var1.translate(this.m1027(var2, f12), this.m1027(var2, f13), this.m1027(var2, f14));
      float var3 = this.m1027(var2, f17);
      if (var3 != 0.0F) {
         var1.multiply(RotationAxis.POSITIVE_Z.rotation(var3));
      }

      float var4 = this.m1027(var2, f16);
      if (var4 != 0.0F) {
         var1.multiply(RotationAxis.POSITIVE_Y.rotation(var4));
      }

      float var5 = this.m1027(var2, f15);
      if (var5 != 0.0F) {
         var1.multiply(RotationAxis.POSITIVE_X.rotation(var5));
      }
   }

   private float m1027(ModelPart var1, Field var2) {
      try {
         return var2 != null ? var2.getFloat(var1) : 0.0F;
      } catch (Exception var4) {
         return 0.0F;
      }
   }

   private static Field m1028(Class<?> var0, String... var1) {
      for (String var5 : var1) {
         try {
            Field var6 = var0.getDeclaredField(var5);
            var6.setAccessible(true);
            return var6;
         } catch (Exception var7) {
         }
      }

      return null;
   }
}
