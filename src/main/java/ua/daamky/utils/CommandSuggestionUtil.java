package ua.daamky.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import ua.daamky.gui.config.ConfigManager;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;

public final class CommandSuggestionUtil {
   private static final CommandDispatcher<Object> f1 = new CommandDispatcher();

   private CommandSuggestionUtil() {
   }

   public static CompletableFuture<Suggestions> m470(String var0, int var1) {
      CompletableFuture var2 = m471(var0, var1);
      return var2 != null ? var2 : f1.getCompletionSuggestions(f1.parse(var0, new Object()), var1);
   }

   private static CompletableFuture<Suggestions> m471(String var0, int var1) {
      int var2 = Math.max(0, Math.min(var1, var0.length()));
      String var3 = var0.substring(0, var2);
      String var4 = var3.toLowerCase(Locale.ROOT);
      CompletableFuture var5 = m472(var0, var2, var4, ".neuro play");
      if (var5 != null) {
         return var5;
      } else {
         CompletableFuture var6 = m472(var0, var2, var4, ".РЅРµР№СЂРѕ start");
         return var6 != null ? var6 : m472(var0, var2, var4, ".РЅРµР№СЂРѕ СЃС‚Р°СЂС‚");
      }
   }

   private static CompletableFuture<Suggestions> m472(String var0, int var1, String var2, String var3) {
      List<String> var4 = NeuroCommand.m76();
      if (var4.isEmpty()) {
         return null;
      } else if (!var2.equals(var3)) {
         String var8 = var3 + " ";
         if (var2.startsWith(var8)) {
            SuggestionsBuilder var9 = new SuggestionsBuilder(var0, var8.length());
            return CommandSource.suggestMatching(var4, var9);
         } else {
            return null;
         }
      } else {
         SuggestionsBuilder var5 = new SuggestionsBuilder(var0, var1);

         for (String var7 : var4) {
            var5.suggest(" " + var7);
         }

         return var5.buildFuture();
      }
   }

   static {
      f1.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal(
                        ".cfg"
                     )
                     .then(LiteralArgumentBuilder.literal("dir")))
                  .then(
                     LiteralArgumentBuilder.literal("save")
                        .then(RequiredArgumentBuilder.argument("name", StringArgumentType.word()))
                  ))
               .then(
                  LiteralArgumentBuilder.literal("load")
                     .then(
                        RequiredArgumentBuilder.argument("name", StringArgumentType.word())
                           .suggests((var0, var1) -> CommandSource.suggestMatching(ConfigManager.m375(), var1))
                     )
               ))
            .then(LiteralArgumentBuilder.literal("reset"))
      );
      f1.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)LiteralArgumentBuilder.literal(".friend")
                  .then(
                     LiteralArgumentBuilder.literal("add")
                        .then(RequiredArgumentBuilder.argument("nick", StringArgumentType.word()))
                  ))
               .then(
                  LiteralArgumentBuilder.literal("remove")
                     .then(RequiredArgumentBuilder.argument("nick", StringArgumentType.word()))
               ))
            .then(LiteralArgumentBuilder.literal("clear"))
      );
      f1.register(LiteralArgumentBuilder.literal(".parse"));
   }
}
