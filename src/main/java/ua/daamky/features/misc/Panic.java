package ua.daamky.features.misc;

import ua.daamky.gui.Daamky_2;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import net.minecraft.util.Formatting;

@NewFunction(
   I0 = "Panic",
   I00 = "Быстро прячет клиент, кнопка возврата",
   I000 = Category.MISC
)
public class Panic extends Module {
   private final KeybindSetting f1 = new KeybindSetting("Кнопка возврата", -1);
   private boolean f2;

   public Panic() {
      this.addSettings(new Setting[]{this.f1});
   }

   public boolean m687() {
      return this.f2;
   }

   public void updateToggled(boolean var1) {
      this.f2 = var1;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (this.mc.player != null) {
         if (this.f1.getKey() == -1) {
            Daamky_2.m467("Забиндите кнопку возврата!", Formatting.RED);
            this.setEnabled(false);
         } else {
            this.f2 = true;
            this.mc.player.closeHandledScreen();
         }
      }
   }
}
