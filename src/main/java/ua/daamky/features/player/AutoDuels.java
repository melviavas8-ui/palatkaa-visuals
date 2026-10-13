package ua.daamky.features.player;

import com.mojang.authlib.GameProfile;
import ua.daamky.events.ChatMessageEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

@NewFunction(
   I0 = "AutoDuels",
   I00 = "Автоматически отправляет игрокам приглашения на дуэль",
   I000 = Category.PLAYER
)
public class AutoDuels extends Module {
   private final Pattern f1 = Pattern.compile("^\\w{3,16}$");
   private final ModeSettingBase f2 = new ModeSettingBase(
      "Режим",
      "balls",
      "shield",
      "thorns",
      "bow",
      "totems",
      "nodebuff",
      "classic",
      "cheaterparadise",
      "netherite"
   );
   private final NumberSetting f3 = new NumberSetting(
      "Скорость отправки", 500.0, 300.0, 1000.0, 100.0
   );
   private final BooleanSetting f4 = new BooleanSetting("Играть на деньги", false);
   private final ModeSettingBase f5 = new ModeSettingBase(
      "Монет",
      "10000",
      "100",
      "500",
      "1000",
      "5000",
      "50000",
      "100000"
   );
   private double f6;
   private double f7;
   private double f8;
   private final List<String> f9 = new ArrayList<>();
   private final AutoDuels$1 f10 = new AutoDuels$1();
   private final AutoDuels$1 f11 = new AutoDuels$1();
   private final AutoDuels$1 f12 = new AutoDuels$1();
   private final AutoDuels$1 f13 = new AutoDuels$1();

   public AutoDuels() {
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4, this.f5});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (this.util.m81()) {
         this.toggle();
      } else {
         this.f10.m63();
         this.f11.m63();
         this.f12.m63();
         this.f13.m63();
         this.f9.clear();
         this.f6 = this.mc.player.getX();
         this.f7 = this.mc.player.getY();
         this.f8 = this.mc.player.getZ();
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         this.m115();
      }
   }

   @EventHandler
   public void m780(ChatMessageEvent var1) {
      String var2 = var1.m556().content().getString();
      if (var2.contains("начало")
            && var2.contains("через")
            && var2.contains("секунд!")
         || var2.contains(
            "дуэли » во время поединка запрещено использовать команды"
         )) {
         this.toggle();
      }
   }

   private void m115() {
      List<String> var1 = this.m1283();
      double var2 = Math.sqrt(
         Math.pow(this.f6 - this.mc.player.getX(), 2.0) + Math.pow(this.f7 - this.mc.player.getY(), 2.0) + Math.pow(this.f8 - this.mc.player.getZ(), 2.0)
      );
      if (var2 > 500.0) {
         this.toggle();
      } else {
         this.f6 = this.mc.player.getX();
         this.f7 = this.mc.player.getY();
         this.f8 = this.mc.player.getZ();
         if (this.f11.m14(800L * (long)var1.size())) {
            this.f9.clear();
            this.f11.m63();
         }

         for (String var5 : var1) {
            if (!this.f9.contains(var5) && !var5.equals(this.mc.player.getGameProfile().name()) && this.f10.m14((long)this.f3.getValue())) {
               if (this.f4.m6()) {
                  this.mc.player.networkHandler.sendChatCommand("duel " + var5 + " " + this.f5.m18());
               } else {
                  this.mc.player.networkHandler.sendChatCommand("duel " + var5);
               }

               this.f9.add(var5);
               this.f10.m63();
            }
         }

         if (this.mc.currentScreen != null && this.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler var6) {
            String var8 = this.mc.currentScreen.getTitle().getString();
            if (var8.contains("Выбор набора (1/1)") && this.f12.m14(150L)) {
               this.mc.interactionManager.clickSlot(var6.syncId, this.m715(), 0, SlotActionType.QUICK_MOVE, this.mc.player);
               this.f12.m63();
            } else if (var8.contains("Настройка поединка") && this.f13.m14(150L)) {
               this.mc.interactionManager.clickSlot(var6.syncId, 0, 0, SlotActionType.QUICK_MOVE, this.mc.player);
               this.f13.m63();
            }
         }
      }
   }

   private int m715() {
      String var1 = this.f2.m18();

      return switch (var1) {
         case "shield" -> 0;
         case "thorns" -> 1;
         case "bow" -> 2;
         case "totems" -> 3;
         case "nodebuff" -> 4;
         case "classic" -> 6;
         case "cheaterparadise" -> 7;
         case "netherite" -> 8;
         default -> 5;
      };
   }

   private List<String> m1283() {
      return this.mc
         .player
         .networkHandler
         .getPlayerList()
         .stream()
         .map(PlayerListEntry::getProfile)
         .<String>map(GameProfile::name)
         .filter(var1 -> this.f1.matcher(var1).matches())
         .collect(Collectors.toList());
   }
}
