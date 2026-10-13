package ua.daamky.utils;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;

public class BooleanSettingRenderer implements SettingRenderer<BooleanSetting> {
   private static final float f1 = 14.0F;
   private static final float f2 = 6.0F;
   private static final float f3 = 7.0F;
   private static final float f4 = 0.0055F;
   private final Map<BooleanSetting, Float> f5 = new HashMap<>();
   private final Map<BooleanSetting, Long> f6 = new HashMap<>();

   @Override
   public boolean m1403(Setting var1) {
      return var1 instanceof BooleanSetting;
   }

   public void m1412(DrawContext var1, BooleanSetting var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = this.m1408(var2);
      float var10 = this.m3(var9);
      float var11 = 7.7F * var7;
      float var12 = 14.0F * var7;
      float var13 = 6.0F * var7;
      float var14 = 7.0F * var7;
      float var15 = var4 + var6 - var12;
      float var16 = var5 + (18.0F * var7 - var13) / 2.0F;
      float var17 = var12 - var14;
      float var18 = var15 + var17 * var10;
      float var19 = var16 + (var13 - var14) / 2.0F;
      Color var20 = var3.m336(new Color(210, 210, 214), var8);
      Color var21 = var3.m336(this.m944(new Color(86, 86, 86), ThemeManager.m1379(), var10), var8);
      Color var22 = var3.m336(Color.WHITE, var8);
      Color var23 = var3.m336(new Color(0, 0, 0), var8 * 0.18F);
      var3.m1450(
         var1,
         var2,
         "name",
         var2.getName(),
         var4,
         var5 + 4.5F * var7,
         var6 - var12 - 8.0F * var7,
         18.0F * var7,
         var11,
         var20,
         false,
         var3.m1451(var4, var5, var6, 18.0F * var7)
      );
      Render2DUtil.m195(var15, var16, var12, var13, var13 / 2.0F, var21);
      Render2DUtil.m217(var18, var19, var14, var14, var14 / 2.0F, 2.0F * var7, 0.25F, 1.0F, var23);
      Render2DUtil.m195(var18, var19, var14, var14, var14 / 2.0F, var22);
   }

   public boolean m1411(BooleanSetting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      var1.m4(!var1.m6());
      return true;
   }

   public float m1410(BooleanSetting var1, SettingRowRenderer var2) {
      return 18.0F;
   }

   public String m1409(BooleanSetting var1) {
      return "";
   }

   private float m1408(BooleanSetting var1) {
      long var2 = System.currentTimeMillis();
      long var4 = this.f6.getOrDefault(var1, var2);
      float var6 = Math.min(50.0F, (float)(var2 - var4));
      this.f6.put(var1, var2);
      float var7 = this.f5.getOrDefault(var1, var1.m6() ? 1.0F : 0.0F);
      float var8 = var1.m6() ? 1.0F : 0.0F;
      if (var7 < var8) {
         var7 = Math.min(var8, var7 + var6 * 0.0055F);
      } else if (var7 > var8) {
         var7 = Math.max(var8, var7 - var6 * 0.0055F);
      }

      this.f5.put(var1, var7);
      return var7;
   }

   private Color m944(Color var1, Color var2, float var3) {
      float var4 = Math.clamp(var3, 0.0F, 1.0F);
      int var5 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var4);
      int var6 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var4);
      int var7 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var4);
      int var8 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var4);
      return new Color(var5, var6, var7, var8);
   }

   private float m3(float var1) {
      float var2 = Math.clamp(var1, 0.0F, 1.0F);
      return var2 * var2 * (3.0F - 2.0F * var2);
   }
}
