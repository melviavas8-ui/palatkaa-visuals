package ua.daamky.features.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import java.awt.Color;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

@NewFunction(
   I0 = "BlockOverlay",
   I00 = "Подсветка блока, на который вы смотрите",
   I000 = Category.RENDER
)
public class BlockOverlay extends Module {
   private static final Identifier f1 = Identifier.of(
      "minecraft", "textures/block/white_concrete.png"
   );
   private static final double f2 = 14.0;
   private static final RenderPipeline f3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "blockoverlay_see_through_lines"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderLayer f4 = RenderLayer.of(
      "daamky_blockoverlay_see_through_lines",
      RenderSetup.builder(f3).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   private static final RenderPipeline f5 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "blockoverlay_shader"))
         .withVertexShader(Identifier.of("daamky", "block_overlay_shader_vertex"))
         .withFragmentShader(
            Identifier.of("daamky", "block_overlay_shader_fragment")
         )
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   public static final RenderLayer f6 = RenderLayer.of(
      "daamky_blockoverlay_shader",
      RenderSetup.builder(f5)
         .texture("Sampler0", f1)
         .translucent()
         .outputTarget(OutputTarget.ITEM_ENTITY_TARGET)
         .expectedBufferSize(1536)
         .build()
   );
   private static final RenderPipeline f7 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "blockoverlay_chams_fill"))
         .withVertexShader(Identifier.of("daamky", "block_overlay_shader_vertex"))
         .withFragmentShader(
            Identifier.of("daamky", "block_overlay_chams_fill_fragment")
         )
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   public static final RenderLayer f8 = RenderLayer.of(
      "daamky_blockoverlay_chams_fill",
      RenderSetup.builder(f7)
         .texture("Sampler0", f1)
         .translucent()
         .outputTarget(OutputTarget.ITEM_ENTITY_TARGET)
         .expectedBufferSize(1536)
         .build()
   );
   public static BlockOverlay f9;
   private final BooleanSetting f10 = new BooleanSetting("Заполнение", true);
   private final BooleanSetting f11 = new BooleanSetting("Контур", true);
   private final NumberSetting f12 = new NumberSetting("Толщина линий", 2.0, 0.5, 5.0, 0.1);
   private final NumberSetting f13 = new NumberSetting("Прозрачность", 0.5, 0.1, 1.0, 0.05);
   private final ModeSettingBase f14 = new ModeSettingBase(
      "Shader",
      "Full",
      "WebShader",
      "Plasma",
      "ChamsFill",
      "BaseWarp"
   );
   private final ColorSetting f15 = new ColorSetting("Цвет", new Color(255, 255, 255));
   private Box f16;
   private long f17;

   public BlockOverlay() {
      f9 = this;
      this.addSettings(new Setting[]{this.f10, this.f11, this.f12, this.f13, this.f15, this.f14});
   }

   @Override
   public void onDisable() {
      this.f16 = null;
      this.f17 = 0L;
   }

   public static void m114(WorldRenderContext var0) {
      if (f9 != null && f9.isEnabled()) {
         f9.m136(var0);
      }
   }

   public static boolean m687() {
      return f9 == null || !f9.isEnabled();
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.crosshairTarget instanceof BlockHitResult var2 && var2.getType() == Type.BLOCK && this.mc.world != null) {
         BlockPos var8 = var2.getBlockPos();
         VoxelShape var4 = this.mc.world.getBlockState(var8).getOutlineShape(this.mc.world, var8);
         if (var4.isEmpty()) {
            this.f16 = null;
            this.f17 = 0L;
            return;
         }

         Box var5 = this.m1018(var4.getBoundingBox().offset(var8));
         if (var5 == null) {
            return;
         }

         Vec3d var6 = var1.worldState().cameraRenderState.pos;
         Box var7 = var5.offset(-var6.x, -var6.y, -var6.z);
         if (this.f10.m6()) {
            this.m1019(var1, var7);
         }

         if (this.f11.m6()) {
            this.m1020(var1, var7);
         }

         return;
      }

      this.f16 = null;
      this.f17 = 0L;
   }

   private Box m1018(Box var1) {
      long var2 = System.nanoTime();
      double var4 = this.f17 == 0L ? 1.0 : Math.min((double)(var2 - this.f17) / 1.0E9, 0.05);
      this.f17 = var2;
      if (this.f16 == null) {
         this.f16 = var1;
         return this.f16;
      } else {
         double var6 = 1.0 - Math.exp(-14.0 * var4);
         this.f16 = new Box(
            this.m154(this.f16.minX, var1.minX, var6),
            this.m154(this.f16.minY, var1.minY, var6),
            this.m154(this.f16.minZ, var1.minZ, var6),
            this.m154(this.f16.maxX, var1.maxX, var6),
            this.m154(this.f16.maxY, var1.maxY, var6),
            this.m154(this.f16.maxZ, var1.maxZ, var6)
         );
         return this.f16;
      }
   }

   private double m154(double var1, double var3, double var5) {
      return var1 + (var3 - var1) * var5;
   }

   private void m1019(WorldRenderContext var1, Box var2) {
      boolean var3 = this.f14.m17("ChamsFill");
      VertexConsumer var4 = var1.consumers().getBuffer(var3 ? f8 : f6);
      Entry var5 = var1.matrices().peek();
      Color var6 = this.f15.m7();
      float var7 = (float)this.f13.getValue();
      float var8 = 0.001F;
      float var9 = var3 ? 0.0F : (float)this.m715() * 2.0F;
      float var10 = (float)var2.minX;
      float var11 = (float)var2.minY;
      float var12 = (float)var2.minZ;
      float var13 = (float)var2.maxX;
      float var14 = (float)var2.maxY;
      float var15 = (float)var2.maxZ;
      this.m338(var4, var5, var6, var10, var14 + var8, var15, var13, var14 + var8, var15, var13, var14 + var8, var12, var10, var14 + var8, var12, var7, var9);
      this.m338(var4, var5, var6, var10, var11 - var8, var12, var13, var11 - var8, var12, var13, var11 - var8, var15, var10, var11 - var8, var15, var7, var9);
      this.m338(var4, var5, var6, var10, var11, var12 - var8, var10, var14, var12 - var8, var13, var14, var12 - var8, var13, var11, var12 - var8, var7, var9);
      this.m338(var4, var5, var6, var10, var11, var15 + var8, var13, var11, var15 + var8, var13, var14, var15 + var8, var10, var14, var15 + var8, var7, var9);
      this.m338(var4, var5, var6, var10 - var8, var11, var12, var10 - var8, var11, var15, var10 - var8, var14, var15, var10 - var8, var14, var12, var7, var9);
      this.m338(var4, var5, var6, var13 + var8, var11, var12, var13 + var8, var14, var12, var13 + var8, var14, var15, var13 + var8, var11, var15, var7, var9);
   }

   private void m1020(WorldRenderContext var1, Box var2) {
      VertexConsumer var3 = var1.consumers().getBuffer(f4);
      Entry var4 = var1.matrices().peek();
      Color var5 = this.f15.m7();
      float var6 = (float)this.f12.getValue();
      this.m139(var3, var4, var5, var2.minX, var2.minY, var2.minZ, var2.maxX, var2.minY, var2.minZ, var6);
      this.m139(var3, var4, var5, var2.maxX, var2.minY, var2.minZ, var2.maxX, var2.minY, var2.maxZ, var6);
      this.m139(var3, var4, var5, var2.maxX, var2.minY, var2.maxZ, var2.minX, var2.minY, var2.maxZ, var6);
      this.m139(var3, var4, var5, var2.minX, var2.minY, var2.maxZ, var2.minX, var2.minY, var2.minZ, var6);
      this.m139(var3, var4, var5, var2.minX, var2.maxY, var2.minZ, var2.maxX, var2.maxY, var2.minZ, var6);
      this.m139(var3, var4, var5, var2.maxX, var2.maxY, var2.minZ, var2.maxX, var2.maxY, var2.maxZ, var6);
      this.m139(var3, var4, var5, var2.maxX, var2.maxY, var2.maxZ, var2.minX, var2.maxY, var2.maxZ, var6);
      this.m139(var3, var4, var5, var2.minX, var2.maxY, var2.maxZ, var2.minX, var2.maxY, var2.minZ, var6);
      this.m139(var3, var4, var5, var2.minX, var2.minY, var2.minZ, var2.minX, var2.maxY, var2.minZ, var6);
      this.m139(var3, var4, var5, var2.maxX, var2.minY, var2.minZ, var2.maxX, var2.maxY, var2.minZ, var6);
      this.m139(var3, var4, var5, var2.maxX, var2.minY, var2.maxZ, var2.maxX, var2.maxY, var2.maxZ, var6);
      this.m139(var3, var4, var5, var2.minX, var2.minY, var2.maxZ, var2.minX, var2.maxY, var2.maxZ, var6);
   }

   private void m139(VertexConsumer var1, Entry var2, Color var3, double var4, double var6, double var8, double var10, double var12, double var14, float var16) {
      float var17 = (float)(var10 - var4);
      float var18 = (float)(var12 - var6);
      float var19 = (float)(var14 - var8);
      float var20 = (float)Math.sqrt((double)(var17 * var17 + var18 * var18 + var19 * var19));
      if (!(var20 <= 1.0E-4F)) {
         var1.vertex(var2, (float)var4, (float)var6, (float)var8)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), 255)
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
         var1.vertex(var2, (float)var10, (float)var12, (float)var14)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), 255)
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
      }
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

   private int m715() {
      if (this.f14.m17("WebShader")) {
         return 2;
      } else if (this.f14.m17("Plasma")) {
         return 4;
      } else {
         return this.f14.m17("BaseWarp") ? 8 : 0;
      }
   }
}
