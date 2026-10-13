package ua.daamky.utils;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.ClientConnection;

public class BotSession {
   public final String f1;
   public final String f2;
   public final ClientConnection f3;
   public final ClientPlayNetworkHandler f4;
   public final ClientWorld f5;
   public final ClientPlayerEntity f6;
   public final ClientPlayerInteractionManager f7;

   public BotSession(
      String var1,
      String var2,
      ClientConnection var3,
      ClientPlayNetworkHandler var4,
      ClientWorld var5,
      ClientPlayerEntity var6,
      ClientPlayerInteractionManager var7
   ) {
      this.f1 = var1;
      this.f2 = var2;
      this.f3 = var3;
      this.f4 = var4;
      this.f5 = var5;
      this.f6 = var6;
      this.f7 = var7;
   }

   public String m37() {
      return this.f1;
   }

   public String m40() {
      return this.f2;
   }

   public ClientConnection m71() {
      return this.f3;
   }

   public ClientPlayNetworkHandler m72() {
      return this.f4;
   }

   public ClientWorld m73() {
      return this.f5;
   }

   public ClientPlayerEntity m74() {
      return this.f6;
   }

   public ClientPlayerInteractionManager m75() {
      return this.f7;
   }
}
