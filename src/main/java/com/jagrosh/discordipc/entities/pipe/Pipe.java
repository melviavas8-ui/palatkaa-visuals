package com.jagrosh.discordipc.entities.pipe;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import ua.daamky.utils.JsonObjectWrapper;
import ua.daamky.utils.JsonParseException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.UUID;

public abstract class Pipe {
   private static final int VERSION = 1;
   PipeStatus status = PipeStatus.CONNECTING;
   IPCListener listener;
   private DiscordBuild build;
   final IPCClient ipcClient;
   private final HashMap<String, Callback> callbacks;
   private static final String[] unixPaths = new String[]{
      "XDG_RUNTIME_DIR",
      "TMPDIR",
      "TMP",
      "TEMP"
   };

   Pipe(IPCClient var1, HashMap<String, Callback> var2) {
      this.ipcClient = var1;
      this.callbacks = var2;
   }

   public static Pipe openPipe(IPCClient var0, long var1, HashMap<String, Callback> var3, DiscordBuild... var4) throws NoDiscordClientException {
      if (var4 == null || var4.length == 0) {
         var4 = new DiscordBuild[]{DiscordBuild.ANY};
      }

      Pipe var5 = null;
      Pipe[] var6 = new Pipe[DiscordBuild.values().length];

      for (int var7 = 0; var7 < 10; var7++) {
         try {
            String var8 = getPipeLocation(var7);
            var5 = createPipe(var0, var3, var8);
            var5.send(
               Packet.OpCode.HANDSHAKE,
               new JsonObjectWrapper().m142("v", 1).m141("client_id", Long.toString(var1)),
               null
            );
            Packet var9 = var5.read();
            var5.build = DiscordBuild.from(
               var9.getJson()
                  .m148("data")
                  .m148("config")
                  .m59("api_endpoint")
            );
            if (var5.build == var4[0] || DiscordBuild.ANY == var4[0]) {
               break;
            }

            var6[var5.build.ordinal()] = var5;
            var6[DiscordBuild.ANY.ordinal()] = var5;
            var5.build = null;
            var5 = null;
         } catch (JsonParseException | IOException var11) {
            var5 = null;
         }
      }

      if (var5 == null) {
         for (int var12 = 1; var12 < var4.length; var12++) {
            DiscordBuild var14 = var4[var12];
            if (var6[var14.ordinal()] != null) {
               var5 = var6[var14.ordinal()];
               var6[var14.ordinal()] = null;
               if (var14 == DiscordBuild.ANY) {
                  for (int var15 = 0; var15 < var6.length; var15++) {
                     if (var6[var15] == var5) {
                        var5.build = DiscordBuild.values()[var15];
                        var6[var15] = null;
                     }
                  }
                  break;
               }

               var5.build = var14;
               break;
            }
         }

         if (var5 == null) {
            throw new NoDiscordClientException();
         }
      }

      for (int var13 = 0; var13 < var6.length; var13++) {
         if (var13 != DiscordBuild.ANY.ordinal() && var6[var13] != null) {
            try {
               var6[var13].close();
            } catch (IOException var10) {
            }
         }
      }

      var5.status = PipeStatus.CONNECTED;
      return var5;
   }

   private static Pipe createPipe(IPCClient var0, HashMap<String, Callback> var1, String var2) {
      String var3 = System.getProperty("os.name").toLowerCase();

      try {
         new RandomAccessFile(var2, "rw");
      } catch (FileNotFoundException var5) {
         return new Pipe(var0, var1) {
            @Override
            public Packet read() throws IOException, JsonParseException {
               return new Packet(Packet.OpCode.CLOSE, new JsonObjectWrapper());
            }

            @Override
            public void write(byte[] var1) throws IOException {
            }

            @Override
            public void close() throws IOException {
            }
         };
      }

      if (var3.contains("win")) {
         return new WindowsPipe(var0, var1, var2);
      } else {
         throw new RuntimeException("Unsupported OS: " + var3);
      }
   }

   public void send(Packet.OpCode var1, JsonObjectWrapper var2, Callback var3) {
      try {
         String var4 = generateNonce();
         Packet var5 = new Packet(var1, var2.m141("nonce", var4));
         if (var3 != null && !var3.isEmpty()) {
            this.callbacks.put(var4, var3);
         }

         this.write(var5.toBytes());
         if (this.listener != null) {
            this.listener.onPacketSent(this.ipcClient, var5);
         }
      } catch (IOException var6) {
         this.status = PipeStatus.DISCONNECTED;
      }
   }

   public abstract Packet read() throws IOException, JsonParseException;

   public abstract void write(byte[] var1) throws IOException;

   private static String generateNonce() {
      return UUID.randomUUID().toString();
   }

   public PipeStatus getStatus() {
      return this.status;
   }

   public void setStatus(PipeStatus var1) {
      this.status = var1;
   }

   public void setListener(IPCListener var1) {
      this.listener = var1;
   }

   public abstract void close() throws IOException;

   public DiscordBuild getDiscordBuild() {
      return this.build;
   }

   private static String getPipeLocation(int var0) {
      if (System.getProperty("os.name").contains("Win")) {
         return "\\\\.\\pipe\\discord-ipc-" + var0;
      } else {
         String var1 = null;

         for (String var5 : unixPaths) {
            var1 = System.getenv(var5);
            if (var1 != null) {
               break;
            }
         }

         if (var1 == null) {
            var1 = "/tmp";
         }

         return var1 + "/discord-ipc-" + var0;
      }
   }
}
