package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;

import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

@NewFunction(
   I0 = "ElytraMotion",
   I00 = "Останавливает элитру у цели прицела",
   I000 = Category.MOVEMENT
)
public class ElytraMotion extends Module {
   private final NumberSetting f1 = new NumberSetting("Дистанция", 3.0, 1.0, 6.0, 0.1);
   private final BooleanSetting f2 = new BooleanSetting("Обход", false);
   private boolean f3;

   public ElytraMotion() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null) {
         LivingEntity var3 = null;
         if (this.mc.crosshairTarget != null && this.mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            Entity e = ((EntityHitResult) this.mc.crosshairTarget).getEntity();
            if (e instanceof LivingEntity living && living.isAlive() && living != this.mc.player) {
               var3 = living;
            }
         }
         if (var3 == null) {
            if (!this.f3) {
               this.mc.player.setNoGravity(false);
               this.f3 = true;
            }
         } else {
            this.f3 = false;
            float var4 = (float)this.mc.player.getEyePos().distanceTo(var3.getBoundingBox().getCenter());
            if (this.mc.player.isGliding() && (double)var4 < this.f1.getValue()) {
               if (this.f2.m6()) {
                  double var5 = Math.toRadians((double)this.mc.player.getYaw());
                  this.mc.player.setVelocity(-Math.sin(var5) * 0.01, -1.0E-4, Math.cos(var5) * 0.01);
               } else {
                  this.mc.player.setVelocity(0.0, 0.0, 0.0);
               }

               this.mc.player.setNoGravity(true);
            } else {
               this.mc.player.setNoGravity(false);
            }
         }
      }
   }

   @Override
   public void onDisable() {
      if (this.mc.player != null) {
         this.mc.player.setNoGravity(false);
      }

      this.f3 = false;
      super.onDisable();
   }
}
