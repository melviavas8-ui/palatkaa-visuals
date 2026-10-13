package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.MovementUtil;
import ua.daamky.utils.BlockCollisionUtil;
import net.minecraft.block.Blocks;

@NewFunction(
   I0 = "NoWeb",
   I00 = "Позволяет быстро перемещаться в паутине",
   I000 = Category.MOVEMENT
)
public class NoWeb extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (BlockCollisionUtil.m95(Blocks.COBWEB)) {
            MovementUtil.m93(0.64, 0.0);
            if (this.mc.options.jumpKey.isPressed()) {
               MovementUtil.m93(0.64, 0.95);
            }

            if (this.mc.options.sneakKey.isPressed()) {
               MovementUtil.m93(0.64, -0.95);
            }
         }
      }
   }
}
