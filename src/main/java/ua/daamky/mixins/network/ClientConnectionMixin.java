package ua.daamky.mixins.network;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.system.events.EventRegistry;
import ua.daamky.utils.player.RotationUtil;
import io.netty.channel.ChannelHandlerContext;
import java.lang.reflect.Method;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientConnection.class})
public class ClientConnectionMixin {
   @ModifyVariable(
      method = {"send"},
      at = @At("HEAD"),
      argsOnly = true
   )
   private Packet<?> onSendModify(Packet<?> var1) {
      if (RotationUtil.m6() && var1 instanceof PlayerInteractItemC2SPacket var2) {
         try {
            return new PlayerInteractItemC2SPacket(var2.getHand(), var2.getSequence(), RotationUtil.m415().m329(), RotationUtil.m415().m271());
         } catch (NoSuchMethodError var9) {
            try {
               Method var4 = var2.getClass().getMethod("hand");
               Method var5 = var2.getClass().getMethod("sequence");
               Hand var6 = (Hand)var4.invoke(var2);
               int var7 = (Integer)var5.invoke(var2);
               return new PlayerInteractItemC2SPacket(var6, var7, RotationUtil.m415().m329(), RotationUtil.m415().m271());
            } catch (Exception var8) {
               var8.printStackTrace();
            }
         }
      }

      return var1;
   }

   @Inject(
      method = {"send(Lnet/minecraft/network/packet/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSend(Packet<?> var1, CallbackInfo var2) {
      PacketSendEvent var3 = new PacketSendEvent(var1);
      EventRegistry.m1315(var3);
      if (var3.isCancelled()) {
         var2.cancel();
      }
   }

   @Inject(
      method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onReceive(ChannelHandlerContext var1, Packet<?> var2, CallbackInfo var3) {
      PacketReceiveEvent var4 = new PacketReceiveEvent(var2);
      EventRegistry.m1315(var4);
      if (var4.isCancelled()) {
         var3.cancel();
      }
   }
}
