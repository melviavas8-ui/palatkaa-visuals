package ua.daamky.features.render;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FireFly$1 {
   public double f1;
   public double f2;
   public double f3;
   public double f4;
   public double f5;
   public double f6;
   public final Vec3d f7;
   public final float f8;
   public float f9;
   public final float f10;
   public final float f11;
   public final float f12;
   public final long f13;

   public FireFly$1(Vec3d var1, Vec3d var2, float var3, float var4, float var5, float var6) {
      this.f1 = var1.x;
      this.f2 = var1.y;
      this.f3 = var1.z;
      this.f4 = var1.x;
      this.f5 = var1.y;
      this.f6 = var1.z;
      this.f7 = var2;
      this.f8 = var3;
      this.f9 = var4;
      this.f10 = var5;
      this.f11 = var6;
      this.f12 = (float)(Math.random() * Math.PI * 2.0);
      this.f13 = System.currentTimeMillis();
   }

   public void m63() {
      this.f4 = this.f1;
      this.f5 = this.f2;
      this.f6 = this.f3;
      long var1 = System.currentTimeMillis() - this.f13;
      double var3 = Math.sin((double)var1 / 420.0 + (double)this.f12) * 0.018;
      this.f1 = this.f1 + this.f7.x + var3;
      this.f2 = this.f2 + this.f7.y;
      this.f3 = this.f3 + this.f7.z + Math.cos((double)var1 / 510.0 + (double)this.f12) * 0.018;
      this.f9 = this.f9 + this.f10;
   }

   public boolean m639(Vec3d var1) {
      long var2 = System.currentTimeMillis() - this.f13;
      if (var2 <= 8000L && !(this.f2 < var1.y - 3.5)) {
         double var4 = this.f1 - var1.x;
         double var6 = this.f2 - var1.y;
         double var8 = this.f3 - var1.z;
         return var4 * var4 + var6 * var6 + var8 * var8 > 2025.0;
      } else {
         return true;
      }
   }

   public Vec3d m1048(float var1) {
      return new Vec3d(
         MathHelper.lerp((double)var1, this.f4, this.f1), MathHelper.lerp((double)var1, this.f5, this.f2), MathHelper.lerp((double)var1, this.f6, this.f3)
      );
   }

   public float m271() {
      long var1 = System.currentTimeMillis() - this.f13;
      float var3 = Math.clamp((float)var1 / 650.0F, 0.0F, 1.0F);
      float var4 = Math.clamp((float)(8000L - var1) / 1300.0F, 0.0F, 1.0F);
      return this.m151(Math.min(var3, var4)) * this.f11;
   }

   public float m151(float var1) {
      var1 = Math.clamp(var1, 0.0F, 1.0F);
      return var1 * var1 * (3.0F - 2.0F * var1);
   }
}
