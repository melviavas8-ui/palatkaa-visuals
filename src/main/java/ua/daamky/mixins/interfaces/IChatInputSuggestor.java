package ua.daamky.mixins.interfaces;

import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ChatInputSuggestor.class})
public interface IChatInputSuggestor {
   @Accessor("textField")
   TextFieldWidget daamky$getTextField();

   @Accessor("pendingSuggestions")
   void daamky$setPendingSuggestions(CompletableFuture<Suggestions> var1);
}
