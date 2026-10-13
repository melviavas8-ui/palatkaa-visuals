package ua.daamky.mixins.interfaces;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ClientPlayerInteractionManager.class})
public interface IClientPlayerInteractionManager {
   @Accessor("blockBreakingCooldown")
   void setBlockBreakingCooldown(int var1);
}
