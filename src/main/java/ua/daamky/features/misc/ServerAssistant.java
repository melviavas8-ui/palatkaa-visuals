package ua.daamky.features.misc;

import ua.daamky.events.KeyEvent;
import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.Daamky_2;
import ua.daamky.mixins.interfaces.IClickSlotC2SPacket;
import ua.daamky.mixins.interfaces.IHandledScreen;
import ua.daamky.mixins.interfaces.IScreenHandlerSlotUpdateS2CPacket;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.ModeSetting;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.FunTimeUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;

@NewFunction(
   I0 = "ServerAssistant",
   I00 = "Помощник для сервера: аукцион, бинды предметов, авто-божья аура",
   I000 = Category.MISC
)
public class ServerAssistant extends Module {
   private final ModeSetting f1 = new ModeSetting(
      "Целевой сервер",
      "FunTime",
      "SpookyTime",
      "HolyWorld"
   );
   private final BooleanSetting f2 = new BooleanSetting("Аукционный ассистент", true);
   private final BooleanSetting f3 = new BooleanSetting("Сортировать по цене", true);
   private final MultiChoiceSetting f4 = new MultiChoiceSetting(
      "Фильтр брони",
      "Защита",
      "Аншип",
      "Починка",
      "Подводная ходьба"
   );
   private final MultiChoiceSetting f5 = new MultiChoiceSetting(
      "Фильтр меча",
      "Острота",
      "Детекция",
      "Вампиризм",
      "Окисление",
      "Яд"
   );
   private final MultiChoiceSetting f6 = new MultiChoiceSetting(
      "Фильтр кирки",
      "Эффективность",
      "Удача",
      "Магнит",
      "Починка"
   );
   private final BooleanSetting f7 = new BooleanSetting("Авто-божья аура", false);
   private final KeybindSetting f8 = new KeybindSetting("Трапка", -1);
   private final KeybindSetting f9 = new KeybindSetting("Снежок заморозка", -1);
   private final KeybindSetting f10 = new KeybindSetting("Пласт", -1);
   private final KeybindSetting f11 = new KeybindSetting("Дезориентация", -1);
   private final KeybindSetting f12 = new KeybindSetting("Явная пыль", -1);
   private final KeybindSetting f13 = new KeybindSetting("Заряд ветра", -1);
   private final KeybindSetting f14 = new KeybindSetting("Огненный заряд", -1);
   private final KeybindSetting f15 = new KeybindSetting("Божья аура", -1);
   private final KeybindSetting f16 = new KeybindSetting("Взрывная штучка (Holy)", -1);
   private final KeybindSetting f17 = new KeybindSetting("Взрывная палочка (Holy)", -1);
   private final KeybindSetting f18 = new KeybindSetting("Взрывная трапка (Holy)", -1);
   private final KeybindSetting f19 = new KeybindSetting("Стан (Holy)", -1);
   private final KeybindSetting f20 = new KeybindSetting("Ком снега (Holy)", -1);
   private long f21;
   private int[] f22;
   private int f23;
   private int f24 = -1;
   private int f25 = -1;

   public ServerAssistant() {
      this.addSettings(
         new Setting[]{
            this.f1,
            this.f2,
            this.f3,
            this.f4,
            this.f5,
            this.f6,
            this.f7,
            this.f8,
            this.f9,
            this.f10,
            this.f11,
            this.f12,
            this.f13,
            this.f14,
            this.f15,
            this.f16,
            this.f17,
            this.f18,
            this.f19,
            this.f20
         }
      );
   }

   private boolean m687() {
      return this.f1.m17("FunTime") && FunTimeUtil.m6()
         || this.f1.m17("SpookyTime") && FunTimeUtil.m101()
         || this.f1.m17("HolyWorld") && FunTimeUtil.m31();
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (!this.util.m81() && this.mc.currentScreen == null && var1.m189() == 1) {
         int var2 = var1.m580().key();
         boolean var3 = this.f1.m17("FunTime") || this.f1.m17("SpookyTime");
         boolean var4 = this.f1.m17("HolyWorld");
         if (var3) {
            this.m876(var2, this.f8, Items.NETHERITE_SCRAP);
            this.m876(var2, this.f9, Items.SNOWBALL);
            this.m876(var2, this.f10, Items.DRIED_KELP);
            this.m876(var2, this.f11, Items.ENDER_EYE);
            this.m876(var2, this.f12, Items.SUGAR);
            this.m876(var2, this.f13, Items.WIND_CHARGE);
            this.m876(var2, this.f14, Items.FIRE_CHARGE);
            this.m876(var2, this.f15, Items.PHANTOM_MEMBRANE);
         }

         if (var4) {
            this.m876(var2, this.f16, Items.FIRE_CHARGE);
            this.m876(var2, this.f17, Items.BLAZE_ROD);
            this.m876(var2, this.f18, Items.PRISMARINE_SHARD);
            this.m876(var2, this.f19, Items.NETHER_STAR);
            this.m876(var2, this.f20, Items.SNOWBALL);
         }
      }
   }

   private void m876(int var1, KeybindSetting var2, Item var3) {
      if (var2.getKey() > 0 && var2.getKey() == var1) {
         if (this.mc.player.getItemCooldownManager().isCoolingDown(var3.getDefaultStack())) {
            Daamky_2.m467(var2.getName() + " — имеет задержку", Formatting.RED);
         } else {
            int var4 = this.m696(var3);
            if (var4 < 0) {
               Daamky_2.m467(var2.getName() + " — нет в инвентаре", Formatting.RED);
            } else {
               this.m691(var4);
            }
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         this.m116();
         if (this.f7.m6()) {
            if (FunTimeUtil.m6() || FunTimeUtil.m101()) {
               boolean var2 = this.mc
                  .player
                  .getStatusEffects()
                  .stream()
                  .anyMatch(var0 -> var0.getEffectType() == StatusEffects.WEAKNESS && var0.getAmplifier() >= 1 && (double)var0.getDuration() / 20.0 >= 15.0);
               int var3 = this.m696(Items.PHANTOM_MEMBRANE);
               if (var3 >= 0
                  && var2
                  && this.f23 == 0
                  && !this.mc.player.getItemCooldownManager().isCoolingDown(Items.PHANTOM_MEMBRANE.getDefaultStack())
                  && System.currentTimeMillis() - this.f21 > 5000L
                  && this.mc.player.getAbsorptionAmount() <= 3.0F) {
                  this.m691(var3);
                  this.f21 = System.currentTimeMillis();
               }
            }
         }
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (!this.util.m81()) {
         if (var1.m581() instanceof InventoryS2CPacket var2) {
            if (this.f3.m6() && (FunTimeUtil.m6() || FunTimeUtil.m101())) {
               this.m877(var2);
            }
         } else if (var1.m581() instanceof ScreenHandlerSlotUpdateS2CPacket var3
            && this.f22 != null
            && var3.getSyncId() != 0
            && var3.getSyncId() == this.mc.player.currentScreenHandler.syncId
            && var3.getSlot() >= 0
            && var3.getSlot() < this.f22.length) {
            for (int var6 = 0; var6 < this.f22.length; var6++) {
               if (this.f22[var6] == var3.getSlot()) {
                  ((IScreenHandlerSlotUpdateS2CPacket)var3).setSlot(var6);
                  break;
               }
            }
         }
      }
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (!this.util.m81()) {
         if (var1.m581() instanceof ClickSlotC2SPacket var2
            && this.f22 != null
            && var2.syncId() != 0
            && var2.syncId() == this.mc.player.currentScreenHandler.syncId
            && var2.slot() >= 0
            && var2.slot() < this.f22.length) {
            ((IClickSlotC2SPacket)(Object)var2).setSlot((short)this.f22[var2.slot()]);
         }
      }
   }

   private void m877(InventoryS2CPacket var1) {
      try {
         List<ItemStack> var2 = var1.contents();
         int var3 = var2.size() > 36 ? var2.size() - 36 : var2.size();
         this.f22 = null;
         if (var3 < 44 || var1.syncId() == 0) {
            return;
         }

         boolean var4 = var2.subList(0, var3).stream().anyMatch(var1x -> FunTimeUtil.m105(var1x) >= 0 && this.m689(var1x));
         int[] var5 = new int[var3];

         for (int var6 = 0; var6 < var3; var6++) {
            ItemStack var7 = (ItemStack)var2.get(var6);
            int var8 = !var7.isEmpty() && (!var4 || this.m689(var7)) ? FunTimeUtil.m105(var7) : -1;
            var5[var6] = var8 < 0 ? Integer.MAX_VALUE : var8;
         }

         Integer[] var10 = IntStream.range(0, var3).boxed().sorted(Comparator.comparingInt(var1x -> var5[var1x])).toArray(Integer[]::new);
         ArrayList var11 = new ArrayList(var2);
         this.f22 = new int[var3];

         for (int var12 = 0; var12 < var3; var12++) {
            this.f22[var12] = var10[var12];
            var2.set(var12, (ItemStack)var11.get(var10[var12]));
         }
      } catch (Throwable var9) {
         this.f22 = null;
      }
   }

   public void m878(DrawContext var1, HandledScreen<?> var2) {
      if (this.f2.m6() && var2 instanceof GenericContainerScreen var3) {
         DefaultedList var4 = ((GenericContainerScreenHandler)var3.getScreenHandler()).slots;
         if (var4.size() >= 90) {
            int var5 = var4.size() - 36;
            IHandledScreen var6 = (IHandledScreen)var2;
            boolean var7 = false;

            for (int var8 = 0; var8 < var5; var8++) {
               ItemStack var9 = ((Slot)var4.get(var8)).getStack();
               if (FunTimeUtil.m105(var9) >= 0 && this.m689(var9)) {
                  var7 = true;
                  break;
               }
            }

            Slot var16 = null;
            int var17 = Integer.MAX_VALUE;
            long var10 = 0L;

            for (int var12 = 0; var12 < var5; var12++) {
               Slot var13 = (Slot)var4.get(var12);
               ItemStack var14 = var13.getStack();
               int var15 = FunTimeUtil.m105(var14);
               var10 += (long)Math.max(var15, 0) * (long)Math.max(var14.getCount(), 1);
               if ((!var7 || this.m689(var14)) && var15 >= 0 && var15 < var17) {
                  var17 = var15;
                  var16 = var13;
               }
            }

            if (var16 != null) {
               float var18 = (float)((Math.sin((double)(System.currentTimeMillis() % 100000L) / 1000.0 * 10.0) + 1.0) * 0.5);
               int var20 = (int)(25.0F + 175.0F * var18);
               int var22 = var6.getGuiX() + var16.x;
               int var23 = var6.getGuiY() + var16.y;
               var1.fill(var22, var23, var22 + 16, var23 + 16, var20 << 24 | 0xFF00);
            }

            String var19 = var2.getTitle().getString();
            if (var19.contains("Хранилище")) {
               String var21 = var10 >= 1000000000L
                  ? String.format(Locale.US, "%.0fккк", (double)var10 / 1.0E9)
                  : (
                     var10 >= 1000000L
                        ? String.format(Locale.US, "%.0fкк", (double)var10 / 1000000.0)
                        : (
                           var10 >= 1000L
                              ? String.format(Locale.US, "%.0fк", (double)var10 / 1000.0)
                              : String.valueOf(var10)
                        )
                  );
               var1.drawText(this.mc.textRenderer, Text.literal("≈ " + var21), var6.getGuiX() + 8, var6.getGuiY() - 10, 5635925, true);
            }
         }
      }
   }

   public List<Text> m879(ItemStack var1, List<Text> var2) {
      int var3 = FunTimeUtil.m105(var1);
      if (var3 > 0 && var1.getCount() > 1) {
         ArrayList var4 = new ArrayList(var2);

         for (int var5 = 0; var5 < var4.size(); var5++) {
            String var6 = ((Text)var4.get(var5)).getString();
            if (var6.contains("$ Цена") || var6.contains("$ Ценa")) {
               var4.add(
                  var5 + 1,
                  Text.literal("$")
                     .formatted(Formatting.GREEN)
                     .append(Text.literal(" Цена за штуку: ").formatted(Formatting.WHITE))
                     .append(Text.literal(String.format(Locale.US, "%,d", var3)).formatted(Formatting.GREEN))
               );
               return var4;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean m689(ItemStack var1) {
      if (!this.m710(var1)) {
         if (var1.isIn(ItemTags.SWORDS)) {
            if (this.m880(var1, Enchantments.KNOCKBACK) >= 1) {
               return false;
            } else if (this.f5.m20("Острота") && this.m880(var1, Enchantments.SHARPNESS) < 6) {
               return false;
            } else if (this.m882(var1, "нестабильн")) {
               return false;
            } else if (this.f5.m20("Детекция")
               && this.m883(var1, "детекция") < 2) {
               return false;
            } else if (this.f5.m20("Вампиризм")
               && this.m883(var1, "вампиризм") < 2) {
               return false;
            } else {
               return this.f5.m20("Окисление")
                     && this.m883(var1, "окисление") < 2
                  ? false
                  : !this.f5.m20("Яд") || this.m883(var1, "яд") >= 3;
            }
         } else if (var1.isIn(ItemTags.PICKAXES)) {
            if (this.f6.m20("Починка") && this.m880(var1, Enchantments.MENDING) < 1) {
               return false;
            } else if (this.f6.m20("Удача") && this.m883(var1, "удача") < 5) {
               return false;
            } else {
               return this.f6.m20("Эффективность")
                     && this.m883(var1, "эффективность") < 4
                  ? false
                  : !this.f6.m20("Магнит") || this.m882(var1, "магнит");
            }
         } else {
            return true;
         }
      } else if (!this.f4.m20("Защита")
         || this.m880(var1, Enchantments.UNBREAKING) >= 4 && this.m880(var1, Enchantments.PROTECTION) >= 5) {
         if (this.f4.m20("Аншип") && this.m880(var1, Enchantments.THORNS) > 0) {
            return false;
         } else {
            return this.f4.m20("Починка") && this.m880(var1, Enchantments.MENDING) < 1
               ? false
               : !this.f4.m20("Подводная ходьба")
                  || !this.m809(var1)
                  || this.m880(var1, Enchantments.DEPTH_STRIDER) >= 1;
         }
      } else {
         return false;
      }
   }

   private int m880(ItemStack var1, RegistryKey<Enchantment> var2) {
      if (this.mc.world == null) {
         return 0;
      } else {
         try {
            Reference var3 = this.mc.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(var2);
            ItemEnchantmentsComponent var4 = (ItemEnchantmentsComponent)var1.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
            return var4.getLevel(var3);
         } catch (Throwable var5) {
            return 0;
         }
      }
   }

   private boolean m710(ItemStack var1) {
      EquippableComponent var2 = (EquippableComponent)var1.get(DataComponentTypes.EQUIPPABLE);
      return var2 != null && var2.slot().isArmorSlot();
   }

   private boolean m809(ItemStack var1) {
      EquippableComponent var2 = (EquippableComponent)var1.get(DataComponentTypes.EQUIPPABLE);
      return var2 != null && var2.slot() == EquipmentSlot.FEET;
   }

   private String m881(ItemStack var1) {
      return this.mc.player == null
         ? ""
         : var1.getTooltip(TooltipContext.DEFAULT, this.mc.player, TooltipType.BASIC)
            .stream()
            .skip(1L)
            .map(
               var0 -> var0.getString()
                     .replaceAll("§.", "")
                     .toLowerCase(Locale.ROOT)
                     .replaceAll("\\s+", "")
            )
            .collect(Collectors.joining());
   }

   private boolean m882(ItemStack var1, String var2) {
      return this.m881(var1).contains(var2.toLowerCase(Locale.ROOT).replaceAll("\\s+", ""));
   }

   private int m883(ItemStack var1, String var2) {
      String var3 = this.m881(var1);
      String var4 = var2.toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
      int var5 = var3.indexOf(var4);
      if (var5 < 0) {
         return 0;
      } else {
         String var6 = var3.substring(var5 + var4.length());
         int var7 = var6.indexOf(32);
         return FunTimeUtil.m106(var7 > 0 ? var6.substring(0, var7) : var6);
      }
   }

   private int m696(Item var1) {
      for (int var2 = 0; var2 < 36; var2++) {
         if (this.mc.player.getInventory().getStack(var2).isOf(var1)) {
            return var2;
         }
      }

      return -1;
   }

   private void m691(int var1) {
      if (var1 >= 0 && this.f23 == 0) {
         this.f25 = this.mc.player.getInventory().getSelectedSlot();
         if (var1 < 9) {
            this.f24 = var1;
            this.f23 = 1;
         } else {
            this.mc.interactionManager.clickSlot(this.mc.player.playerScreenHandler.syncId, var1, 8, SlotActionType.SWAP, this.mc.player);
            this.m692(8);
            this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
            this.mc.interactionManager.clickSlot(this.mc.player.playerScreenHandler.syncId, var1, 8, SlotActionType.SWAP, this.mc.player);
            this.m692(this.f25);
         }
      }
   }

   private void m116() {
      if (this.f23 != 0) {
         switch (this.f23) {
            case 1:
               this.m692(this.f24);
               this.f23 = 2;
               break;
            case 2:
               this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
               this.mc.player.swingHand(Hand.MAIN_HAND);
               this.f23 = 3;
               break;
            case 3:
               this.m692(this.f25);
               this.f23 = 0;
               this.f24 = -1;
         }
      }
   }

   private void m692(int var1) {
      this.mc.player.getInventory().setSelectedSlot(var1);
      this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(var1));
   }
}
