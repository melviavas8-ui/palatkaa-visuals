package ua.daamky.utils.render.fonts;

import ua.daamky.utils.MsdfFontManager;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.shaders.MsdfGlowShader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.joml.Matrix4f;

public class FontRenderUtil {
   public static FontAtlas f1;
   public static FontAtlas f2;
   public static FontAtlas f3;
   private static boolean f4 = false;

   public static void m61(boolean var0) {
      f4 = var0;
   }

   public static boolean m91() {
      MsdfFontManager.m63();
      MsdfFontRenderer.m63();
      return MsdfFontManager.m281() != null && MsdfFontManager.m281().m276();
   }

   public static void m226(DrawContext var0, String var1, float var2, float var3, float var4, int var5) {
      m227(var0, var1, var2, var3, var4, var5, false);
   }

   public static void m227(DrawContext var0, String var1, float var2, float var3, float var4, int var5, boolean var6) {
      if (!f4 && m91()) {
         m229(var0, MsdfFontManager.m281(), var1, var2, var3, var4, var5, var6);
      } else {
         m234(var0, var1, var2, var3, var4, var5);
      }
   }

   public static void m228(DrawContext var0, String var1, String var2, float var3, float var4, float var5, int var6, boolean var7) {
      FontAtlas var8 = MsdfFontManager.m280(var1);
      if (!f4 && var8 != null && var8.m276()) {
         m229(var0, var8, var2, var3, var4, var5, var6, var7);
      } else {
         m234(var0, var2, var3, var4, var5, var6);
      }
   }

   public static void m229(DrawContext var0, FontAtlas var1, String var2, float var3, float var4, float var5, int var6, boolean var7) {
      m230(var0, var1, var2, var3, var4, var5, var6, 0.0F, var7);
   }

   public static void m230(DrawContext var0, FontAtlas var1, String var2, float var3, float var4, float var5, int var6, float var7, boolean var8) {
      if (m91()) {
         if (var1 == null) {
            var1 = MsdfFontManager.m281();
         }

         if (var8) {
            FontAtlas var9 = var1;
            Render2DUtil.m5(() -> MsdfFontRenderer.m321(Render2DUtil.m191(), var9, var2, var3, var4, var5, var6, var7, 500.0F));
         } else if (var1 != null && var1.m276()) {
            MsdfFontRenderer.m321(Render2DUtil.m191(), var1, var2, var3, var4, var5, var6, var7, 0.0F);
         } else {
            m234(var0, var2, var3, var4, var5, var6);
         }
      }
   }

   public static void m231(Matrix4f var0, String var1, float var2, float var3, float var4, int var5) {
      MsdfFontRenderer.m321(var0, MsdfFontManager.m281(), var1, var2, var3, var4, var5, 0.0F, 0.0F);
   }

   public static void m232(Matrix4f var0, String var1, String var2, float var3, float var4, float var5, int var6) {
      MsdfFontRenderer.m321(var0, MsdfFontManager.m280(var1), var2, var3, var4, var5, var6, 0.0F, 0.0F);
   }

   public static void m233(Matrix4f var0, FontAtlas var1, String var2, float var3, float var4, float var5, int var6) {
      MsdfFontRenderer.m321(var0, var1, var2, var3, var4, var5, var6, 0.0F, 0.0F);
   }

   private static void m234(DrawContext var0, String var1, float var2, float var3, float var4, int var5) {
      MinecraftClient var6 = MinecraftClient.getInstance();
      var0.drawText(var6.textRenderer, var1, (int)var2, (int)var3, var5, false);
   }

   public static float m235(String var0, float var1) {
      return m237(MsdfFontManager.m281(), var0, var1);
   }

   public static float m236(String var0, String var1, float var2) {
      return m237(MsdfFontManager.m280(var0), var1, var2);
   }

   public static float m237(FontAtlas var0, String var1, float var2) {
      return var0 == null ? 0.0F : var0.m235(var1, var2);
   }

   public static float m3(float var0) {
      return m239(MsdfFontManager.m281(), var0);
   }

   public static float m238(String var0, float var1) {
      return m239(MsdfFontManager.m280(var0), var1);
   }

   public static float m239(FontAtlas var0, float var1) {
      return var0 == null ? var1 : var0.m271() * var1;
   }

   public static void m240(DrawContext var0, String var1, float var2, float var3, float var4, int var5) {
      m227(var0, var1, var2 - m235(var1, var4) / 2.0F, var3, var4, var5, false);
   }

   public static void m241(DrawContext var0, String var1, String var2, float var3, float var4, float var5, int var6) {
      m228(var0, var1, var2, var3 - m236(var1, var2, var5) / 2.0F, var4, var5, var6, false);
   }

   public static void m242(DrawContext var0, String var1, float var2, float var3, float var4, int var5) {
      m227(var0, var1, var2 - m235(var1, var4), var3, var4, var5, false);
   }

   public static void m243(DrawContext var0, String var1, float var2, float var3, float var4, int var5, int var6) {
      float var7 = var2 - m235(var1, var4) / 2.0F;
      m256(var0, MsdfFontManager.m281(), var1, var7, var3, var4, var5, var6, false);
   }

   public static void m244(DrawContext var0, String var1, String var2, float var3, float var4, float var5, int var6, int var7) {
      float var8 = var3 - m236(var1, var2, var5) / 2.0F;
      m256(var0, MsdfFontManager.m280(var1), var2, var8, var4, var5, var6, var7, false);
   }

   public static void m245(DrawContext var0, String var1, float var2, float var3, float var4, int var5, int var6) {
      float var7 = var2 - m235(var1, var4);
      m256(var0, MsdfFontManager.m281(), var1, var7, var3, var4, var5, var6, false);
   }

   public static void m246(DrawContext var0, String var1, String var2, float var3, float var4, float var5, int var6, int var7) {
      float var8 = var3 - m236(var1, var2, var5);
      m256(var0, MsdfFontManager.m280(var1), var2, var8, var4, var5, var6, var7, false);
   }

   public static void m247(DrawContext var0, String var1, String var2, float var3, float var4, float var5, int var6) {
      m228(var0, var1, var2, var3 - m236(var1, var2, var5), var4, var5, var6, false);
   }

   public static void m248(DrawContext var0, String var1, float var2, float var3, float var4, int var5) {
      m227(var0, var1, var2 + 1.0F, var3 + 1.0F, var4, 1140850688, false);
      m227(var0, var1, var2, var3, var4, var5, false);
   }

   public static void m249(DrawContext var0, String var1, String var2, float var3, float var4, float var5, int var6) {
      m228(var0, var1, var2, var3 + 1.0F, var4 + 1.0F, var5, 1140850688, false);
      m228(var0, var1, var2, var3, var4, var5, var6, false);
   }

   public static void m250(DrawContext var0, String var1, float var2, float var3, float var4, int var5, int var6, float var7, boolean var8) {
      m251(var0, MsdfFontManager.m281(), var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static void m251(DrawContext var0, FontAtlas var1, String var2, float var3, float var4, float var5, int var6, int var7, float var8, boolean var9) {
      if (m91()) {
         if (var1 == null) {
            var1 = MsdfFontManager.m281();
         }

         if (var9) {
            FontAtlas var10 = var1;
            Render2DUtil.m5(() -> MsdfGlowShader.m319(Render2DUtil.m191(), var10, var2, var3, var4, var5, var6, var7, var8, 500.0F));
         } else if (var1 != null && var1.m276()) {
            MsdfGlowShader.m319(Render2DUtil.m191(), var1, var2, var3, var4, var5, var6, var7, var8, 0.0F);
         }
      }
   }

   public static void m252(DrawContext var0, String var1, String var2, float var3, float var4, float var5, int var6, int var7, float var8, boolean var9) {
      FontAtlas var10 = MsdfFontManager.m280(var1);
      m251(var0, var10, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public static void m253(DrawContext var0, String var1, float var2, float var3, float var4, float var5) {
      m254(var0, MsdfFontManager.m281(), var1, var2, var3, var4, var5, false);
   }

   public static void m254(DrawContext var0, FontAtlas var1, String var2, float var3, float var4, float var5, float var6, boolean var7) {
      if (var7) {
         Render2DUtil.m5(() -> m254(var0, var1, var2, var3, var4, var5, var6, false));
      } else if (var1 != null && !var2.isEmpty()) {
         int[] var8 = new int[var2.length()];
         long var9 = System.currentTimeMillis();

         for (int var11 = 0; var11 < var2.length(); var11++) {
            float var12 = ((float)var9 * var6 / 1000.0F + (float)var11 * 0.1F) % 1.0F;
            var8[var11] = m259(var12, 1.0F, 1.0F);
         }

         MsdfFontRenderer.m322(Render2DUtil.m191(), var1, var2, var3, var4, var5, var8, 500.0F);
      }
   }

   public static void m255(DrawContext var0, String var1, float var2, float var3, float var4, int var5, int var6) {
      m256(var0, MsdfFontManager.m281(), var1, var2, var3, var4, var5, var6, false);
   }

   public static void m256(DrawContext var0, FontAtlas var1, String var2, float var3, float var4, float var5, int var6, int var7, boolean var8) {
      if (var8) {
         Render2DUtil.m5(() -> m256(var0, var1, var2, var3, var4, var5, var6, var7, false));
      } else if (var1 != null && !var2.isEmpty()) {
         int[] var9 = new int[var2.length()];

         for (int var10 = 0; var10 < var2.length(); var10++) {
            float var11 = var2.length() > 1 ? (float)var10 / (float)(var2.length() - 1) : 0.0F;
            var9[var10] = m260(var6, var7, var11);
         }

         MsdfFontRenderer.m322(Render2DUtil.m191(), var1, var2, var3, var4, var5, var9, 500.0F);
      }
   }

   public static void m257(DrawContext var0, String var1, float var2, float var3, float var4, int... var5) {
      m258(var0, MsdfFontManager.m281(), var1, var2, var3, var4, false, var5);
   }

   public static void m258(DrawContext var0, FontAtlas var1, String var2, float var3, float var4, float var5, boolean var6, int... var7) {
      if (var6) {
         Render2DUtil.m5(() -> m258(var0, var1, var2, var3, var4, var5, false, var7));
      } else if (var1 != null && !var2.isEmpty() && var7.length != 0) {
         float var8 = Render2DUtil.m41() ? 500.0F : 0.0F;
         MsdfFontRenderer.m322(Render2DUtil.m191(), var1, var2, var3, var4, var5, var7, var8);
      }
   }

   private static int m259(float var0, float var1, float var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      if (var1 == 0.0F) {
         var3 = var4 = var5 = (int)(var2 * 255.0F + 0.5F);
      } else {
         float var6 = (var0 - (float)Math.floor((double)var0)) * 6.0F;
         float var7 = var6 - (float)Math.floor((double)var6);
         float var8 = var2 * (1.0F - var1);
         float var9 = var2 * (1.0F - var1 * var7);
         float var10 = var2 * (1.0F - var1 * (1.0F - var7));
         switch ((int)var6) {
            case 0:
               var3 = (int)(var2 * 255.0F + 0.5F);
               var4 = (int)(var10 * 255.0F + 0.5F);
               var5 = (int)(var8 * 255.0F + 0.5F);
               break;
            case 1:
               var3 = (int)(var9 * 255.0F + 0.5F);
               var4 = (int)(var2 * 255.0F + 0.5F);
               var5 = (int)(var8 * 255.0F + 0.5F);
               break;
            case 2:
               var3 = (int)(var8 * 255.0F + 0.5F);
               var4 = (int)(var2 * 255.0F + 0.5F);
               var5 = (int)(var10 * 255.0F + 0.5F);
               break;
            case 3:
               var3 = (int)(var8 * 255.0F + 0.5F);
               var4 = (int)(var9 * 255.0F + 0.5F);
               var5 = (int)(var2 * 255.0F + 0.5F);
               break;
            case 4:
               var3 = (int)(var10 * 255.0F + 0.5F);
               var4 = (int)(var8 * 255.0F + 0.5F);
               var5 = (int)(var2 * 255.0F + 0.5F);
               break;
            case 5:
               var3 = (int)(var2 * 255.0F + 0.5F);
               var4 = (int)(var8 * 255.0F + 0.5F);
               var5 = (int)(var9 * 255.0F + 0.5F);
         }
      }

      return 0xFF000000 | var3 << 16 | var4 << 8 | var5;
   }

   private static int m260(int var0, int var1, float var2) {
      int var3 = var0 >> 24 & 0xFF;
      int var4 = var0 >> 16 & 0xFF;
      int var5 = var0 >> 8 & 0xFF;
      int var6 = var0 & 0xFF;
      int var7 = var1 >> 24 & 0xFF;
      int var8 = var1 >> 16 & 0xFF;
      int var9 = var1 >> 8 & 0xFF;
      int var10 = var1 & 0xFF;
      int var11 = (int)((float)var3 + (float)(var7 - var3) * var2);
      int var12 = (int)((float)var4 + (float)(var8 - var4) * var2);
      int var13 = (int)((float)var5 + (float)(var9 - var5) * var2);
      int var14 = (int)((float)var6 + (float)(var10 - var6) * var2);
      return var11 << 24 | var12 << 16 | var13 << 8 | var14;
   }
}
