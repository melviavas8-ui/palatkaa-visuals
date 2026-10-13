package ua.daamky.features.render;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.shaders.Sampler0;
import ua.daamky.utils.render.shaders.Sampler0$2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Fire Fly",
   I00 = "Летающие снежные частицы вокруг игрока",
   I000 = Category.RENDER
)
public class FireFly extends Module {
   private static final Identifier f1 = Identifier.of(
      "daamky", "images/particles/glow.png"
   );
   private static final Identifier f2 = Identifier.of(
      "daamky", "images/particles/firefly.png"
   );
   private static final Identifier f3 = Identifier.of(
      "daamky", "images/particles/star.png"
   );
   private static final double f4 = 3.0;
   private static final double f5 = 28.0;
   private static final double f6 = 11.0;
   private static final long f7 = 8000L;
   private static final float f8 = 2.35F;
   private static final float f9 = 0.28F;
   private static final float f10 = 1.18F;
   private static final float f11 = 0.72F;
   public static FireFly f12;
   public final ModeSettingBase f13 = new ModeSettingBase(
      "Текстура",
      "Glow",
      "Firefly",
      "Star"
   );
   public final NumberSetting f14 = new NumberSetting("Количество", 55.0, 10.0, 120.0, 1.0);
   public final NumberSetting f15 = new NumberSetting("Размер", 1.0, 0.4, 3.0, 0.1);
   public final BooleanSetting f16 = new BooleanSetting("Цвет от темы", true);
   public final ColorSetting f17 = new ColorSetting("Цвет", new Color(245, 248, 255));
   private final List<FireFly$1> f18 = new ArrayList<>();
   private final Random f19 = new Random();

   public FireFly() {
      f12 = this;
      this.addSettings(new Setting[]{this.f14, this.f15, this.f13, this.f16, this.f17});
      this.f16.m5(this::m116);
      this.m116();
   }

   @Override
   public void onDisable() {
      this.f18.clear();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         Vec3d var2 = this.mc.player.getEntityPos();
         Iterator var3 = this.f18.iterator();

         while (var3.hasNext()) {
            FireFly$1 var4 = (FireFly$1)var3.next();
            var4.m63();
            if (var4.m639(var2)) {
               var3.remove();
            }
         }

         int var5 = (int)this.f14.getValue();

         while (this.f18.size() > var5) {
            this.f18.remove(this.f18.size() - 1);
         }

         while (this.f18.size() < var5) {
            this.m117(var2);
         }
      }
   }

   public static void m114(WorldRenderContext var0) {
      if (f12 != null && f12.isEnabled()) {
         f12.m136(var0);
      }
   }

   private void m117(Vec3d var1) {
      double var2 = Math.toRadians(this.f19.nextDouble() * 360.0);
      double var4 = 3.0 + this.f19.nextDouble() * 25.0;
      double var6 = 1.0 + this.f19.nextDouble() * 11.0;
      Vec3d var8 = var1.add(Math.cos(var2) * var4, 0.0, Math.sin(var2) * var4).add(0.0, var6, 0.0);
      Vec3d var9 = new Vec3d((this.f19.nextDouble() - 0.5) * 0.055, -0.035 - this.f19.nextDouble() * 0.055, (this.f19.nextDouble() - 0.5) * 0.055);
      this.f18
         .add(
            new FireFly$1(
               var8,
               var9,
               0.055F + this.f19.nextFloat() * 0.075F,
               this.f19.nextFloat() * 360.0F,
               -1.4F + this.f19.nextFloat() * 2.8F,
               0.55F + this.f19.nextFloat() * 0.35F
            )
         );
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.player != null && this.mc.world != null && !this.f18.isEmpty()) {
         Color var2 = this.f16.m6() ? ThemeManager.m1379() : this.f17.m7();
         boolean var3 = this.f16.m6();
         int var4 = var3 ? m1009(var2.getRed()) : var2.getRed();
         int var5 = var3 ? m1009(var2.getGreen()) : var2.getGreen();
         int var6 = var3 ? m1009(var2.getBlue()) : var2.getBlue();
         float var7 = (float)this.f15.getValue();
         float var8 = this.mc.getRenderTickCounter().getTickProgress(false);
         Identifier var9 = this.m1049();
         Sampler0$2 var10 = Sampler0.m174(var1, var9);

         for (FireFly$1 var12 : this.f18) {
            float var13 = var12.m271();
            if (!(var13 <= 0.01F)) {
               Vec3d var14 = var12.m1048(var8);
               float var15 = var12.f8 * var7;
               var10.m163(var14, var15 * 2.35F, var12.f9, var4, var5, var6, var13 * 0.28F);
            }
         }

         Sampler0$2 var17 = Sampler0.m174(var1, var9);

         for (FireFly$1 var19 : this.f18) {
            float var20 = var19.m271();
            if (!(var20 <= 0.01F)) {
               Vec3d var21 = var19.m1048(var8);
               float var16 = var19.f8 * var7;
               var17.m163(var21, var16 * 1.18F, var19.f9, var4, var5, var6, var20 * 0.72F);
               var17.m163(var21, var16, var19.f9, 255, 255, 255, var20);
            }
         }
      }
   }

   private Identifier m1049() {
      if (this.f13.m17("Firefly")) {
         return f2;
      } else {
         return this.f13.m17("Star") ? f3 : f1;
      }
   }

   private static int m1009(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.58F)), 0, 255);
   }

   private void m116() {
      this.f17.setVisible(!this.f16.m6());
   }
}
