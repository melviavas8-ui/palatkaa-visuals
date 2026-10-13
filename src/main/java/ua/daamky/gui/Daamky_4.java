package ua.daamky.gui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

public class Daamky_4 {
   private static final Map<String, Identifier> f1 = new ConcurrentHashMap<>();
   private static final Set<String> f2 = ConcurrentHashMap.newKeySet();
   private static final ExecutorService f3 = Executors.newFixedThreadPool(2);

   public static Identifier m221(String var0) {
      if (f1.containsKey(var0)) {
         return f1.get(var0);
      } else {
         if (f2.add(var0)) {
            f3.submit(
               () -> {
                  try {
                     URL var1 = new URL("https://mc-heads.net/avatar/" + var0 + "/64");
                     HttpURLConnection var2 = (HttpURLConnection)var1.openConnection();
                     var2.setRequestProperty(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                     );
                     var2.setConnectTimeout(5000);
                     var2.setReadTimeout(5000);
                     if (var2.getResponseCode() == 200) {
                        try (InputStream var3 = var2.getInputStream()) {
                           NativeImage var4 = NativeImage.read(var3);
                           MinecraftClient.getInstance().execute(() -> {
                              NativeImageBackedTexture var2x = new NativeImageBackedTexture(() -> "avatar_" + var0.toLowerCase(), var4);
                              var2x.upload();
                              Identifier var3x = Identifier.of("daamky", "avatar_" + var0.toLowerCase());
                              MinecraftClient.getInstance().getTextureManager().registerTexture(var3x, var2x);
                              f1.put(var0, var3x);
                           });
                        }
                     } else {
                        f1.put(var0, null);
                     }
                  } catch (Exception var8) {
                     f1.put(var0, null);
                  }
               }
            );
         }

         return null;
      }
   }
}
