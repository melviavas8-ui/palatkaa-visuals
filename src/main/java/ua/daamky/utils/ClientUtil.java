package ua.daamky.utils;

import net.minecraft.client.MinecraftClient;

public class ClientUtil {
   MinecraftClient mc = MinecraftClient.getInstance();

   public boolean m91() {
      return this.mc.player == null;
   }

   public boolean m81() {
      return this.mc.player == null || this.mc.world == null;
   }

   public boolean m6() {
      return this.mc.world == null;
   }
}
