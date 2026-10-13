package ua.daamky.utils;

import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;

public class ScissorUtil {
   private static final Deque<ScissorBox> f1 = new ArrayDeque<>();
   public static boolean f2 = false;

   public static void m355(int var0, int var1, int var2, int var3) {
      m357((double)var0, (double)var1, (double)Math.max(0, var2 - var0), (double)Math.max(0, var3 - var1));
   }

   public static void m356(double var0, double var2, double var4, double var6) {
      m357(var0, var2, Math.max(0.0, var4 - var0), Math.max(0.0, var6 - var2));
   }

   public static void m63() {
      m29();
   }

   public static void m357(double var0, double var2, double var4, double var6) {
      m358(var0, var2, var4, var6, false);
   }

   public static void m358(double var0, double var2, double var4, double var6, boolean var8) {
      if (!f2) {
         if (var8) {
            Render2DUtil.m5(() -> m358(var0, var2, var4, var6, false));
         } else {
            ScissorBox var9 = new ScissorBox(var0, var2, Math.max(0.0, var4), Math.max(0.0, var6));
            if (!f1.isEmpty()) {
               var9 = m361(var9, f1.peek());
            }

            f1.push(var9);
            m362(var9);
         }
      }
   }

   public static void m93(double var0, double var2) {
      m359(var0, var2, false);
   }

   public static void m359(double var0, double var2, boolean var4) {
      if (!f2) {
         double var5 = (double)Render2DUtil.m113();
         m358(0.0, var0, var5, var2, var4);
      }
   }

   public static void m314() {
      m4(false);
   }

   public static void m61(boolean var0) {
      if (!f2) {
         m4(var0);
      }
   }

   public static void m29() {
      m4(false);
   }

   public static void m4(boolean var0) {
      if (!f2) {
         if (var0) {
            Render2DUtil.m5(() -> m4(false));
         } else {
            if (!f1.isEmpty()) {
               f1.pop();
            }

            if (f1.isEmpty()) {
               GL11.glDisable(3089);
            } else {
               m362(f1.peek());
            }
         }
      }
   }

   public static void m360(float var0, Color var1) {
      if (!f1.isEmpty() && !(var0 <= 0.0F)) {
         ScissorBox var2 = f1.peek();
         if (!(var2.f3 <= 0.0) && !(var2.f4 <= 0.0)) {
            Color var3 = new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), 0);
            float var4 = (float)var2.f1;
            float var5 = (float)var2.f2;
            float var6 = (float)var2.f3;
            float var7 = (float)var2.f4;
            float var8 = Math.min(var0, Math.min(var6, var7) * 0.5F);
            Render2DUtil.m194(var4, var5, var6, var8, 0.0F, 0.0F, 0.0F, 0.0F, var1, var1, var3, var1, var1, var3, var3, var3, var3);
            Render2DUtil.m194(var4, var5 + var7 - var8, var6, var8, 0.0F, 0.0F, 0.0F, 0.0F, var3, var3, var3, var1, var1, var3, var1, var1, var3);
            Render2DUtil.m194(
               var4, var5 + var8, var8, Math.max(0.0F, var7 - var8 * 2.0F), 0.0F, 0.0F, 0.0F, 0.0F, var1, var3, var3, var1, var3, var3, var1, var3, var3
            );
            Render2DUtil.m194(
               var4 + var6 - var8,
               var5 + var8,
               var8,
               Math.max(0.0F, var7 - var8 * 2.0F),
               0.0F,
               0.0F,
               0.0F,
               0.0F,
               var3,
               var3,
               var1,
               var3,
               var3,
               var1,
               var3,
               var3,
               var1
            );
         }
      }
   }

   private static ScissorBox m361(ScissorBox var0, ScissorBox var1) {
      double var2 = Math.max(var0.f1, var1.f1);
      double var4 = Math.max(var0.f2, var1.f2);
      double var6 = Math.min(var0.f1 + var0.f3, var1.f1 + var1.f3) - var2;
      double var8 = Math.min(var0.f2 + var0.f4, var1.f2 + var1.f4) - var4;
      return new ScissorBox(var2, var4, Math.max(0.0, var6), Math.max(0.0, var8));
   }

   private static void m362(ScissorBox var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      float var2 = (float)var1.getWindow().getWidth() / Math.max(1.0F, (float)Render2DUtil.m113());
      int var3 = (int)Math.floor(var0.f1 * (double)var2);
      int var4 = (int)Math.floor((double)var1.getWindow().getHeight() - (var0.f2 + var0.f4) * (double)var2);
      int var5 = (int)Math.ceil(var0.f3 * (double)var2);
      int var6 = (int)Math.ceil(var0.f4 * (double)var2);
      GL11.glEnable(3089);
      GL11.glScissor(Math.max(0, var3), Math.max(0, var4), Math.max(0, var5), Math.max(0, var6));
   }
}
