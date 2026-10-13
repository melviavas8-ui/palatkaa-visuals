package ua.daamky.utils;

public class EasingUtil {
   public static float m3(float var0) {
      float var1 = 1.70158F;
      float var2 = var1 + 1.0F;
      return (float)(1.0 + (double)var2 * Math.pow((double)var0 - 1.0, 3.0) + (double)var1 * Math.pow((double)var0 - 1.0, 2.0));
   }

   public static float m151(float var0) {
      return (float)(1.0 - Math.pow(1.0 - (double)var0, 3.0));
   }
}
