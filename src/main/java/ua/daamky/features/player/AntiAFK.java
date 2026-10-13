package ua.daamky.features.player;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "AntiAFK",
   I00 = "Предотвращает кик за AFK",
   I000 = Category.PLAYER
)
public class AntiAFK extends Module {
   private long f1 = 0L;

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         if (System.currentTimeMillis() - this.f1 >= 10000L) {
            this.mc.player.swingHand(Hand.MAIN_HAND);
            this.mc.player.jump();
            this.f1 = System.currentTimeMillis();
         }
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.f1 = System.currentTimeMillis();
   }
}
