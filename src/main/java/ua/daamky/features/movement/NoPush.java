package ua.daamky.features.movement;

import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;

@NewFunction(
   I0 = "NoPush",
   I00 = "Отключает отталкивание от сущностей и воды",
   I000 = Category.MOVEMENT
)
public class NoPush extends Module {
   public static NoPush f1;
   public static BooleanSetting f2 = new BooleanSetting("Сущности", true);
   public static BooleanSetting f3 = new BooleanSetting("Вода", true);

   public NoPush() {
      f1 = this;
      this.addSettings(new Setting[]{f2, f3});
   }
}
