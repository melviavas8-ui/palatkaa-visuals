package com.jagrosh.discordipc;

import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.entities.pipe.Pipe;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import ua.daamky.utils.JsonObjectWrapper;
import ua.daamky.utils.JsonParseException;
import java.io.Closeable;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;

public final class IPCClient implements Closeable {
   public static User connectedUser;
   private final long clientId;
   private final HashMap<String, Callback> callbacks = new HashMap<>();
   private volatile Pipe pipe;
   private IPCListener listener = null;
   private Thread readThread = null;

   public IPCClient(long var1) {
      this.clientId = var1;
   }

   public void setListener(IPCListener var1) {
      this.listener = var1;
      if (this.pipe != null) {
         this.pipe.setListener(var1);
      }
   }

   public void connect(DiscordBuild... var1) throws NoDiscordClientException {
      this.checkConnected(false);
      this.callbacks.clear();
      this.pipe = null;
      this.pipe = Pipe.openPipe(this, this.clientId, this.callbacks, var1);
      if (this.listener != null) {
         this.listener.onReady(this);
      }

      this.startReading();
   }

   public void sendRichPresence(RichPresence var1) {
      this.sendRichPresence(var1, null);
   }

   public void sendRichPresence(RichPresence var1, Callback var2) {
      this.checkConnected(true);
      this.pipe
         .send(
            Packet.OpCode.FRAME,
            new JsonObjectWrapper()
               .m141("cmd", "SET_ACTIVITY")
               .m144(
                  "args",
                  new JsonObjectWrapper()
                     .m142("pid", getPID())
                     .m144("activity", var1 == null ? null : var1.toJson())
               ),
            var2
         );
   }

   public void subscribe(IPCClient.Event var1) {
      this.subscribe(var1, null);
   }

   public void subscribe(IPCClient.Event var1, Callback var2) {
      this.checkConnected(true);
      if (!var1.isSubscribable()) {
         throw new IllegalStateException("Cannot subscribe to " + var1 + " event!");
      } else {
         this.pipe
            .send(
               Packet.OpCode.FRAME,
               new JsonObjectWrapper()
                  .m141("cmd", "SUBSCRIBE")
                  .m141("evt", var1.name()),
               var2
            );
      }
   }

   public PipeStatus getStatus() {
      return this.pipe == null ? PipeStatus.UNINITIALIZED : this.pipe.getStatus();
   }

   @Override
   public void close() {
      this.checkConnected(true);

      try {
         this.pipe.close();
      } catch (IOException var2) {
      }
   }

   public DiscordBuild getDiscordBuild() {
      return this.pipe == null ? null : this.pipe.getDiscordBuild();
   }

   private void checkConnected(boolean var1) {
      if (var1 && this.getStatus() != PipeStatus.CONNECTED) {
         throw new IllegalStateException(String.format("IPCClient (ID: %d) is not connected!", this.clientId));
      } else if (!var1 && this.getStatus() == PipeStatus.CONNECTED) {
         throw new IllegalStateException(String.format("IPCClient (ID: %d) is already connected!", this.clientId));
      }
   }

   private void startReading() {
      this.readThread = new Thread(
         () -> {
            try {
               while (true) {
                  Packet var1;
                  if ((var1 = this.pipe.read()).getOp() != Packet.OpCode.CLOSE) {
                     JsonObjectWrapper var2 = var1.getJson();
                     IPCClient.Event var3 = IPCClient.Event.of(var2.m150("evt", null));
                     String var4 = var2.m150("nonce", null);
                     switch (var3) {
                        case NULL:
                           if (var4 != null && this.callbacks.containsKey(var4)) {
                              this.callbacks.remove(var4).succeed(var1);
                           }
                        case READY:
                        case ACTIVITY_JOIN:
                        case ACTIVITY_SPECTATE:
                        case ACTIVITY_JOIN_REQUEST:
                        case UNKNOWN:
                        default:
                           break;
                        case ERROR:
                           if (var4 != null && this.callbacks.containsKey(var4)) {
                              this.callbacks
                                 .remove(var4)
                                 .fail(var2.m148("data").m150("message", null));
                           }
                     }

                     if (this.listener != null
                        && var2.m20("cmd")
                        && var2.m59("cmd").equals("DISPATCH")) {
                        try {
                           JsonObjectWrapper var5 = var2.m148("data");
                           switch (IPCClient.Event.of(var2.m59("evt"))) {
                              case ACTIVITY_JOIN:
                                 this.listener.onActivityJoin(this, var5.m59("secret"));
                                 break;
                              case ACTIVITY_SPECTATE:
                                 this.listener.onActivitySpectate(this, var5.m59("secret"));
                                 break;
                              case ACTIVITY_JOIN_REQUEST:
                                 JsonObjectWrapper var6 = var5.m148("user");
                                 User var7 = new User(
                                    var6.m59("username"),
                                    var6.m59("discriminator"),
                                    Long.parseLong(var6.m59("id")),
                                    var6.m150("avatar", null)
                                 );
                                 this.listener.onActivityJoinRequest(this, var5.m150("secret", null), var7);
                           }
                        } catch (Exception var8) {
                        }
                     }
                  } else {
                     this.pipe.setStatus(PipeStatus.DISCONNECTED);
                     if (this.listener != null) {
                        this.listener.onClose(this, var1.getJson());
                     }
                     break;
                  }
               }
            } catch (JsonParseException | IOException var9) {
               this.pipe.setStatus(PipeStatus.DISCONNECTED);
               if (this.listener != null) {
                  this.listener.onDisconnect(this, var9);
               }
            }
         }
      );
      this.readThread.start();
   }

   private static int getPID() {
      String var0 = ManagementFactory.getRuntimeMXBean().getName();
      return Integer.parseInt(var0.substring(0, var0.indexOf(64)));
   }

   public static enum Event {
      NULL(false),
      READY(false),
      ERROR(false),
      ACTIVITY_JOIN(true),
      ACTIVITY_SPECTATE(true),
      ACTIVITY_JOIN_REQUEST(true),
      UNKNOWN(false);

      private final boolean subscribable;

      private Event(boolean var3) {
         this.subscribable = var3;
      }

      public boolean isSubscribable() {
         return this.subscribable;
      }

      static IPCClient.Event of(String var0) {
         if (var0 == null) {
            return NULL;
         } else {
            for (IPCClient.Event var4 : values()) {
               if (var4 != UNKNOWN && var4.name().equalsIgnoreCase(var0)) {
                  return var4;
               }
            }

            return UNKNOWN;
         }
      }
   }
}
