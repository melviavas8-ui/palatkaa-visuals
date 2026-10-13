package ua.daamky.features.render;

import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;

@NewFunction(
   I0 = "NoRender",
   I00 = "Отключает выбранные визуальные эффекты",
   I000 = Category.RENDER
)
public class NoRender extends Module {
   public static NoRender f1;
   private final BooleanSetting f2 = new BooleanSetting("Виньетка", true);
   private final BooleanSetting f3 = new BooleanSetting("Плохие эффекты", true);
   private final BooleanSetting f4 = new BooleanSetting("Огонь на весь экран", true);
   private final BooleanSetting f5 = new BooleanSetting("Тряска камеры", true);
   private final BooleanSetting f6 = new BooleanSetting("Скрыть прицел", false);

   public NoRender() {
      f1 = this;
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4, this.f5, this.f6});
   }

   public static boolean m687() {
      return f1 != null && f1.isEnabled() && f1.f2.m6();
   }

   public static boolean m1() {
      return f1 != null && f1.isEnabled() && f1.f3.m6();
   }

   public static boolean m585() {
      return f1 != null && f1.isEnabled() && f1.f4.m6();
   }

   public static boolean m665() {
      return f1 != null && f1.isEnabled() && f1.f5.m6();
   }

   public static boolean m534() {
      return f1 != null && f1.isEnabled() && f1.f6.m6();
   }
}
