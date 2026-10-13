package ua.daamky.features.movement;

import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.features.combat.TriggerBot;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;

@NewFunction(
   I0 = "Sprint",
   I00 = "Автоматически удерживает состояние бега",
   I000 = Category.MOVEMENT
)
public class Sprint extends Module {
   private boolean f1;

   @Override
   public void onEnable() {
      this.f1 = this.mc.player != null && this.mc.player.isSprinting();
   }

   @Override
   public void onDisable() {
      this.f1 = false;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m91()) {
         TriggerBot var3 = ModuleManager.getModule(TriggerBot.class);
         if (var3 == null || !var3.m687()) {
            if (this.mc.player.isSprinting()) {
               if (!this.m687()) {
                  this.mc.player.setSprinting(false);
               }
            } else if (this.m687()) {
               this.mc.player.setSprinting(true);
            }
         }
      }
   }

   private boolean m687() {
      return !this.mc.player.hasBlindnessEffect()
         && (!this.mc.player.hasVehicle() || this.mc.player.canSprintAsVehicle())
         && this.mc.player.input.hasForwardMovement()
         && this.mc.player.getHungerManager().canSprint();
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (var1.m581() instanceof ClientCommandC2SPacket var2) {
         if (var2.getMode() == Mode.START_SPRINTING) {
            if (this.f1) {
               var1.m29();
               return;
            }

            this.f1 = true;
         } else if (var2.getMode() == Mode.STOP_SPRINTING) {
            if (!this.f1) {
               var1.m29();
               return;
            }

            this.f1 = false;
         }
      }
   }
}
