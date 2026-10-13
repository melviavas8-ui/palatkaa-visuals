package ua.daamky.mixins.interfaces;

import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ClickSlotC2SPacket.class})
public interface IClickSlotC2SPacket {
   @Mutable
   @Accessor("slot")
   void setSlot(short var1);
}
