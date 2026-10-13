package ua.daamky.utils.render;

import java.awt.Color;

public class ColorUtil {
   public static int[] m367(Color... var0) {
      int[] var1 = new int[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].getRGB();
      }

      return var1;
   }

   public static int m368(Color var0) {
      return var0.getRGB();
   }

   public static int m260(int var0, int var1, float var2) {
      int var3 = var0 >> 24 & 0xFF;
      int var4 = var0 >> 16 & 0xFF;
      int var5 = var0 >> 8 & 0xFF;
      int var6 = var0 & 0xFF;
      int var7 = var1 >> 24 & 0xFF;
      int var8 = var1 >> 16 & 0xFF;
      int var9 = var1 >> 8 & 0xFF;
      int var10 = var1 & 0xFF;
      return (int)((float)var3 + (float)(var7 - var3) * var2) << 24
         | (int)((float)var4 + (float)(var8 - var4) * var2) << 16
         | (int)((float)var5 + (float)(var9 - var5) * var2) << 8
         | (int)((float)var6 + (float)(var10 - var6) * var2);
   }
}
