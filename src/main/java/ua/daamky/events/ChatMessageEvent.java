package ua.daamky.events;

import ua.daamky.system.events.CancellableEvent;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

public class ChatMessageEvent extends CancellableEvent {
   private final GameMessageS2CPacket f1;

   public ChatMessageEvent(GameMessageS2CPacket var1) {
      this.f1 = var1;
   }
   public GameMessageS2CPacket m556() {
      return this.f1;
   }
}
