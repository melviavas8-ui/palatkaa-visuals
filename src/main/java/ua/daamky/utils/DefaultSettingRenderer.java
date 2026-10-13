package ua.daamky.utils;

import ua.daamky.settings.Setting;
import net.minecraft.client.gui.DrawContext;

public class DefaultSettingRenderer implements SettingRenderer<Setting> {
   public static final DefaultSettingRenderer f1 = new DefaultSettingRenderer();

   public DefaultSettingRenderer() {
   }

   @Override
   public boolean m1403(Setting var1) {
      return true;
   }

   @Override
   public void m1412(DrawContext var1, Setting var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
      var3.m1448(var1, var2, this.m1409(var2), var4, var5, var6, var7, var8);
   }

   @Override
   public boolean m1411(Setting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      return true;
   }

   @Override
   public float m1410(Setting var1, SettingRowRenderer var2) {
      return 18.0F;
   }

   @Override
   public String m1409(Setting var1) {
      return "...";
   }
}
