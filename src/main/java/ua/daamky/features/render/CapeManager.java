package ua.daamky.features.render;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ua.daamky.utils.CosmeticModelItem;
import ua.daamky.utils.CosmeticAttachPoint;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

public final class CapeManager {
   private static final int f1 = 256;
   private static List<CapeManager$1> f2 = m483();
   private static boolean f8;
   private static final Map<Integer, JsonObject> f3 = new HashMap<>();
   private static final Map<Integer, CosmeticModelItem> f4 = new HashMap<>();
   private static final Map<Integer, Identifier> f5 = new HashMap<>();
   private static final LinkedHashMap<String, Integer> f6 = new LinkedHashMap<>();
   private static Path f7;

   private CapeManager() {
   }

   // Ленивая инициализация: путь к файлу выбора и загрузка сохранённой косметики (один раз).
   private static void init() {
      if (!f8) {
         f8 = true;

         try {
            f7 = FabricLoader.getInstance().getGameDir().resolve("Daamky").resolve("cosmetics.properties");
         } catch (Throwable var1) {
            f7 = null;
         }

         m299();
      }
   }

   public static void m63() {
      init();
   }

   public static int m189() {
      init();
      return f2.size();
   }

   public static String m90(int var0) {
      return m487(var0).m40();
   }

   public static String m288(int var0) {
      return m487(var0).m18();
   }

   public static String m476(int var0) {
      String var1 = m288(var0);

      return switch (var1) {
         case "cape" -> "Плащ";
         case "wings" -> "Крылья";
         case "bodywear" -> "На спину";
         case "bag" -> "Сумка";
         case "pet" -> "Питомец";
         case "hat" -> "Головной";
         case "mask" -> "На лицо";
         default -> "Косметика";
      };
   }

   public static Identifier m477(int var0) {
      return Identifier.of("daamky", "textures/cosmetics/item_" + m487(var0).m113() + ".png");
   }

   public static boolean m13(int var0) {
      init();
      return f6.containsValue(var0);
   }

   public static List<Integer> m24() {
      init();
      return List.copyOf(f6.values());
   }

   public static void m15(int var0) {
      init();
      CapeManager$1 var1 = m487(var0);
      Integer var2 = f6.get(var1.m18());
      if (var2 != null && var2 == var0) {
         f6.remove(var1.m18());
      } else {
         f6.put(var1.m18(), Integer.valueOf(var0));
         if (!"cape".equals(var1.m18())) {
            m478(var0);
         }
      }

      m308();
   }

   // var0 - индекс в списке косметики; модель собирается один раз и кэшируется по id.
   public static CosmeticModelItem m478(int var0) {
      init();
      if (var0 >= 0 && var0 < f2.size()) {
         int var1 = f2.get(var0).m113();
         CosmeticModelItem var2 = f4.get(var1);
         if (var2 == null || var2.m560() == null) {
            var2 = m480(var1);
            if (var2 != null && var2.m560() != null) {
               f4.put(var1, var2);
            }
         }

         return var2;
      } else {
         return null;
      }
   }

   public static Identifier m479() {
      return null;
   }

   private static CosmeticModelItem m480(int var0) {
      JsonObject var1 = m482(var0);
      if (var1 != null && var1.has("model")) {
         try {
            String var2 = var1.has("name")
               ? var1.get("name").getAsString()
               : "Cosmetic " + var0;
            int var3 = var1.has("id") ? var1.get("id").getAsInt() : var0;
            int var4 = var1.has("category") ? var1.get("category").getAsInt() : 1;
            CosmeticModelItem var5 = new CosmeticModelItem(var2, var3, var4);
            var5.m16(var1.getAsJsonObject("model").toString());
            var5.m267(m481(var0));
            if (var1.has("pos")) {
               var5.m562(CosmeticAttachPoint.m577(var1.get("pos").getAsInt()));
            }

            if (var1.has("scale")) {
               var5.m348(var1.get("scale").getAsFloat());
            }

            if (var1.has("x")) {
               var5.m410(var1.get("x").getAsFloat());
            }

            if (var1.has("y")) {
               var5.m519(var1.get("y").getAsFloat());
            }

            if (var1.has("z")) {
               var5.m520(var1.get("z").getAsFloat());
            }

            if (var1.has("yaw")) {
               var5.m521(var1.get("yaw").getAsFloat());
            }

            if (var1.has("pitch")) {
               var5.m522(var1.get("pitch").getAsFloat());
            }

            if (var1.has("roll")) {
               var5.m526(var1.get("roll").getAsFloat());
            }

            if (var0 >= 58 && var0 <= 62) {
               var5.m519(0.056F);
            }

            if (var1.has("height")) {
               var5.m527(var1.get("height").getAsFloat());
            }

            if (var1.has("previewScale")) {
               var5.m528(var1.get("previewScale").getAsFloat());
            }

            if (var1.has("previewY")) {
               var5.m531(var1.get("previewY").getAsFloat());
            }

            if (var1.has("animation")) {
               var5.m568(var1.getAsJsonObject("animation"));
            }

            return var5;
         } catch (Exception var6) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static Identifier m481(int var0) {
      Identifier var1 = f5.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         JsonObject var2 = m482(var0);
         if (var2 != null && var2.has("texture")) {
            try {
               byte[] var3 = Base64.getDecoder().decode(var2.get("texture").getAsString());
               NativeImage var4 = NativeImage.read(new ByteArrayInputStream(var3));
               Identifier var5 = Identifier.of("daamky", "cosmetic/model_" + var0);
               MinecraftClient var6 = MinecraftClient.getInstance();
               if (!var6.isOnThread()) {
                  var6.execute(() -> m481(var0));
                  return null;
               } else {
                  var6.getTextureManager()
                     .registerTexture(var5, new NativeImageBackedTexture(() -> "Daamky cosmetic", var4));
                  f5.put(var0, var5);
                  return var5;
               }
            } catch (Exception var7) {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private static JsonObject m482(int var0) {
      if (f3.containsKey(var0)) {
         return f3.get(var0);
      } else {
         String var1 = "/assets/daamky/cosmetics/models/cosmetic_" + var0 + ".json";

         try {
            JsonObject var4;
            try (InputStream var2 = CapeManager.class.getResourceAsStream(var1)) {
               JsonObject var3 = var2 == null ? null : JsonParser.parseString(new String(var2.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
               if (var3 != null) {
                  f3.put(var0, var3);
               }

               var4 = var3;
            }

            return var4;
         } catch (Exception var7) {
            return null;
         }
      }
   }

   private static List<CapeManager$1> m483() {
      ArrayList var0 = new ArrayList();

      for (int var1 = 0; var1 < 256; var1++) {
         String var2 = "/assets/daamky/cosmetics/models/cosmetic_" + var1 + ".json";

         try (InputStream var3 = CapeManager.class.getResourceAsStream(var2)) {
            if (var3 != null) {
               JsonObject var4 = JsonParser.parseString(new String(var3.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject();
               String var5 = var4.has("name")
                  ? var4.get("name").getAsString()
                  : "Cosmetic " + (var1 + 1);
               String var6 = var4.has("type") ? var4.get("type").getAsString() : "";
               String var7 = var5 == null ? "" : var5.replace('_', ' ').trim();
               if (!"cape".equalsIgnoreCase(var6.trim())
                  && !"spider wings".equalsIgnoreCase(var7)
                  && !"easter wings".equalsIgnoreCase(var7)) {
                  int var8 = var4.has("pos") ? var4.get("pos").getAsInt() : -1;
                  var0.add(new CapeManager$1(var1, m484(var5, var1), m486(var1, var5, var6, var8)));
               }
            }
         } catch (Exception var11) {
         }
      }

      var0.sort(Comparator.comparingInt(CapeManager$1::m113));
      return List.copyOf(var0);
   }

   private static String m484(String var0, int var1) {
      String var2 = m485(var1);
      if (var2 != null) {
         return var2;
      } else {
         String var3 = var0 != null && !var0.isBlank() ? var0 : "Cosmetic " + (var1 + 1);
         if (var3.regionMatches(true, 0, "Daamky_", 0, 7)) {
            var3 = var3.substring(7);
         }

         if (var3.regionMatches(true, 0, "pulse_", 0, 6)) {
            var3 = var3.substring(6);
         }

         return var3.replace('_', ' ').trim();
      }
   }

   private static String m485(int var0) {
      return switch (var0) {
         case 0 -> "Плащ Кометы";
         case 1 -> "Классический плащ";
         case 2 -> "Тёмный плащ";
         case 3 -> "Плащ Аквыч";
         case 4 -> "Плащ Брои 3";
         case 5 -> "Плащ Брои 2";
         case 6 -> "Плащ Флюгера 2";
         case 7 -> "Плащ Флюгера 3";
         case 8 -> "Плащ Флюгера 5";
         case 9 -> "Плащ Брои";
         case 10 -> "Плащ Аквыч";
         case 11 -> "Плащ с алмазным мечом";
         case 12 -> "Глитч-плащ";
         case 13 -> "Плащ Повелителя";
         case 14 -> "Крылья Кометы";
         case 15 -> "Тёмные крылья";
         case 16 -> "Кагуне";
         case 17 -> "Рокерские крылья";
         case 18 -> "Крылья Воты";
         case 19 -> "Крылья Флюгера";
         case 20 -> "Крылья Трезубца";
         case 21 -> "Пасхальные крылья";
         case 22 -> "Ангельские крылья";
         case 23 -> "Крылья дракончика";
         case 24 -> "Синие драконьи крылья";
         case 25 -> "Паучьи крылья";
         case 26 -> "Вулканические крылья";
         case 27 -> "Катана за спиной";
         case 28 -> "Рюкзак-лягушка";
         case 29 -> "Красный рюкзак";
         case 30 -> "Тактический рюкзак";
         case 31 -> "Камуфляжный рюкзак";
         case 32 -> "Графитовый рюкзак";
         case 33 -> "Спортивный рюкзак";
         case 34 -> "Штурмовой рюкзак";
         case 35 -> "Чёрно-золотой рюкзак";
         case 36 -> "Технологичный рюкзак";
         case 37 -> "Кожаный рюкзак";
         case 38 -> "Зелёный питомец";
         case 39 -> "Белый питомец";
         case 40 -> "Тёмный питомец";
         case 41 -> "Золотой питомец";
         case 42 -> "Питомец-тортик";
         case 43 -> "Питомец-пчёлка";
         case 44 -> "Питомец Аквыч";
         case 45 -> "Питомец Флюгера";
         case 46 -> "Алмазный питомец";
         case 47 -> "Питомец-редиска";
         case 48 -> "Питомец-демон";
         case 49 -> "Питомец-ангел";
         case 50 -> "Тёмная шапка";
         case 51 -> "Нимб";
         case 52 -> "Шапка-лист";
         case 53 -> "Шапка-лягушка Кометы";
         case 54 -> "Маска Брои";
         case 55 -> "Шапка-пингвин";
         case 56 -> "Шапка Флюгера";
         case 57 -> "Шапка-медведь";
         case 58 -> "Шапка-броненосец";
         case 59 -> "Шапка-верблюд";
         case 60 -> "Шапка-курица";
         case 61 -> "Шапка лягушонка";
         case 62 -> "Шапка-овца";
         case 63 -> "Сумка через плечо";
         default -> null;
      };
   }

   private static String m486(int var0, String var1, String var2, int var3) {
      if (var0 == 63) {
         return "bag";
      } else if (var2 != null && !var2.isBlank()) {
         return var2.trim().toLowerCase(Locale.ROOT);
      } else {
         String var4 = var1 == null ? "" : var1.toLowerCase(Locale.ROOT);
         if (var4.contains("mask")) {
            return "mask";
         } else if (var4.contains("cape")) {
            return "cape";
         } else if (var4.contains("wing")) {
            return "wings";
         } else if (var4.contains("pet")
            || var4.contains("bee")
            || var4.contains("radish")) {
            return "pet";
         } else if (var4.contains("hat") || var4.contains("nimb") || var3 == 2) {
            return "hat";
         } else if (var0 <= 13) {
            return "cape";
         } else if (var0 <= 26) {
            return "wings";
         } else if (var0 <= 37) {
            return "bodywear";
         } else {
            return var0 <= 49 ? "pet" : "hat";
         }
      }
   }

   private static CapeManager$1 m487(int var0) {
      if (var0 >= 0 && var0 < f2.size()) {
         return f2.get(var0);
      } else {
         throw new IndexOutOfBoundsException("Cosmetic " + var0);
      }
   }

   private static int m488(int var0) {
      for (int var1 = 0; var1 < f2.size(); var1++) {
         if (f2.get(var1).m113() == var0) {
            return var1;
         }
      }

      return -1;
   }

   private static void m299() {
      f6.clear();
      if (f7 != null && Files.isRegularFile(f7)) {
         Properties var0 = new Properties();

         try (InputStream var1 = Files.newInputStream(f7)) {
            var0.load(var1);

            for (String var3 : var0.stringPropertyNames()) {
               int var4 = m488(Integer.parseInt(var0.getProperty(var3)));
               if (var4 >= 0 && var3.equals(m487(var4).m18())) {
                  f6.put(var3, Integer.valueOf(var4));
               }
            }
         } catch (Exception var7) {
            f6.clear();
         }
      }
   }

   private static void m308() {
      if (f7 != null) {
         Properties var0 = new Properties();

         for (Entry var2 : f6.entrySet()) {
            var0.setProperty((String)var2.getKey(), Integer.toString(m487((Integer)var2.getValue()).m113()));
         }

         try {
            Files.createDirectories(f7.getParent());

            try (OutputStream var7 = Files.newOutputStream(f7)) {
               var0.store(var7, "Daamky cosmetics");
            }
         } catch (Exception var6) {
         }
      }
   }
}
