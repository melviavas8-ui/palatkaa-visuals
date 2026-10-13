package ua.daamky.utils;

import java.io.Serializable;

public class MotionSnapshot implements Serializable {
   private static final long f1 = 1L;
   private final float f2;
   private final float f3;
   private final double f4;
   private final long f5;
   private final boolean f6;
   private final double f7;
   private final String f8;
   private final float f9;
   private final float f10;

   public MotionSnapshot(float var1, float var2, double var3, boolean var5, double var6, String var8) {
      this(var1, var2, var3, var5, var6, var8, 0.0f, 0.0f);
   }

   public MotionSnapshot(float var1, float var2, double var3, boolean var5, double var6, String var8, float var9) {
      this(var1, var2, var3, var5, var6, var8, var9, 0.0f);
   }

   public MotionSnapshot(float var1, float var2, double var3, boolean var5, double var6, String var8, float var9, float var10) {
      this.f9 = var9;
      this.f10 = var10;
      this.f2 = var1;
      this.f3 = var2;
      this.f4 = var3;
      this.f5 = System.currentTimeMillis();
      this.f6 = var5;
      this.f7 = var6;
      this.f8 = var8;
   }

   public float m329() {
      return this.f2;
   }

   public float m271() {
      return this.f3;
   }

   public double m353() {
      return this.f4;
   }

   public long m768() {
      return this.f5;
   }

   public boolean m31() {
      return this.f6;
   }

   public double m769() {
      return this.f7;
   }

   public String m275() {
      return this.f8;
   }

   public float m524() {
      return this.f9;
   }

   public float m525() {
      return this.f10;
   }
}
