package ua.daamky.utils;

import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.Setting;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;

public class ColorSettingRenderer implements SettingRenderer<ColorSetting> {
   private static final float f1 = 62.0F;
   private static final float f2 = 7.0F;
   private static final float f3 = 7.0F;
   private static final int f4 = 72;
   private final Map<ColorSetting, ColorPickerState> f5 = new HashMap<>();

   @Override
   public boolean m1403(Setting var1) {
      return var1 instanceof ColorSetting;
   }

   public void m1412(DrawContext var1, ColorSetting var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
      Color var9 = var2.m7();
      float var10 = 7.7F * var7;
      Color var11 = var3.m336(new Color(210, 210, 214), var8);
      Color var12 = var3.m336(new Color(150, 150, 156), var8);
      float var13 = 10.0F * var7;
      float var14 = var4 + var6 - var13;
      float var15 = var5 + 4.0F * var7;
      var3.m1450(
         var1,
         var2,
         "name",
         var2.getName(),
         var4,
         var5 + 4.5F * var7,
         var6 * 0.55F,
         18.0F * var7,
         var10,
         var11,
         false,
         var3.m1451(var4, var5, var6, 18.0F * var7)
      );
      Render2DUtil.m206(var1, var14 - 6.0F * var7, var5 + 4.5F * var7, this.m386(var9), var10, var12, "right");
      Render2DUtil.m195(var14, var15, var13, var13, 3.0F * var7, var3.m336(var9, var8));
      Render2DUtil.m202(var14, var15, var13, var13, 3.0F * var7, 0.8F * var7, var3.m336(new Color(255, 255, 255), var8 * 0.42F));
      float var16 = var3.m1398(var2);
      if (!(var16 <= 0.02F)) {
         ColorPickerState var17 = this.m1419(var2);
         float var18 = var5 + 18.0F * var7;
         float var19 = 62.0F * var7 * var16;
         float var20 = 62.0F * var7;
         float var21 = 7.0F * var7;
         float var22 = 7.0F * var7;
         float var24 = var4 + var20 + var22;
         float var25 = 5.0F * var7;
         this.m1421(var3, var4, var18, var20, var19, var25, var17.f1, var8 * var16);
         Render2DUtil.m202(var4, var18, var20, var19, var25, 0.9F * var7, var3.m336(new Color(255, 255, 255), var8 * var16 * 0.25F));
         this.m1422(var3, var24, var18, var21, var19, var21 / 2.0F, var8 * var16);
         Render2DUtil.m202(var24, var18, var21, var19, var21 / 2.0F, 0.9F * var7, var3.m336(new Color(255, 255, 255), var8 * var16 * 0.25F));
         float var26 = var4 + var17.f2 * var20;
         float var27 = var18 + (1.0F - var17.f3) * var20;
         float var28 = var18 + var17.f1 * var20;
         Color var29 = var3.m336(Color.WHITE, var8 * var16);
         Render2DUtil.m202(var26 - 2.5F * var7, var27 - 2.5F * var7, 5.0F * var7, 5.0F * var7, 2.5F * var7, 1.0F * var7, var29);
         Render2DUtil.m202(var24 - 1.5F * var7, var28 - 1.5F * var7, var21 + 3.0F * var7, 3.0F * var7, 1.5F * var7, 1.0F * var7, var29);
      }
   }

   public boolean m1411(ColorSetting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      if (var3 == 1) {
         var2.m1399(var1);
         return false;
      } else if (var2.m1403(var1) && !(var5 < 18.0F)) {
         this.m1418(var1, var4, var5);
         return true;
      } else {
         var2.m1399(var1);
         return false;
      }
   }

   public boolean m1423(ColorSetting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      if (var3 == 0 && var2.m1403(var1)) {
         this.m1418(var1, var4, var5);
         return true;
      } else {
         return false;
      }
   }

   public float m1410(ColorSetting var1, SettingRowRenderer var2) {
      return 18.0F + 62.0F * var2.m1398(var1);
   }

   public String m1409(ColorSetting var1) {
      return this.m386(var1.m7());
   }

   private void m1418(ColorSetting var1, float var2, float var3) {
      ColorPickerState var4 = this.m1419(var1);
      float var5 = 18.0F;
      float var6 = 69.0F;
      if (var2 >= 0.0F && var2 <= 62.0F && var3 >= var5 && var3 <= var5 + 62.0F) {
         var4.f2 = Math.clamp(var2 / 62.0F, 0.0F, 1.0F);
         var4.f3 = 1.0F - Math.clamp((var3 - var5) / 62.0F, 0.0F, 1.0F);
         this.m1420(var1, var4);
      } else if (var2 >= var6 && var2 <= var6 + 7.0F && var3 >= var5 && var3 <= var5 + 62.0F) {
         var4.f1 = Math.clamp((var3 - var5) / 62.0F, 0.0F, 1.0F);
         this.m1420(var1, var4);
      }
   }

   private ColorPickerState m1419(ColorSetting var1) {
      ColorPickerState var2 = this.f5.computeIfAbsent(var1, var0 -> new ColorPickerState());
      Color var3 = var1.m7();
      if (var2.f4 != var3.getRGB()) {
         float[] var4 = Color.RGBtoHSB(var3.getRed(), var3.getGreen(), var3.getBlue(), null);
         var2.f1 = var4[0];
         var2.f2 = var4[1];
         var2.f3 = var4[2];
         var2.f4 = var3.getRGB();
      }

      return var2;
   }

   private void m1420(ColorSetting var1, ColorPickerState var2) {
      Color var3 = Color.getHSBColor(var2.f1, var2.f2, var2.f3);
      if (var1.m7().getRGB() != var3.getRGB()) {
         var1.m8(var3);
         var2.f4 = var3.getRGB();
      }
   }

   private String m386(Color var1) {
      return String.format("#%02X%02X%02X", var1.getRed(), var1.getGreen(), var1.getBlue());
   }

   private void m1421(SettingRowRenderer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      Color var9 = Color.WHITE;
      Color var10 = this.m944(Color.WHITE, Color.getHSBColor(var7, 1.0F, 1.0F), 0.5F);
      Color var11 = Color.getHSBColor(var7, 1.0F, 1.0F);
      Color var12 = new Color(128, 128, 128);
      Color var13 = Color.getHSBColor(var7, 0.5F, 0.5F);
      Color var14 = Color.getHSBColor(var7, 1.0F, 0.5F);
      Color var15 = Color.BLACK;
      Render2DUtil.m195(
         var2,
         var3,
         var4,
         var5,
         var6,
         var1.m336(var9, var8),
         var1.m336(var10, var8),
         var1.m336(var11, var8),
         var1.m336(var12, var8),
         var1.m336(var13, var8),
         var1.m336(var14, var8),
         var1.m336(var15, var8),
         var1.m336(var15, var8),
         var1.m336(var15, var8)
      );
   }

   private Color m944(Color var1, Color var2, float var3) {
      float var4 = Math.clamp(var3, 0.0F, 1.0F);
      int var5 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var4);
      int var6 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var4);
      int var7 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var4);
      int var8 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var4);
      return new Color(var5, var6, var7, var8);
   }

   private void m1422(SettingRowRenderer var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (!(var5 <= 0.0F) && !(var4 <= 0.0F)) {
         float var8 = var4;
         float var9 = Math.max(0.0F, var5 - var4);
         float var10 = var9 / 71.0F;

         for (int var11 = 0; var11 < 72; var11++) {
            float var12 = (float)var11 / 71.0F;
            Color var13 = var1.m336(Color.getHSBColor(var12, 1.0F, 1.0F), var7);
            Render2DUtil.m195(var2, var3 + (float)var11 * var10, var8, var8, var6, var13);
         }
      }
   }
}
