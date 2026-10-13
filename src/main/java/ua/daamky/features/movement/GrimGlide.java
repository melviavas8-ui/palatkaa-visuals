package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "GrimGlide",
   I000 = Category.MOVEMENT,
   I00 = "Ускорение на элитре без фееров"
)
public class GrimGlide extends Module {
   private long f1 = 0L;
   private int f2 = 0;

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m91() && this.mc.player.isGliding()) {
         this.f2++;
         Vec3d var2 = this.mc.player.getEntityPos();
         float var3 = this.mc.player.getYaw();
         double var4 = this.mc.player.age % 2 == 0 ? 0.087 : 0.09;
         double var6 = -Math.sin(Math.toRadians((double)var3)) * var4;
         double var8 = Math.cos(Math.toRadians((double)var3)) * var4;
         if (System.currentTimeMillis() - this.f1 >= 40L) {
            this.mc.player.setPosition(var2.getX() + var6, var2.getY(), var2.getZ() + var8);
            this.f1 = System.currentTimeMillis();
         }

         if (this.f2 % 40 == 0) {
            this.mc
               .player
               .setVelocity(
                  var6 * (double)ThreadLocalRandom.current().nextFloat(1.001F, 1.0021F),
                  this.mc.player.getVelocity().y + 0.00600000075995922,
                  var8 * (double)ThreadLocalRandom.current().nextFloat(1.001F, 1.0021F)
               );
         }
      }
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.f2 = 0;
      this.f1 = System.currentTimeMillis();
   }

   @Override
   public void onDisable() {
      this.f2 = 0;
      super.onDisable();
   }
}
