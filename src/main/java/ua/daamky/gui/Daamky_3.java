package ua.daamky.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.daamky.utils.InventoryPreset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;

public final class Daamky_3 {
   private static final Gson f1 = new GsonBuilder().setPrettyPrinting().create();

   private Daamky_3() {
   }

   private static Path m899() {
      return FabricLoader.getInstance()
         .getGameDir()
         .resolve("Daamky")
         .resolve("invbuilder");
   }

   public static List<String> m76() {
      ArrayList var0 = new ArrayList();
      Path var1 = m899();
      if (Files.exists(var1)) {
         try (Stream<Path> var2 = Files.list(var1)) {
            var2.filter(var0x -> var0x.toString().endsWith(".json")).forEach(var1x -> {
               String var2x = var1x.getFileName().toString();
               var0.add(var2x.substring(0, var2x.length() - 5));
            });
         } catch (Exception var7) {
         }
      }

      return var0;
   }

   public static InventoryPreset m900(String var0) {
      try {
         Path var1 = m899().resolve(var0 + ".json");
         if (!Files.exists(var1)) {
            return null;
         } else {
            InventoryPreset var2 = (InventoryPreset)f1.fromJson(Files.readString(var1), InventoryPreset.class);
            if (var2 != null) {
               var2.m314();
               var2.f1 = var0;
            }

            return var2;
         }
      } catch (Exception var3) {
         return null;
      }
   }

   public static boolean m901(InventoryPreset var0) {
      if (var0 != null && var0.f1 != null && !var0.f1.isBlank()) {
         try {
            Files.createDirectories(m899());
            Files.writeString(m899().resolve(var0.f1 + ".json"), f1.toJson(var0));
            return true;
         } catch (Exception var2) {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void m21(String var0) {
      try {
         Files.deleteIfExists(m899().resolve(var0 + ".json"));
      } catch (Exception var2) {
      }
   }

   public static String m40() {
      return m899().toAbsolutePath().toString();
   }
}
