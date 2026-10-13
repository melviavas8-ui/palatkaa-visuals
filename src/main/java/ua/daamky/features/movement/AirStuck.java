package ua.daamky.features.movement;

import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "AirStuck",
   I00 = "Замораживает игрока, отменяя пакеты перемещения",
   I000 = Category.MOVEMENT
)
public class AirStuck extends Module {
   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         this.mc.player.setVelocity(Vec3d.ZERO);
         this.mc.player.fallDistance = 0.0;
         if (this.mc.player.getAbilities() != null) {
            this.mc.player.getAbilities().flying = false;
         }
      }
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         if (var1.m581() instanceof PlayerMoveC2SPacket) {
            var1.m29();
         }
      }
   }

   @Override
   public void onDisable() {
      if (this.mc.player != null) {
         this.mc.player.setVelocity(Vec3d.ZERO);
         this.mc.player.fallDistance = 0.0;
      }
   }
}
