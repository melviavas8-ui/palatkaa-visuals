package ua.daamky.utils;

import ua.daamky.system.api.ModuleManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public final class NeuroCommand {
   private static final List<String> f1 = List.of(
      "record",
      "stop",
      "train",
      "play",
      "list",
      "context",
      "dir",
      "clear",
      "status"
   );
   private static final List<String> f2 = List.of(
      "train", "play", "stop"
   );

   private NeuroCommand() {
   }

   private static NeuroMovementModel m764() {
      // AttackAura removed — Neuro commands unavailable
      return null;
   }

   public static boolean m20(String var0) {
      String var1 = var0.trim();
      if (!var1.startsWith(".neuro") && !var1.startsWith(".нейро")) {
         return false;
      } else {
         NeuroMovementModel var2 = m764();
         if (var2 == null) {
            m60("§c[Neuro] §fNeuro (Aura) недоступен");
            return true;
         } else {
            String[] var3 = var1.split("\\s+");
            String var4 = var3.length > 1 ? var3[1].toLowerCase() : "status";
            String var5 = var3.length > 2 ? var3[2] : null;
            int var7 = -1;
            switch (var4.hashCode()) {
               case -2145470045:
                  if (var4.equals("список")) {
                     var7 = 10;
                  }
                  break;
               case -1268966290:
                  if (var4.equals("folder")) {
                     var7 = 7;
                  }
                  break;
               case -1068799382:
                  if (var4.equals("models")) {
                     var7 = 11;
                  }
                  break;
               case -934908847:
                  if (var4.equals("record")) {
                     var7 = 0;
                  }
                  break;
               case -892481550:
                  if (var4.equals("status")) {
                     var7 = 16;
                  }
                  break;
               case -270849297:
                  if (var4.equals("контекст")) {
                     var7 = 14;
                  }
                  break;
               case 99469:
                  if (var4.equals("dir")) {
                     var7 = 6;
                  }
                  break;
               case 112784:
                  if (var4.equals("rec")) {
                     var7 = 1;
                  }
                  break;
               case 3322014:
                  if (var4.equals("list")) {
                     var7 = 9;
                  }
                  break;
               case 3443508:
                  if (var4.equals("play")) {
                     var7 = 4;
                  }
                  break;
               case 3540994:
                  if (var4.equals("stop")) {
                     var7 = 2;
                  }
                  break;
               case 94746189:
                  if (var4.equals("clear")) {
                     var7 = 15;
                  }
                  break;
               case 109757538:
                  if (var4.equals("start")) {
                     var7 = 5;
                  }
                  break;
               case 110621192:
                  if (var4.equals("train")) {
                     var7 = 3;
                  }
                  break;
               case 951530927:
                  if (var4.equals("context")) {
                     var7 = 13;
                  }
                  break;
               case 1036882500:
                  if (var4.equals("папка")) {
                     var7 = 8;
                  }
                  break;
               case 2005297184:
                  if (var4.equals("модели")) {
                     var7 = 12;
                  }
            }

            switch (var7) {
               case 0:
               case 1:
                  var2.m699();
                  m23();
                  if (NeuroMovementModel.m665()) {
                     m60(
                        "§a[Neuro] §fЗапись начата §d(владелец — без лимита)"
                     );
                     m60(
                        "§7[Neuro] Пиши сколько хочешь: работает с любого количества сэмплов."
                     );
                  } else {
                     m60("§a[Neuro] §fЗапись начата §7(" + var2.m681() + "/12000 сэмплов)");
                     m60("§e[Neuro] §fДля обхода нужно §c10 минут обучения §7— это §e12000 сэмплов§7. Осталось: §e" + var2.m751());
                  }

                  m60(
                     "§7[Neuro] Дерись как обычно рядом с игроком — пишется каждый тик. Таймер идёт в вотермарке."
                  );
                  m60(
                     "§7[Neuro] Для чистой записи лучше выключить Aura — тогда учится именно твоя рука."
                  );
                  break;
               case 2:
                  if (var2.m681() == 0) {
                     m60("§c[Neuro] §fНет паттернов");
                  } else {
                     String var17 = var5 != null && !var5.isEmpty() ? var5 : "neuro_" + System.currentTimeMillis();
                     boolean var19 = var2.m80(var17);
                     String var20 = var2.m309();
                     int var21 = var2.m753();
                     int var22 = var2.m147(var17);
                     if (var19) {
                        m60("§a[Neuro] §fДозаписано к §e" + var17 + " §7(+" + var21 + " сэмплов за " + var20 + ", всего: " + var22 + ")");
                     } else {
                        m60("§a[Neuro] §fСохранено как §e" + var17 + " §7(записано " + var20 + ", сэмплов: " + var22 + ")");
                     }

                     if (NeuroMovementModel.m665()) {
                        m60(
                           "§d[Neuro] §fВладелец — обучение засчитано без лимита, аура готова."
                        );
                     } else if (!var2.m534()) {
                        m60("§c[Neuro] §fОбучение неполное: нужно ещё §e" + var2.m751() + " §fсэмплов (до 10 минут). Аура будет работать хуже.");
                     } else {
                        m60(
                           "§a[Neuro] §fОбучение завершено — ауре хватает данных для обхода."
                        );
                     }

                     m60("§7[Neuro] Дальше: §f.neuro train " + var17 + " §7— и только потом §f.neuro play " + var17);
                  }
                  break;
               case 3:
                  if (var2.m6()) {
                     m60("§e[Neuro] §fОбучение уже идёт...");
                  } else {
                     if (var5 == null || var5.isEmpty()) {
                        var5 = var2.m39();
                        if (var5 == null) {
                           List var16 = var2.m761();
                           if (var16.size() != 1) {
                              m60("§c[Neuro] §fУкажи имя: §e.neuro train <имя>");
                              m60(
                                 var16.isEmpty()
                                    ? "§7[Neuro] Записей пока нет — начни с §f.neuro record"
                                    : "§7[Neuro] Есть: §f" + String.join("§7, §f", var16)
                              );
                              break;
                           }

                           var5 = (String)var16.get(0);
                        }
                     }

                     if (!var2.m759(var5)) {
                        m60("§c[Neuro] §fНет такой записи: §e" + var5);
                     } else if (!var2.m17(var5)) {
                        m60("§c[Neuro] §fЗапись пустая");
                     }
                  }
                  break;
               case 4:
               case 5:
                  String var15 = var5 != null && !var5.isEmpty() ? var5 : var2.m39();
                  if (var15 == null) {
                     m60("§c[Neuro] §fСначала обучись: §e.neuro train <имя>");
                  } else if (!var2.m20(var15)) {
                     m60("§c[Neuro] §fЗапись §e" + var15 + " §fещё не обучена");
                     m60("§7[Neuro] Выполни сначала: §f.neuro train " + var15);
                  } else if (!var2.m276()) {
                     m60("§e[Neuro] §fАура обучена старой версией §7(v" + var2.m102() + " -> v5)");
                     m60("§7[Neuro] Прогони §f.neuro train " + var15 + " §7— запись перезаписывать не надо");
                  } else {
                     var2.m300(false);
                     var2.m4(true);
                     m23();
                     m60("§a[Neuro] §fАура включена по обучению §e" + var15 + " §7(" + var2.m681() + " сэмплов) §f— выбери ротацию §bNeuro Beta §fв Aura");
                  }
                  break;
               case 6:
               case 7:
               case 8:
                  List var14 = var2.m761();
                  boolean var18 = var2.m758();
                  m60(
                     (
                           var18
                              ? "§a[Neuro] §fПапка открыта"
                              : "§c[Neuro] §fНе смог открыть папку"
                        )
                        + " §7(записей: "
                        + var14.size()
                        + ")"
                  );
                  if (!var14.isEmpty()) {
                     m60("§7[Neuro] Есть: §f" + String.join("§7, §f", var14));
                  }

                  m60(
                     "§7[Neuro] Ненужные удаляй прямо там — это файлы §f*.neuro"
                  );
                  m60("§8[Neuro] " + NeuroMovementModel.m757());
                  break;
               case 9:
               case 10:
               case 11:
               case 12:
                  List<String> var8 = var2.m761();
                  if (var8.isEmpty()) {
                     m60("§7[Neuro] Записей нет — начни с §f.neuro record");
                  } else {
                     String var9 = var2.m39();
                     m60("§b[Neuro] §fЗаписи (§e" + var8.size() + "§f):");

                     for (String var11 : var8) {
                        boolean var12 = var11.equalsIgnoreCase(var9);
                        String var13 = var12
                           ? (
                              var2.m629()
                                 ? " §a[активна]"
                                 : " §b[обучена]"
                           )
                           : "";
                        m60("  §7• §f" + var11 + var13);
                     }

                     m60(
                        "§7[Neuro] Переключение: §f.neuro train <имя> §7→ §f.neuro play <имя>"
                     );
                  }
                  break;
               case 13:
               case 14:
                  if (var5 == null) {
                     var2.m61(!var2.m81());
                  } else {
                     var2.m61(
                        var5.equalsIgnoreCase("on")
                           || var5.equalsIgnoreCase("вкл")
                           || var5.equals("1")
                     );
                  }

                  m60(
                     "§b[Neuro] §fКонтекст (подстройка под твоё движение): "
                        + (var2.m81() ? "§aВКЛ" : "§cВЫКЛ")
                  );
                  if (var2.m81()) {
                     m60(
                        "§7[Neuro] Аура выбирает кусок записи под ситуацию (стоишь/движешься + дистанция). Для эффекта перезапиши .neuro record — в новых записях пишется твоё движение."
                     );
                  }
                  break;
               case 15:
                  var2.m698();
                  m60("§e[Neuro] §fПаттерны очищены");
                  break;
               case 16:
               default:
                  m60("§8[§bNeuro §eBETA§8] §f" + var2.m762());
            }

            return true;
         }
      }
   }

   public static List<String> m76() {
      NeuroMovementModel var0 = m764();
      return (List<String>)(var0 == null ? new ArrayList<>() : var0.m761());
   }

   public static List<String> m428(String var0) {
      String var1 = var0.stripLeading().toLowerCase(Locale.ROOT);
      if (!var1.isEmpty() && var1.startsWith(".")) {
         if (".".equals(var1) || ".neuro".startsWith(var1)) {
            return List.of(".neuro");
         } else if (".нейро".startsWith(var1)) {
            return List.of(".нейро");
         } else {
            String var2 = var1.startsWith(".neuro ")
               ? ".neuro "
               : (var1.startsWith(".нейро ") ? ".нейро " : null);
            if (var2 == null) {
               return List.of();
            } else {
               String var3 = var1.substring(var2.length());
               int var4 = var3.indexOf(32);
               if (var4 < 0) {
                  return f1.stream().filter(var1x -> var1x.startsWith(var3)).map(var1x -> var2 + var1x).toList();
               } else {
                  String var5 = var3.substring(0, var4);
                  String var6 = var3.substring(var4 + 1);
                  return !var6.contains(" ") && f2.contains(var5)
                     ? m76().stream().filter(var1x -> var1x.toLowerCase(Locale.ROOT).startsWith(var6)).map(var2x -> var2 + var5 + " " + var2x).toList()
                     : List.of();
               }
            }
         }
      } else {
         return List.of();
      }
   }

   public static void m314() {
      NeuroMovementModel var0 = m764();
      if (var0 != null) {
         var0.m63();
         var0.m277();

         String var1;
         while ((var1 = var0.m30()) != null) {
            m60("§8[§bNeuro§8] " + var1);
         }
      }
   }

   public static String m18() {
      NeuroMovementModel var0 = m764();
      return var0 != null && var0.m752() ? var0.m309() : null;
   }

   private static void m23() {
      m60(
         "§6[Neuro §eBETA§6] §fФункция в бета-тесте — возможны сбои"
      );
   }

   private static void m60(String var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      if (var1.inGameHud != null) {
         var1.inGameHud.getChatHud().addMessage(Text.literal(var0));
      }
   }
}
