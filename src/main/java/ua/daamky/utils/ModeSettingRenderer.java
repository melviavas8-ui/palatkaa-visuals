package ua.daamky.utils;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;

public class ModeSettingRenderer implements SettingRenderer<ModeSettingBase> {
   private final Map<Setting, Float> f1 = new HashMap<>();
   private final Map<Setting, Long> f2 = new HashMap<>();
   private final Map<Setting, String> f3 = new HashMap<>();
   private final Map<Setting, String> f4 = new HashMap<>();
   private final Map<Setting, Float> f5 = new HashMap<>();
   private final Map<String, Float> f6 = new HashMap<>();

   @Override
   public boolean m1403(Setting var1) {
      return var1 instanceof ModeSettingBase;
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
      float var2 = 7.5625F;
      float var3 = 2.75F;
      if (var1 < 1.0F / var3) {
         return var2 * var1 * var1;
      } else if (var1 < 2.0F / var3) {
         float var6;
         return var2 * (var6 = var1 - 1.5F / var3) * var6 + 0.75F;
      } else {
         float var4;
         float var5;
         return var1 < 2.5F / var3 ? var2 * (var4 = var1 - 2.25F / var3) * var4 + 0.9375F : var2 * (var5 = var1 - 2.625F / var3) * var5 + 0.984375F;
      }
   }

   public void m1412(DrawContext var1, ModeSettingBase var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
      long var9 = System.currentTimeMillis();
      long var11 = this.f2.getOrDefault(var2, var9);
      float var13 = Math.min(50.0F, (float)(var9 - var11)) / 1000.0F;
      this.f2.put(var2, var9);
      float var14 = this.m1410(var2, var3) * var7;
      float var15 = var4 - 4.0F * var7;
      float var16 = var6 + 8.0F * var7;
      Render2DUtil.m198(var15, var5, var16, var14, 4.0F * var7, 15.0F, var8, new Color(30, 30, 35, 100));
      Color var17 = var3.m336(new Color(210, 210, 214), var8);
      float var18 = 7.7F * var7;
      var3.m1450(
         var1,
         var2,
         "name",
         var2.getName(),
         var4,
         var5 + 4.5F * var7,
         var6 * 0.55F,
         18.0F * var7,
         var18,
         var17,
         false,
         var3.m1451(var4, var5, var6, 18.0F * var7)
      );
      String var19 = var2.m18();
      String var20 = this.f4.getOrDefault(var2, var19);
      String var21 = this.f3.getOrDefault(var2, var19);
      if (!var21.equals(var19)) {
         this.f4.put(var2, var21);
         this.f3.put(var2, var19);
         this.f5.put(var2, 0.0F);
         var20 = var21;
      }

      float var22 = 2.5F;
      float var23 = this.f5.getOrDefault(var2, 1.0F);
      if (var23 < 1.0F) {
         var23 = Math.min(1.0F, var23 + var13 * var22);
         this.f5.put(var2, var23);
      }

      Color var24 = var3.m336(new Color(150, 150, 156), var8);
      float var25 = var4 + var6 - var6 * 0.45F;
      float var26 = var6 * 0.45F;
      float var27 = var5 + 4.5F * var7;
      float var28 = 18.0F * var7;
      ScissorUtil.m357((double)var25, (double)var5, (double)var26, (double)var28);
      if (var23 < 1.0F) {
         float var29 = this.m3(var23);
         float var30 = var29 * var28;
         var3.m1450(
            var1,
            var2,
            "value_old",
            var20,
            var25,
            var27 - var30,
            var26,
            var28,
            var18,
            var3.m336(var24, var8 * (1.0F - var23)),
            true,
            false
         );
         var3.m1450(
            var1,
            var2,
            "value_new",
            var19,
            var25,
            var27 + var28 - var30,
            var26,
            var28,
            var18,
            var3.m336(var24, var8 * var23),
            true,
            false
         );
      } else {
         var3.m1450(
            var1,
            var2,
            "value",
            var19,
            var25,
            var27,
            var26,
            var28,
            var18,
            var24,
            true,
            var3.m1451(var4, var5, var6, 18.0F * var7)
         );
      }

      ScissorUtil.m29();
      List var45 = var2.m19();
      int var46 = Math.max(0, var45.indexOf(var2.m18()));
      float var31 = (float)var46 * 18.0F * var7;
      float var32 = this.f1.getOrDefault(var2, var31);
      if (var32 < var31) {
         var32 = Math.min(var31, var32 + var13 * 250.0F * var7);
      } else if (var32 > var31) {
         var32 = Math.max(var31, var32 - var13 * 250.0F * var7);
      }

      this.f1.put(var2, var32);
      float var33 = var5 + 18.0F * var7;
      float var34 = (float)var45.size() * 18.0F * var7;
      Render2DUtil.m195(var4, var33 + 2.0F * var7, 1.5F * var7, var34 - 4.0F * var7, 0.75F * var7, var3.m336(new Color(60, 60, 65, 100), var8));
      Render2DUtil.m195(var4, var33 + var32 + 2.0F * var7, 1.5F * var7, 18.0F * var7 - 4.0F * var7, 0.75F * var7, var3.m336(ThemeManager.m1379(), var8));
      float var35 = var33;
      float var36 = 7.2F * var7;

      for (int var37 = 0; var37 < var45.size(); var37++) {
         String var38 = (String)var45.get(var37);
         boolean var39 = var37 == var46;
         String var40 = System.identityHashCode(var2) + ":" + var38;
         float var41 = this.f6.getOrDefault(var40, var39 ? 1.0F : 0.0F);
         if (var39 && var41 < 1.0F) {
            var41 = Math.min(1.0F, var41 + var13 * 8.0F);
         } else if (!var39 && var41 > 0.0F) {
            var41 = Math.max(0.0F, var41 - var13 * 8.0F);
         }

         this.f6.put(var40, var41);
         Color var42 = var3.m336(new Color(170, 170, 175), var8);
         Color var43 = var3.m336(ThemeManager.m1379(), var8);
         Color var44 = this.m944(var42, var43, var41);
         var3.m1450(
            var1,
            var2,
            "option:" + var38,
            var38,
            var4 + 6.0F * var7,
            var35 + 4.2F * var7,
            var6 - 6.0F * var7,
            18.0F * var7,
            var36,
            var44,
            false,
            var3.m1451(var4, var35, var6, 18.0F * var7)
         );
         var35 += 18.0F * var7;
      }
   }

   public boolean m1411(ModeSettingBase var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      if (var3 != 0) {
         return false;
      } else if (var5 >= 18.0F) {
         int var7 = (int)((var5 - 18.0F) / 18.0F);
         List var8 = var1.m19();
         if (var7 >= 0 && var7 < var8.size()) {
            var1.m16((String)var8.get(var7));
         }

         return true;
      } else {
         return false;
      }
   }

   public float m1410(ModeSettingBase var1, SettingRowRenderer var2) {
      return 18.0F * (1.0F + (float)var1.m19().size());
   }

   public String m1409(ModeSettingBase var1) {
      return var1.m18();
   }
}
