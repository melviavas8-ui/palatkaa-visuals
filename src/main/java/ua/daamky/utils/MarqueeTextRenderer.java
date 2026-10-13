package ua.daamky.utils;

import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontAtlas;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;

public class MarqueeTextRenderer {
   private static final Map<String, Float> f1 = new HashMap<>();
   private static final Map<String, Long> f2 = new HashMap<>();

   public static void m364(
      DrawContext var0,
      FontAtlas var1,
      String var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      int var8,
      int var9,
      boolean var10,
      String var11,
      float var12,
      float var13,
      float var14
   ) {
      if (var1 != null && var2 != null && !var2.isEmpty()) {
         float var15 = FontRenderUtil.m237(var1, var2, var5);
         if (var15 <= var6) {
            FontRenderUtil.m229(var0, var1, var2, var3, var4, var5, var8, var10);
         } else {
            long var16 = System.currentTimeMillis();
            long var18 = f2.getOrDefault(var11, var16);
            float var20 = Math.min((float)(var16 - var18) / 1000.0F, 0.05F);
            f2.put(var11, var16);
            float var21 = f1.getOrDefault(var11, 0.0F);
            boolean var22 = var12 >= var3 && var12 <= var3 + var6 && var13 >= var4 - 2.0F && var13 <= var4 + var14 + 2.0F;
            float var23 = var15 - var6;
            float var24 = 0.0F;
            if (var22) {
               var24 = -(var23 + 4.0F);
            }

            float var25 = Math.max(0.8F, 3.0F - var23 / 60.0F);
            float var26 = Math.max(1.5F, 5.0F - var23 / 80.0F);
            float var27 = var22 ? var25 : var26;
            var21 += (var24 - var21) * Math.min(var20 * var27, 1.0F);
            if (Math.abs(var21 - var24) < 0.05F) {
               var21 = var24;
            }

            f1.put(var11, var21);
            float var28 = var3 + var21;
            ScissorUtil.m358((double)var3, (double)(var4 - 2.0F), (double)var6, (double)(var5 + 6.0F), var10);
            FontRenderUtil.m229(var0, var1, var2, var28, var4, var5, var8, var10);
            ScissorUtil.m4(var10);
            int var29 = var9 >> 16 & 0xFF;
            int var30 = var9 >> 8 & 0xFF;
            int var31 = var9 & 0xFF;
            int var32 = var9 >> 24 & 0xFF;
            Color var33 = new Color(var29, var30, var31, 0);
            Color var34 = new Color(var29, var30, var31, var32);
            float var35 = var3 + var6 - var7;
            float var36 = var4 - 2.0F;
            float var37 = var5 + 6.0F;
            Render2DUtil.m194(var35, var36, var7, var37, 0.0F, 0.0F, 0.0F, 0.0F, var33, var34, var34, var33);
         }
      }
   }

   public static void m365(
      DrawContext var0,
      FontAtlas var1,
      String var2,
      float var3,
      float var4,
      float var5,
      float var6,
      int var7,
      int var8,
      boolean var9,
      String var10,
      float var11,
      float var12,
      float var13
   ) {
      m364(var0, var1, var2, var3, var4, var5, var6, 14.0F, var7, var8, var9, var10, var11, var12, var13);
   }

   public static void m366(
      DrawContext var0, FontAtlas var1, String var2, float var3, float var4, float var5, float var6, float var7, int var8, int var9, boolean var10
   ) {
      if (var1 != null && var2 != null && !var2.isEmpty()) {
         float var11 = FontRenderUtil.m237(var1, var2, var5);
         if (var11 <= var6) {
            FontRenderUtil.m229(var0, var1, var2, var3, var4, var5, var8, var10);
         } else {
            ScissorUtil.m358((double)var3, (double)(var4 - 2.0F), (double)var6, (double)(var5 + 6.0F), var10);
            FontRenderUtil.m229(var0, var1, var2, var3, var4, var5, var8, var10);
            ScissorUtil.m4(var10);
            int var12 = var9 >> 16 & 0xFF;
            int var13 = var9 >> 8 & 0xFF;
            int var14 = var9 & 0xFF;
            int var15 = var9 >> 24 & 0xFF;
            Color var16 = new Color(var12, var13, var14, 0);
            Color var17 = new Color(var12, var13, var14, var15);
            float var18 = var3 + var6 - var7;
            float var19 = var4 - 2.0F;
            float var20 = var5 + 6.0F;
            Render2DUtil.m194(var18, var19, var7, var20, 0.0F, 0.0F, 0.0F, 0.0F, var16, var17, var17, var16);
         }
      }
   }

   public static void m16(String var0) {
      f1.remove(var0);
      f2.remove(var0);
   }
}
