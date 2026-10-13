package ua.daamky.features.render;

import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;

@NewFunction(
   I0 = "SeeInvisibles",
   I00 = "Делает невидимых игроков видимыми",
   I000 = Category.RENDER
)
public class SeeInvisibles extends Module {
   private final NumberSetting f1 = new NumberSetting("Прозрачность", 0.5, 0.1, 1.0, 0.1);

   public SeeInvisibles() {
      this.addSettings(new Setting[]{this.f1});
   }

   public float m2() {
      return (float)this.f1.getValue();
   }
}
