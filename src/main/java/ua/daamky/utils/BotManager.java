package ua.daamky.utils;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.mixins.interfaces.IMinecraftClient;
import ua.daamky.system.events.EventBus;
import ua.daamky.system.events.EventHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ServerType;
import net.minecraft.client.session.Session;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

public final class BotManager {
   public static final List<BotSession> f1 = new ArrayList<>();
   private static final Map<BotSession, Integer> f2 = new WeakHashMap<>();

   public static List<BotSession> m76() {
      return new ArrayList<>(f1);
   }

   public static void m77(String var0, String var1) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      m29();
      ((IMinecraftClient)var2).setSession(m36(var2.getSession(), var0));
      var2.execute(
         () -> ConnectScreen.connect(
               new MultiplayerScreen(new TitleScreen()), var2, ServerAddress.parse(var1), new ServerInfo(var1, var1, ServerType.OTHER), false, null
            )
      );
      f1.removeIf(var1x -> var1x.m37().equalsIgnoreCase(var0));
   }

   public static void m61(boolean var0) {
      for (BotSession var2 : f1) {
         if (var2.m72() != null && var2.m74() != null) {
            if (var0) {
               var2.m72().sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, var2.m74().getYaw(), var2.m74().getPitch()));
            } else {
               var2.m72().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
            }
         }
      }
   }

   public static void m16(String var0) {
      for (BotSession var2 : f1) {
         if (var2.m72() != null) {
            if (var0.startsWith("/")) {
               var2.m72().sendChatCommand(var0.substring(1));
            } else {
               var2.m72().sendChatMessage(var0);
            }
         }
      }
   }

   public static boolean m17(String var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      return f1.stream()
         .filter(var1x -> var1x.m37().equalsIgnoreCase(var0))
         .findFirst()
         .map(
            var1x -> {
               m29();
               f1.remove(var1x);
               Channel var2 = m82(var1x.m71());
               if (var2 != null && var2.pipeline().get("bot_filter") != null) {
                  var2.pipeline().remove("bot_filter");
               }

               var1.world = var1x.m73();
               var1.player = var1x.m74();
               var1.setCameraEntity(var1x.m74());
               var1.interactionManager = var1x.m75();

               try {
                  for (Field var6 : MinecraftClient.class.getDeclaredFields()) {
                     if ((ClientPlayNetworkHandler.class.isAssignableFrom(var6.getType()) || var6.getType().isAssignableFrom(ClientPlayNetworkHandler.class))
                        && var6.getType() != Object.class) {
                        var6.setAccessible(true);
                        var6.set(var1, var1x.m72());
                        break;
                     }
                  }
               } catch (Exception var7) {
               }

               if (var1.worldRenderer != null) {
                  var1.worldRenderer.setWorld(var1x.m73());
                  var1.worldRenderer.reload();
               }

               ((IMinecraftClient)var1).setSession(m36(var1.getSession(), var1x.m37()));
               var1.setScreen(null);
               return true;
            }
         )
         .orElse(false);
   }

   private static void m29() {
      MinecraftClient var0 = MinecraftClient.getInstance();
      if (var0.getNetworkHandler() != null && var0.world != null) {
         ClientPlayNetworkHandler var1 = var0.getNetworkHandler();
         m78(var1, var0.getSession().getUsername(), var0.player);
         f1.add(
            new BotSession(
               var0.getSession().getUsername(),
               var0.getCurrentServerEntry() != null ? var0.getCurrentServerEntry().address : "",
               var1.getConnection(),
               var1,
               var0.world,
               var0.player,
               var0.interactionManager
            )
         );
         var0.world = null;
         var0.player = null;

         try {
            for (Field var5 : MinecraftClient.class.getDeclaredFields()) {
               if ((ClientPlayNetworkHandler.class.isAssignableFrom(var5.getType()) || var5.getType().isAssignableFrom(ClientPlayNetworkHandler.class))
                  && var5.getType() != Object.class) {
                  var5.setAccessible(true);
                  var5.set(var0, null);
                  break;
               }
            }
         } catch (Exception var6) {
         }
      }
   }

   private static void m78(final ClientPlayNetworkHandler var0, final String var1, final ClientPlayerEntity var2) {
      Channel var3 = m82(var0.getConnection());
      if (var3 != null) {
         var3.pipeline()
            .addBefore(
               "packet_handler",
               "bot_filter",
               new ChannelDuplexHandler() {
                  public void channelRead(ChannelHandlerContext var1x, Object var2x) throws Exception {
                     if (var2x instanceof PlayerPositionLookS2CPacket var6) {
                        MinecraftClient.getInstance()
                           .execute(
                              () -> {
                                 var0.getConnection().send(new TeleportConfirmC2SPacket(var6.teleportId()));
                                 if (var2 != null) {
                                    double var3x = var6.change().position().x;
                                    double var5x = var6.change().position().y;
                                    double var7 = var6.change().position().z;
                                    float var9 = var6.change().yaw();
                                    float var10 = var6.change().pitch();
                                    if (var6.relatives().contains(PositionFlag.X)) {
                                       var3x += var2.getX();
                                    }

                                    if (var6.relatives().contains(PositionFlag.Y)) {
                                       var5x += var2.getY();
                                    }

                                    if (var6.relatives().contains(PositionFlag.Z)) {
                                       var7 += var2.getZ();
                                    }

                                    if (var6.relatives().contains(PositionFlag.Y_ROT)) {
                                       var9 += var2.getYaw();
                                    }

                                    if (var6.relatives().contains(PositionFlag.X_ROT)) {
                                       var10 += var2.getPitch();
                                    }

                                    var2.setPosition(var3x, var5x, var7);
                                    var2.setYaw(var9);
                                    var2.setPitch(var10);
                                    var0.getConnection()
                                       .send(
                                          new Full(
                                             var2.getX(), var2.getY(), var2.getZ(), var2.getYaw(), var2.getPitch(), var2.isOnGround(), var2.horizontalCollision
                                          )
                                       );

                                    for (BotSession var12 : BotManager.f1) {
                                       if (var12.m74() == var2) {
                                          BotManager.f2.put(var12, 0);
                                       }
                                    }
                                 }
                              }
                           );
                        ReferenceCountUtil.release(var2x);
                     } else if (var2x instanceof HealthUpdateS2CPacket var5) {
                        MinecraftClient.getInstance().execute(() -> {
                           if (var2 != null) {
                              var2.setHealth(var5.getHealth());
                           }
                        });
                        ReferenceCountUtil.release(var2x);
                     } else if (var2x instanceof DisconnectS2CPacket) {
                        BotManager.f1.removeIf(var1xxx -> var1xxx.m37().equalsIgnoreCase(var1));
                        var1x.close();
                        ReferenceCountUtil.release(var2x);
                     } else if (var2x instanceof KeepAliveS2CPacket var4) {
                        var0.getConnection().send(new KeepAliveC2SPacket(var4.getId()));
                        ReferenceCountUtil.release(var2x);
                     } else if (var2x instanceof CommonPingS2CPacket var3) {
                        var0.getConnection().send(new CommonPongC2SPacket(var3.getParameter()));
                        ReferenceCountUtil.release(var2x);
                     } else {
                        ReferenceCountUtil.release(var2x);
                     }
                  }
               }
            );
      }
   }

   public static boolean m79(String var0, String var1) {
      return f1.stream().filter(var1x -> var1x.m37().equalsIgnoreCase(var0)).findFirst().map(var1x -> {
         if (var1.startsWith("/")) {
            var1x.m72().sendChatCommand(var1.substring(1));
         } else {
            var1x.m72().sendChatMessage(var1);
         }

         return true;
      }).orElse(false);
   }

   public static boolean m80(String var0) {
      return f1.removeIf(var1 -> {
         if (var1.m37().equalsIgnoreCase(var0)) {
            var1.m71().disconnect(Text.literal("Removed"));
            return true;
         } else {
            return false;
         }
      });
   }

   public static boolean m81() {
      return !f1.isEmpty() && m17(f1.get(f1.size() - 1).m37());
   }

   private static Channel m82(ClientConnection var0) {
      try {
         for (Field var4 : ClientConnection.class.getDeclaredFields()) {
            if (Channel.class.isAssignableFrom(var4.getType())) {
               var4.setAccessible(true);
               return (Channel)var4.get(var0);
            }
         }
      } catch (Exception var5) {
      }

      return null;
   }

   private static Session m36(Session var0, String var1) {
      try {
         Constructor var2 = Session.class.getDeclaredConstructor(String.class, UUID.class, String.class, Optional.class, Optional.class);
         var2.setAccessible(true);
         return (Session)var2.newInstance(var1, UUID.randomUUID(), var0.getAccessToken(), Optional.empty(), Optional.empty());
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   static {
      EventBus.register(new Object() {
         @EventHandler
         public void m67(PostMotionEvent var1) {
            for (BotSession var3 : BotManager.f1) {
               if (var3.m74() != null && var3.m72() != null && var3.m71() != null && var3.m71().isOpen()) {
                  int var4 = BotManager.f2.getOrDefault(var3, 0) + 1;
                  if (var4 >= 20) {
                     var3.m72().getConnection().send(new OnGroundOnly(var3.m74().isOnGround(), var3.m74().horizontalCollision));
                     var4 = 0;
                  }

                  BotManager.f2.put(var3, var4);
               }
            }
         }
      });
   }
}
