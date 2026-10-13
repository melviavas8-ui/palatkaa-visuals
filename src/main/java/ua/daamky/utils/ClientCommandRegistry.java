package ua.daamky.utils;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import ua.daamky.gui.Daamky_2;
import ua.daamky.gui.config.ConfigManager;
import java.util.Locale;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Formatting;

public final class ClientCommandRegistry {
   private ClientCommandRegistry() {
   }

   public static void m63() {
      ClientCommandRegistrationCallback.EVENT
         .register(
            (ClientCommandRegistrationCallback)(var0, var1) -> var0.register(
                  (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)ClientCommandManager.literal(
                                 ".cfg"
                              )
                              .then(ClientCommandManager.literal("dir").executes(var0x -> {
                                 m314();
                                 return 1;
                              })))
                           .then(((LiteralArgumentBuilder)ClientCommandManager.literal("save").executes(var0x -> {
                              m21(null);
                              return 1;
                           })).then(ClientCommandManager.argument("name", StringArgumentType.word()).executes(var0x -> {
                              m21(StringArgumentType.getString(var0x, "name"));
                              return 1;
                           }))))
                        .then(
                           ((LiteralArgumentBuilder)ClientCommandManager.literal("load").executes(var0x -> {
                                 m16(null);
                                 return 1;
                              }))
                              .then(
                                 ClientCommandManager.argument("name", StringArgumentType.word())
                                    .suggests((var0x, var1x) -> CommandSource.suggestMatching(ConfigManager.m375(), var1x))
                                    .executes(var0x -> {
                                       m16(StringArgumentType.getString(var0x, "name"));
                                       return 1;
                                    })
                              )
                        ))
                     .then(ClientCommandManager.literal("reset").executes(var0x -> {
                        ConfigManager.m286();
                        Daamky_2.m467("Settings reset.", Formatting.GREEN);
                        return 1;
                     }))
               )
         );
      ClientCommandRegistrationCallback.EVENT
         .register(
            (ClientCommandRegistrationCallback)(var0, var1) -> var0.register(
                  (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)ClientCommandManager.literal(
                              ".friend"
                           )
                           .then(
                              ClientCommandManager.literal("add")
                                 .then(
                                    ClientCommandManager.argument("nick", StringArgumentType.word())
                                       .executes(
                                          var0x -> m436(
                                                "add",
                                                StringArgumentType.getString(var0x, "nick")
                                             )
                                       )
                                 )
                           ))
                        .then(
                           ClientCommandManager.literal("remove")
                              .then(
                                 ClientCommandManager.argument("nick", StringArgumentType.word())
                                    .executes(
                                       var0x -> m436(
                                             "remove",
                                             StringArgumentType.getString(var0x, "nick")
                                          )
                                    )
                              )
                        ))
                     .then(ClientCommandManager.literal("clear").executes(var0x -> {
                        FriendCommand.m20(".friend clear");
                        return 1;
                     }))
               )
         );
      ClientCommandRegistrationCallback.EVENT
         .register(
            (ClientCommandRegistrationCallback)(var0, var1) -> var0.register(
                  (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)ClientCommandManager.literal(
                                          ".bot"
                                       )
                                       .then(ClientCommandManager.literal("list").executes(var0x -> {
                                          BotCommand.m20(".bot list");
                                          return 1;
                                       })))
                                    .then(ClientCommandManager.literal("return").executes(var0x -> {
                                       BotCommand.m20(".bot return");
                                       return 1;
                                    })))
                                 .then(
                                    ClientCommandManager.literal("connect")
                                       .then(
                                          ClientCommandManager.argument("name", StringArgumentType.word())
                                             .then(
                                                ClientCommandManager.argument("ip", StringArgumentType.string())
                                                   .executes(
                                                      var0x -> {
                                                         BotCommand.m20(
                                                            ".bot connect "
                                                               + StringArgumentType.getString(var0x, "name")
                                                               + " "
                                                               + StringArgumentType.getString(var0x, "ip")
                                                         );
                                                         return 1;
                                                      }
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ClientCommandManager.literal("remove")
                                    .then(ClientCommandManager.argument("name", StringArgumentType.word()).executes(var0x -> {
                                       BotCommand.m20(".bot remove " + StringArgumentType.getString(var0x, "name"));
                                       return 1;
                                    }))
                              ))
                           .then(
                              ClientCommandManager.literal("control")
                                 .then(ClientCommandManager.argument("name", StringArgumentType.word()).executes(var0x -> {
                                    BotCommand.m20(".bot control " + StringArgumentType.getString(var0x, "name"));
                                    return 1;
                                 }))
                           ))
                        .then(
                           ClientCommandManager.literal("say")
                              .then(
                                 ClientCommandManager.argument("name", StringArgumentType.word())
                                    .then(
                                       ClientCommandManager.argument("message", StringArgumentType.greedyString())
                                          .executes(
                                             var0x -> {
                                                BotCommand.m20(
                                                   ".bot say "
                                                      + StringArgumentType.getString(var0x, "name")
                                                      + " "
                                                      + StringArgumentType.getString(var0x, "message")
                                                );
                                                return 1;
                                             }
                                          )
                                    )
                              )
                        ))
                     .then(
                        ClientCommandManager.literal("sayall")
                           .then(
                              ClientCommandManager.argument("message", StringArgumentType.greedyString()).executes(var0x -> {
                                 BotCommand.m20(".bot sayall " + StringArgumentType.getString(var0x, "message"));
                                 return 1;
                              })
                           )
                     )
               )
         );
      ClientCommandRegistrationCallback.EVENT
         .register(
            (ClientCommandRegistrationCallback)(var0, var1) -> var0.register(
                  (LiteralArgumentBuilder)ClientCommandManager.literal(".parse").executes(var0x -> {
                     ParseCommand.m63();
                     return 1;
                  })
               )
         );
   }

   private static int m436(String var0, String var1) {
      FriendCommand.m20(".friend " + var0 + " " + var1);
      return 1;
   }

   private static void m314() {
      if (ConfigManager.m31()) {
         Daamky_2.m467("Config folder opened.", Formatting.GREEN);
      } else {
         Daamky_2.m467("Could not open config folder.", Formatting.RED);
      }
   }

   private static void m16(String var0) {
      if (var0 != null && !ConfigManager.m80(var0)) {
         Daamky_2.m467("Invalid config name. Use letters, numbers, _ or -.", Formatting.YELLOW);
      } else {
         if (ConfigManager.m17(var0)) {
            Daamky_2.m467(m223(var0) + " loaded.", Formatting.GREEN);
         } else {
            Daamky_2.m467(m223(var0) + " was not found or could not be loaded.", Formatting.RED);
         }
      }
   }

   private static void m21(String var0) {
      if (var0 != null && !ConfigManager.m80(var0)) {
         Daamky_2.m467("Invalid config name. Use letters, numbers, _ or -.", Formatting.YELLOW);
      } else {
         if (ConfigManager.m20(var0)) {
            Daamky_2.m467(m223(var0) + " saved.", Formatting.GREEN);
         } else {
            Daamky_2.m467("Could not save " + m223(var0).toLowerCase(Locale.ROOT) + ".", Formatting.RED);
         }
      }
   }

   private static String m223(String var0) {
      return var0 == null ? "Config" : "Config '" + var0 + "'";
   }
}
