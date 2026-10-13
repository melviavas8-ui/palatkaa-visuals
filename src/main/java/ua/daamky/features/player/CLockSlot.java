package ua.daamky.features.player;

import ua.daamky.gui.Daamky_2;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.utils.FunTimeUtil;
import net.minecraft.util.Formatting;

@NewFunction(
   I0 = "LockSlot",
   I00 = "Запрещает выбрасывать предметы из выбранных слотов",
   I000 = Category.PLAYER
)
public class CLockSlot extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Блокировать только в PVP", true);
   private final MultiChoiceSetting f2 = new MultiChoiceSetting(
      "Заблокированные слоты",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9"
   );

   public CLockSlot() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   public boolean m10(int var1) {
      if (this.f1.m6() && !FunTimeUtil.m104()) {
         return false;
      } else if (this.f2.m20(String.valueOf(var1 + 1))) {
         Daamky_2.m467("Выброс из слота " + (var1 + 1) + " заблокирован", Formatting.RED);
         return true;
      } else {
         return false;
      }
   }
}
