package ua.daamky.features.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import ua.daamky.events.InteractEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "HitWave",
   I00 = "Эффект волны при ударе",
   I000 = Category.RENDER
)
public class HitWave extends Module {
   private static final Identifier f1 = Identifier.of(
      "minecraft", "textures/block/white_concrete.png"
   );
   private static final RenderPipeline f2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "hitwave_lines_depth"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderLayer f3 = RenderLayer.of(
      "daamky_hitwave_lines_depth",
      RenderSetup.builder(f2).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   public static HitWave f4;
   public final BooleanSetting f5 = new BooleanSetting("Заливка", true);
   public final BooleanSetting f6 = new BooleanSetting("Контур", true);
   public final NumberSetting f7 = new NumberSetting("Толщина линий", 2.0, 0.5, 5.0, 0.1);
   public final NumberSetting f8 = new NumberSetting(
      "Прозрачность заливки", 0.15, 0.01, 1.0, 0.01
   );
   public final NumberSetting f9 = new NumberSetting("Длительность (сек)", 1.5, 0.5, 3.0, 0.1);
   public final NumberSetting f10 = new NumberSetting(
      "Максимальный радиус", 12.0, 5.0, 20.0, 1.0
   );
   public final NumberSetting f11 = new NumberSetting("Ширина волны", 2.5, 1.0, 5.0, 0.5);
   public final BooleanSetting f12 = new BooleanSetting("Использовать цвет темы", true);
   private final ColorSetting f13 = new ColorSetting("Цвет волны", new Color(255, 255, 255));
   private final List<HitWave$1> f14 = new ArrayList<>();

   public HitWave() {
      f4 = this;
      this.addSettings(new Setting[]{this.f5, this.f6, this.f7, this.f8, this.f9, this.f10, this.f11, this.f12, this.f13});
      this.f12.m5(() -> this.f13.setVisible(!this.f12.m6()));
      this.f13.setVisible(!this.f12.m6());
   }

   @Override
   public void onDisable() {
      this.f14.clear();
   }

   @EventHandler
   private void m771(InteractEvent var1) {
      if (var1.m553() != null && this.mc.world != null) {
         Vec3d var2 = var1.m553().getEntityPos();
         BlockPos var3 = BlockPos.ofFloored(var2.x, var2.y - 0.1, var2.z);
         this.f14.add(new HitWave$1(this, var3, System.currentTimeMillis()));
      }
   }

   public static void m114(WorldRenderContext var0) {
      if (f4 != null && f4.isEnabled() && !f4.f14.isEmpty()) {
         f4.m136(var0);
      }
   }

   private void m136(WorldRenderContext var1) {
      Iterator var2 = this.f14.iterator();

      while (var2.hasNext()) {
         HitWave$1 var3 = (HitWave$1)var2.next();
         if (var3.m91()) {
            var2.remove();
         } else {
            var3.m114(var1);
         }
      }
   }

   public Color m1159() {
      return this.f12.m6() ? ThemeManager.m1379() : this.f13.m7();
   }

   public static int m1009(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.45F)), 0, 255);
   }

   public static int m11(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.72F)), 0, 255);
   }

   public void m1160(WorldRenderContext var1, Box var2, Color var3, float var4) {
      VertexConsumer var5 = var1.consumers().getBuffer(RenderLayers.entityTranslucentEmissive(f1, false));
      Entry var6 = var1.matrices().peek();
      float var7 = 0.001F;
      float var8 = (float)var2.minX;
      float var9 = (float)var2.minY;
      float var10 = (float)var2.minZ;
      float var11 = (float)var2.maxX;
      float var12 = (float)var2.maxY;
      float var13 = (float)var2.maxZ;
      this.m1162(var5, var6, var3, var8, var12 + var7, var13, var11, var12 + var7, var13, var11, var12 + var7, var10, var8, var12 + var7, var10, var4);
      this.m1162(var5, var6, var3, var8, var9 - var7, var10, var11, var9 - var7, var10, var11, var9 - var7, var13, var8, var9 - var7, var13, var4);
      this.m1162(var5, var6, var3, var8, var9, var10 - var7, var8, var12, var10 - var7, var11, var12, var10 - var7, var11, var9, var10 - var7, var4);
      this.m1162(var5, var6, var3, var8, var9, var13 + var7, var11, var9, var13 + var7, var11, var12, var13 + var7, var8, var12, var13 + var7, var4);
      this.m1162(var5, var6, var3, var8 - var7, var9, var10, var8 - var7, var9, var13, var8 - var7, var12, var13, var8 - var7, var12, var10, var4);
      this.m1162(var5, var6, var3, var11 + var7, var9, var10, var11 + var7, var12, var10, var11 + var7, var12, var13, var11 + var7, var9, var13, var4);
   }

   public void m1161(WorldRenderContext var1, Box var2, Color var3, float var4) {
      VertexConsumer var5 = var1.consumers().getBuffer(f3);
      Entry var6 = var1.matrices().peek();
      float var7 = (float)this.f7.getValue();
      this.m1046(var5, var6, var3, var2.minX, var2.minY, var2.minZ, var2.maxX, var2.minY, var2.minZ, var7, var4);
      this.m1046(var5, var6, var3, var2.maxX, var2.minY, var2.minZ, var2.maxX, var2.minY, var2.maxZ, var7, var4);
      this.m1046(var5, var6, var3, var2.maxX, var2.minY, var2.maxZ, var2.minX, var2.minY, var2.maxZ, var7, var4);
      this.m1046(var5, var6, var3, var2.minX, var2.minY, var2.maxZ, var2.minX, var2.minY, var2.minZ, var7, var4);
      this.m1046(var5, var6, var3, var2.minX, var2.maxY, var2.minZ, var2.maxX, var2.maxY, var2.minZ, var7, var4);
      this.m1046(var5, var6, var3, var2.maxX, var2.maxY, var2.minZ, var2.maxX, var2.maxY, var2.maxZ, var7, var4);
      this.m1046(var5, var6, var3, var2.maxX, var2.maxY, var2.maxZ, var2.minX, var2.maxY, var2.maxZ, var7, var4);
      this.m1046(var5, var6, var3, var2.minX, var2.maxY, var2.maxZ, var2.minX, var2.maxY, var2.minZ, var7, var4);
      this.m1046(var5, var6, var3, var2.minX, var2.minY, var2.minZ, var2.minX, var2.maxY, var2.minZ, var7, var4);
      this.m1046(var5, var6, var3, var2.maxX, var2.minY, var2.minZ, var2.maxX, var2.maxY, var2.minZ, var7, var4);
      this.m1046(var5, var6, var3, var2.maxX, var2.minY, var2.maxZ, var2.maxX, var2.maxY, var2.maxZ, var7, var4);
      this.m1046(var5, var6, var3, var2.minX, var2.minY, var2.maxZ, var2.minX, var2.maxY, var2.maxZ, var7, var4);
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
      this.m339(var1, var2, var3, var4, var5, var6, 0.0F, 1.0F, var16);
      this.m339(var1, var2, var3, var7, var8, var9, 1.0F, 1.0F, var16);
      this.m339(var1, var2, var3, var10, var11, var12, 1.0F, 0.0F, var16);
      this.m339(var1, var2, var3, var13, var14, var15, 0.0F, 0.0F, var16);
   }

   private void m339(VertexConsumer var1, Entry var2, Color var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      var1.vertex(var2, var4, var5, var6)
         .texture(var7, var8)
         .color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var9, 0.0F, 1.0F) * 255.0F))
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(var2, 0.0F, 1.0F, 0.0F);
   }
}
