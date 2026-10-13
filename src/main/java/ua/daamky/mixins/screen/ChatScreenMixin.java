package ua.daamky.mixins.screen;

import ua.daamky.commands.CommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChatScreen.class})
public class ChatScreenMixin {
   @Inject(
      method = {"sendMessage(Ljava/lang/String;Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$handleCommand(String var1, boolean var2, CallbackInfo var3) {
      if (CommandManager.m20(var1)) {
         MinecraftClient var4 = MinecraftClient.getInstance();
         if (var2) {
            var4.inGameHud.getChatHud().addToMessageHistory(var1.trim());
         }

         var3.cancel();
      }
   }
}
