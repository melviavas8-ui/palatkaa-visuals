package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.MovementUtil;

@NewFunction(
   I0 = "DragonFly",
   I00 = "Ускоряет уже активный полёт",
   I000 = Category.MOVEMENT
)
public class DragonFly extends Module {
   private final NumberSetting f1 = new NumberSetting("Скорость по X/Z", 1.0, 0.0, 2.0, 0.1);
   private final NumberSetting f2 = new NumberSetting("Скорость по Y", 1.0, 0.0, 2.0, 0.1);

   public DragonFly() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.player.getAbilities().flying) {
         MovementUtil.m25(this.f1.getValue());
         if (this.mc.options.jumpKey.isPressed()) {
            this.mc.player.setVelocity(this.mc.player.getVelocity().x, this.f2.getValue(), this.mc.player.getVelocity().z);
         }

         if (this.mc.options.sneakKey.isPressed()) {
            this.mc.player.setVelocity(this.mc.player.getVelocity().x, -this.f2.getValue(), this.mc.player.getVelocity().z);
         }
      }
   }
}
