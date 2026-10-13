package ua.daamky.utils;

import ua.daamky.settings.Setting;
import net.minecraft.client.gui.DrawContext;

public interface SettingRenderer<T extends Setting> {
   boolean m1403(Setting var1);

   default void m1412(DrawContext var1, T var2, SettingRowRenderer var3, float var4, float var5, float var6, float var7, float var8) {}

   default boolean m1411(T var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      return false;
   }

   default boolean m1423(T var1, SettingRowRenderer var2, int var3, float var4, float var5, float var6) {
      return false;
   }

   default float m1410(T var1, SettingRowRenderer var2) {
      return 0.0F;
   }

   default String m1409(T var1) {
      return var1.getName();
   }
}
