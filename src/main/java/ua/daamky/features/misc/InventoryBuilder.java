package ua.daamky.features.misc;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.SaveScreen;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.PresetItemData;
import ua.daamky.utils.InventoryPreset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@NewFunction(
   I0 = "Inventory Builder",
   I00 = "Авто-сборка лоадаута через аукцион (/ah)",
   I000 = Category.MISC
)
public class InventoryBuilder extends Module {
   public static InventoryBuilder f1;
   private final NumberSetting f2 = new NumberSetting("Задержка", 300.0, 100.0, 1500.0, 50.0);
   private final NumberSetting f3 = new NumberSetting("Попытки", 6.0, 1.0, 15.0, 1.0);
   private final NumberSetting f4 = new NumberSetting("Страниц", 6.0, 1.0, 30.0, 1.0);
   private final BooleanSetting f5 = new BooleanSetting("Сортировка в конце", false);
   private final BooleanSetting f6 = new BooleanSetting("Лог в файл", true);
   private static final Pattern f7 = Pattern.compile("увеличить\\D+(\\d+)");
   private static final Pattern f8 = Pattern.compile("уменьшить\\D+(\\d+)");
   private static final Pattern f9 = Pattern.compile("\\((\\d+)\\s*/\\s*(\\d+)\\)");
   private static final Pattern f10 = Pattern.compile("(\\d[\\d\\s.,]*)");
   private InventoryBuilder$1 f11 = InventoryBuilder$1.f1;
   private InventoryPreset f12;
   private int f13;
   private int f14;
   private int f15;
   private int f16;
   private int f17;
   private boolean f18;
   private int f19 = -1;
   private int f20;
   private int f21;
   private long f22;
   private String f23 = "";
   private int f24;
   private final Deque<int[]> f25 = new ArrayDeque<>();
   private final Set<String> f26 = new HashSet<>();
   private volatile boolean f27;
   private volatile String f28 = "";
   private volatile boolean f29;
   private long f30;
   private long f31;
   private long f32;

   public InventoryBuilder() {
      f1 = this;
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4, this.f5, this.f6});
   }

   @Override
   public void onEnable() {
      if (this.f11 == InventoryBuilder$1.f1) {
         this.mc.execute(() -> {
            if (this.f11 == InventoryBuilder$1.f1) {
               this.mc.setScreen(new SaveScreen());
            }
         });
      }
   }

   @Override
   public void onDisable() {
      if (this.f11 != InventoryBuilder$1.f1) {
         this.m16("модуль выключен");
      }
   }

   public boolean m687() {
      return this.f11 != InventoryBuilder$1.f1;
   }

   public void m853(InventoryPreset var1) {
      if (var1 != null && var1.m113() != 0) {
         this.f12 = var1;
         this.f13 = 0;
         this.f14 = 0;
         this.f15 = 0;
         this.f24 = 0;
         this.f25.clear();
         this.f26.clear();
         this.f27 = false;
         this.f29 = false;
         this.f11 = InventoryBuilder$1.f2;
         this.f30 = this.f31 = m866();
         if (!this.isEnabled()) {
            this.toggle();
         }

         this.m844("§bСборка «" + var1.f1 + "» запущена: " + var1.m113() + " слот(ов). Отмена — Esc/выключи модуль.");
         this.m868("=== RUN " + var1.f1 + " (" + var1.m113() + " slots) ===");
      }
   }

   public void m16(String var1) {
      if (this.f11 != InventoryBuilder$1.f1) {
         this.m844("§eСборка остановлена: " + var1 + " (куплено " + this.f24 + ")");
      }

      this.m868("STOP: " + var1 + " bought=" + this.f24);
      this.f11 = InventoryBuilder$1.f1;
      this.f12 = null;
      this.f25.clear();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.f11 != InventoryBuilder$1.f1) {
         if (this.util.m81()) {
            this.m16("нет игрока");
         } else if (m866() - this.f30 >= Math.max(50L, (long)this.f2.getValue() + this.f32)) {
            this.f30 = m866();
            this.f32 = ThreadLocalRandom.current().nextLong(-50L, 51L);
            if (this.f27) {
               this.f27 = false;
               if (this.f15 >= 3) {
                  this.m16("не хватает денег");
                  return;
               }

               if (this.f11 == InventoryBuilder$1.f4 || this.f11 == InventoryBuilder$1.f5 || this.f11 == InventoryBuilder$1.f6) {
                  this.m21(this.f28.isEmpty() ? "Лот не куплен" : this.f28);
                  return;
               }
            }

            switch (this.f11) {
               case f2:
                  this.m116();
                  break;
               case f3:
                  this.m676();
                  break;
               case f4:
                  this.m134();
                  break;
               case f5:
                  this.m135();
                  break;
               case f6:
                  this.m677();
            }
         }
      }
   }

   private void m116() {
      while (this.f13 < 41 && (this.f12.f2[this.f13] == null || this.m10(this.f13))) {
         this.f13++;
      }

      if (this.f13 >= 41) {
         this.m607();
      } else {
         PresetItemData var1 = this.f12.f2[this.f13];
         int var2 = this.m859(var1) - this.m858(var1);
         if (var2 <= 0) {
            this.m707();
         } else if (this.f14 >= (int)this.f3.getValue()) {
            this.m844("§7Не удалось купить: " + var1.m37() + " (не хватает " + var2 + ")");
            this.m707();
         } else {
            this.f16 = 0;
            this.f17 = 0;
            this.f18 = false;
            String var3 = this.m856(var1);
            this.mc.player.networkHandler.sendChatCommand("ah search " + var3);
            this.m868("SEARCH slot=" + this.f13 + " '" + var3 + "' need=" + var2 + " try=" + this.f14);
            this.f11 = InventoryBuilder$1.f3;
            this.f31 = m866();
         }
      }
   }

   private void m676() {
      if (this.f29) {
         this.f29 = false;
         this.m844("§7Не найдено на аукционе: " + this.f12.f2[this.f13].m37());
         this.m707();
         this.f11 = InventoryBuilder$1.f2;
      } else if (m866() - this.f31 > 6000L) {
         this.f14++;
         this.f11 = InventoryBuilder$1.f2;
      } else {
         GenericContainerScreenHandler var1 = this.m855();
         if (var1 != null) {
            if (this.m754().contains("покупк")) {
               this.f11 = InventoryBuilder$1.f4;
               this.f31 = m866();
            } else {
               int var2 = var1.slots.size() - 36;
               PresetItemData var3 = this.f12.f2[this.f13];
               int var4 = this.m859(var3) - this.m858(var3);
               if (var4 <= 0) {
                  this.m707();
                  this.f11 = InventoryBuilder$1.f2;
               } else {
                  if (!this.f18) {
                     boolean var5 = true;
                     int var6 = 0;

                     while (true) {
                        if (var6 < var2) {
                           if (var1.getSlot(var6).getStack().isEmpty()) {
                              var6++;
                              continue;
                           }

                           var5 = false;
                        }

                        if (var5 || m866() - this.f31 < Math.max(400L, (long)this.f2.getValue())) {
                           return;
                        }

                        this.f18 = true;
                        break;
                     }
                  }

                  int var16 = -1;
                  int var17 = 0;
                  long var7 = Long.MAX_VALUE;
                  String var9 = "";

                  for (int var10 = 0; var10 < var2; var10++) {
                     ItemStack var11 = var1.getSlot(var10).getStack();
                     if (this.m857(var11, var3)) {
                        long var12 = this.m861(var11);
                        if (var12 >= 0L && (var3.f3 <= 0L || var12 <= var3.f3)) {
                           int var14 = Math.max(1, var11.getCount());
                           String var15 = this.m862(var11, var12);
                           if (!this.f26.contains(var15) && var12 < var7) {
                              var7 = var12;
                              var16 = var10;
                              var17 = var14;
                              var9 = var15;
                           }
                        }
                     }
                  }

                  if (var16 >= 0) {
                     this.f21 = this.m858(var3);
                     this.f20 = 0;
                     this.f22 = var7;
                     this.f23 = var9;
                     this.f27 = false;
                     this.f25.clear();
                     if (var17 <= var4) {
                        this.mc.interactionManager.clickSlot(var1.syncId, var16, 0, SlotActionType.PICKUP, this.mc.player);
                     } else {
                        this.mc.interactionManager.clickSlot(var1.syncId, var16, 1, SlotActionType.PICKUP, this.mc.player);
                        this.f20 = var4;
                     }

                     this.m868("BUY lot slot=" + var16 + " count=" + var17 + " price=" + var7 + " qtyToBuy=" + this.f20);
                     this.f11 = InventoryBuilder$1.f4;
                     this.f31 = m866();
                  } else {
                     int var18 = this.m863(var1, var2);
                     int[] var19 = this.m864();
                     if (var18 < 0 || this.f16 + 1 >= (int)this.f4.getValue() || var19[1] > 0 && var19[0] >= var19[1]) {
                        this.m844(
                           "§7"
                              + (
                                 this.f26.isEmpty()
                                    ? "Нет подходящих лотов: "
                                    : "Все лоты разобрали: "
                              )
                              + var3.m37()
                        );
                        this.m707();
                        this.f11 = InventoryBuilder$1.f2;
                     } else {
                        this.f16++;
                        this.f17 = var19[0] + 1;
                        this.f18 = false;
                        this.mc.interactionManager.clickSlot(var1.syncId, var18, 0, SlotActionType.PICKUP, this.mc.player);
                        this.f31 = m866();
                     }
                  }
               }
            }
         }
      }
   }

   private void m134() {
      if (m866() - this.f31 > 5000L) {
         this.m21("Лот перехватили");
      } else {
         GenericContainerScreenHandler var1 = this.m855();
         if (var1 != null && this.m754().contains("покупк")) {
            int var2 = var1.slots.size() - 36;
            int var3 = -1;
            int var4 = -1;
            int var5 = -1;
            int var6 = -1;
            int var7 = Integer.MAX_VALUE;
            int var8 = -1;
            boolean[] var9 = new boolean[var2];

            for (int var10 = 0; var10 < var2; var10++) {
               ItemStack var11 = var1.getSlot(var10).getStack();
               if (!var11.isEmpty()) {
                  String var12 = var11.getName().getString().toLowerCase(Locale.ROOT);
                  Matcher var13 = f7.matcher(var12);
                  Matcher var14 = f8.matcher(var12);
                  boolean var15 = false;
                  if (var13.find()) {
                     int var16 = Integer.parseInt(var13.group(1));
                     if (var16 == 1) {
                        var3 = var10;
                     } else if (var16 == 10) {
                        var4 = var10;
                     }

                     var15 = true;
                  } else if (var14.find()) {
                     if (Integer.parseInt(var14.group(1)) == 1) {
                        var5 = var10;
                     }

                     var15 = true;
                  } else if (var12.contains("подтверд")
                     || var12.contains("купить")
                     || var12.contains("приобрест")) {
                     var6 = var10;
                  }

                  if (var15) {
                     var9[var10] = true;
                     var7 = Math.min(var7, var10);
                     var8 = Math.max(var8, var10);
                  }
               }
            }

            if (var6 == -1 && var8 != -1) {
               for (int var17 = var7 + 1; var17 < var8; var17++) {
                  if (!var9[var17] && !var1.getSlot(var17).getStack().isEmpty()) {
                     var6 = var17;
                     break;
                  }
               }
            }

            if (var6 == -1) {
               this.m854(var1, var2, "нет кнопки подтверждения");
            } else if (this.f20 > 1 && var3 == -1 && var4 == -1) {
               this.m854(var1, var2, "нет кнопок количества");
            } else {
               this.f19 = var6;
               if (this.f20 > 1) {
                  this.m355(this.f20 - 1, var3, var4, var5);
                  this.f11 = InventoryBuilder$1.f5;
                  this.f31 = m866();
               } else {
                  this.mc.interactionManager.clickSlot(var1.syncId, var6, 0, SlotActionType.PICKUP, this.mc.player);
                  this.f11 = InventoryBuilder$1.f6;
                  this.f31 = m866();
               }
            }
         }
      }
   }

   private void m135() {
      GenericContainerScreenHandler var1 = this.m855();
      if (var1 != null && this.m754().contains("покупк")) {
         int[] var2 = this.f25.poll();
         if (var2 != null) {
            this.mc.interactionManager.clickSlot(var1.syncId, var2[0], 0, SlotActionType.PICKUP, this.mc.player);
         } else {
            this.mc.interactionManager.clickSlot(var1.syncId, this.f19, 0, SlotActionType.PICKUP, this.mc.player);
            this.f11 = InventoryBuilder$1.f6;
            this.f31 = m866();
         }
      } else {
         this.f25.clear();
         this.f11 = InventoryBuilder$1.f6;
         this.f31 = m866();
      }
   }

   private void m677() {
      if (m866() - this.f31 >= 1200L) {
         PresetItemData var1 = this.f12.f2[this.f13];
         int var2 = this.m858(var1);
         if (var2 <= this.f21) {
            if (m866() - this.f31 > 2500L) {
               this.m21("Лот перехватили");
            }
         } else {
            this.f14 = 0;
            this.f15 = 0;
            int var3 = var2 - this.f21;
            this.f24 += var3;
            this.m844("§aКуплено " + var3 + "× " + var1.m37() + (this.f22 > 0L ? " по " + m867(this.f22) : ""));
            this.m868("BOUGHT " + var3 + "x " + var1.m37() + " price=" + this.f22 + " total=" + this.f24);
            this.f22 = 0L;
            this.f23 = "";
            int var4 = this.m859(var1) - this.m858(var1);
            if (var4 > 0 && this.m855() != null && !this.m754().contains("покупк")) {
               this.f18 = false;
               this.f11 = InventoryBuilder$1.f3;
               this.f31 = m866();
            } else {
               this.m707();
               this.f11 = InventoryBuilder$1.f2;
            }
         }
      }
   }

   private void m21(String var1) {
      this.f14++;
      if (!this.f23.isEmpty()) {
         this.f26.add(this.f23);
      }

      this.f23 = "";
      this.f22 = 0L;
      this.f25.clear();
      PresetItemData var2 = this.f12 != null && this.f13 >= 0 && this.f13 < 41 ? this.f12.f2[this.f13] : null;
      String var3 = var2 == null ? "" : ": " + var2.m37();
      if (this.f14 >= (int)this.f3.getValue()) {
         this.m844("§7" + var1 + var3 + " — пропускаю");
         this.m707();
         this.f11 = InventoryBuilder$1.f2;
      } else {
         this.m844("§7" + var1 + var3 + " — беру следующий");
         this.f18 = false;
         this.f11 = InventoryBuilder$1.f3;
         this.f31 = m866();
      }
   }

   private void m854(GenericContainerScreenHandler var1, int var2, String var3) {
      this.m868("skip lot: " + var3 + " | " + this.m865(var1, var2));
      this.m844("§7Меню покупки: " + var3 + " — лот пропущен");
      this.f14++;
      this.f25.clear();
      this.m707();
      this.f11 = InventoryBuilder$1.f2;
      this.f31 = m866();
   }

   private void m707() {
      this.f13++;
      this.f14 = 0;
      this.f26.clear();
   }

   private void m607() {
      String var1 = this.f12 == null ? "" : this.f12.f1;
      this.m844("§aСборка «" + var1 + "» завершена: куплено " + this.f24);
      this.m868("FINISH bought=" + this.f24);
      this.f11 = InventoryBuilder$1.f1;
      this.f12 = null;
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (this.f11 != InventoryBuilder$1.f1) {
         if (var1.m581() instanceof GameMessageS2CPacket var2) {
            String var6 = var2.content().getString().toLowerCase(Locale.ROOT);
            if (this.f11 == InventoryBuilder$1.f3) {
               if (var6.contains("не было найдено")
                  || var6.contains("ничего не найдено")) {
                  this.f29 = true;
               }
            } else {
               boolean var4 = (
                     var6.contains("недостаточно")
                        || var6.contains("не хватает")
                  )
                  && (
                     var6.contains("средств")
                        || var6.contains("денег")
                        || var6.contains("монет")
                  );
               if (var4) {
                  this.f15++;
                  this.f27 = true;
                  this.f28 = "Не хватило денег на лот";
               } else {
                  boolean var5 = var6.contains("уже куп")
                     || var6.contains("уже прода")
                     || var6.contains("успел")
                     || var6.contains("перекуп")
                     || var6.contains("лот")
                        && (
                           var6.contains("не найден")
                              || var6.contains("не существует")
                              || var6.contains("снят")
                              || var6.contains("истёк")
                              || var6.contains("истек")
                        );
                  if (var5) {
                     this.f27 = true;
                     this.f28 = "Лот перехватили";
                  }
               }
            }
         }
      }
   }

   private GenericContainerScreenHandler m855() {
      if (this.mc.currentScreen instanceof HandledScreen) {
         ScreenHandler var2 = this.mc.player.currentScreenHandler;
         if (var2 instanceof GenericContainerScreenHandler) {
            return (GenericContainerScreenHandler)var2;
         }
      }

      return null;
   }

   private String m754() {
      return this.mc.currentScreen != null ? this.mc.currentScreen.getTitle().getString().toLowerCase(Locale.ROOT) : "";
   }

   private String m856(PresetItemData var1) {
      if (var1.f6 != null && !var1.f6.isBlank()) {
         return var1.f6;
      } else if (var1.f5 != null && !var1.f5.isBlank()) {
         return var1.f5;
      } else {
         Item var2 = (Item)Registries.ITEM.get(Identifier.tryParse(var1.f1));
         return new ItemStack(var2).getName().getString();
      }
   }

   private boolean m857(ItemStack var1, PresetItemData var2) {
      if (var1 != null && !var1.isEmpty()) {
         Item var3 = (Item)Registries.ITEM.get(Identifier.tryParse(var2.f1));
         if (var1.getItem() != var3) {
            return false;
         } else {
            String var4 = var1.getName().getString().trim().toLowerCase(Locale.ROOT);
            if (var2.f4 && var2.f5 != null && !var2.f5.isBlank()) {
               return var4.equals(var2.f5.trim().toLowerCase(Locale.ROOT));
            } else {
               String var5 = var2.m40();
               return var5.isBlank() || var4.contains(var5.toLowerCase(Locale.ROOT));
            }
         }
      } else {
         return false;
      }
   }

   private int m858(PresetItemData var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < this.mc.player.getInventory().size(); var3++) {
         if (this.m857(this.mc.player.getInventory().getStack(var3), var1)) {
            var2 += this.mc.player.getInventory().getStack(var3).getCount();
         }
      }

      return var2;
   }

   private int m859(PresetItemData var1) {
      int var2 = 0;

      for (PresetItemData var6 : this.f12.f2) {
         if (var6 != null && this.m860(var6, var1)) {
            var2 += var6.f2;
         }
      }

      return var2;
   }

   private boolean m860(PresetItemData var1, PresetItemData var2) {
      return var1.f1.equals(var2.f1) && var1.m37().equalsIgnoreCase(var2.m37());
   }

   private boolean m10(int var1) {
      PresetItemData var2 = this.f12.f2[var1];

      for (int var3 = 0; var3 < var1; var3++) {
         if (this.f12.f2[var3] != null && this.m860(this.f12.f2[var3], var2)) {
            return true;
         }
      }

      return false;
   }

   private long m861(ItemStack var1) {
      LoreComponent var2 = (LoreComponent)var1.get(DataComponentTypes.LORE);
      if (var2 == null) {
         return -1L;
      } else {
         long var3 = -1L;

         for (Text var6 : var2.lines()) {
            String var7 = var6.getString().toLowerCase(Locale.ROOT);
            boolean var8 = var7.contains("цена")
               || var7.contains("купить")
               || var7.contains("стоим")
               || var7.contains("монет")
               || var7.contains("$");
            Matcher var9 = f10.matcher(var7);
            if (var9.find()) {
               long var10 = m146(var9.group(1));
               if (var8 && var10 > 0L) {
                  return var10;
               }

               if (var3 < 0L && var10 > 0L) {
                  var3 = var10;
               }
            }
         }

         return var3;
      }
   }

   private static long m146(String var0) {
      String var1 = var0.replaceAll("[^0-9]", "");

      try {
         return var1.isEmpty() ? -1L : Long.parseLong(var1);
      } catch (Exception var3) {
         return -1L;
      }
   }

   private String m862(ItemStack var1, long var2) {
      StringBuilder var4 = new StringBuilder(var1.getName().getString().trim().toLowerCase(Locale.ROOT));
      var4.append('|').append(var1.getCount()).append('|').append(var2);
      LoreComponent var5 = (LoreComponent)var1.get(DataComponentTypes.LORE);
      if (var5 != null) {
         for (Text var7 : var5.lines()) {
            String var8 = var7.getString();
            if (var8.toLowerCase(Locale.ROOT).contains("продав")
               || var8.toLowerCase(Locale.ROOT).contains("владел")) {
               var4.append('|').append(var8.trim());
               break;
            }
         }
      }

      return var4.toString();
   }

   private int m863(GenericContainerScreenHandler var1, int var2) {
      for (int var3 = 0; var3 < var2; var3++) {
         ItemStack var4 = var1.getSlot(var3).getStack();
         if (!var4.isEmpty()
            && var4.getName().getString().toLowerCase(Locale.ROOT).contains("следующая страница")) {
            return var3;
         }
      }

      return -1;
   }

   private int[] m864() {
      Matcher var1 = f9.matcher(this.m754());
      return var1.find() ? new int[]{Integer.parseInt(var1.group(1)), Integer.parseInt(var1.group(2))} : new int[]{1, 0};
   }

   private void m355(int var1, int var2, int var3, int var4) {
      this.f25.clear();
      if (var1 > 0) {
         if (var3 == -1) {
            for (int var11 = 0; var11 < var1 && var2 != -1; var11++) {
               this.f25.add(new int[]{var2});
            }
         } else {
            int var5 = var1 / 10;
            int var6 = var1 % 10;
            boolean var7 = var6 > 0 && var4 != -1;
            int var8 = var7 ? var5 + 1 + (10 - var6) : Integer.MAX_VALUE;
            int var9 = var6 != 0 && var2 == -1 ? Integer.MAX_VALUE : var5 + var6;
            if (var8 <= var9) {
               for (int var10 = 0; var10 <= var5; var10++) {
                  this.f25.add(new int[]{var3});
               }

               for (int var12 = 0; var12 < 10 - var6; var12++) {
                  this.f25.add(new int[]{var4});
               }
            } else {
               for (int var13 = 0; var13 < var5; var13++) {
                  this.f25.add(new int[]{var3});
               }

               for (int var14 = 0; var14 < var6 && var2 != -1; var14++) {
                  this.f25.add(new int[]{var2});
               }
            }
         }
      }
   }

   private String m865(GenericContainerScreenHandler var1, int var2) {
      StringBuilder var3 = new StringBuilder();

      for (int var4 = 0; var4 < var2; var4++) {
         ItemStack var5 = var1.getSlot(var4).getStack();
         if (!var5.isEmpty()) {
            var3.append(var4).append('=').append(var5.getName().getString()).append("; ");
         }
      }

      return var3.toString();
   }

   private static long m866() {
      return System.currentTimeMillis();
   }

   private static String m867(long var0) {
      return String.format("%,d", var0).replace(',', ' ');
   }

   private void m844(String var1) {
      if (this.mc.inGameHud != null) {
         this.mc.inGameHud.getChatHud().addMessage(Text.literal("§8[§bInvBuilder§8] §r" + var1));
      }
   }

   private void m868(String var1) {
      if (this.f6.m6()) {
         try {
            Path var2 = FabricLoader.getInstance()
               .getGameDir()
               .resolve("Daamky")
               .resolve("invbuilder-log.txt");
            Files.createDirectories(var2.getParent());
            Files.writeString(
               var2,
               "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + var1 + "\n",
               StandardOpenOption.CREATE,
               StandardOpenOption.APPEND
            );
         } catch (Exception var3) {
         }
      }
   }
}
