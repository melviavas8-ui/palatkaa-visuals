package ua.daamky.features.movement;

import com.mojang.authlib.GameProfile;
import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.SilentPacketUtil;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket.Mode;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;

@NewFunction(
   I0 = "Blink",
   I00 = "Задерживает пакеты движения",
   I000 = Category.MOVEMENT
)
public class Blink extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Фейк игрок", true);
   private final BooleanSetting f2 = new BooleanSetting("Засада", false);
   private final BooleanSetting f3 = new BooleanSetting("Авто сброс", false);
   private final NumberSetting f4 = new NumberSetting("Сброс после", 100.0, 1.0, 1000.0, 1.0);
   private final ModeSettingBase f5 = new ModeSettingBase(
      "Действие",
      "Blink",
      "Blink",
      "Reset"
   );
   private final BooleanSetting f6 = new BooleanSetting("Авто выкл", true);
   private final CopyOnWriteArrayList<Packet<?>> f7 = new CopyOnWriteArrayList<>();
   private OtherClientPlayerEntity f8;

   public Blink() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4, this.f5, this.f6});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (!this.util.m81()) {
         this.f7.clear();
         if (this.f1.m6()) {
            this.m115();
         }
      }
   }

   @Override
   public void onDisable() {
      this.m676();
      this.m116();
      this.f7.clear();
      super.onDisable();
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (!this.util.m81() && !SilentPacketUtil.f1) {
         Packet var2 = var1.m581();
         if (var2 instanceof ClientStatusC2SPacket var3 && var3.getMode() == Mode.PERFORM_RESPAWN) {
            this.setEnabled(false);
            return;
         }

         if (this.f2.m6() && var2 instanceof PlayerInteractEntityC2SPacket) {
            this.setEnabled(false);
         } else {
            if (var2 instanceof PlayerMoveC2SPacket) {
               this.f7.add(var2);
               var1.m29();
            }
         }
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (var1.m581() instanceof PlayerRespawnS2CPacket || var1.m581() instanceof GameJoinS2CPacket) {
         this.setEnabled(false);
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (this.f3.m6() && this.f7.size() > (int)this.f4.getValue()) {
            if (this.f5.m17("Reset")) {
               this.f7.clear();
            } else {
               this.m676();
               if (this.f8 != null) {
                  this.f8.copyPositionAndRotation(this.mc.player);
               }
            }

            if (this.f6.m6()) {
               this.setEnabled(false);
            }
         }
      }
   }

   private void m115() {
      GameProfile var1 = new GameProfile(UUID.randomUUID(), this.mc.player.getName().getString());
      this.f8 = new OtherClientPlayerEntity(this.mc.world, var1);
      this.f8.copyFrom(this.mc.player);
      this.f8.setPos(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ());
      this.f8.setHeadYaw(this.mc.player.getHeadYaw());
      this.mc.world.addEntity(this.f8);
   }

   private void m116() {
      if (this.f8 != null && this.mc.world != null) {
         this.mc.world.removeEntity(this.f8.getId(), RemovalReason.DISCARDED);
         this.f8 = null;
      }
   }

   private void m676() {
      for (Packet var2 : this.f7) {
         SilentPacketUtil.m94(var2);
      }

      this.f7.clear();
   }
}
