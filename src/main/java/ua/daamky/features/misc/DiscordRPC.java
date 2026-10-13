package ua.daamky.features.misc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.RichPresence;
import ua.daamky.gui.Daamky_2;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import net.minecraft.util.Formatting;

@NewFunction(
   I0 = "DiscordRPC",
   I00 = "Отображает ваш статус в Discord",
   I000 = Category.MISC
)
public class DiscordRPC extends Module {
   private static final long f1 = 1488235920854356141L;
   private static final long f2 = 4000L;
   private static final Path f3 = Paths.get(
      System.getProperty("user.home"),
      ".daamky",
      "account.json"
   );
   private volatile IPCClient f4;
   private volatile boolean enabled;
   private volatile boolean f5;
   private Thread f6;
   private OffsetDateTime f7;
   private String f8 = "";
   private String f9 = "";
   private boolean f10;
   private boolean f11;

   @Override
   public void onEnable() {
      this.f7 = OffsetDateTime.now();
      this.enabled = false;
      this.f10 = false;
      this.f11 = false;
      this.f8 = "";
      this.f9 = "";
      this.f5 = true;
      this.f6 = new Thread(this::m115, "Daamky-DiscordRPC");
      this.f6.setDaemon(true);
      this.f6.start();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.f5 = false;
      this.m134();
      super.onDisable();
   }

   private void m115() {
      for (; this.f5; m843(4000L)) {
         try {
            if (this.f4 == null) {
               this.m116();
            } else if (this.enabled && (!this.m841().equals(this.f8) || !this.m842().equals(this.f9))) {
               this.m840(this.f4);
            }
         } catch (Throwable var2) {
         }
      }
   }

   private void m116() {
      try {
         IPCClient var1 = new IPCClient(1488235920854356141L);
         this.f4 = var1;
         var1.setListener(new IPCListener() {
            @Override
            public void onReady(IPCClient var1) {
               DiscordRPC.this.enabled = true;
               DiscordRPC.this.m840(var1);
               if (!DiscordRPC.this.f10) {
                  DiscordRPC.this.f10 = true;
                  DiscordRPC.this.m21("подключён к Discord, статус отправлен");
               }

               System.out.println("[Daamky] RPC: READY, presence отправлен");
            }

            @Override
            public void onDisconnect(IPCClient var1, Throwable var2) {
               DiscordRPC.this.enabled = false;
               DiscordRPC.this.f4 = null;
               System.out.println("[Daamky] RPC: disconnect, будет переподключение");
            }
         });
         var1.connect();
      } catch (Throwable var2) {
         this.enabled = false;
         this.f4 = null;
         System.out.println("[Daamky] RPC: подключение не удалось (" + var2.getClass().getSimpleName() + "), повтор через 4с");
         if (!this.f11) {
            this.f11 = true;
            this.m676();
         }
      }
   }

   private void m676() {
      boolean var1 = false;

      for (int var2 = 0; var2 < 10; var2++) {
         String var3 = "\\\\?\\pipe\\discord-ipc-" + var2;

         try (RandomAccessFile var4 = new RandomAccessFile(var3, "rw")) {
            var1 = true;
            System.out.println("[Daamky] RPC диаг: " + var3 + " -> ОТКРЫВАЕТСЯ (пайп есть и доступен)");
         } catch (Throwable var9) {
            String var5 = var9.getMessage() == null ? "" : var9.getMessage();
            if (!var5.toLowerCase().contains("cannot find")
               && !var5.toLowerCase().contains("не удается найти")
               && !var5.toLowerCase().contains("не удаётся найти")) {
               System.out.println("[Daamky] RPC диаг: " + var3 + " -> " + var9.getClass().getSimpleName() + ": " + var5);
            }
         }
      }

      if (!var1) {
         System.out
            .println(
               "[Daamky] RPC диаг: ни один пайп discord-ipc не открылся — либо desktop-Discord не запущен, либо игра запущена с иными правами (напр. от администратора), чем Discord"
            );
      }
   }

   private void m134() {
      this.enabled = false;
      IPCClient var1 = this.f4;
      this.f4 = null;
      if (var1 != null) {
         try {
            var1.close();
         } catch (Throwable var3) {
         }
      }
   }

   private void m840(IPCClient var1) {
      if (var1 != null) {
         String var2 = this.m841();
         String var3 = this.m842();
         this.f8 = var2;
         this.f9 = var3;

         try {
            var1.sendRichPresence(
               new RichPresence.Builder().setDetails(var2).setState(var3).setStartTimestamp(this.f7).build(),
               new Callback(
                  var0 -> System.out.println("[Daamky] RPC: Discord принял активность"),
                  var0 -> System.out.println("[Daamky] RPC: Discord отклонил: " + var0)
               )
            );
         } catch (Throwable var5) {
            System.out.println("[Daamky] RPC: ошибка отправки " + var5);
         }
      }
   }

   private String m841() {
      String var1 = this.m58("id");
      return "UID » " + (var1 != null && !var1.isEmpty() ? var1 : "—");
   }

   private String m842() {
      String var1 = this.m58("username");
      return "Nick » " + (var1 != null && !var1.isEmpty() ? var1 : "—");
   }

   private String m58(String var1) {
      try {
         if (!Files.exists(f3)) {
            return null;
         } else {
            String var2 = Files.readString(f3);
            String var3 = "\"" + var1 + "\"";
            int var4 = var2.indexOf(var3);
            if (var4 < 0) {
               return null;
            } else {
               var4 = var2.indexOf(58, var4 + var3.length());
               if (var4 < 0) {
                  return null;
               } else {
                  int var5 = var2.indexOf(34, var4);
                  int var6 = var2.indexOf(44, var4);
                  int var7 = var2.indexOf(125, var4);
                  int var8 = var6 < 0 ? var7 : (var7 < 0 ? var6 : Math.min(var6, var7));
                  if (var5 < 0 || var8 >= 0 && var5 >= var8) {
                     return var8 < 0 ? null : var2.substring(var4 + 1, var8).trim();
                  } else {
                     int var9 = var2.indexOf(34, var5 + 1);
                     return var9 < 0 ? null : var2.substring(var5 + 1, var9);
                  }
               }
            }
         }
      } catch (Throwable var10) {
         return null;
      }
   }

   private void m21(String var1) {
      this.mc.execute(() -> Daamky_2.m467("DiscordRPC - " + var1, Formatting.GRAY));
   }

   private static void m843(long var0) {
      try {
         Thread.sleep(var0);
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
      }
   }
}
