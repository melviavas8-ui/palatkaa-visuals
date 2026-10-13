package ua.daamky.utils;

import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.Setting;
import net.minecraft.client.gui.DrawContext;

public class KeybindSettingRenderer implements SettingRenderer<KeybindSetting> {
   @Override
   public boolean m1403(Setting var1) {
      return var1 instanceof KeybindSetting;
   }

   public void m1412(DrawContext var1, KeybindSetting var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {
      var3.m1448(var1, var2, this.m1409(var2), var4, var5, var6, var7, var8);
   }

   public boolean m1411(KeybindSetting var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      return var3 == 0;
   }

   public float m1410(KeybindSetting var1, SettingRowRenderer var2) {
      return 18.0F;
   }

   public String m1409(KeybindSetting var1) {
      return var1.getKey() < 0 ? "Press key..." : KeybindFormatter.m90(var1.getKey());
   }
}
