package ua.daamky.features.combat;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.Daamky_2;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Formatting;

@NewFunction(
   I0 = "AntiBot",
   I00 = "Скрывает фальшивых игроков, появляющихся в мире",
   I000 = Category.COMBAT
)
public class AntiBot extends Module {
   private final List<UUID> f1 = new ArrayList<>();
   private final Set<UUID> f2 = new HashSet<>();

   @Override
   public void onEnable() {
      super.onEnable();
      this.f1.clear();
      this.f2.clear();
   }

   @Override
   public void onDisable() {
      this.f1.clear();
      this.f2.clear();
      super.onDisable();
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (!this.util.m81() && this.mc.getNetworkHandler() != null) {
         if (var1.m581() instanceof EntitySpawnS2CPacket var2) {
            if (var2.getEntityType() == EntityType.PLAYER) {
               PlayerListEntry var6 = this.mc.getNetworkHandler().getPlayerListEntry(var2.getUuid());
               boolean var4 = var6 != null && !var6.getProfile().properties().get("textures").isEmpty();
               boolean var5 = var6 == null || var6.getLatency() == 0;
               if (!var4 && var5) {
                  this.f1.add(var2.getUuid());
               }
            }
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         ArrayList var2 = new ArrayList();

         for (UUID var4 : this.f1) {
            for (PlayerEntity var6 : this.mc.world.getPlayers()) {
               if (var6.getUuid().equals(var4)) {
                  boolean var7 = !var6.getEquippedStack(EquipmentSlot.HEAD).isEmpty()
                     && !var6.getEquippedStack(EquipmentSlot.CHEST).isEmpty()
                     && !var6.getEquippedStack(EquipmentSlot.LEGS).isEmpty()
                     && !var6.getEquippedStack(EquipmentSlot.FEET).isEmpty();
                  if (var7) {
                     this.f2.add(var4);
                     Daamky_2.m467(
                        "Фальшивый игрок обнаружен и удалён из мира",
                        Formatting.GRAY
                     );
                     this.mc.world.removeEntity(var6.getId(), RemovalReason.DISCARDED);
                  }

                  var2.add(var4);
                  break;
               }
            }
         }

         this.f1.removeAll(var2);
      }
   }

   public boolean m595(PlayerEntity var1) {
      return var1 != null && this.f2.contains(var1.getUuid());
   }
}
