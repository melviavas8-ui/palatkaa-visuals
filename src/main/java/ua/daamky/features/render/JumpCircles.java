package ua.daamky.features.render;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.shaders.Sampler0;
import ua.daamky.utils.render.shaders.Sampler0$4;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "JumpCircles",
   I00 = "Эффект кругов под ногами при прыжке",
   I000 = Category.RENDER
)
public class JumpCircles extends Module {
   private static final Identifier f1 = Identifier.of(
      "daamky", "images/world/circle.png"
   );
   private static final List<JumpCircles$1> f2 = new ArrayList<>();
   private static final long f3 = 850000000L;
   private static final double f4 = 0.025;
   private static final float f5 = 0.5F;
   private static final float f6 = 2.35F;
   private static final float f7 = 0.28F;
   private static final float f8 = 1.18F;
   private static final float f9 = 0.72F;
   public static JumpCircles f10;
   private final NumberSetting f11 = new NumberSetting("Size", 1.25, 0.35, 3.0, 0.05);
   private final BooleanSetting f12 = new BooleanSetting("Theme color", true);
   private final ColorSetting f13 = new ColorSetting("Color", new Color(255, 255, 255));
   private boolean f14;
   private double f15;

   public JumpCircles() {
      f10 = this;
      this.addSettings(new Setting[]{this.f11, this.f12, this.f13});
      this.f12.m5(this::m116);
      this.m116();
   }

   @Override
   public void onEnable() {
      this.f14 = this.mc.player != null && this.mc.player.isOnGround();
      this.f15 = this.mc.player != null ? this.mc.player.getY() : 0.0;
   }

   @Override
   public void onDisable() {
      f2.clear();
      this.f14 = false;
   }

   @EventHandler
   private void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         boolean var2 = this.mc.player.isOnGround();
         if (var2) {
            this.f15 = this.mc.player.getY();
         } else if (this.f14 && this.mc.player.getVelocity().y > 0.0) {
            this.m115();
         }

         this.f14 = var2;
      } else {
         this.f14 = false;
         f2.clear();
      }
   }

   private void m115() {
      Vec3d var1 = new Vec3d(this.mc.player.getX(), this.f15 + 0.025, this.mc.player.getZ());
      f2.add(new JumpCircles$1(var1, (float)(Math.random() * 360.0), System.nanoTime()));
   }

   public static void m114(WorldRenderContext var0) {
      if (f10 != null && f10.isEnabled() && !f2.isEmpty()) {
         f10.m136(var0);
      }
   }

   private void m136(WorldRenderContext var1) {
      long var2 = System.nanoTime();
      Color var4 = this.f12.m6() ? ThemeManager.m1379() : this.f13.m7();
      int var5 = this.f12.m6() ? m1009(var4.getRed()) : var4.getRed();
      int var6 = this.f12.m6() ? m1009(var4.getGreen()) : var4.getGreen();
      int var7 = this.f12.m6() ? m1009(var4.getBlue()) : var4.getBlue();
      float var8 = (float)this.f11.getValue();
      Iterator var9 = f2.iterator();
      Sampler0$4 var10 = Sampler0.m179(var1, f1);

      while (var9.hasNext()) {
         JumpCircles$1 var11 = (JumpCircles$1)var9.next();
         float var12 = (float)(var2 - var11.f3) / 8.5E8F;
         if (var12 >= 1.0F) {
            var9.remove();
         } else {
            float var13 = m3(Math.clamp(var12 / 0.48F, 0.0F, 1.0F));
            float var14 = m3(Math.clamp((1.0F - var12) / 0.34F, 0.0F, 1.0F));
            float var15 = var8 * var13;
            float var16 = 0.5F * var14;
            var10.m163(var11.f1, var15 * 1.18F, var11.f2, var5, var6, var7, var16 * 0.72F);
            var10.m163(var11.f1, var15, var11.f2, m11(var5), m11(var6), m11(var7), Math.clamp(var16 * 1.25F, 0.0F, 1.0F));
         }
      }
   }

   private static float m3(float var0) {
      return var0 * var0 * (3.0F - 2.0F * var0);
   }

   private static int m1009(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.45F)), 0, 255);
   }

   private static int m11(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.72F)), 0, 255);
   }

   private void m116() {
      this.f13.setVisible(!this.f12.m6());
   }
}
