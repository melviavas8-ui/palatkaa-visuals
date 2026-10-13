package ua.daamky.features.player;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.mixins.interfaces.IMinecraftClient;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.item.Items;

@NewFunction(
   I0 = "FastExp",
   I00 = "Позволяет очень быстро бросать опыт",
   I000 = Category.PLAYER
)
public class FastExp extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (this.mc.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
            ((IMinecraftClient)this.mc).setItemUseCooldown(0);
         }
      }
   }
}
