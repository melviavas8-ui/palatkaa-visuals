package ua.daamky.features.render;

import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;

@NewFunction(
   I0 = "ItemPhysic",
   I00 = "Добавляет физику предметам, лежащим на земле",
   I000 = Category.RENDER
)
public class ItemPhysic extends Module {
   private final BooleanSetting f1 = new BooleanSetting(
      "Уменьшить размер предметов", false
   );

   public ItemPhysic() {
      this.addSettings(new Setting[]{this.f1});
   }

   public boolean m687() {
      return this.f1.m6();
   }
}
