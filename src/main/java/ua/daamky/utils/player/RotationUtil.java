package ua.daamky.utils.player;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
   private static final MinecraftClient f1 = MinecraftClient.getInstance();
   private static final long f2 = 120L;
   private static final float f3 = 30.0F;
   private static RotationVec f4 = RotationVec.f3;
   private static RotationVec f5 = RotationVec.f3;
   private static RotationVec f6 = RotationVec.f3;
   private static RotationVec f7;
   private static float f8;
   private static float f9;
   private static float f10;
   private static RotationMode f11 = RotationMode.f1;
   private static long f12;
   private static RotationPriority f13 = RotationPriority.f1;
   private static boolean f14;

   public static void m63() {
      f5 = f4;
      if (f1.player == null) {
         m314();
      } else if (f7 == null) {
         f4 = m419();
         f13 = RotationPriority.f1;
      } else if (System.currentTimeMillis() - f12 > 120L) {
         RotationVec var6 = m419();
         if (f4.m402(var6) < 1.0F) {
            f7 = null;
            f4 = var6;
            f13 = RotationPriority.f1;
         } else {
            f13 = RotationPriority.f3;
            float var1 = m424(f4.m329(), var6.m329());
            float var2 = m424(f4.m271(), var6.m271());
            float var3 = Math.max(4.0F, Math.abs(var1) * 0.55F);
            float var4 = Math.max(4.0F, Math.abs(var2) * 0.55F);
            var3 = Math.min(var3, f10);
            var4 = Math.min(var4, f10);
            RotationVec var5 = new RotationVec(m334(f4.m329(), var6.m329(), var3), m334(f4.m271(), var6.m271(), var4));
            f4 = f14 ? var5 : m420(var5);
         }
      } else {
         f13 = RotationPriority.f2;
         RotationVec var0 = new RotationVec(m334(f4.m329(), f7.m329(), f8), m334(f4.m271(), f7.m271(), f9));
         f4 = f14 ? var0 : m420(var0);
      }
   }

   public static void m348(float var0) {
      float var1 = m335(f5.m329(), f4.m329(), var0);
      float var2 = MathHelper.lerp(var0, f5.m271(), f4.m271());
      f6 = new RotationVec(var1, var2 <= -85.0F ? 0.0F : var2);
   }

   public static void m406(RotationVec var0, RotationMode var1, float var2, float var3, float var4) {
      m407(var0, var1, var2, var3, var4, false);
   }

   public static void m407(RotationVec var0, RotationMode var1, float var2, float var3, float var4, boolean var5) {
      m408(var0, var1, var2, var3, var4, var5, 30.0F);
   }

   public static void m408(RotationVec var0, RotationMode var1, float var2, float var3, float var4, boolean var5, float var6) {
      if (f1.player != null) {
         float var7 = m425(f7 == null ? m419().m329() : f7.m329(), var0.m329());
         f7 = new RotationVec(var7, MathHelper.clamp(var0.m271(), -90.0F, 90.0F));
         f8 = m423(var2, var6);
         f9 = m423(var3, var6);
         f10 = m423(var4, var6);
         f11 = var1;
         f14 = var5;
         f12 = System.currentTimeMillis();
         f13 = RotationPriority.f2;
         RotationVec var8 = new RotationVec(m334(f4.m329(), f7.m329(), f8), m334(f4.m271(), f7.m271(), f9));
         f4 = var5 ? var8 : m420(var8);
      }
   }

   public static void m409(RotationVec var0, float var1, float var2, float var3) {
      m407(var0, RotationMode.f1, var1, var2, var3, false);
   }

   public static void m314() {
      f7 = null;
      f13 = RotationPriority.f1;
      f11 = RotationMode.f1;
      f14 = false;
      if (f1.player != null) {
         f4 = m419();
         f5 = f4;
         f6 = f4;
      }
   }

   public static void m410(float var0) {
      if (f1.player != null && f7 != null) {
         f10 = m422(var0);
         f12 = 0L;
      } else {
         m314();
      }
   }

   public static boolean m6() {
      return f1.player != null && f13 != RotationPriority.f1;
   }

   public static float m411(float var0) {
      return m6() ? f4.m329() : var0;
   }

   public static float m412(float var0) {
      return m6() ? f4.m271() : var0;
   }

   public static float m413(float var0) {
      return m6() ? f6.m329() : var0;
   }

   public static float m414(float var0) {
      return m6() ? f6.m271() : var0;
   }

   public static RotationVec m415() {
      return f4;
   }

   public static boolean m31() {
      return m6() && f11 != RotationMode.f1;
   }

   public static boolean m41() {
      return m6() && f11 == RotationMode.f3;
   }

   public static Vec2f m416(float var0, float var1) {
      if (f1.player != null && m41() && (var0 != 0.0F || var1 != 0.0F)) {
         double var2 = MathHelper.wrapDegrees(Math.toDegrees(m426(f1.player.getYaw(), var0, var1)));
         float var4 = var0;
         float var5 = var1;
         float var6 = Float.MAX_VALUE;

         for (float var7 = -1.0F; var7 <= 1.0F; var7++) {
            for (float var8 = -1.0F; var8 <= 1.0F; var8++) {
               if (var7 != 0.0F || var8 != 0.0F) {
                  double var9 = MathHelper.wrapDegrees(Math.toDegrees(m426(f4.m329(), var7, var8)));
                  float var11 = (float)Math.abs(MathHelper.wrapDegrees(var2 - var9));
                  if (var11 < var6) {
                     var6 = var11;
                     var4 = var7;
                     var5 = var8;
                  }
               }
            }
         }

         return new Vec2f(var5, var4);
      } else {
         return new Vec2f(var1, var0);
      }
   }

   public static RotationVec m417(Vec3d var0) {
      Vec3d var1 = f1.player.getEyePos();
      double var2 = var0.x - var1.x;
      double var4 = var0.y - var1.y;
      double var6 = var0.z - var1.z;
      double var8 = Math.sqrt(var2 * var2 + var6 * var6);
      float var10 = (float)Math.toDegrees(Math.atan2(var6, var2)) - 90.0F;
      float var11 = (float)(-Math.toDegrees(Math.atan2(var4, var8)));
      return new RotationVec(var10, var11);
   }

   public static Vec3d m418(LivingEntity var0) {
      Vec3d var1 = f1.player.getEyePos();
      return new Vec3d(
         MathHelper.clamp(var1.x, var0.getBoundingBox().minX, var0.getBoundingBox().maxX),
         MathHelper.clamp(var1.y, var0.getBoundingBox().minY, var0.getBoundingBox().maxY),
         MathHelper.clamp(var1.z, var0.getBoundingBox().minZ, var0.getBoundingBox().maxZ)
      );
   }

   private static RotationVec m419() {
      return f1.player == null ? RotationVec.f3 : new RotationVec(f1.player.getYaw(), f1.player.getPitch());
   }

   private static RotationVec m420(RotationVec var0) {
      double var1 = m421();
      float var3 = (float)((double)var0.m329() - (double)var0.m329() % var1);
      float var4 = (float)((double)var0.m271() - (double)var0.m271() % var1);
      return new RotationVec(var3, var4);
   }

   private static double m421() {
      double var0 = (Double)f1.options.getMouseSensitivity().getValue() * 0.6F + 0.2F;
      return var0 * var0 * var0 * 8.0 * 0.15F;
   }

   private static float m422(float var0) {
      return m423(var0, 30.0F);
   }

   private static float m423(float var0, float var1) {
      return MathHelper.clamp(var0, 0.0F, Math.max(0.0F, var1));
   }

   private static float m334(float var0, float var1, float var2) {
      float var3 = m424(var0, var1);
      return Math.abs(var3) <= var2 ? var1 : var0 + Math.signum(var3) * var2;
   }

   private static float m424(float var0, float var1) {
      return MathHelper.wrapDegrees(var1 - var0);
   }

   private static float m425(float var0, float var1) {
      float var2 = var0 % 360.0F;
      if (var2 < 0.0F) {
         var2 += 360.0F;
      }

      float var3 = var1 % 360.0F;
      if (var3 < 0.0F) {
         var3 += 360.0F;
      }

      int var4 = (int)(var0 / 360.0F);
      if (var0 < 0.0F && var0 % 360.0F != 0.0F) {
         var4--;
      }

      float var5 = var3 + (float)var4 * 360.0F;
      float var6 = var5 - var0;
      if (var6 > 180.0F) {
         var5 -= 360.0F;
      }

      if (var6 < -180.0F) {
         var5 += 360.0F;
      }

      return var5;
   }

   private static float m335(float var0, float var1, float var2) {
      return var0 + MathHelper.wrapDegrees(var1 - var0) * var2;
   }

   private static double m426(float var0, float var1, float var2) {
      if (var1 < 0.0F) {
         var0 += 180.0F;
      }

      float var3 = 1.0F;
      if (var1 < 0.0F) {
         var3 = -0.5F;
      } else if (var1 > 0.0F) {
         var3 = 0.5F;
      }

      if (var2 > 0.0F) {
         var0 -= 90.0F * var3;
      }

      if (var2 < 0.0F) {
         var0 += 90.0F * var3;
      }

      return Math.toRadians((double)var0);
   }
}
