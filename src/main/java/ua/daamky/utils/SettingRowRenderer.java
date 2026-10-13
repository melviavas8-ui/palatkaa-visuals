package ua.daamky.utils;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.Setting;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.gui.DrawContext;

public class SettingRowRenderer {
   public static final float f1 = 18.0F;
   private static final float f2 = 26.0F;
   private static final float f3 = 6.0F;
   private final Predicate<Setting> f4;
   private final Function<Setting, Float> f5;
   private final Consumer<Setting> f6;
   private final Map<String, Float> f7 = new HashMap<>();
   private final Map<String, Long> f8 = new HashMap<>();
   private final Map<String, Float> f9 = new HashMap<>();
   private final Map<String, Float> f10 = new HashMap<>();
   private final Map<String, Long> f11 = new HashMap<>();
   private float f12;
   private float f13;

   public SettingRowRenderer(Predicate<Setting> var1, Function<Setting, Float> var2, Consumer<Setting> var3) {
      this.f4 = var1;
      this.f5 = var2;
      this.f6 = var3;
   }

   public boolean m1403(Setting var1) {
      return this.f4.test(var1);
   }

   public float m1398(Setting var1) {
      return Math.clamp(this.f5.apply(var1), 0.0F, 1.0F);
   }

   public void m1399(Setting var1) {
      this.f6.accept(var1);
   }

   public void m1387(float var1, float var2) {
      this.f12 = var1;
      this.f13 = var2;
   }

   public float m1368(String var1, boolean var2) {
      long var3 = System.currentTimeMillis();
      long var5 = this.f11.getOrDefault(var1, var3);
      this.f11.put(var1, var3);
      float var7 = Math.min(50.0F, (float)(var3 - var5));
      float var8 = var2 ? 1.0F : 0.0F;
      float var9 = this.f10.getOrDefault(var1, 0.0F);
      float var10 = 1.0F - (float)Math.exp((double)(-0.02F * var7));
      float var11 = var9 + (var8 - var9) * Math.clamp(var10, 0.0F, 1.0F);
      if (Math.abs(var11 - var8) < 0.001F) {
         var11 = var8;
      }

      this.f10.put(var1, var11);
      return var11;
   }

   public void m1448(DrawContext var1, Setting var2, String var3, float var4, float var5, float var6, float var7, float var8) {
      boolean var9 = this.m1451(var4, var5, var6, 18.0F * var7);
      float var10 = this.m1368("row:" + var2.getName(), var9);
      if (var10 > 0.01F) {
         Render2DUtil.m195(
            var4 - 3.0F * var7,
            var5 + 1.0F * var7,
            var6 + 6.0F * var7,
            18.0F * var7 - 2.0F * var7,
            4.0F * var7,
            new Color(255, 255, 255, Math.round(14.0F * var10 * Math.clamp(var8, 0.0F, 1.0F)))
         );
      }

      Color var11 = this.m336(m944(new Color(210, 210, 214), new Color(245, 245, 250), var10), var8);
      Color var12 = this.m336(m944(new Color(150, 150, 156), ThemeManager.m1379(), var10 * 0.75F), var8);
      float var13 = 7.7F * var7;
      this.m1450(
         var1,
         var2,
         "name",
         var2.getName(),
         var4,
         var5 + 4.5F * var7,
         var6 * 0.55F,
         18.0F * var7,
         var13,
         var11,
         false,
         this.m1451(var4, var5, var6, 18.0F * var7)
      );
      this.m1450(
         var1,
         var2,
         "value",
         var3,
         var4 + var6 - var6 * 0.45F,
         var5 + 4.5F * var7,
         var6 * 0.45F,
         18.0F * var7,
         var13,
         var12,
         true,
         this.m1451(var4, var5, var6, 18.0F * var7)
      );
   }

   public void m1449(DrawContext var1, List<String> var2, Predicate<String> var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = var5 + 18.0F * var7;
      Color var11 = this.m336(ThemeManager.m1379(), var8 * var9);
      Color var12 = this.m336(new Color(130, 130, 136), var8 * var9);
      float var13 = 7.2F * var7;
      ScissorUtil.m357((double)var4, (double)var10, (double)var6, (double)((float)var2.size() * 18.0F * var7 * var9));

      for (String var15 : var2) {
         boolean var16 = this.m1451(var4, var10, var6, 18.0F * var7);
         float var17 = this.m1368("opt:" + var15, var16) * var9;
         if (var17 > 0.01F) {
            Render2DUtil.m195(
               var4 - 3.0F * var7,
               var10 + 1.0F * var7,
               var6 + 6.0F * var7,
               18.0F * var7 - 2.0F * var7,
               4.0F * var7,
               new Color(255, 255, 255, Math.round(14.0F * var17 * Math.clamp(var8, 0.0F, 1.0F)))
            );
         }

         boolean var18 = var3.test(var15);
         Color var19 = var18 ? var11 : var12;
         Color var20 = var18 ? var19 : this.m336(m944(new Color(130, 130, 136), new Color(235, 235, 240), var17), var8 * var9);
         this.m1450(
            var1,
            null,
            "option:" + var15,
            var15,
            var4 + 8.0F * var7 + var17 * 1.5F * var7,
            var10 + 4.2F * var7,
            var6 - 8.0F * var7,
            18.0F * var7,
            var13,
            var20,
            false,
            var16
         );
         var10 += 18.0F * var7;
      }

      ScissorUtil.m29();
   }

   public Color m336(Color var1, float var2) {
      int var3 = (int)((float)var1.getAlpha() * Math.clamp(var2, 0.0F, 1.0F));
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), var3);
   }

   public String m999(String var1, float var2, float var3) {
      if (FontRenderUtil.m235(var1, var3) <= var2) {
         return var1;
      } else {
         String var4 = "...";
         String var5 = var1;

         while (!var5.isEmpty() && FontRenderUtil.m235(var5 + var4, var3) > var2) {
            var5 = var5.substring(0, var5.length() - 1);
         }

         return var5.isEmpty() ? var4 : var5 + var4;
      }
   }

   public void m1450(
      DrawContext var1,
      Setting var2,
      String var3,
      String var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      Color var10,
      boolean var11,
      boolean var12
   ) {
      if (var4 != null && !var4.isEmpty() && !(var7 <= 0.0F)) {
         float var13 = FontRenderUtil.m235(var4, var9);
         if (var13 <= var7) {
            Render2DUtil.m206(
               var1,
               var11 ? var5 + var7 : var5,
               var6,
               var4,
               var9,
               var10,
               var11 ? "right" : "left"
            );
            this.m16(this.m1453(var2, var3, var4));
         } else {
            String var14 = this.m1453(var2, var3, var4);
            float var15 = var13 - var7 + 6.0F;
            float var16 = this.m1452(var14, var15, var12);
            ScissorUtil.m357((double)var5, (double)(var6 - 2.0F), (double)var7, (double)(var9 + 6.0F));
            Render2DUtil.m205(var1, var5 - var16, var6, var4, var9, var10);
            ScissorUtil.m29();
         }
      }
   }

   public boolean m1451(float var1, float var2, float var3, float var4) {
      return this.f12 >= var1 && this.f12 <= var1 + var3 && this.f13 >= var2 && this.f13 <= var2 + var4;
   }

   private float m1452(String var1, float var2, boolean var3) {
      long var4 = System.currentTimeMillis();
      long var6 = this.f8.getOrDefault(var1, var4);
      float var8 = Math.min(50.0F, (float)(var4 - var6)) / 1000.0F;
      this.f8.put(var1, var4);
      float var9 = this.f7.getOrDefault(var1, 0.0F);
      if (!var3) {
         var9 = this.m334(var9, 0.0F, 46.8F * var8);
         this.f9.remove(var1);
         this.f7.put(var1, var9);
         return var9;
      } else {
         float var10 = this.f9.getOrDefault(var1, 0.0F) + var8;
         float var11 = Math.max(1.8F, var2 * 2.0F / 26.0F);
         float var12 = var10 % var11 / var11;
         var9 = var2 * (0.5F - 0.5F * (float)Math.cos((double)var12 * Math.PI * 2.0));
         this.f9.put(var1, var10);
         this.f7.put(var1, var9);
         return var9;
      }
   }

   private float m334(float var1, float var2, float var3) {
      if (var1 < var2) {
         return Math.min(var2, var1 + var3);
      } else {
         return var1 > var2 ? Math.max(var2, var1 - var3) : var1;
      }
   }

   private void m16(String var1) {
      this.f7.remove(var1);
      this.f8.remove(var1);
      this.f9.remove(var1);
   }

   private String m1453(Setting var1, String var2, String var3) {
      return (var1 == null ? "options" : System.identityHashCode(var1)) + ":" + var2 + ":" + var3;
   }

   private static Color m944(Color var0, Color var1, float var2) {
      float var3 = Math.clamp(var2, 0.0F, 1.0F);
      return new Color(
         Math.round((float)var0.getRed() + (float)(var1.getRed() - var0.getRed()) * var3),
         Math.round((float)var0.getGreen() + (float)(var1.getGreen() - var0.getGreen()) * var3),
         Math.round((float)var0.getBlue() + (float)(var1.getBlue() - var0.getBlue()) * var3),
         Math.round((float)var0.getAlpha() + (float)(var1.getAlpha() - var0.getAlpha()) * var3)
      );
   }
}
