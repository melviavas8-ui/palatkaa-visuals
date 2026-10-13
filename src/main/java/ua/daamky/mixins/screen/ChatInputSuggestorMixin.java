package ua.daamky.mixins.screen;

import ua.daamky.mixins.interfaces.IChatInputSuggestor;
import ua.daamky.utils.CommandSuggestionUtil;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChatInputSuggestor.class})
public abstract class ChatInputSuggestorMixin {
   @Shadow
   public abstract void show(boolean var1);

   @Shadow
   public abstract void clearWindow();

   @Inject(
      method = {"refresh()V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$refreshDotCommands(CallbackInfo var1) {
      IChatInputSuggestor var2 = (IChatInputSuggestor)this;
      TextFieldWidget var3 = var2.daamky$getTextField();
      String var4 = var3.getText();
      if (var4.startsWith(".")) {
         this.clearWindow();
         CompletableFuture var5 = CommandSuggestionUtil.m470(var4, var3.getCursor());
         var2.daamky$setPendingSuggestions(var5);
         if (var5.isDone()) {
            this.show(false);
         } else {
            var5.thenRun(() -> this.show(false));
         }

         var1.cancel();
      }
   }
}
