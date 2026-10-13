package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RaytraceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "MaceTarget",
   I00 = "Автоатака булавой при падении",
   I000 = Category.COMBAT
)
public class MaceTarget extends Module {
   private final NumberSetting f1 = new NumberSetting("Дистанция", 3.0, 2.0, 3.0, 0.5);
   private final NumberSetting f2 = new NumberSetting("Мин. высота падения", 3.0, 0.0, 20.0, 0.5);
   private final BooleanSetting f3 = new BooleanSetting("Свап на булаву", true);
   private final BooleanSetting f4 = new BooleanSetting("Только при падении вниз", true);
   private long f5;
   private int f6 = -1;

   public MaceTarget() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4});
   }

   @Override
   public void onDisable() {
      this.m676();
      super.onDisable();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (!this.mc.player.isOnGround() && (!this.f4.m6() || !(this.mc.player.getVelocity().y >= 0.0)) && !(this.mc.player.fallDistance < this.f2.getValue())
            )
          {
            LivingEntity var2 = this.m770();
            if (var2 == null) {
               this.m676();
            } else {
               if (this.f3.m6()) {
                  int var3 = this.m715();
                  if (var3 == -1) {
                     return;
                  }

                  if (this.mc.player.getInventory().getSelectedSlot() != var3) {
                     this.f6 = this.mc.player.getInventory().getSelectedSlot();
                     this.mc.player.getInventory().setSelectedSlot(var3);
                     this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(var3));
                  }
               } else if (this.mc.player.getMainHandStack().getItem() != Items.MACE) {
                  return;
               }

               if (System.currentTimeMillis() - this.f5 >= 450L) {
                  this.mc.interactionManager.attackEntity(this.mc.player, var2);
                  this.mc.player.swingHand(Hand.MAIN_HAND);
                  this.m676();
                  this.f5 = System.currentTimeMillis();
               }
            }
         } else {
            this.m676();
         }
      }
   }

   private LivingEntity m770() {
      LivingEntity var1 = null;
      double var2 = Double.MAX_VALUE;

      for (Entity var5 : this.mc.world.getEntities()) {
         if (var5 instanceof LivingEntity) {
            LivingEntity var6 = (LivingEntity)var5;
            if (var5 != this.mc.player && var5.isAlive() && !(var5 instanceof ArmorStandEntity)) {
               if (var5 instanceof PlayerEntity) {
                  PlayerEntity var7 = (PlayerEntity)var5;
                  if (RaytraceUtil.m80(var7.getNameForScoreboard())) {
                     continue;
                  }
               }

               double var9 = (double)this.mc.player.distanceTo(var5);
               if (!(var9 > this.f1.getValue()) && !(var9 >= var2)) {
                  var2 = var9;
                  var1 = var6;
               }
            }
         }
      }

      return var1;
   }

   private int m715() {
      for (int var1 = 0; var1 < 9; var1++) {
         if (this.mc.player.getInventory().getStack(var1).getItem() == Items.MACE) {
            return var1;
         }
      }

      return -1;
   }

   private void m676() {
      if (this.f6 != -1 && this.mc.player != null) {
         this.mc.player.getInventory().setSelectedSlot(this.f6);
         this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.f6));
         this.f6 = -1;
      }
   }
}
