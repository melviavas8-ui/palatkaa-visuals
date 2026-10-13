package ua.daamky.utils;

import ua.daamky.utils.render.fonts.FontAtlas;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class FontMeasureUtil {
   public static float m1170(FontAtlas var0, ColoredText var1, float var2) {
      if (var0 == null) {
         var0 = MsdfFontManager.m281();
      }

      TextRenderer var3 = MinecraftClient.getInstance().textRenderer;
      float var4 = 0.0F;
      float var5 = var2 / var0.m274();
      int var6 = MinecraftClient.getInstance().getWindow().getScaleFactor();
      float var7 = 2.0F / (float)var6;

      for (int var8 = 0; var8 < var1.f1.length(); var8++) {
         char var9 = var1.f1.charAt(var8);
         boolean var10 = var0.m269(var9) != null || var9 == ' ';
         if (var10) {
            if (var9 == ' ') {
               FontGlyph var11 = var0.m269(32);
               var4 += var11 != null ? var11.f2 * var5 : 0.25F * var2;
            } else {
               var4 += var0.m269(var9).f2 * var5;
            }
         } else {
            var4 += (float)var3.getWidth(String.valueOf(var9)) * var7;
         }
      }

      return var4;
   }

   public static void m1171(DrawContext var0, FontAtlas var1, ColoredText var2, float var3, float var4, float var5) {
      if (var1 == null) {
         var1 = MsdfFontManager.m281();
      }

      TextRenderer var6 = MinecraftClient.getInstance().textRenderer;
      int var7 = MinecraftClient.getInstance().getWindow().getScaleFactor();
      float var8 = 2.0F / (float)var7;
      float var9 = var3;
      StringBuilder var10 = new StringBuilder();
      ArrayList var11 = new ArrayList();
      boolean var12 = true;

      for (int var13 = 0; var13 <= var2.f1.length(); var13++) {
         boolean var14 = true;
         char var15 = 0;
         if (var13 < var2.f1.length()) {
            var15 = var2.f1.charAt(var13);
            var14 = var1.m269(var15) != null || var15 == ' ';
         }

         if (var13 == 0) {
            var12 = var14;
         }

         if (var13 != var2.f1.length() && var14 == var12) {
            var10.append(var15);
            var11.add(var2.f2[var13]);
         } else {
            if (var10.length() > 0) {
               if (var12) {
                  int[] var16 = new int[var11.size()];

                  for (int var17 = 0; var17 < var11.size(); var17++) {
                     var16[var17] = (Integer)var11.get(var17);
                  }

                  FontRenderUtil.m258(var0, var1, var10.toString(), var9, var4 - 2.0F, var5, false, var16);
                  float var22 = var5 / var1.m274();

                  for (int var18 = 0; var18 < var10.length(); var18++) {
                     char var19 = var10.charAt(var18);
                     if (var19 == ' ') {
                        FontGlyph var20 = var1.m269(32);
                        var9 += var20 != null ? var20.f2 * var22 : 0.25F * var5;
                     } else {
                        var9 += var1.m269(var19).f2 * var22;
                     }
                  }
               } else {
                  for (int var21 = 0; var21 < var10.length(); var21++) {
                     char var23 = var10.charAt(var21);
                     int var24 = (Integer)var11.get(var21);
                     var0.getMatrices().pushMatrix();
                     var0.getMatrices().translate(var9, var4 + var5 / 2.0F - 4.5F * var8);
                     var0.getMatrices().scale(var8, var8);
                     var0.drawText(var6, Text.literal(String.valueOf(var23)), 0, 0, var24, false);
                     var0.getMatrices().popMatrix();
                     var9 += (float)var6.getWidth(String.valueOf(var23)) * var8;
                  }
               }
            }

            if (var13 < var2.f1.length()) {
               var10 = new StringBuilder();
               var11 = new ArrayList();
               var10.append(var15);
               var11.add(var2.f2[var13]);
               var12 = var14;
            }
         }
      }
   }
}
