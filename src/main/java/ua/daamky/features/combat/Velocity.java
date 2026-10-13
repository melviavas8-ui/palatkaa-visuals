package ua.daamky.features.combat;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Velocity",
   I000 = Category.COMBAT,
   I00 = "Полностью убирает откидывание"
)
public class Velocity extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Режим",
      "Пакетный",
      "New Grim",
      "Old Grim",
      "Matrix",
      "Normal"
   );
   private boolean f2;
   private int f3;
   private int key;
   private Vec3d f4;

   public Velocity() {
      this.addSettings(new Setting[]{this.f1});
   }

   @Override
   public void onEnable() {
      this.f3 = 0;
      this.f2 = false;
      this.key = 0;
      this.f4 = null;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.f4 = null;
      super.onDisable();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (!this.mc.player.isTouchingWater() && !this.mc.player.isSubmergedInWater()) {
            if (this.f1.m17("Matrix")) {
               this.m115();
            }

            if (this.f1.m17("New Grim") && this.f2) {
               this.m116();
            }

            if (this.f3 > 0) {
               this.f3--;
            }
         }
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (!this.util.m81()) {
         if (!this.mc.player.isTouchingWater() && !this.mc.player.isSubmergedInWater() && !this.mc.player.isInLava()) {
            if (this.key > 0) {
               this.key--;
            } else {
               if (var1.m581() instanceof EntityVelocityUpdateS2CPacket var2 && var2.getEntityId() == this.mc.player.getId()) {
                  this.m735(var1, var2);
               }

               if (var1.m581() instanceof ExplosionS2CPacket
                  && (this.f1.m17("Пакетный") || this.f1.m17("Normal"))) {
                  var1.m29();
               }

               if (this.f1.m17("Old Grim") && var1.m581() instanceof CommonPingS2CPacket && this.f3 > 0) {
                  var1.m29();
                  this.f3--;
               }

               if (var1.m581() instanceof PlayerPositionLookS2CPacket && this.f1.m17("New Grim")) {
                  this.key = 5;
               }
            }
         }
      }
   }

   private void m735(PacketReceiveEvent var1, EntityVelocityUpdateS2CPacket var2) {
      Vec3d var3 = var2.getVelocity();
      if (this.f1.m17("Matrix")) {
         if (!this.f2) {
            var1.m29();
            this.f2 = true;
         } else {
            this.f2 = false;
            var1.m29();
            this.f4 = new Vec3d(var3.x * -0.1, var3.y, var3.z * -0.1);
         }
      } else if (this.f1.m17("Normal") || this.f1.m17("Пакетный")) {
         var1.m29();
      } else if (this.f1.m17("Old Grim")) {
         var1.m29();
         this.f3 = 6;
      } else if (this.f1.m17("New Grim")) {
         var1.m29();
         this.f2 = true;
      }
   }

   private void m115() {
      if (this.f4 != null) {
         this.mc.player.setVelocity(this.f4);
         this.f4 = null;
      }

      if (this.mc.player.hurtTime > 0 && !this.mc.player.isOnGround()) {
         double var1 = (double)(this.mc.player.getYaw() * (float) (Math.PI / 180.0));
         double var3 = Math.hypot(this.mc.player.getVelocity().x, this.mc.player.getVelocity().z);
         this.mc.player.setVelocity(-Math.sin(var1) * var3, this.mc.player.getVelocity().y, Math.cos(var1) * var3);
         this.mc.player.setSprinting(this.mc.player.age % 2 != 0);
      }
   }

   private void m116() {
      if (this.key <= 0) {
         this.mc
            .player
            .networkHandler
            .sendPacket(
               new Full(
                  this.mc.player.getX(),
                  this.mc.player.getY(),
                  this.mc.player.getZ(),
                  this.mc.player.getYaw(),
                  this.mc.player.getPitch(),
                  this.mc.player.isOnGround(),
                  false
               )
            );
         this.mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, this.mc.player.getBlockPos(), Direction.DOWN));
      }

      this.f2 = false;
   }
}
