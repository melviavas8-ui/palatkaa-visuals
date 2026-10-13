package ua.daamky.utils;

import java.util.Random;

public class PerlinNoise2D {
   private final int[] f1 = new int[512];

   public PerlinNoise2D() {
      int[] var1 = new int[256];
      int var2 = 0;

      while (var2 < 256) {
         var1[var2] = var2++;
      }

      Random var6 = new Random();

      for (int var3 = 255; var3 > 0; var3--) {
         int var4 = var6.nextInt(var3 + 1);
         int var5 = var1[var3];
         var1[var3] = var1[var4];
         var1[var4] = var5;
      }

      for (int var7 = 0; var7 < 512; var7++) {
         this.f1[var7] = var1[var7 & 255];
      }
   }

   public double m152(double var1) {
      return this.m153(var1, 0.0);
   }

   public double m153(double var1, double var3) {
      int var5 = (int)Math.floor(var1) & 255;
      int var6 = (int)Math.floor(var3) & 255;
      var1 -= Math.floor(var1);
      var3 -= Math.floor(var3);
      double var7 = m155(var1);
      double var9 = m155(var3);
      int var11 = this.f1[var5] + var6;
      int var12 = this.f1[var5 + 1] + var6;
      return m154(
         var9,
         m154(var7, m767(this.f1[var11], var1, var3), m767(this.f1[var12], var1 - 1.0, var3)),
         m154(
            var7,
            m767(this.f1[var11 + 1], var1, var3 - 1.0),
            m767(this.f1[var12 + 1], var1 - 1.0, var3 - 1.0)
         )
      );
   }

   private static double m155(double var0) {
      return var0
         * var0
         * var0
         * (var0 * (var0 * 6.0 - 15.0) + 10.0);
   }

   private static double m154(double var0, double var2, double var4) {
      return var2 + var0 * (var4 - var2);
   }

   private static double m767(int var0, double var1, double var3) {
      var0 &= 7;
      double var5 = var0 < 4 ? var1 : var3;
      double var7 = var0 < 4 ? var3 : var1;
      return ((var0 & 1) == 0 ? var5 : -var5) + ((var0 & 2) == 0 ? var7 : -var7);
   }
}
