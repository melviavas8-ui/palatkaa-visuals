package org.palatkaa.utils.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import org.palatkaa.mixin.accessor.ClientLevelAccessor;

public final class PacketUtil {
   private static final Minecraft MC = Minecraft.getInstance();

   private PacketUtil() {
   }

   public static void sendHeldItemChange(int slot) {
      if (MC.player != null && MC.getConnection() != null && slot >= 0 && slot <= 8) {
         MC.getConnection().send(new ServerboundSetCarriedItemPacket(slot));
      }
   }

   public static void sendUseItem(InteractionHand hand) {
      if (MC.player != null) {
         sendUseItem(hand, MC.player.getYRot(), MC.player.getXRot());
      }
   }

   public static void sendUseItem(InteractionHand hand, float yaw, float pitch) {
      if (MC.player != null && MC.getConnection() != null && MC.level != null) {
         int sequence = 0;
         BlockStatePredictionHandler pendingUpdateManager = null;

         try {
            ClientLevelAccessor accessor = (ClientLevelAccessor)MC.level;
            pendingUpdateManager = accessor.palatkaa$getBlockStatePredictionHandler().startPredicting();
            sequence = pendingUpdateManager.currentSequence();
         } catch (Exception var30) {
         } finally {
            try {
               MC.getConnection().send(new ServerboundUseItemPacket(hand, sequence, yaw, pitch));
            } finally {
               if (pendingUpdateManager != null) {
                  pendingUpdateManager.close();
               }
            }
         }
      }
   }
}

