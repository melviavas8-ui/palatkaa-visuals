package ua.daamky.features.misc;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.events.RenderEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

@NewFunction(
   I0 = "TrapViewer",
   I00 = "Подсвечивает ловушки из верстаков под игроком",
   I000 = Category.MISC
)
public class TrapViewer extends Module {
   private static final RenderPipeline f1 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "trapviewer_lines"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderLayer f2 = RenderLayer.of(
      "daamky_trapviewer_lines",
      RenderSetup.builder(f1).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   private static final Identifier f3 = Identifier.of(
      "minecraft", "textures/block/white_concrete.png"
   );
   private static final RenderPipeline f4 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "trapviewer_shader"))
         .withVertexShader(Identifier.of("daamky", "trapviewer_shader_vertex"))
         .withFragmentShader(Identifier.of("daamky", "trapviewer_shader_fragment"))
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final RenderLayer f5 = RenderLayer.of(
      "daamky_trapviewer_shader",
      RenderSetup.builder(f4).texture("Sampler0", f3).translucent().expectedBufferSize(1536).build()
   );
   private static final RenderPipeline f6 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "trapviewer_chams_fill"))
         .withVertexShader(Identifier.of("daamky", "trapviewer_shader_vertex"))
         .withFragmentShader(
            Identifier.of("daamky", "trapviewer_chams_fill_fragment")
         )
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final RenderLayer f7 = RenderLayer.of(
      "daamky_trapviewer_chams_fill",
      RenderSetup.builder(f6).texture("Sampler0", f3).translucent().expectedBufferSize(1536).build()
   );
   public static TrapViewer f8;
   private final NumberSetting f9 = new NumberSetting("Horizontal Range", 16.0, 4.0, 32.0, 1.0);
   private final NumberSetting f10 = new NumberSetting("Down Range", 128.0, 4.0, 128.0, 1.0);
   private final NumberSetting f11 = new NumberSetting("Fade Speed", 0.15, 0.02, 1.0, 0.01);
   private final BooleanSetting f12 = new BooleanSetting("Fill", true);
   private final NumberSetting f13 = new NumberSetting("Fill Alpha", 0.45, 0.1, 1.0, 0.05);
   private final ModeSettingBase f14 = new ModeSettingBase(
      "Shader",
      "Full",
      "WebShader",
      "Plasma",
      "ChamsFill",
      "BaseWarp",
      "Waves"
   );
   private final Set<BlockPos> f15 = new HashSet<>();
   private final Map<BlockPos, Float> f16 = new HashMap<>();
   private Box f17;
   private Vec3d f18;
   private float f19;
   private int f20;
   private float f21 = 1.0F;
   private float f22;
   private float f23;
   private boolean f24;
   private float f25 = 1.0F;

   public TrapViewer() {
      f8 = this;
      this.addSettings(new Setting[]{this.f9, this.f10, this.f11, this.f12, this.f13, this.f14});
   }

   @Override
   public void onDisable() {
      this.f15.clear();
      this.f16.clear();
      this.f17 = null;
      this.f18 = null;
      this.f19 = 0.0F;
      this.f20 = 0;
      this.f21 = 1.0F;
      this.f22 = 0.0F;
      this.f23 = 0.0F;
      this.f24 = false;
      this.f25 = 1.0F;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.util.m81()) {
         this.f15.clear();
         this.f16.clear();
         this.f17 = null;
         this.f18 = null;
         this.f19 = this.m334(this.f19, 0.0F, 0.15F);
         this.f20 = 0;
      } else {
         this.f15.clear();
         BlockPos var2 = this.mc.player.getBlockPos();
         int var3 = (int)this.f9.getValue();
         int var4 = (int)this.f10.getValue();

         for (int var5 = -var3; var5 <= var3; var5++) {
            for (int var6 = -var4; var6 <= -1; var6++) {
               for (int var7 = -var3; var7 <= var3; var7++) {
                  BlockPos var8 = var2.add(var5, var6, var7);
                  if (this.mc.world.getBlockState(var8).isOf(Blocks.CRAFTING_TABLE)) {
                     this.f15.add(var8.toImmutable());
                  }
               }
            }
         }

         this.f20 = this.f15.size();
         float var9 = (float)this.f11.getValue();

         for (BlockPos var11 : this.f15) {
            this.f16.put(var11, Math.min(1.0F, this.f16.getOrDefault(var11, 0.0F) + var9));
         }

         this.f16.entrySet().removeIf(var2x -> {
            if (this.f15.contains(var2x.getKey())) {
               return false;
            } else {
               float var3x = var2x.getValue() - var9;
               if (var3x <= 0.0F) {
                  return true;
               } else {
                  var2x.setValue(var3x);
                  return false;
               }
            }
         });
         this.f19 = this.m334(this.f19, this.f15.isEmpty() ? 0.0F : 1.0F, 0.12F);
      }
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      if (this.mc.player != null && this.mc.world != null && !this.mc.options.hudHidden && !this.mc.getDebugHud().shouldShowDebugHud()) {
         if (!(this.f19 <= 0.02F) && this.f18 != null) {
            DrawContext var2 = var1.m583();
            if (var2 != null) {
               TrapViewer$1 var3 = this.m333(this.f18);
               if (var3 == null) {
                  this.f24 = false;
               } else {
                  float var4 = this.m3(this.f19);
                  String var5 = "Трапка";
                  float var6 = (float)Math.sqrt(this.mc.player.squaredDistanceTo(this.f18));
                  float var7 = Math.clamp(7.0F / Math.max(1.0F, var6), 0.45F, 1.15F);
                  var7 *= 0.85F + 0.15F * var4;
                  this.f21 = this.m334(this.f21, var7, 0.12F);
                  float var8 = this.f21;
                  float var9 = 8.5F * var8;
                  float var10 = 8.0F * var8;
                  float var11 = 7.0F * var8;
                  float var12 = 4.0F * var8;
                  float var13 = 5.0F * var8;
                  float var14 = FontRenderUtil.m235(var5, var9);
                  float var15 = var14 + var13 + var11 * 2.0F;
                  float var16 = 18.0F * var8;
                  float var17 = var3.f1 - var15 / 2.0F;
                  float var18 = var3.f2 - var16 / 2.0F;
                  if (!this.f24) {
                     this.f22 = var17;
                     this.f23 = var18;
                     this.f24 = true;
                  }

                  this.f22 = this.m334(this.f22, var17, 0.18F);
                  this.f23 = this.m334(this.f23, var18, 0.18F);
                  float var19 = this.f22;
                  float var20 = this.f23;
                  Render2DUtil.m195(var19, var20, var15, var16, 6.0F * var8, this.m336(new Color(20, 20, 22, 190), var4));
                  Render2DUtil.m205(var2, var19 + var11, var20 + var12, var5, var9, this.m336(new Color(248, 248, 250), var4));
               }
            }
         }
      }
   }

   public static void m114(WorldRenderContext var0) {
      if (f8 != null && f8.isEnabled()) {
         f8.m136(var0);
      }
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.world != null && this.mc.player != null && !this.f16.isEmpty()) {
         Vec3d var2 = var1.worldState().cameraRenderState.pos;
         Entry var3 = var1.matrices().peek();
         Box var4 = null;
         float var5 = 0.0F;

         for (java.util.Map.Entry var7 : this.f16.entrySet()) {
            BlockPos var8 = (BlockPos)var7.getKey();
            VoxelShape var9 = this.mc.world.getBlockState(var8).getOutlineShape(this.mc.world, var8);
            Box var10 = var9.isEmpty() ? new Box(var8) : var9.getBoundingBox().offset(var8);
            var4 = var4 == null ? var10 : var4.union(var10);
            var5 = Math.max(var5, (Float)var7.getValue());
         }

         if (var4 == null) {
            this.f17 = null;
            this.f18 = null;
         } else {
            boolean var11 = this.mc.player.getX() >= var4.minX && this.mc.player.getX() <= var4.maxX;
            this.f25 = this.m334(this.f25, var11 ? 0.0F : 1.0F, 0.1F);
            if (this.f17 == null) {
               if (this.f25 < 0.01F) {
                  this.f18 = null;
                  return;
               }

               Vec3d var12 = var4.getCenter();
               this.f17 = new Box(var12.x, var12.y, var12.z, var12.x, var12.y, var12.z);
            }

            float var13 = (float)this.f11.getValue();
            this.f17 = new Box(
               this.m154(this.f17.minX, var4.minX, (double)var13),
               this.m154(this.f17.minY, var4.minY, (double)var13),
               this.m154(this.f17.minZ, var4.minZ, (double)var13),
               this.m154(this.f17.maxX, var4.maxX, (double)var13),
               this.m154(this.f17.maxY, var4.maxY, (double)var13),
               this.m154(this.f17.maxZ, var4.maxZ, (double)var13)
            );
            this.f18 = this.m332(this.m331(var4).add(0.0, 0.65, 0.0));
            Box var14 = this.f17.expand(0.01).offset(-var2.x, -var2.y, -var2.z);
            float var15 = var5 * this.f25;
            if (this.f12.m6()) {
               this.m337(var1, var14, var15);
            }

            VertexConsumer var16 = var1.consumers().getBuffer(f2);
            this.m138(var16, var3, var14, this.m336(ThemeManager.m1379(), var15), 3.0F);
         }
      } else {
         this.f17 = null;
         this.f18 = null;
      }
   }

   private Vec3d m331(Box var1) {
      return new Vec3d((var1.minX + var1.maxX) / 2.0, var1.maxY, (var1.minZ + var1.maxZ) / 2.0);
   }

   private Vec3d m332(Vec3d var1) {
      return new Vec3d(Math.floor(var1.x * 100.0) / 100.0, Math.floor(var1.y * 100.0) / 100.0, Math.floor(var1.z * 100.0) / 100.0);
   }

   private TrapViewer$1 m333(Vec3d var1) {
      Vec3d var2 = this.mc.gameRenderer.getCamera().getCameraPos();
      Vec3d var3 = Vec3d.fromPolar(this.mc.gameRenderer.getCamera().getPitch(), this.mc.gameRenderer.getCamera().getYaw());
      if (var1.subtract(var2).normalize().dotProduct(var3) <= 0.0) {
         return null;
      } else {
         Vec3d var4 = this.mc.gameRenderer.project(var1);
         if (!(var4.z < -1.0) && !(var4.z > 1.0)) {
            float var5 = (float)((var4.x + 1.0) * 0.5 * (double)Render2DUtil.m113());
            float var6 = (float)((1.0 - var4.y) * 0.5 * (double)Render2DUtil.m189());
            return !(var5 < -100.0F) && !(var5 > (float)Render2DUtil.m113() + 100.0F) && !(var6 < -60.0F) && !(var6 > (float)Render2DUtil.m189() + 60.0F)
               ? new TrapViewer$1(var5, var6)
               : null;
         } else {
            return null;
         }
      }
   }

   private float m334(float var1, float var2, float var3) {
      return var1 + (var2 - var1) * var3;
   }

   private double m154(double var1, double var3, double var5) {
      return var1 + (var3 - var1) * var5;
   }

   private float m3(float var1) {
      float var2 = this.m335(var1, 0.0F, 1.0F) - 1.0F;
      return var2 * var2 * var2 + 1.0F;
   }

   private float m335(float var1, float var2, float var3) {
      return Math.max(var2, Math.min(var3, var1));
   }

   private Color m336(Color var1, float var2) {
      int var3 = (int)((float)var1.getAlpha() * this.m335(var2, 0.0F, 1.0F));
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), var3);
   }

   private void m138(VertexConsumer var1, Entry var2, Box var3, Color var4, float var5) {
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.maxZ, var3.minX, var3.minY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.maxZ, var3.minX, var3.minY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.minZ, var3.minX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.maxZ, var3.maxX, var3.maxY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var5);
   }

   private void m139(VertexConsumer var1, Entry var2, Color var3, double var4, double var6, double var8, double var10, double var12, double var14, float var16) {
      float var17 = (float)(var10 - var4);
      float var18 = (float)(var12 - var6);
      float var19 = (float)(var14 - var8);
      float var20 = (float)Math.sqrt((double)(var17 * var17 + var18 * var18 + var19 * var19));
      if (!(var20 <= 1.0E-4F)) {
         var1.vertex(var2, (float)var4, (float)var6, (float)var8)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), var3.getAlpha())
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
         var1.vertex(var2, (float)var10, (float)var12, (float)var14)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), var3.getAlpha())
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
      }
   }

   private void m337(WorldRenderContext var1, Box var2, float var3) {
      boolean var4 = this.f14.m17("ChamsFill");
      VertexConsumer var5 = var1.consumers().getBuffer(var4 ? f7 : f5);
      Entry var6 = var1.matrices().peek();
      Color var7 = ThemeManager.m1379();
      float var8 = var3 * (float)this.f13.getValue();
      float var9 = 0.001F;
      float var10 = var4 ? 0.0F : (float)this.m107() * 2.0F;
      float var11 = (float)var2.minX;
      float var12 = (float)var2.minY;
      float var13 = (float)var2.minZ;
      float var14 = (float)var2.maxX;
      float var15 = (float)var2.maxY;
      float var16 = (float)var2.maxZ;
      this.m338(var5, var6, var7, var11, var15 + var9, var16, var14, var15 + var9, var16, var14, var15 + var9, var13, var11, var15 + var9, var13, var8, var10);
      this.m338(var5, var6, var7, var11, var12 - var9, var13, var14, var12 - var9, var13, var14, var12 - var9, var16, var11, var12 - var9, var16, var8, var10);
      this.m338(var5, var6, var7, var11, var12, var13 - var9, var11, var15, var13 - var9, var14, var15, var13 - var9, var14, var12, var13 - var9, var8, var10);
      this.m338(var5, var6, var7, var11, var12, var16 + var9, var14, var12, var16 + var9, var14, var15, var16 + var9, var11, var15, var16 + var9, var8, var10);
      this.m338(var5, var6, var7, var11 - var9, var12, var13, var11 - var9, var12, var16, var11 - var9, var15, var16, var11 - var9, var15, var13, var8, var10);
      this.m338(var5, var6, var7, var14 + var9, var12, var13, var14 + var9, var15, var13, var14 + var9, var15, var16, var14 + var9, var12, var16, var8, var10);
   }

   private void m338(
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
      float var16,
      float var17
   ) {
      this.m339(var1, var2, var3, var4, var5, var6, 0.0F, 1.0F + var17, var16);
      this.m339(var1, var2, var3, var7, var8, var9, 1.0F, 1.0F + var17, var16);
      this.m339(var1, var2, var3, var10, var11, var12, 1.0F, 0.0F + var17, var16);
      this.m339(var1, var2, var3, var13, var14, var15, 0.0F, 0.0F + var17, var16);
   }

   private void m339(VertexConsumer var1, Entry var2, Color var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      var1.vertex(var2, var4, var5, var6)
         .texture(var7, var8)
         .color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var9, 0.0F, 1.0F) * 255.0F));
   }

   private int m107() {
      if (this.f14.m17("WebShader")) {
         return 2;
      } else if (this.f14.m17("Plasma")) {
         return 4;
      } else if (this.f14.m17("BaseWarp")) {
         return 8;
      } else {
         return this.f14.m17("Waves") ? 10 : 0;
      }
   }
}
