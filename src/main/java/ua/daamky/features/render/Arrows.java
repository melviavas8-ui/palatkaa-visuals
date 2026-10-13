package ua.daamky.features.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.textures.GpuTextureView;
import ua.daamky.events.RenderEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.shaders.TextureShader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

@NewFunction(
   I0 = "Arrows",
   I00 = "Показывает стрелочки, указывающие на игроков",
   I000 = Category.RENDER
)
public class Arrows extends Module {
   private static final Identifier f1 = Identifier.of(
      "daamky", "images/world/arrows.png"
   );
   private static final Identifier f2 = Identifier.of(
      "daamky", "images/particles/glow.png"
   );
   private final NumberSetting f3 = new NumberSetting("Radius", 75.0, 30.0, 160.0, 1.0);
   private final NumberSetting f4 = new NumberSetting("Size", 22.0, 8.0, 50.0, 1.0);
   private final Map<UUID, Arrows$1> f5 = new HashMap<>();
   private long f6;

   public Arrows() {
      this.addSettings(new Setting[]{this.f3, this.f4});
   }

   @Override
   public void onDisable() {
      this.f5.clear();
      this.f6 = 0L;
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      DrawContext var2 = var1.m583();
      if (var2 != null && this.mc.player != null && this.mc.world != null) {
         if (!this.mc.options.hudHidden && !this.mc.getDebugHud().shouldShowDebugHud()) {
            long var3 = System.currentTimeMillis();
            float var5 = this.f6 == 0L ? 0.0F : Math.min(50.0F, (float)(var3 - this.f6));
            this.f6 = var3;
            float var6 = (float)Render2DUtil.m113() / 2.0F;
            float var7 = (float)Render2DUtil.m189() / 2.0F;
            float var8 = this.mc.getRenderTickCounter().getTickProgress(false);

            for (PlayerEntity var10 : this.mc.world.getPlayers()) {
               if (var10 != this.mc.player && var10.isAlive()) {
                  UUID var11 = var10.getUuid();
                  Arrows$1 var12 = this.f5.computeIfAbsent(var11, var0 -> new Arrows$1());
                  var12.f1 = var10;
                  var12.f2 = true;
               }
            }

            Iterator var13 = this.f5.entrySet().iterator();

            while (var13.hasNext()) {
               Arrows$1 var14 = (Arrows$1)((Entry)var13.next()).getValue();
               var14.f3 = this.m1007(var14.f3, var14.f2 ? 1.0F : 0.0F, 0.018F, var5);
               if (var14.f1 != null && var14.f3 > 0.01F) {
                  this.m1005(var14.f1, var8, var6, var7, this.m3(var14.f3), var14.f3);
               }

               var14.f2 = false;
               if (var14.f3 <= 0.001F) {
                  var13.remove();
               }
            }
         }
      }
   }

   private void m1005(PlayerEntity var1, float var2, float var3, float var4, float var5, float var6) {
      Vec3d var7 = var1.getLerpedPos(var2);
      Vec3d var8 = this.mc.player.getLerpedPos(var2);
      double var9 = var7.x - var8.x;
      double var11 = var7.z - var8.z;
      if (!(var9 * var9 + var11 * var11 < 0.01)) {
         float var13 = (float)Math.atan2(var11, var9);
         float var14 = (float)Math.toRadians((double)this.mc.player.getYaw()) + (float) (Math.PI / 2);
         float var15 = var13 - var14;
         float var16 = (float)this.f3.getValue();
         float var17 = (float)this.f4.getValue() * var5;
         float var18 = var3 + (float)Math.sin((double)var15) * var16;
         float var19 = var4 - (float)Math.cos((double)var15) * var16;
         this.m1006(var18, var19, var15, var17, var6);
      }
   }

   private void m1006(float var1, float var2, float var3, float var4, float var5) {
      GpuTextureView var6 = this.mc.getTextureManager().getTexture(f1).getGlTextureView();
      GpuTextureView var7 = this.mc.getTextureManager().getTexture(f2).getGlTextureView();
      GlStateManager._enableBlend();
      GlStateManager._blendFuncSeparate(770, 1, 1, 0);
      int var8 = this.m1009(ThemeManager.m1379().getRed());
      int var9 = this.m1009(ThemeManager.m1379().getGreen());
      int var10 = this.m1009(ThemeManager.m1379().getBlue());
      Matrix4f var11 = Render2DUtil.m191();
      var11.translate(var1, var2, 0.0F);
      var11.rotateZ(-var3 * 0.85F);
      float var12 = var4 * 1.85F;
      var11.translate(-var12 / 2.0F, -var12 / 2.0F, 0.0F);
      Matrix4f var13 = Render2DUtil.m191();
      var13.translate(var1, var2, 0.0F);
      var13.rotateZ(var3);
      var13.translate(-var4 / 2.0F, -var4 / 2.0F, 0.0F);
      TextureShader.m342(var13, 0.0F, 0.0F, var4, var6, this.m1008(255.0F * var5, var8, var9, var10), 0.0F, 0.0F);
      GlStateManager._blendFuncSeparate(770, 771, 1, 0);
   }

   private float m1007(float var1, float var2, float var3, float var4) {
      float var5 = 1.0F - (float)Math.exp((double)(-var3 * var4));
      float var6 = var1 + (var2 - var1) * Math.clamp(var5, 0.0F, 1.0F);
      return Math.abs(var6 - var2) < 0.001F ? var2 : var6;
   }

   private float m3(float var1) {
      float var2 = 1.70158F;
      float var3 = var2 + 1.0F;
      float var4 = Math.clamp(var1, 0.0F, 1.0F) - 1.0F;
      return 1.0F + var3 * var4 * var4 * var4 + var2 * var4 * var4;
   }

   private int m1008(float var1, int var2, int var3, int var4) {
      int var5 = Math.clamp((long)((int)var1), 0, 255);
      return var5 << 24 | var2 << 16 | var3 << 8 | var4;
   }

   private int m1009(int var1) {
      return Math.clamp((long)((int)((float)var1 + (float)(255 - var1) * 0.45F)), 0, 255);
   }
}
