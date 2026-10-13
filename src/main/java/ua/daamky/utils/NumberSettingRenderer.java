package ua.daamky.utils;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;

public class NumberSettingRenderer implements SettingRenderer<NumberSetting> {
   private static final float f1 = 25.0F;
   private static final float f2 = 5.5F;
   private static final float f3 = 9.0F;
   private static final float f4 = 0.012F;
   private static final float f5 = 0.02F;
   private final Map<NumberSetting, Double> f6 = new HashMap<>();
   private final Map<NumberSetting, Float> f7 = new HashMap<>();
   private final Map<NumberSetting, Long> f8 = new HashMap<>();

   @Override
   public boolean m1403(Setting var1) {
      return var1 instanceof NumberSetting;
   }

   public void m1412(DrawContext var1, NumberSetting var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = this.m1445(var2);
      float var10 = 7.7F * var7;
      float var12 = 5.5F * var7;
      float var13 = 9.0F * var7;
      float var15 = var5 + 16.5F * var7;
      float var16 = this.m1444(var2, var9);
      float var17 = Math.max(var12, var6 * var16);
      float var18 = var4 + (var6 - var13) * var16;
      float var19 = var15 + (var12 - var13) / 2.0F;
      Color var20 = var3.m336(new Color(210, 210, 214), var8);
      Color var21 = var3.m336(new Color(150, 150, 156), var8);
      Color var22 = var3.m336(new Color(64, 64, 68), var8);
      Color var23 = var3.m336(ThemeManager.m1379(), var8);
      Color var24 = var3.m336(ThemeManager.m1379(), var8);
      Color var25 = var3.m336(new Color(0, 0, 0), var8 * 0.18F);
      boolean var26 = var3.m1451(var4, var5, var6, 25.0F * var7);
      var3.m1450(
         var1, var2, "name", var2.getName(), var4, var5 + 4.2F * var7, var6 * 0.58F, 25.0F * var7, var10, var20, false, var26
      );
      var3.m1450(
         var1,
         var2,
         "value",
         this.m1443(var2, var9),
         var4 + var6 - var6 * 0.38F,
         var5 + 4.2F * var7,
         var6 * 0.38F,
         25.0F * var7,
         var10,
         var21,
         true,
         var26
      );
      Render2DUtil.m195(var4, var15, var6, var12, var12 / 2.0F, var22);
      Render2DUtil.m195(var4, var15, var17, var12, var12 / 2.0F, var23);
      Render2DUtil.m217(var18, var19, var13, var13, var13 / 2.0F, 2.3F * var7, 0.25F, 1.0F, var25);
      Render2DUtil.m195(var18, var19, var13, var13, var13 / 2.0F, var24);
   }

   public boolean m1411(NumberSetting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      if (var3 == 1) {
         var1.setValue(var1.getValue() - var1.getStep());
         return false;
      } else {
         this.m1447(var1, var4, var6, 1.0F);
         return true;
      }
   }

   public boolean m1423(NumberSetting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      if (var3 != 0) {
         return false;
      } else {
         this.m1447(var1, var4, var6, 1.0F);
         return true;
      }
   }

   public float m1410(NumberSetting var1, SettingRowRenderer var2) {
      return 25.0F;
   }

   public String m1409(NumberSetting var1) {
      return this.m1446(var1, var1.getValue());
   }

   private float m1442(NumberSetting var1) {
      double var2 = var1.getMax() - var1.getMin();
      return var2 <= 0.0 ? 0.0F : Math.clamp((float)((var1.getValue() - var1.getMin()) / var2), 0.0F, 1.0F);
   }

   private String m1443(NumberSetting var1, float var2) {
      double var3 = var1.getValue();
      double var5 = this.f6.getOrDefault(var1, var3);
      double var7 = 1.0 - Math.exp((double)(-0.012F * var2));
      var5 += (var3 - var5) * (double)Math.clamp((float)var7, 0.0F, 1.0F);
      if (Math.abs(var3 - var5) <= Math.max(1.0E-4, var1.getStep() * 0.08)) {
         var5 = var3;
      }

      this.f6.put(var1, var5);
      return this.m1446(var1, var5);
   }

   private float m1444(NumberSetting var1, float var2) {
      float var3 = this.m1442(var1);
      float var4 = this.f7.getOrDefault(var1, var3);
      float var5 = 1.0F - (float)Math.exp((double)(-0.02F * var2));
      var4 += (var3 - var4) * Math.clamp(var5, 0.0F, 1.0F);
      if (Math.abs(var3 - var4) <= 0.001F) {
         var4 = var3;
      }

      this.f7.put(var1, var4);
      return var4;
   }

   private float m1445(NumberSetting var1) {
      long var2 = System.currentTimeMillis();
      long var4 = this.f8.getOrDefault(var1, var2);
      float var6 = Math.min(50.0F, (float)(var2 - var4));
      this.f8.put(var1, var2);
      return var6;
   }

   private String m1446(NumberSetting var1, double var2) {
      double var4 = var1.getStep();
      return !(var4 >= 1.0) && var2 != Math.rint(var2) ? String.format("%.1f", var2) : String.valueOf((int)Math.round(var2));
   }

   private void m1447(NumberSetting var1, float var2, float var3, float var4) {
      float var6 = 0.0F;
      float var7 = Math.clamp((var2 - var6) / var3, 0.0F, 1.0F);
      double var8 = var1.getMax() - var1.getMin();
      double var10 = var1.getMin() + var8 * (double)var7;
      double var12 = var1.getStep();
      double var14 = var12 > 0.0 ? (double)Math.round(var10 / var12) * var12 : var10;
      var1.setValue(var14);
   }
}
