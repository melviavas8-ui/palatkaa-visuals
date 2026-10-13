package ua.daamky.utils;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.MultiChoiceSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.DrawContext;

public class MultiChoiceSettingRenderer implements SettingRenderer<MultiChoiceSettingBase> {
   private final Map<String, Float> f1 = new HashMap<>();
   private final Map<Setting, Long> f2 = new HashMap<>();
   private final Map<Setting, String> f3 = new HashMap<>();
   private final Map<Setting, String> f4 = new HashMap<>();
   private final Map<Setting, Float> f5 = new HashMap<>();

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

   @Override
   public boolean m1403(Setting var1) {
      return var1 instanceof MultiChoiceSettingBase;
   }

   public void m1412(DrawContext var1, MultiChoiceSettingBase var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
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
      String var19 = this.m1409(var2);
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
      List<String> var42 = var2.m19();
      float var43 = var5 + 18.0F * var7;
      float var31 = (float)var42.size() * 18.0F * var7;
      Render2DUtil.m195(var4, var43 + 2.0F * var7, 1.5F * var7, var31 - 4.0F * var7, 0.75F * var7, var3.m336(new Color(60, 60, 65, 100), var8));
      float var32 = var43;
      float var33 = 7.2F * var7;

      for (String var35 : var42) {
         String var36 = System.identityHashCode(var2) + ":" + var35;
         boolean var37 = var2.m20(var35);
         float var38 = this.f1.getOrDefault(var36, var37 ? 1.0F : 0.0F);
         if (var37 && var38 < 1.0F) {
            var38 = Math.min(1.0F, var38 + var13 * 8.0F);
         } else if (!var37 && var38 > 0.0F) {
            var38 = Math.max(0.0F, var38 - var13 * 8.0F);
         }

         this.f1.put(var36, var38);
         if (var38 > 0.01F) {
            Render2DUtil.m195(var4, var32 + 2.0F * var7, 1.5F * var7, 18.0F * var7 - 4.0F * var7, 0.75F * var7, var3.m336(ThemeManager.m1379(), var8 * var38));
         }

         Color var39 = var3.m336(new Color(170, 170, 175), var8);
         Color var40 = var3.m336(ThemeManager.m1379(), var8);
         Color var41 = this.m944(var39, var40, var38);
         var3.m1450(
            var1,
            var2,
            "option:" + var35,
            var35,
            var4 + 6.0F * var7,
            var32 + 4.2F * var7,
            var6 - 6.0F * var7,
            18.0F * var7,
            var33,
            var41,
            false,
            var3.m1451(var4, var32, var6, 18.0F * var7)
         );
         var32 += 18.0F * var7;
      }
   }

   private Color m944(Color var1, Color var2, float var3) {
      float var4 = Math.clamp(var3, 0.0F, 1.0F);
      int var5 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var4);
      int var6 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var4);
      int var7 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var4);
      int var8 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var4);
      return new Color(var5, var6, var7, var8);
   }

   public boolean m1411(MultiChoiceSettingBase var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      if (var3 != 0) {
         return false;
      } else {
         List var7 = var1.m19();
         if (var5 >= 18.0F) {
            int var8 = (int)((var5 - 18.0F) / 18.0F);
            if (var8 >= 0 && var8 < var7.size()) {
               var1.m21((String)var7.get(var8));
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public float m1410(MultiChoiceSettingBase var1, SettingRowRenderer var2) {
      return 18.0F * (1.0F + (float)var1.m19().size());
   }

   public String m1409(MultiChoiceSettingBase var1) {
      return var1.m24().size() + " options";
   }
}
