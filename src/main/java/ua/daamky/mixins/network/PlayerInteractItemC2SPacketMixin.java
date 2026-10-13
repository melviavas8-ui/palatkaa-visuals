package ua.daamky.mixins.network;

import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerInteractItemC2SPacket.class})
public interface PlayerInteractItemC2SPacketMixin {
   @Mutable
   @Accessor("yaw")
   void setYaw(float var1);

   @Mutable
   @Accessor("pitch")
   void setPitch(float var1);
}
