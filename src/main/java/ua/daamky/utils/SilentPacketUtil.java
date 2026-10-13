package ua.daamky.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;

public class SilentPacketUtil {
   public static boolean f1;

   public static void m94(Packet<?> var0) {
      if (MinecraftClient.getInstance().getNetworkHandler() != null) {
         f1 = true;
         MinecraftClient.getInstance().getNetworkHandler().sendPacket(var0);
         f1 = false;
      }
   }
}
