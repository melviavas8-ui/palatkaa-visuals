package ua.daamky.utils.render;

public class RenderUtil {
   private static float f1 = 1.0F;

   public static void m348(float var0) {
      f1 = Math.max(0.05F, var0);
   }

   public static float m329() {
      return f1;
   }

   public static void m314() {
      f1 = 1.0F;
   }
}
