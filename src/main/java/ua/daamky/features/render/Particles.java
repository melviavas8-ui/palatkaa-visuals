package ua.daamky.features.render;

import ua.daamky.events.GameJoinEvent;
import ua.daamky.events.InteractEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.MultiChoiceSetting;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Particles",
   I00 = "Картинки-партиклы при ударе по сущности",
   I000 = Category.RENDER
)
public class Particles extends Module {
   private static final Identifier f1 = Identifier.of(
      "daamky", "images/particles/glow.png"
   );
   private static final Identifier f2 = Identifier.of(
      "daamky", "images/particles/firefly.png"
   );
   private static final Identifier f3 = Identifier.of(
      "daamky", "images/particles/star.png"
   );
   private static final long f4 = 1350000000L;
   private static final double f5 = -9.8;
   private static final double f6 = 0.32;
   private static final double f7 = 0.72;
   private static final float f8 = 2.35F;
   private static final float f9 = 0.28F;
   private static final float f10 = 1.18F;
   private static final float f11 = 0.72F;
   private static final byte f12 = 35;
   private static final int f13 = 30;
   private static final List<Particles$1> f14 = new ArrayList<>();
   private static final Random f15 = new Random();
   public static Particles f16;
   public final ModeSettingBase f17 = new ModeSettingBase(
      "Текстура",
      "Glow",
      "Firefly",
      "Star"
   );
   public final MultiChoiceSetting f18 = new MultiChoiceSetting(
      "Триггеры",
      "При ударе",
      "При тотеме"
   );
   public final NumberSetting f19 = new NumberSetting("Количество", 12.0, 1.0, 60.0, 1.0);
   public final NumberSetting f20 = new NumberSetting("Скорость", 1.0, 0.1, 4.0, 0.1);
   public final NumberSetting f21 = new NumberSetting("Сила разлёта", 1.0, 0.1, 4.0, 0.1);
   public final NumberSetting f22 = new NumberSetting("Размер", 1.0, 0.3, 3.0, 0.1);
   public final BooleanSetting f23 = new BooleanSetting("Цвет от темы", true);
   public final ColorSetting f24 = new ColorSetting("Цвет", new Color(255, 255, 255));

   public Particles() {
      f16 = this;
      this.addSettings(new Setting[]{this.f18, this.f19, this.f20, this.f21, this.f22, this.f17, this.f23, this.f24});
      this.f23.m5(this::m135);
      this.m135();
   }

   @Override
   public void onDisable() {
      synchronized (f14) {
         f14.clear();
      }
   }

   @EventHandler
   private void m771(InteractEvent var1) {
      if (this.f18.m20("При ударе")) {
         if (this.mc.player != null && var1.m552() == this.mc.player) {
            this.m1192(var1.m553(), false);
         }
      }
   }

   @EventHandler
   private void m988(GameJoinEvent var1) {
      if (this.f18.m20("При тотеме")) {
         if (var1.m555().getStatus() == 35) {
            if (var1.m553() instanceof PlayerEntity) {
               this.m1192(var1.m553(), true);
            }
         }
      }
   }

   private void m1192(Entity var1, boolean var2) {
      Vec3d var3 = var1.getEntityPos().add(0.0, (double)var1.getHeight() * 0.55, 0.0);
      int var4 = var2 ? 30 : (int)this.f19.getValue();
      float var5 = (float)this.f20.getValue();
      float var6 = (float)this.f21.getValue();
      float var7 = (float)this.f22.getValue();
      long var8 = System.nanoTime();
      synchronized (f14) {
         for (int var11 = 0; var11 < var4; var11++) {
            Vec3d var12 = this.m1193();
            double var13 = (1.15 + f15.nextDouble() * 1.55) * (double)var5 * (double)var6;
            Vec3d var15 = var12.multiply(var13).add(0.0, f15.nextDouble() * 0.25 * (double)var5, 0.0);
            float var16 = (0.13F + f15.nextFloat() * 0.08F) * var7;
            float var17 = -220.0F + f15.nextFloat() * 440.0F;
            boolean var18 = var2 && f15.nextBoolean();
            f14.add(new Particles$1(var3, var15, var16, f15.nextFloat() * 360.0F, var17, var8, var18));
         }
      }
   }

   private Vec3d m1193() {
      double var1 = f15.nextDouble() * 2.0 - 1.0;
      double var3 = f15.nextDouble() * 1.8 - 0.75;
      double var5 = f15.nextDouble() * 2.0 - 1.0;
      Vec3d var7 = new Vec3d(var1, var3, var5);
      return var7.lengthSquared() < 0.001 ? new Vec3d(1.0, 0.0, 0.0) : var7.normalize();
   }

   public static void m114(WorldRenderContext var0) {
      if (f16 != null && f16.isEnabled()) {
         long var1 = System.nanoTime();
         List<Particles$1> var3;
         synchronized (f14) {
            Iterator var5 = f14.iterator();

            while (var5.hasNext()) {
               Particles$1 var6 = (Particles$1)var5.next();
               float var7 = (float)(var1 - var6.f6) / 1.35E9F;
               if (var7 >= 1.0F) {
                  var5.remove();
               } else {
                  var6.m843(var1);
               }
            }

            if (f14.isEmpty()) {
               return;
            }

            var3 = new ArrayList<>(f14);
         }

         Color var17 = f16.f23.m6() ? ThemeManager.m1379() : f16.f24.m7();
         boolean var18 = f16.f23.m6();
         int var19 = var18 ? m1009(var17.getRed()) : var17.getRed();
         int var20 = var18 ? m1009(var17.getGreen()) : var17.getGreen();
         int var8 = var18 ? m1009(var17.getBlue()) : var17.getBlue();
         if (m665()) {
            Identifier var21 = m1196();
            Sampler0$2 var22 = Sampler0.m174(var0, var21);

            for (Particles$1 var25 : var3) {
               float var27 = (float)(var1 - var25.f6) / 1.35E9F;
               int[] var29 = var25.f8 ? m1195() : m1194(var19, var20, var8);
               m1197(var22, var25, var27, var29[0], var29[1], var29[2]);
            }

            Sampler0$2 var24 = Sampler0.m174(var0, var21);

            for (Particles$1 var28 : var3) {
               float var30 = (float)(var1 - var28.f6) / 1.35E9F;
               int[] var15 = var28.f8 ? m1195() : m1194(var19, var20, var8);
               m1198(var24, var28, var30, var15[0], var15[1], var15[2]);
            }
         } else {
            Identifier var9 = m1196();
            Sampler0$2 var10 = Sampler0.m174(var0, var9);

            for (Particles$1 var12 : var3) {
               float var13 = (float)(var1 - var12.f6) / 1.35E9F;
               int[] var14 = var12.f8 ? m1195() : m1194(var19, var20, var8);
               m1199(var10, var12, var13, var14[0], var14[1], var14[2]);
            }
         }
      }
   }

   private static int[] m1194(int var0, int var1, int var2) {
      return new int[]{var0, var1, var2};
   }

   private static int[] m1195() {
      boolean var0 = f15.nextBoolean();
      return var0 ? new int[]{255, 255, 0} : new int[]{0, 255, 0};
   }

   private static Identifier m1196() {
      if (f16 != null && f16.f17.m17("Firefly")) {
         return f2;
      } else {
         return f16 != null && f16.f17.m17("Star") ? f3 : f1;
      }
   }

   private static boolean m665() {
      return f16 == null || f16.f17.m17("Glow");
   }

   private static void m1197(Sampler0$2 var0, Particles$1 var1, float var2, int var3, int var4, int var5) {
      float var6 = m3(var2);
      if (!(var6 <= 0.01F)) {
         var0.m163(var1.f1, m1200(var1, var2) * 2.35F, var1.f4, var3, var4, var5, var6 * 0.28F);
      }
   }

   private static void m1198(Sampler0$2 var0, Particles$1 var1, float var2, int var3, int var4, int var5) {
      float var6 = m3(var2);
      if (!(var6 <= 0.01F)) {
         float var7 = m1200(var1, var2);
         var0.m163(var1.f1, var7 * 1.18F, var1.f4, var3, var4, var5, Math.clamp(var6 * 0.72F, 0.0F, 1.0F));
         var0.m163(var1.f1, var7, var1.f4, m11(var3), m11(var4), m11(var5), Math.clamp(var6 * 1.25F, 0.0F, 1.0F));
      }
   }

   private static void m1199(Sampler0$2 var0, Particles$1 var1, float var2, int var3, int var4, int var5) {
      float var6 = m3(var2);
      if (!(var6 <= 0.01F)) {
         float var7 = m1200(var1, var2);
         var0.m163(var1.f1, var7 * 1.18F, var1.f4, var3, var4, var5, Math.clamp(var6 * 0.72F, 0.0F, 1.0F));
         var0.m163(var1.f1, var7, var1.f4, m11(var3), m11(var4), m11(var5), Math.clamp(var6 * 1.25F, 0.0F, 1.0F));
      }
   }

   private static float m1200(Particles$1 var0, float var1) {
      float var2 = 1.0F - (1.0F - var1) * (1.0F - var1);
      float var3 = m3(var1);
      return var0.f3 * (0.65F + var2 * 0.55F) * (0.18F + var3 * 0.82F);
   }

   private static float m3(float var0) {
      float var1 = Math.clamp((1.0F - var0) / 0.28F, 0.0F, 1.0F);
      return m151(var1);
   }

   private static float m151(float var0) {
      return var0 * var0 * (3.0F - 2.0F * var0);
   }

   private static int m1009(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.45F)), 0, 255);
   }

   private static int m11(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.72F)), 0, 255);
   }

   private void m135() {
      this.f24.setVisible(!this.f23.m6());
   }
}
