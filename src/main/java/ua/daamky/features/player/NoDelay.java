package ua.daamky.features.player;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.mixins.interfaces.IClientPlayerInteractionManager;
import ua.daamky.mixins.interfaces.ILivingEntity;
import ua.daamky.mixins.interfaces.IMinecraftClient;
import ua.daamky.settings.MultiChoiceSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;

@NewFunction(
   I0 = "NoDelay",
   I00 = "Убирает задержку у выбранных элементов",
   I000 = Category.PLAYER
)
public class NoDelay extends Module {
   final MultiChoiceSettingBase f1 = new MultiChoiceSettingBase(
      "Убрать задержку",
      "Прыжка",
      "Ломания",
      "ПКМ"
   );

   public NoDelay() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m91()) {
         if (this.f1.m20("Ломания")) {
            ((IClientPlayerInteractionManager)this.mc.interactionManager).setBlockBreakingCooldown(0);
         }

         if (this.f1.m20("Прыжка")) {
            ((ILivingEntity)this.mc.player).setJumpingCooldown(0);
         }

         if (this.f1.m20("ПКМ")) {
            ((IMinecraftClient)this.mc).setItemUseCooldown(0);
         }
      }
   }
}
