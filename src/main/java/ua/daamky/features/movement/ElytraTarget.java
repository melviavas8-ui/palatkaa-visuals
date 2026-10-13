package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "ElytraTarget",
   I00 = "Таргетит игроков на элитре и летит за ними",
   I000 = Category.MOVEMENT
)
public class ElytraTarget extends Module {
   private final NumberSetting f1 = new NumberSetting("Дистанция", 50.0, 5.0, 200.0, 1.0);
   private PlayerEntity f2 = null;

   public ElytraTarget() {
      this.addSettings(new Setting[]{this.f1});
   }

   public double m724() {
      return this.f1.getValue();
   }

   @Override
   public void onDisable() {
      this.f2 = null;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.player != null && this.mc.world != null) {
         this.f2 = null;
         double var2 = this.f1.getValue() * this.f1.getValue();

         for (PlayerEntity var5 : this.mc.world.getPlayers()) {
            if (var5 != this.mc.player && var5.isAlive() && var5.isGliding()) {
               double var6 = this.mc.player.squaredDistanceTo(var5);
               if (var6 <= var2) {
                  var2 = var6;
                  this.f2 = var5;
               }
            }
         }

         if (this.f2 != null && this.mc.player.isGliding()) {
            Vec3d var13 = this.f2.getBoundingBox().getCenter();
            Vec3d var14 = this.f2.getVelocity();
            if (var14.lengthSquared() > 0.001) {
               var13 = var13.add(var14.multiply(1.5));
            }

            RotationVec var15 = RotationUtil.m417(var13);
            RotationUtil.m406(var15, RotationMode.f3, 180.0F, 180.0F, 180.0F);
            Vec3d var7 = var13.subtract(this.mc.player.getEyePos());
            double var8 = Math.sqrt(this.mc.player.squaredDistanceTo(var13));
            Vec3d var10 = var7.normalize();
            if (var8 < 3.0) {
               this.mc.player.setVelocity(this.f2.getVelocity().add(var10.multiply(0.2)));
            } else {
               double var11 = Math.min(this.mc.player.getVelocity().length(), 3.0);
               this.mc.player.setVelocity(var10.multiply(var11));
            }
         }
      }
   }
}
