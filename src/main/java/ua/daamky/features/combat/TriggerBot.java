package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

@NewFunction(
   I0 = "TriggerBot",
   I00 = "Бьёт сущностей при наводке",
   I000 = Category.COMBAT
)
public class TriggerBot extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Только криты", true);
   private final KeybindSetting f2 = new KeybindSetting("Бинд", 0);
   private long f3 = 0L;

   public TriggerBot() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @Override
   public void setKey(int var1) {
      super.setKey(var1);
      if (this.f2 != null) {
         this.f2.m15(var1);
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.f2.getKey() != super.getKey()) {
         super.setKey(this.f2.getKey());
      }

      HitResult var2 = this.mc.crosshairTarget;
      if (System.currentTimeMillis() - this.f3 >= 450L) {
         if (var2 instanceof EntityHitResult var3) {
            if (!(var3.getEntity() instanceof PlayerEntity)) {
               return;
            }

            float var4 = this.mc.player.getAttackCooldownProgress(1.5F);
            if (var4 < 0.92F) {
               return;
            }

            if (this.f1.m6() && this.mc.player.fallDistance < 0.2F) {
               return;
            }

            boolean var5 = this.mc.player.isSprinting();
            if (var5) {
               this.mc.player.setSprinting(false);
               this.mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(this.mc.player, Mode.STOP_SPRINTING));
            }

            this.f3 = System.currentTimeMillis();
            this.mc.interactionManager.attackEntity(this.mc.player, var3.getEntity());
            this.mc.player.swingHand(Hand.MAIN_HAND);
            if (var5) {
               this.mc.player.setSprinting(true);
               this.mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(this.mc.player, Mode.START_SPRINTING));
            }
         }
      }
   }

   public boolean m687() {
      return false;
   }

   public LivingEntity m584() {
      if (this.mc.crosshairTarget instanceof EntityHitResult var1) {
         Entity var4 = var1.getEntity();
         if (var4 instanceof PlayerEntity) {
            return (PlayerEntity)var4;
         }
      }

      return null;
   }
}
