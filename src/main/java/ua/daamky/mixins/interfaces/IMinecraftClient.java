package ua.daamky.mixins.interfaces;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({MinecraftClient.class})
public interface IMinecraftClient {
   @Accessor("itemUseCooldown")
   void setItemUseCooldown(int var1);

   @Invoker("doAttack")
   boolean invokeDoAttack();

   @Invoker("doItemUse")
   void invokeDoItemUse();

   @Mutable
   @Accessor("session")
   void setSession(Session var1);
}
