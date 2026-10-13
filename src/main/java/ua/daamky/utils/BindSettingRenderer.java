package ua.daamky.utils;

import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BindSetting;
import ua.daamky.settings.Setting;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
import net.minecraft.client.gui.DrawContext;

public class BindSettingRenderer implements SettingRenderer<BindSetting> {
   @Override
   public boolean m1403(Setting var1) {
      return var1 instanceof BindSetting;
   }

   public void m1412(DrawContext var1, BindSetting var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
      var3.m1448(var1, var2, this.m1409(var2), var4, var5, var6, var7, var8);
      if (var2.m31()) {
         Color var9 = var3.m336(ThemeManager.m1379(), var8);
         Render2DUtil.m195(var4 + var6 - 2.0F * var7, var5 + 4.0F * var7, 1.0F * var7, 10.0F * var7, 0.5F * var7, var9);
      }
   }

   public boolean m1411(BindSetting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      if (var3 != 0) {
         return false;
      } else {
         var1.m4(true);
         return true;
      }
   }

   public float m1410(BindSetting var1, SettingRowRenderer var2) {
      return 18.0F;
   }

   public String m1409(BindSetting var1) {
      return var1.m31() ? var1.m30() + "_" : var1.m30();
   }
}
