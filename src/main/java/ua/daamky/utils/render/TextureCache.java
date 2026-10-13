package ua.daamky.utils.render;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

public final class TextureCache {
   private static final Map<String, Identifier> f1 = new ConcurrentHashMap<>();
   private static final Map<String, Long> f2 = new ConcurrentHashMap<>();
   private static final Set<String> f3 = ConcurrentHashMap.newKeySet();
   private static final long f4 = 60000L;

   private TextureCache() {
   }

   public static Identifier m221(String var0) {
      return Identifier.of("daamky", "textures/avatar.png");
   }

   private static NativeImage m984(byte[] var0) throws Exception {
      if (m985(var0)) {
         return NativeImage.read(new ByteArrayInputStream(var0));
      } else {
         byte[] var1 = var0;

         try {
            BufferedImage var2 = ImageIO.read(new ByteArrayInputStream(var0));
            if (var2 != null) {
               ByteArrayOutputStream var3 = new ByteArrayOutputStream();
               if (ImageIO.write(var2, "png", var3)) {
                  var1 = var3.toByteArray();
               }
            }
         } catch (Throwable var4) {
         }

         return NativeImage.read(new ByteArrayInputStream(var1));
      }
   }

   private static boolean m985(byte[] var0) {
      return var0 != null && var0.length > 8 && (var0[0] & 255) == 137 && var0[1] == 80 && var0[2] == 78 && var0[3] == 71;
   }

   private static String m59(String var0) {
      return Integer.toHexString(var0.hashCode()) + "_" + var0.length();
   }
}
