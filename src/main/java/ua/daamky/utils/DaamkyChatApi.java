package ua.daamky.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ua.daamky.utils.client.UserProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.net.Proxy.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;

public final class DaamkyChatApi {
   private static final String f1 = "https://daamkydlc.pages.dev";
   private static final MinecraftClient f2 = MinecraftClient.getInstance();
   private static final ExecutorService f3 = Executors.newSingleThreadExecutor(var0 -> {
      Thread var1 = new Thread(var0, "daamky-chat");
      var1.setDaemon(true);
      return var1;
   });

   private DaamkyChatApi() {
   }

   public static boolean m91() {
      String var0 = UserProfile.m18();
      return var0 != null && !var0.isEmpty();
   }

   public static void m906(String var0, String var1, Consumer<List<ChatMessage>> var2, Consumer<String> var3) {
      m5(
         () -> {
            JsonObject var4 = m914();
            var4.addProperty("channel", var0);
            if (var1 != null) {
               var4.addProperty("with", var1);
            }

            JsonObject var5 = m396("/api/chat/poll", var4);
            if (!m917(var5)) {
               m916(var3, var5);
            } else {
               ArrayList var6 = new ArrayList();
               JsonArray var7 = var5.getAsJsonArray("messages");

               for (int var8 = 0; var8 < var7.size(); var8++) {
                  JsonObject var9 = var7.get(var8).getAsJsonObject();
                  var6.add(
                     new ChatMessage(
                        var9.get("id").getAsLong(),
                        m918(var9, "from"),
                        m918(var9, "uid"),
                        m918(var9, "role"),
                        m918(var9, "text"),
                        m918(var9, "ts"),
                        var9.has("mine") && var9.get("mine").getAsBoolean()
                     )
                  );
               }

               m915(() -> var2.accept(var6));
            }
         }
      );
   }

   public static void m907(String var0, String var1, Runnable var2, Consumer<String> var3) {
      m5(() -> {
         JsonObject var4 = m914();
         if (var0 != null && !var0.isEmpty()) {
            var4.addProperty("to", var0);
         }

         var4.addProperty("text", var1);
         JsonObject var5 = m396("/api/chat/send", var4);
         if (!m917(var5)) {
            m916(var3, var5);
         } else {
            m915(var2);
         }
      });
   }

   public static void m908(String var0, Consumer<ChatPresence> var1, Consumer<String> var2) {
      m5(
         () -> {
            JsonObject var3 = m914();
            var3.addProperty("username", var0);
            JsonObject var4 = m396("/api/chat/profile", var3);
            if (!m917(var4)) {
               m916(var2, var4);
            } else {
               ChatPresence var5 = new ChatPresence(
                  m918(var4, "username"),
                  m918(var4, "uid"),
                  m918(var4, "role"),
                  var4.has("online") && var4.get("online").getAsBoolean()
               );
               m915(() -> var1.accept(var5));
            }
         }
      );
   }

   public static void m909(Consumer<List<ChatChannel>> var0, Consumer<String> var1) {
      m5(
         () -> {
            JsonObject var2 = m396("/api/chat/inbox", m914());
            if (!m917(var2)) {
               m916(var1, var2);
            } else {
               List<ChatChannel> var3 = new ArrayList<>();
               JsonArray var4 = var2.getAsJsonArray("chats");

               for (int var5 = 0; var5 < var4.size(); var5++) {
                  JsonObject var6 = var4.get(var5).getAsJsonObject();
                  var3.add(
                     new ChatChannel(
                        m918(var6, "user"),
                        m918(var6, "last"),
                        m918(var6, "ts"),
                        var6.has("mine") && var6.get("mine").getAsBoolean()
                     )
                  );
               }

               m915(() -> var0.accept(var3));
            }
         }
      );
   }

   public static void m910(Consumer<List<ChatUser>> var0, Consumer<Boolean> var1, Consumer<String> var2) {
      m5(
         () -> {
            JsonObject var3 = m396("/api/chat/online", m914());
            if (!m917(var3)) {
               m916(var2, var3);
            } else {
               ArrayList var4 = new ArrayList();
               JsonArray var5 = var3.getAsJsonArray("users");

               for (int var6 = 0; var6 < var5.size(); var6++) {
                  JsonObject var7 = var5.get(var6).getAsJsonObject();
                  var4.add(
                     new ChatUser(
                        m918(var7, "username"),
                        m918(var7, "uid"),
                        m918(var7, "role"),
                        var7.has("self") && var7.get("self").getAsBoolean()
                     )
                  );
               }

               boolean var8 = var3.has("you_hidden")
                  && var3.get("you_hidden").getAsInt() == 1;
               m915(() -> {
                  var0.accept(var4);
                  if (var1 != null) {
                     var1.accept(var8);
                  }
               });
            }
         }
      );
   }

   public static void m911(boolean var0, Runnable var1, Consumer<String> var2) {
      m5(() -> {
         JsonObject var3 = m914();
         var3.addProperty("hidden", var0 ? 1 : 0);
         JsonObject var4 = m396("/api/chat/visibility", var3);
         if (!m917(var4)) {
            m916(var2, var4);
         } else {
            m915(var1);
         }
      });
   }

   public static void m912(String var0, Consumer<byte[]> var1, Runnable var2) {
      m5(() -> {
         byte[] var3;
         try {
            var3 = m919("/api/avatarimg?u=" + URLEncoder.encode(var0, StandardCharsets.UTF_8));
         } catch (Exception var5) {
            var3 = null;
         }

         byte[] var4 = var3;
         if (var4 != null && var4.length > 0) {
            m915(() -> var1.accept(var4));
         } else {
            m915(var2);
         }
      });
   }

   public static void m913(String var0, Long var1, String var2, Integer var3, Runnable var4, Consumer<String> var5) {
      m5(() -> {
         JsonObject var6 = m914();
         var6.addProperty("action", var0);
         if (var1 != null) {
            var6.addProperty("id", var1);
         }

         if (var2 != null) {
            var6.addProperty("username", var2);
         }

         if (var3 != null) {
            var6.addProperty("minutes", var3);
         }

         JsonObject var7 = m396("/api/chat/moderate", var6);
         if (!m917(var7)) {
            m916(var5, var7);
         } else {
            m915(var4);
         }
      });
   }

   private static JsonObject m914() {
      JsonObject var0 = new JsonObject();
      var0.addProperty("token", UserProfile.m18());
      return var0;
   }

   private static void m5(Runnable var0) {
      f3.submit(var0);
   }

   private static void m915(Runnable var0) {
      if (var0 != null) {
         f2.execute(var0);
      }
   }

   private static void m916(Consumer<String> var0, JsonObject var1) {
      String var2 = var1 == null ? "network" : m918(var1, "reason");
      m915(() -> {
         if (var0 != null) {
            var0.accept(var2.isEmpty() ? "error" : var2);
         }
      });
   }

   private static boolean m917(JsonObject var0) {
      return var0 != null
         && var0.has("status")
         && "ok".equals(var0.get("status").getAsString());
   }

   private static String m918(JsonObject var0, String var1) {
      return var0 != null && var0.has(var1) && !var0.get(var1).isJsonNull() ? var0.get(var1).getAsString() : "";
   }

   private static JsonObject m396(String var0, JsonObject var1) {
      byte[] var2 = var1.toString().getBytes(StandardCharsets.UTF_8);
      List<Proxy> var3 = new ArrayList<>();
      Proxy var4 = m920();
      if (var4 != null) {
         var3.add(var4);
      }

      var3.add(Proxy.NO_PROXY);
      Object var5 = null;

      for (Proxy var7 : var3) {
         HttpURLConnection var8 = null;

         JsonObject var13;
         try {
            URL var9 = new URL("https://daamkydlc.pages.dev" + var0);
            var8 = (HttpURLConnection)var9.openConnection(var7);
            var8.setRequestMethod("POST");
            var8.setConnectTimeout(6000);
            var8.setReadTimeout(8000);
            var8.setInstanceFollowRedirects(true);
            var8.setDoOutput(true);
            var8.setRequestProperty(
               "Content-Type", "application/json; charset=utf-8"
            );
            var8.setRequestProperty("Accept", "application/json");
            var8.setRequestProperty("User-Agent", "DaamkyClient/1.0");

            try (OutputStream var10 = var8.getOutputStream()) {
               var10.write(var2);
            }

            int var23 = var8.getResponseCode();
            InputStream var11 = var23 >= 200 && var23 < 400 ? var8.getInputStream() : var8.getErrorStream();
            if (var11 == null) {
               var5 = new IOException("HTTP " + var23);
               continue;
            }

            String var12 = new String(var11.readAllBytes(), StandardCharsets.UTF_8);
            var13 = JsonParser.parseString(var12).getAsJsonObject();
         } catch (Throwable var21) {
            var5 = var21;
            continue;
         } finally {
            if (var8 != null) {
               var8.disconnect();
            }
         }

         return var13;
      }

      System.out.println("[Daamky] chat network error " + var0 + " (прокси=" + (var4 != null) + "): " + var5);
      return null;
   }

   private static byte[] m919(String var0) {
      List<Proxy> var1 = new ArrayList<>();
      Proxy var2 = m920();
      if (var2 != null) {
         var1.add(var2);
      }

      var1.add(Proxy.NO_PROXY);

      for (Proxy var4 : var1) {
         HttpURLConnection var5 = null;

         byte[] var9;
         try {
            URL var6 = new URL("https://daamkydlc.pages.dev" + var0);
            var5 = (HttpURLConnection)var6.openConnection(var4);
            var5.setRequestMethod("GET");
            var5.setConnectTimeout(6000);
            var5.setReadTimeout(8000);
            var5.setInstanceFollowRedirects(true);
            var5.setRequestProperty("User-Agent", "DaamkyClient/1.0");
            int var7 = var5.getResponseCode();
            if (var7 != 200) {
               return null;
            }

            try (InputStream var8 = var5.getInputStream()) {
               var9 = var8.readAllBytes();
            }
         } catch (Throwable var18) {
            continue;
         } finally {
            if (var5 != null) {
               var5.disconnect();
            }
         }

         return var9;
      }

      return null;
   }

   private static Proxy m920() {
      for (String var3 : new String[]{
         "HTTPS_PROXY",
         "https_proxy",
         "HTTP_PROXY",
         "http_proxy",
         "ALL_PROXY",
         "all_proxy"
      }) {
         String var4 = System.getenv(var3);
         if (var4 != null && !var4.isBlank()) {
            try {
               String var5 = var4.trim().replaceFirst("^[a-zA-Z0-9]+://", "");
               int var6 = var5.indexOf(64);
               if (var6 >= 0) {
                  var5 = var5.substring(var6 + 1);
               }

               int var7 = var5.indexOf(47);
               if (var7 >= 0) {
                  var5 = var5.substring(0, var7);
               }

               int var8 = var5.lastIndexOf(58);
               if (var8 >= 0) {
                  String var9 = var5.substring(0, var8);
                  int var10 = Integer.parseInt(var5.substring(var8 + 1));
                  return new Proxy(Type.HTTP, new InetSocketAddress(var9, var10));
               }
            } catch (Exception var11) {
            }
         }
      }

      return null;
   }
}
