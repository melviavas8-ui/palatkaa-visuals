package ua.daamky.features.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.utils.render.ColorUtil;
import ua.daamky.utils.render.shaders.Sampler0;
import ua.daamky.utils.render.shaders.Sampler0$2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@NewFunction(
   I0 = "TargetESP",
   I00 = "Ghost glow around aimed target",
   I000 = Category.RENDER
)
public class TargetESP extends Module {
   private static final Identifier f1 = Identifier.of(
      "daamky", "images/particles/glow.png"
   );
   private static final Identifier f2 = Identifier.of(
      "daamky", "images/targetesp/target.png"
   );
   private static final Identifier f3 = Identifier.of(
      "minecraft", "textures/block/white_concrete.png"
   );
   private static final Identifier f4 = Identifier.of(
      "daamky", "images/targetesp/skull_state_0.png"
   );
   private static final Identifier f5 = Identifier.of(
      "daamky", "images/targetesp/skull_state_1.png"
   );
   private static final Identifier f6 = Identifier.of(
      "daamky", "images/targetesp/skull_state_2.png"
   );
   private static final Identifier f7 = Identifier.of(
      "daamky", "images/targetesp/capture2.png"
   );
   private static final Identifier f8 = Identifier.of(
      "daamky", "images/targetesp/targetpro.png"
   );
   private static final RenderPipeline f9 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "targetesp_cubes_lines"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthWrite(false)
         .build()
   );
   public static final RenderLayer f10 = RenderLayer.of(
      "daamky_targetesp_cubes_lines",
      RenderSetup.builder(f9).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   private static final RenderPipeline f11 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "targetesp_cubes_fill"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final RenderLayer f12 = RenderLayer.of(
      "daamky_targetesp_cubes_fill", RenderSetup.builder(f11).translucent().expectedBufferSize(1536).build()
   );
   private static final RenderPipeline f13 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "targetesp_cubes_lines_nodepth"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthWrite(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .build()
   );
   public static final RenderLayer f14 = RenderLayer.of(
      "daamky_targetesp_cubes_lines_nodepth",
      RenderSetup.builder(f13).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   private static final RenderPipeline f15 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "targetesp_cubes_fill_nodepth"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthWrite(false)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .build()
   );
   private static final RenderLayer f16 = RenderLayer.of(
      "daamky_targetesp_cubes_fill_nodepth", RenderSetup.builder(f15).translucent().expectedBufferSize(1536).build()
   );
   public static TargetESP f17;
   public final ModeSettingBase f18 = new ModeSettingBase("Режим", m1203());
   public final ModeSettingBase f19 = new ModeSettingBase(
      "Модель",
      "Обычные",
      "Nursultan",
      "Rock"
   );
   public final BooleanSetting f20 = new BooleanSetting("Краснеть", true);
   public final BooleanSetting f21 = new BooleanSetting("Через стены", true);
   public final NumberSetting f22 = new NumberSetting("Радиус", 0.72, 0.35, 1.6, 0.05);
   public final NumberSetting f23 = new NumberSetting("Размер", 0.48, 0.15, 1.1, 0.05);
   public final NumberSetting f24 = new NumberSetting("Скорость", 120.0, 30.0, 300.0, 5.0);
   public final NumberSetting f25 = new NumberSetting("Длина хвоста", 7.0, 1.0, 20.0, 1.0);
   public final NumberSetting f26 = new NumberSetting("Размер ромба", 0.48, 0.15, 1.1, 0.05);
   public final NumberSetting f27 = new NumberSetting("Скорость ромба", 120.0, 30.0, 300.0, 5.0);
   public final ModeSettingBase f28 = new ModeSettingBase(
      "Текстура ромба",
      "Target",
      "Capture2",
      "TargetPro"
   );
   public final NumberSetting f29 = new NumberSetting("Размер черепа", 0.6, 0.1, 1.5, 0.05);
   public final NumberSetting f30 = new NumberSetting("Размер цепей", 0.18, 0.08, 0.6, 0.01);
   public final NumberSetting f31 = new NumberSetting("Скорость цепей", 55.0, 5.0, 120.0, 1.0);
   public final NumberSetting f32 = new NumberSetting("Радиус цепей", 1.15, 0.6, 2.5, 0.05);
   public final NumberSetting f33 = new NumberSetting("Прозрачность кубов", 0.5, 0.1, 1.0, 0.05);
   public final NumberSetting f34 = new NumberSetting("Затухание", 1.0, 0.0, 1.0, 0.01);
   public final NumberSetting f35 = new NumberSetting("Размер кристаллов", 2.0, 0.5, 3.0, 0.1);
   public final NumberSetting f36 = new NumberSetting("Свечение кристаллов", 1.0, 0.0, 3.0, 0.1);
   private Entity f37;
   private float f38;
   private long f39;
   private float f40 = 0.0F;
   private int f41 = 0;
   private float f42;
   private static final float[] f43 = new float[]{1.0F, 0.8F, 0.6F, 0.9F, 0.7F, 0.5F, 0.4F, 0.6F};

   public TargetESP() {
      f17 = this;
      this.addSettings(
         new Setting[]{
            this.f18,
            this.f19,
            this.f20,
            this.f21,
            this.f22,
            this.f23,
            this.f24,
            this.f25,
            this.f26,
            this.f27,
            this.f28,
            this.f29,
            this.f30,
            this.f31,
            this.f32,
            this.f33,
            this.f34,
            this.f35,
            this.f36
         }
      );
      this.f18.m5(this::m676);
      this.f19.m5(this::m676);
      this.m676();
   }

   @Override
   public void onDisable() {
      this.f37 = null;
      this.f38 = 0.0F;
      this.f39 = 0L;
      this.f40 = 0.0F;
      this.f41 = 0;
      this.f42 = 0.0F;
   }

   public static void m114(WorldRenderContext var0) {
      if (f17 != null && f17.isEnabled()) {
         f17.m136(var0);
      }
   }

   private void m136(WorldRenderContext var1) {
      this.m676();
      long var2 = System.nanoTime();
      float var4 = this.f39 == 0L ? 0.0F : Math.min((float)(var2 - this.f39) / 1.0E9F, 0.05F);
      this.f39 = var2;
      Entity var5 = this.m1212();
      if (var5 != null) {
         this.f37 = var5;
      } else if (!this.m1040(this.f37)) {
         this.f37 = null;
      }

      this.f38 = this.m334(this.f38, var5 != null ? 1.0F : 0.0F, var4 * 6.5F);
      if (this.f37 != null && !(this.f38 <= 0.01F)) {
         if (this.f37 instanceof LivingEntity var6) {
            if (var6.hurtTime > this.f41 && var6.hurtTime > 0) {
               this.f40 = 1.0F;
            }

            this.f41 = var6.hurtTime;
         }

         this.f40 = Math.max(0.0F, this.f40 - var4 * 3.5F);
         Color var19 = ThemeManager.m1379();
         if (this.f20.m6() && this.f40 > 0.0F) {
            var19 = new Color(ColorUtil.m260(var19.getRGB(), Color.RED.getRGB(), this.f40));
         }

         float var20 = this.mc.getRenderTickCounter().getTickProgress(false);
         Vec3d var8 = this.f37.getLerpedPos(var20);
         float var9 = this.m3(this.f38);
         if (this.f18.m17("Кристаллы")) {
            this.m1208(var1, var8, (double)this.f37.getHeight(), var19, var9);
         } else if (this.f18.m17("Кольцо test 2")) {
            this.m1206(var1, var8, (double)this.f37.getHeight(), var19, var9);
         } else if (this.f18.m17("Кольцо test")) {
            this.m1207(var1, var8, (double)this.f37.getHeight(), var19, var9);
         } else if (this.f18.m17("Cylinder")) {
            this.m1211(var1, var8, (double)this.f37.getHeight(), var19, var9);
         } else if (this.f18.m17("Кубы")) {
            this.m1223(var1, var8, (double)this.f37.getHeight(), var19, var9, var2);
         } else if (this.f18.m17("Кресты")) {
            this.m1224(var1, var8, (double)this.f37.getHeight(), var19, var9, var2);
         } else if (this.f18.m17("Череп")) {
            this.m1219(var1, var8, (double)this.f37.getHeight(), this.m11(var19.getRed()), this.m11(var19.getGreen()), this.m11(var19.getBlue()), var9);
         } else if (this.f18.m17("Ромб")) {
            this.m1220(
               var1,
               var8,
               (double)this.f37.getHeight(),
               this.m1221(var2, this.f27.getValue()),
               (float)this.f26.getValue(),
               this.m11(var19.getRed()),
               this.m11(var19.getGreen()),
               this.m11(var19.getBlue()),
               var9
            );
         } else if (this.f19.m17("Nursultan")) {
            this.m1214(var1, var8, (double)this.f37.getHeight(), var19, var9);
         } else if (this.f19.m17("Rock")) {
            this.m1217(var1, var8, (double)this.f37.getHeight(), var19, var9);
         } else {
            int var10 = this.m1009(var19.getRed());
            int var11 = this.m1009(var19.getGreen());
            int var12 = this.m1009(var19.getBlue());
            double var13 = this.f22.getValue();
            float var15 = (float)this.f23.getValue();
            float var16 = this.m1221(var2, this.f24.getValue());
            Sampler0$2 var17 = this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);

            for (int var18 = 0; var18 < 3; var18++) {
               this.m1213(var17, var8, (double)this.f37.getHeight(), var16 + (float)var18 * 120.0F, var13, var15, var10, var11, var12, var9);
            }
         }
      }
   }

   private static String[] m1203() {
      ArrayList<String> var0 = new ArrayList<>(
         List.of(
            "Призраки",
            "Ромб",
            "Cylinder",
            "Кубы",
            "Череп",
            "Кресты",
            "Кристаллы",
            "Кольцо test"
         )
      );
      return var0.toArray(new String[0]);
   }

   private Sampler0$2 m1204(WorldRenderContext var1) {
      return this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);
   }

   private Color m1205(Color var1, double var2) {
      float var4 = (float)((Math.sin(Math.toRadians(var2)) + 1.0) * 0.5);
      int var5 = Math.round((float)var1.getRed() * (0.5F + 0.5F * var4));
      int var6 = Math.round((float)var1.getGreen() * (0.5F + 0.5F * var4));
      int var7 = Math.round((float)var1.getBlue() * (0.5F + 0.5F * var4));
      return new Color(var5, var6, var7);
   }

   private void m1206(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6) {
      this.f42 += 4.0F;
      if (this.f42 >= 540.0F) {
         this.f42 -= 540.0F;
      }

      if (!(var6 <= 0.0F)) {
         float var7 = this.m151(var6);
         float var8 = this.f37.getWidth() * 1.65F;
         float var9 = Math.max(0.1F, this.f37.getHeight() - 0.15F);
         float var10 = Math.max(0.5F, 0.7F - 0.2F * var7);
         int var11 = var5.getRed();
         int var12 = var5.getGreen();
         int var13 = var5.getBlue();
         Sampler0$2 var14 = this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);

         for (byte var15 = 0; var15 < 360; var15 += 2) {
            double var16 = Math.toRadians((double)((float)var15 + this.f42));
            double var18 = Math.cos(var16) * (double)var8 * (double)var10;
            double var20 = Math.sin(var16) * (double)var8 * (double)var10;

            for (int var22 = 0; var22 < 15; var22++) {
               double var23 = (double)(var9 / 1.7F) + (double)(var9 / 2.0F) * Math.cos(Math.toRadians((double)(this.f42 / 1.5F + (float)var22 * 2.0F)));
               float var25 = var7 * ((float)var22 / 15.0F) * 0.05F;
               if (!(var25 <= 0.001F)) {
                  var14.m163(var2.add(var18, var23, var20), 0.2F, 0.0F, var11, var12, var13, var25);
               }
            }
         }

         for (byte var26 = 0; var26 < 360; var26 += 2) {
            double var27 = Math.toRadians((double)((float)var26 + this.f42));
            double var28 = Math.cos(var27) * (double)var8 * (double)var10;
            double var29 = Math.sin(var27) * (double)var8 * (double)var10;
            double var30 = (double)(var9 / 1.75F) + (double)(var9 / 2.0F) * Math.cos(Math.toRadians((double)(this.f42 / 1.5F + 30.0F)));
            var14.m163(var2.add(var28, var30, var29), 0.2F, 0.0F, var11, var12, var13, var7 * 0.2F);
         }
      }
   }

   private void m1207(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6) {
      Sampler0$2 var7 = this.m1204(var1);
      double var8 = 2000.0;
      double var10 = (double)System.currentTimeMillis() % var8;
      double var12 = var10 / (var8 / 2.0);
      var12 = var10 > var8 / 2.0 ? var12 - 1.0 : 1.0 - var12;
      var12 = var12 < 0.5 ? 2.0 * var12 * var12 : 1.0 - Math.pow(-2.0 * var12 + 2.0, 2.0) / 2.0;
      double var14 = Math.max(0.35, (double)this.f37.getWidth() * 0.8);
      double var16 = var2.y + var3 * var12;
      double var18 = var10 > var8 / 2.0 ? -1.0 : 1.0;
      double var20 = var3 / 2.0 * (var12 > 0.5 ? 1.0 - var12 : var12) * var18;
      byte var22 = 110;
      byte var23 = 9;
      double var24 = (Math.PI * 2) / (double)var22;

      for (int var26 = 0; var26 < var22; var26++) {
         double var27 = var24 * (double)var26;
         double var29 = Math.cos(var27);
         double var31 = Math.sin(var27);
         Color var33 = this.m1205(var5, Math.toDegrees(var27) * 5.0);
         int var34 = this.m11(var33.getRed());
         int var35 = this.m11(var33.getGreen());
         int var36 = this.m11(var33.getBlue());
         var7.m163(new Vec3d(var2.x + var29 * var14, var16, var2.z + var31 * var14), 0.16F, 0.0F, var34, var35, var36, var6 * 0.85F);

         for (int var37 = 1; var37 <= var23; var37++) {
            float var38 = (float)var37 / (float)var23;
            float var39 = (1.0F - var38) * (1.0F - var38);
            float var40 = var6 * var39 * 0.32F;
            if (!(var40 <= 0.004F)) {
               var7.m163(new Vec3d(var2.x + var29 * var14, var16 + var20 * (double)var38, var2.z + var31 * var14), 0.13F, 0.0F, var34, var35, var36, var40);
            }
         }
      }
   }

   private void m1208(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6) {
      float var7 = (float)this.f34.getValue();
      float var8 = var6 * var7;
      if (!(var8 <= 0.01F)) {
         float var9 = this.f37.getWidth() * 1.46F;
         float var10 = (float)var3;
         int var11 = (int)(var9 + var10 * 9.0F);
         if (var11 > 0) {
            Random var12 = new Random((long)this.f37.getId() * 133769420L);
            byte var13 = 15;
            float[][] var14 = new float[var11][];
            int var15 = 0;

            for (int var16 = 0; var16 < var11; var16++) {
               float[] var17 = null;
               float var18 = -1.0F;

               for (int var19 = 0; var19 < var13; var19++) {
                  float var20 = var12.nextFloat() * 360.0F;
                  float var21 = var12.nextFloat() * var10;
                  float var22 = (float)Math.toRadians((double)var20);
                  float var23 = (float)(Math.sin((double)var22) * (double)var9);
                  float var24 = (float)(Math.cos((double)var22) * (double)var9);
                  if (var15 == 0) {
                     var17 = new float[]{var23, var21, var24};
                     break;
                  }

                  float var25 = Float.MAX_VALUE;

                  for (int var26 = 0; var26 < var15; var26++) {
                     float[] var27 = var14[var26];
                     float var28 = var27[0] - var23;
                     float var29 = var27[1] - var21;
                     float var30 = var27[2] - var24;
                     float var31 = var28 * var28 + var29 * var29 + var30 * var30;
                     if (var31 < var25) {
                        var25 = var31;
                     }
                  }

                  if (var25 > var18) {
                     var18 = var25;
                     var17 = new float[]{var23, var21, var24};
                  }
               }

               if (var17 != null) {
                  var14[var15++] = var17;
               }
            }

            float var45 = (float)(System.currentTimeMillis() % 360000L) / 7.25F;
            float var46 = (float)Math.toRadians((double)var45);
            float var47 = (float)Math.sin((double)var46);
            float var48 = (float)Math.cos((double)var46);
            float var49 = 1.25F - 0.5F * var8;
            float var50 = (float)System.currentTimeMillis() * 0.001F;
            float var51 = 0.11F * (float)this.f35.getValue();
            Vec3d var52 = var2.add(0.0, var3 * 0.5, 0.0);
            Vec3d[] var53 = new Vec3d[var15];

            for (int var54 = 0; var54 < var15; var54++) {
               float[] var56 = var14[var54];
               float var58 = var56[0] * var48 - var56[2] * var47;
               float var60 = var56[0] * var47 + var56[2] * var48;
               float var62 = (float)(0.05F * Math.sin((double)(var50 * 2.0F + (float)var54 * 1337.0F)));
               var53[var54] = var2.add((double)(var58 * var49), (double)(var56[1] + var62), (double)(var60 * var49));
            }

            float var55 = (float)this.f36.getValue();
            Sampler0$2 var57 = this.m1204(var1);
            float var59 = 0.8F + 0.2F * (float)Math.sin((double)(var50 * 3.2F));
            float var61 = var51 * 4.6F * var55;
            float var63 = var51 * 2.34F * (0.85F + 0.35F * var55);
            float var64 = var51 * 0.765F;
            int var65 = var5.getRed();
            int var32 = var5.getGreen();
            int var33 = var5.getBlue();

            for (Vec3d var37 : var53) {
               if (var55 > 0.01F) {
                  var57.m163(var37, var61, 0.0F, var65, var32, var33, var8 * 0.12F * var55 * var59);
               }

               var57.m163(var37, var63, 0.0F, var65, var32, var33, var8 * (0.2F + 0.22F * var55) * var59);
               var57.m163(var37, var64, 0.0F, var65, var32, var33, var8);
            }

            VertexConsumer var66 = var1.consumers().getBuffer(Sampler0.f4.apply(f3));
            Vec3d var67 = var1.worldState().cameraRenderState.pos;
            int var68 = (int)(Math.clamp(var8, 0.0F, 1.0F) * 255.0F);

            for (Vec3d var40 : var53) {
               Vec3d var41 = var52.subtract(var40);
               double var42 = var41.length();
               Quaternionf var44 = new Quaternionf();
               if (var42 > 0.001) {
                  var44.rotateTo(0.0F, 1.0F, 0.0F, (float)(var41.x / var42), (float)(var41.y / var42), (float)(var41.z / var42));
               }

               this.m1209(var66, var67, var40, var44, var51, var5.getRed(), var5.getGreen(), var5.getBlue(), var68);
            }
         }
      }
   }

   private void m1209(VertexConsumer var1, Vec3d var2, Vec3d var3, Quaternionf var4, float var5, int var6, int var7, int var8, int var9) {
      float[][] var10 = new float[][]{
         {0.0F, 0.72F, 0.0F}, {0.0F, -0.72F, 0.0F}, {0.27F, 0.0F, 0.27F}, {-0.27F, 0.0F, -0.27F}, {0.27F, 0.0F, -0.27F}, {-0.27F, 0.0F, 0.27F}
      };

      for (int var11 = 0; var11 < 8; var11++) {
         float var12 = f43[var11 % f43.length];
         int var13 = (int)((float)var6 * var12);
         int var14 = (int)((float)var7 * var12);
         int var15 = (int)((float)var8 * var12);
         byte var16;
         int var17;
         int var18;
         if (var11 < 4) {
            var16 = 0;
            var17 = 2 + var11 % 4;
            var18 = 2 + (var11 + 1) % 4;
         } else {
            var16 = 1;
            var17 = 2 + (var11 - 3) % 4;
            var18 = 2 + (var11 - 4) % 4;
         }

         this.m1210(var1, var2, var3, var4, var5, var10[var16], 0.0F, 0.0F, var13, var14, var15, var9);
         this.m1210(var1, var2, var3, var4, var5, var10[var17], 1.0F, 0.0F, var13, var14, var15, var9);
         this.m1210(var1, var2, var3, var4, var5, var10[var18], 1.0F, 1.0F, var13, var14, var15, var9);
         this.m1210(var1, var2, var3, var4, var5, var10[var18], 0.0F, 1.0F, var13, var14, var15, var9);
      }
   }

   private void m1210(
      VertexConsumer var1,
      Vec3d var2,
      Vec3d var3,
      Quaternionf var4,
      float var5,
      float[] var6,
      float var7,
      float var8,
      int var9,
      int var10,
      int var11,
      int var12
   ) {
      Vector3f var13 = new Vector3f(var6[0] * var5, var6[1] * var5, var6[2] * var5).rotate(var4);
      var1.vertex((float)(var3.x + (double)var13.x - var2.x), (float)(var3.y + (double)var13.y - var2.y), (float)(var3.z + (double)var13.z - var2.z))
         .color(var9, var10, var11, var12)
         .texture(var7, var8)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 1.0F, 0.0F);
   }

   private void m1211(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6) {
      Sampler0$2 var7 = this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);
      long var8 = System.nanoTime();
      short var10 = 180;
      byte var11 = 50;
      double var12 = Math.max((double)(this.f37.getWidth() * 0.9F), 0.42);
      double var14 = 0.1;
      double var16 = Math.max(var14 + 0.1, var3 - 0.04);
      double var18 = var16 - var14;
      double var20 = 0.62;
      double var22 = (double)var8 / 1.0E9 * var20;
      double var24 = var14 + (Math.sin(var22 * Math.PI) + 1.0) * 0.5 * var18;
      double var26 = -Math.cos(var22 * Math.PI) * 0.82;
      int var28 = this.m11(var5.getRed());
      int var29 = this.m11(var5.getGreen());
      int var30 = this.m11(var5.getBlue());
      double var31 = (Math.PI * 2) / (double)var10;
      double var33 = var18 * 0.8;

      for (int var35 = 0; var35 < var11; var35++) {
         float var36 = (float)var35 / (float)(var11 - 1);
         double var37 = var24 + var26 * var33 * (double)var36;
         if (!(var37 < var14 - 0.15) && !(var37 > var16 + 0.15)) {
            float var39 = 1.0F - var36;
            float var40 = var6 * var39 * var39 * 0.25F;
            if (!(var40 <= 0.004F)) {
               float var41 = 0.17F * (0.72F + var39 * 0.28F) - 0.2F;

               for (int var42 = 0; var42 < var10; var42++) {
                  double var43 = var31 * (double)var42;
                  var7.m163(var2.add(Math.cos(var43) * var12, var37, Math.sin(var43) * var12), var41, 0.0F, var28, var29, var30, var40);
               }
            }
         }
      }
   }

   private Entity m1212() {
      if (this.mc.player == null || this.mc.world == null) {
         return null;
      }
      // Target from crosshair (independent of Aura / TriggerBot)
      if (this.mc.crosshairTarget != null && this.mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
         EntityHitResult hit = (EntityHitResult) this.mc.crosshairTarget;
         Entity entity = hit.getEntity();
         if (this.m1040(entity)) {
            return entity;
         }
      }
      return null;
   }

   private boolean m1040(Entity var1) {
      return var1 != null && var1 != this.mc.player && var1.isAlive();
   }

   private void m676() {
      boolean var1 = this.f18.m17("Ромб");
      boolean var2 = this.f18.m17("Cylinder");
      boolean var3 = this.f18.m17("Chain");
      boolean var4 = this.f18.m17("Кубы");
      boolean var5 = this.f18.m17("Череп");
      boolean var6 = this.f18.m17("Кресты");
      boolean var7 = this.f18.m17("Ghost Rock");
      boolean var8 = this.f18.m17("Кристаллы");
      boolean var9 = this.f18.m17("Кольцо test 2");
      boolean var10 = this.f18.m17("Кольцо test");
      boolean var11 = !var1 && !var2 && !var3 && !var4 && !var5 && !var6 && !var7 && !var8 && !var9 && !var10;
      boolean var12 = this.f18.m17("Призраки");
      this.f19.setVisible(var12);
      boolean var13 = var11 && (!var12 || this.f19.m17("Обычные"));
      this.f22.setVisible(var13);
      this.f23.setVisible(var13);
      this.f24.setVisible(var11);
      this.f25.setVisible(var13);
      this.f26.setVisible(var1);
      this.f27.setVisible(var1);
      this.f28.setVisible(var1);
      this.f29.setVisible(var5);
      this.f30.setVisible(var3);
      this.f31.setVisible(var3);
      this.f32.setVisible(var3);
      this.f33.setVisible(var4 || var6);
      this.f34.setVisible(var8);
      this.f35.setVisible(var8);
      this.f36.setVisible(var8);
   }

   private void m1213(Sampler0$2 var1, Vec3d var2, double var3, float var5, double var6, float var8, int var9, int var10, int var11, float var12) {
      int var13 = Math.max(1, (int)Math.round(this.f25.getValue()));

      for (int var14 = var13 - 1; var14 >= 0; var14--) {
         float var15 = var13 <= 1 ? 0.0F : (float)var14 / (float)(var13 - 1);
         float var16 = var5 - (float)var14 * 7.0F;
         Vec3d var17 = this.m1222(var2, var3, var16, var6, var15);
         float var18 = var8 * (1.0F - var15 * 0.48F) * (0.45F + var12 * 0.55F);
         float var19 = var12 * (1.0F - var15 * 0.74F);
         if (!(var19 <= 0.01F)) {
            var1.m163(var17, var18, var16 * 1.35F, var9, var10, var11, var19);
         }
      }
   }

   private void m1214(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6) {
      long var7 = System.nanoTime();
      double var9 = (double)var7 / 1.0E9;
      double var11 = var9 % 100000.0 * 20.0;
      float var13 = (float)(var3 / 2.0 + 0.1);
      float var14 = this.f37.getWidth();
      float[] var15 = new float[]{var13 * 0.6F, -var13 * 0.6F, 0.0F};
      float[] var16 = new float[]{1.35F, 1.35F, 1.27F};
      float[] var17 = new float[]{-15.0F, -15.0F, -15.0F};
      float var18 = 2.5F;
      float var19 = 3.0F;
      double var20 = this.f24.getValue() / 100.0;
      RenderLayer var22 = this.f21.m6() ? Sampler0.f4.apply(f1) : Sampler0.f2.apply(f1);
      VertexConsumer var23 = var1.consumers().getBuffer(var22);
      Vec3d var24 = var1.worldState().cameraRenderState.pos;
      Camera var25 = this.mc.getEntityRenderDispatcher().camera;
      float var26 = var25.getYaw();
      float var27 = var25.getPitch();

      for (int var28 = 0; var28 < 3; var28++) {
         int var29 = 0;

         for (byte var30 = 11; var29 <= var30; var29++) {
            double var31 = Math.toRadians(
               (((double)((float)var29 / 1.8F) + var11 * var20 * (double)var18) * (double)var30 + (double)(var28 * 90)) % (double)(var30 * 360)
            );
            if (var28 == 1) {
               var31 = -var31;
            }

            double var33 = Math.sin(Math.toRadians(var11 * (double)var19 * var20 + (double)var29 * ((double)var28 / 2.0 + (double)var13)) * 5.5) / 3.0;
            float var35 = (float)(var29 + var30) / (float)(var30 + var30);
            float var36 = var14 + 0.19F * var16[var28];
            double var37 = Math.cos(var31) * (double)var36;
            double var39 = (double)var13 + var33 + (double)var15[var28] - 0.1;
            double var41 = Math.sin(var31) * (double)var36;
            Vec3d var43 = var2.add(var37, var39, var41);
            float var44 = var35 * var6;
            if (!(var44 <= 0.01F)) {
               float var45 = 0.52F * var35;
               int var46 = this.m11(var5.getRed());
               int var47 = this.m11(var5.getGreen());
               int var48 = this.m11(var5.getBlue());
               int var49 = var46 << 16 | var47 << 8 | var48;
               this.m1215(var23, var24, var26, var27, var43, var45, var17[var28], var49, var44);
            }
         }
      }
   }

   private void m1215(VertexConsumer var1, Vec3d var2, float var3, float var4, Vec3d var5, float var6, float var7, int var8, float var9) {
      MatrixStack var10 = new MatrixStack();
      var10.translate(var5.x - var2.x, var5.y - var2.y, var5.z - var2.z);
      var10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-var3));
      var10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var4));
      var10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var7));
      Entry var11 = var10.peek();
      float var12 = -var6 / 3.0F;
      float var13 = -var6 / 2.0F;
      int var16 = var8 >> 16 & 0xFF;
      int var17 = var8 >> 8 & 0xFF;
      int var18 = var8 & 0xFF;
      int var19 = (int)(Math.clamp(var9, 0.0F, 1.0F) * 255.0F);
      this.m1216(var1, var11, var12, var6, 0.0F, 0.0F, 1.0F, var16, var17, var18, var19);
      this.m1216(var1, var11, var6, var6, 0.0F, 1.0F, 1.0F, var16, var17, var18, var19);
      this.m1216(var1, var11, var6, var13, 0.0F, 1.0F, 0.0F, var16, var17, var18, var19);
      this.m1216(var1, var11, var12, var13, 0.0F, 0.0F, 0.0F, var16, var17, var18, var19);
   }

   private void m1216(VertexConsumer var1, Entry var2, float var3, float var4, float var5, float var6, float var7, int var8, int var9, int var10, int var11) {
      var1.vertex(var2, var3, var4, var5)
         .color(var8, var9, var10, var11)
         .texture(var6, var7)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(var2, 0.0F, 1.0F, 0.0F);
   }

   private void m1217(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6) {
      RenderLayer var7 = this.f21.m6() ? Sampler0.f4.apply(f1) : Sampler0.f2.apply(f1);
      VertexConsumer var8 = var1.consumers().getBuffer(var7);
      Vec3d var9 = var1.worldState().cameraRenderState.pos;
      Camera var10 = this.mc.getEntityRenderDispatcher().camera;
      float var11 = var10.getYaw();
      float var12 = var10.getPitch();
      long var13 = System.nanoTime();
      double var15 = (double)var13 / 1.0E9;
      double var17 = var15 * this.f24.getValue() * 5.0;
      float var19 = this.f37.getWidth() * 1.5F;
      byte var20 = 2;
      byte var21 = 0;
      byte var22 = 0;
      int var23 = this.m11(var5.getRed());
      int var24 = this.m11(var5.getGreen());
      int var25 = this.m11(var5.getBlue());
      int var26 = var23 << 16 | var24 << 8 | var25;

      for (byte var27 = 0; var27 < 360; var27 += var20) {
         float var28 = 0.13F + 0.005F * (float)var21;
         float var29 = 0.7F + 0.005F * (float)var21;
         if (var22 > 0) {
            var22 -= var20;
         } else {
            var21 += var20;
            if (var21 > 50) {
               var22 = 100;
               var21 = 0;
            } else {
               float var30 = Math.max(0.5F, 1.2F - 0.5F * var6);
               double var31 = Math.sin(Math.toRadians((double)var27 + var17 * 1.0)) * (double)var19 * (double)var30;
               double var33 = Math.cos(Math.toRadians((double)var27 + var17 * 1.0)) * (double)var19 * (double)var30;
               double var35 = var3 / 1.5 + var3 / 3.0 * Math.sin(Math.toRadians((double)var27 / 2.0 + var17 / 5.0));
               Vec3d var37 = var2.add(var31, var35, var33);
               float var38 = var6 * 0.05F;
               float var39 = var6 * 0.6F;
               if (var38 > 0.01F) {
                  this.m1218(var8, var9, var11, var12, var37, var29, var26, var38);
               }

               if (var39 > 0.01F) {
                  this.m1218(var8, var9, var11, var12, var37, var28, var26, var39);
               }
            }
         }
      }
   }

   private void m1218(VertexConsumer var1, Vec3d var2, float var3, float var4, Vec3d var5, float var6, int var7, float var8) {
      MatrixStack var9 = new MatrixStack();
      var9.translate(var5.x - var2.x, var5.y - var2.y, var5.z - var2.z);
      var9.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-var3));
      var9.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var4));
      Entry var10 = var9.peek();
      float var11 = -var6 / 2.0F;
      float var12 = -var6 / 2.0F;
      float var13 = var6 / 2.0F;
      float var14 = var6 / 2.0F;
      int var15 = var7 >> 16 & 0xFF;
      int var16 = var7 >> 8 & 0xFF;
      int var17 = var7 & 0xFF;
      int var18 = (int)(Math.clamp(var8, 0.0F, 1.0F) * 255.0F);
      this.m1216(var1, var10, var11, var14, 0.0F, 0.0F, 1.0F, var15, var16, var17, var18);
      this.m1216(var1, var10, var13, var14, 0.0F, 1.0F, 1.0F, var15, var16, var17, var18);
      this.m1216(var1, var10, var13, var12, 0.0F, 1.0F, 0.0F, var15, var16, var17, var18);
      this.m1216(var1, var10, var11, var12, 0.0F, 0.0F, 0.0F, var15, var16, var17, var18);
   }

   private void m1219(WorldRenderContext var1, Vec3d var2, double var3, int var5, int var6, int var7, float var8) {
      if (this.f37 instanceof LivingEntity var9) {
         float var19 = var9.getHealth() / var9.getMaxHealth();
         Identifier var11 = f4;
         if (var19 <= 0.33F) {
            var11 = f6;
         } else if (var19 <= 0.66F) {
            var11 = f5;
         }

         Vec3d var12 = var2.add(0.0, var3 * 0.56, 0.0);
         float var13 = 1.0F + 0.35F * (float)(Math.sin((double)this.f40 * Math.PI * 2.5) * (double)this.f40);
         float var14 = (float)(Math.sin((double)this.f40 * Math.PI * 4.0) * 15.0 * (double)this.f40);
         float var15 = (float)this.f29.getValue() * 0.92F * var13 * (0.55F + var8 * 0.45F);
         float var16 = Math.clamp(var8, 0.0F, 1.0F);
         Sampler0$2 var17 = this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);
         var17.m163(var12, var15 * 1.85F, -var14 * 0.85F, var5, var6, var7, var16 * 0.65F);
         Sampler0$2 var18 = this.f21.m6() ? Sampler0.m175(var1, var11) : Sampler0.m174(var1, var11);
         var18.m165(var12, var15, var14, var5, var6, var7, var16, 0.0F, 0.4F);
      }
   }

   private void m1220(WorldRenderContext var1, Vec3d var2, double var3, float var5, float var6, int var7, int var8, int var9, float var10) {
      Vec3d var11 = var2.add(0.0, var3 * 0.56, 0.0);
      float var12 = 0.94F + 0.06F * (float)Math.sin(Math.toRadians((double)(var5 * 2.0F)));
      float var13 = var6 * 0.92F * var12 * (0.55F + var10 * 0.45F);
      float var14 = Math.clamp(var10, 0.0F, 1.0F);
      Sampler0$2 var15 = this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);
      var15.m163(var11, var13 * 1.85F, -var5 * 0.85F, var7, var8, var9, var14 * 0.85F);
      Identifier var16 = f2;
      if (this.f28.m17("Capture2")) {
         var16 = f7;
      } else if (this.f28.m17("TargetPro")) {
         var16 = f8;
      }

      Sampler0$2 var17 = this.f21.m6() ? Sampler0.m175(var1, var16) : Sampler0.m174(var1, var16);
      var17.m165(var11, var13, var5, var7, var8, var9, var14, 0.0F, 0.4F);
   }

   private float m1221(long var1, double var3) {
      return (float)((double)var1 / 1.0E9 * var3 % 360.0);
   }

   private Vec3d m1222(Vec3d var1, double var2, float var4, double var5, float var7) {
      double var8 = Math.toRadians((double)var4);
      double var10 = Math.cos(var8) * var5;
      double var12 = Math.sin(var8) * var5;
      double var14 = Math.sin(var8 * 2.0) * var2 * 0.18;
      double var16 = var2 * (0.52 + (double)var7 * 0.06) + var14;
      return var1.add(var10, var16, var12);
   }

   private float m334(float var1, float var2, float var3) {
      return var1 < var2 ? Math.min(var1 + var3, var2) : Math.max(var1 - var3, var2);
   }

   private float m3(float var1) {
      var1 = Math.clamp(var1, 0.0F, 1.0F);
      return var1 * var1 * (3.0F - 2.0F * var1);
   }

   private float m151(float var1) {
      float var2 = 1.0F - Math.clamp(var1, 0.0F, 1.0F);
      return 1.0F - var2 * var2 * var2;
   }

   private int m1009(int var1) {
      return Math.clamp((long)((int)((float)var1 + (float)(255 - var1) * 0.35F)), 0, 255);
   }

   private int m11(int var1) {
      return Math.clamp((long)((int)((float)var1 + (float)(255 - var1) * 0.45F)), 0, 255);
   }

   private void m1223(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6, long var7) {
      byte var9 = 3;
      float var10 = 0.2F;
      double var11 = 1.6;
      float var13 = 95.0F;
      Vec3d var14 = var1.worldState().cameraRenderState.pos;
      int var15 = this.m11(var5.getRed());
      int var16 = this.m11(var5.getGreen());
      int var17 = this.m11(var5.getBlue());
      Sampler0$2 var18 = this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);
      double var19 = Math.max(0.2, (double)this.f37.getWidth() / 2.0);
      double var21 = Math.max(0.2, var3);
      int var23 = var9 * 3;
      Vec3d[] var24 = new Vec3d[var23];
      double var25 = Math.toRadians((double)this.m1221(var7, (double)var13));
      double var27 = (Math.PI * 2) / (double)var9;
      double var29 = var19 * var11 * 1.2;
      double var31 = var19 * var11 * 1.8;

      for (int var33 = 0; var33 < 3; var33++) {
         double var34 = var33 == 1 ? var31 : var29;
         double var36 = var33 == 0 ? var21 * 0.15 : (var33 == 1 ? var21 * 0.5 : var21 * 0.85);
         double var38 = var33 == 1 ? var27 / 2.0 : 0.0;

         for (int var40 = 0; var40 < var9; var40++) {
            double var41 = (var33 == 1 ? -var25 : var25) + var38 + (double)var40 * var27;
            double var43 = Math.cos(var41) * var34;
            double var45 = Math.sin(var41) * var34;
            int var47 = var33 * var9 + var40;
            var24[var47] = var2.add(var43, var36, var45);
            var18.m163(var24[var47], var10 * 2.8F, 0.0F, var15, var16, var17, var6 * 0.85F);
         }
      }

      for (int var48 = 0; var48 < var23; var48++) {
         Vec3d var49 = var24[var48];
         MatrixStack var35 = var1.matrices();
         var35.push();
         Vec3d var50 = var49.subtract(var14);
         var35.translate(var50.x, var50.y, var50.z);
         float var37 = this.m1221(var7, (double)(var13 * 1.5F));
         var35.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var37));
         var35.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var37 * 0.5F));
         Box var51 = new Box(
            (double)(-var10 / 2.0F), (double)(-var10 / 2.0F), (double)(-var10 / 2.0F), (double)(var10 / 2.0F), (double)(var10 / 2.0F), (double)(var10 / 2.0F)
         );
         Color var39 = new Color(var15, var16, var17);
         this.m1225(var1, var35.peek(), var51, var39, var6 * (float)this.f33.getValue());
         var35.pop();
      }
   }

   private void m1224(WorldRenderContext var1, Vec3d var2, double var3, Color var5, float var6, long var7) {
      byte var9 = 3;
      float var10 = 0.2F;
      double var11 = 1.4;
      float var13 = 95.0F;
      Vec3d var14 = var1.worldState().cameraRenderState.pos;
      int var15 = this.m11(var5.getRed());
      int var16 = this.m11(var5.getGreen());
      int var17 = this.m11(var5.getBlue());
      Sampler0$2 var18 = this.f21.m6() ? Sampler0.m175(var1, f1) : Sampler0.m174(var1, f1);
      double var19 = Math.max(0.2, (double)this.f37.getWidth() / 2.0);
      double var21 = Math.max(0.2, var3);
      byte var23 = var9;
      Vec3d[] var24 = new Vec3d[var9];
      double var25 = Math.toRadians((double)this.m1221(var7, (double)var13));
      double var27 = (Math.PI * 2) / (double)var9;
      double var29 = var19 * var11 * 1.5;
      double var31 = var21 * 0.5;

      for (int var33 = 0; var33 < var9; var33++) {
         double var34 = var25 + (double)var33 * var27;
         double var36 = Math.cos(var34) * var29;
         double var38 = Math.sin(var34) * var29;
         var24[var33] = var2.add(var36, var31, var38);
         var18.m163(var24[var33], var10 * 4.0F, 0.0F, var15, var16, var17, var6 * 0.85F);
      }

      for (int var42 = 0; var42 < var23; var42++) {
         Vec3d var43 = var24[var42];
         MatrixStack var35 = var1.matrices();
         var35.push();
         Vec3d var44 = var43.subtract(var14);
         var35.translate(var44.x, var44.y, var44.z);
         float var37 = this.m1221(var7, (double)(var13 * 1.5F));
         var35.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var37));
         float var45 = var10 / 8.0F;
         Box var39 = new Box((double)(-var45), (double)(-var10), (double)(-var45), (double)var45, (double)var10 * 1.5, (double)var45);
         Box var40 = new Box(
            (double)(-var10) * 0.8, (double)var10 * 0.35, (double)(-var45), (double)var10 * 0.8, (double)var10 * 0.35 + (double)(var45 * 2.0F), (double)var45
         );
         Color var41 = new Color(var15, var16, var17);
         this.m1225(var1, var35.peek(), var39, var41, var6 * (float)this.f33.getValue());
         this.m1225(var1, var35.peek(), var40, var41, var6 * (float)this.f33.getValue());
         var35.pop();
      }
   }

   private void m1225(WorldRenderContext var1, Entry var2, Box var3, Color var4, float var5) {
      VertexConsumer var6 = var1.consumers().getBuffer(this.f21.m6() ? f16 : f12);
      float var7 = 0.001F;
      float var8 = (float)var3.minX;
      float var9 = (float)var3.minY;
      float var10 = (float)var3.minZ;
      float var11 = (float)var3.maxX;
      float var12 = (float)var3.maxY;
      float var13 = (float)var3.maxZ;
      this.m1162(var6, var2, var4, var8, var12 + var7, var13, var11, var12 + var7, var13, var11, var12 + var7, var10, var8, var12 + var7, var10, var5);
      this.m1162(var6, var2, var4, var8, var9 - var7, var10, var11, var9 - var7, var10, var11, var9 - var7, var13, var8, var9 - var7, var13, var5);
      this.m1162(var6, var2, var4, var8, var9, var10 - var7, var8, var12, var10 - var7, var11, var12, var10 - var7, var11, var9, var10 - var7, var5);
      this.m1162(var6, var2, var4, var8, var9, var13 + var7, var11, var9, var13 + var7, var11, var12, var13 + var7, var8, var12, var13 + var7, var5);
      this.m1162(var6, var2, var4, var8 - var7, var9, var10, var8 - var7, var9, var13, var8 - var7, var12, var13, var8 - var7, var12, var10, var5);
      this.m1162(var6, var2, var4, var11 + var7, var9, var10, var11 + var7, var12, var10, var11 + var7, var12, var13, var11 + var7, var9, var13, var5);
   }

   private void m1226(WorldRenderContext var1, Entry var2, Box var3, Color var4, float var5, float var6) {
      VertexConsumer var7 = var1.consumers().getBuffer(this.f21.m6() ? f14 : f10);
      this.m1046(var7, var2, var4, var3.minX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.minZ, var6, var5);
      this.m1046(var7, var2, var4, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.maxZ, var6, var5);
      this.m1046(var7, var2, var4, var3.maxX, var3.minY, var3.maxZ, var3.minX, var3.minY, var3.maxZ, var6, var5);
      this.m1046(var7, var2, var4, var3.minX, var3.minY, var3.maxZ, var3.minX, var3.minY, var3.minZ, var6, var5);
      this.m1046(var7, var2, var4, var3.minX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var6, var5);
      this.m1046(var7, var2, var4, var3.maxX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.maxZ, var6, var5);
      this.m1046(var7, var2, var4, var3.maxX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var6, var5);
      this.m1046(var7, var2, var4, var3.minX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.minZ, var6, var5);
      this.m1046(var7, var2, var4, var3.minX, var3.minY, var3.minZ, var3.minX, var3.maxY, var3.minZ, var6, var5);
      this.m1046(var7, var2, var4, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var6, var5);
      this.m1046(var7, var2, var4, var3.maxX, var3.minY, var3.maxZ, var3.maxX, var3.maxY, var3.maxZ, var6, var5);
      this.m1046(var7, var2, var4, var3.minX, var3.minY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var6, var5);
   }

   private void m1046(
      VertexConsumer var1, Entry var2, Color var3, double var4, double var6, double var8, double var10, double var12, double var14, float var16, float var17
   ) {
      float var18 = (float)(var10 - var4);
      float var19 = (float)(var12 - var6);
      float var20 = (float)(var14 - var8);
      float var21 = (float)Math.sqrt((double)(var18 * var18 + var19 * var19 + var20 * var20));
      if (!(var21 <= 1.0E-4F)) {
         var1.vertex(var2, (float)var4, (float)var6, (float)var8)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var17, 0.0F, 1.0F) * 255.0F))
            .normal(var2, var18 / var21, var19 / var21, var20 / var21)
            .lineWidth(var16);
         var1.vertex(var2, (float)var10, (float)var12, (float)var14)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var17, 0.0F, 1.0F) * 255.0F))
            .normal(var2, var18 / var21, var19 / var21, var20 / var21)
            .lineWidth(var16);
      }
   }

   private void m1162(
      VertexConsumer var1,
      Entry var2,
      Color var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16
   ) {
      this.m1227(var1, var2, var3, var4, var5, var6, var16);
      this.m1227(var1, var2, var3, var7, var8, var9, var16);
      this.m1227(var1, var2, var3, var10, var11, var12, var16);
      this.m1227(var1, var2, var3, var13, var14, var15, var16);
   }

   private void m1227(VertexConsumer var1, Entry var2, Color var3, float var4, float var5, float var6, float var7) {
      var1.vertex(var2, var4, var5, var6).color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var7, 0.0F, 1.0F) * 255.0F));
   }
}
