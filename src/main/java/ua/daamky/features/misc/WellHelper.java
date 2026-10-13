package ua.daamky.features.misc;

import ua.daamky.events.ChatMessageEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.Daamky_2;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Formatting;

@NewFunction(
   I0 = "WellHelper",
   I00 = "Проверяет элитры у ближних игроков через invsee",
   I000 = Category.MISC
)
public class WellHelper extends Module {
   private final BooleanSetting f1 = new BooleanSetting("Invsee элитры", true);
   private final List<String> f2 = new ArrayList<>();
   private String f3;
   private boolean f4;
   private boolean enabled;
   private int f5;
   private int f6;
   private static final Pattern f7 = Pattern.compile("([a-zA-Z0-9_]{3,16})\\s+\\d+");

   public WellHelper() {
      this.addSettings(new Setting[]{this.f1});
   }

   @EventHandler
   public void m780(ChatMessageEvent var1) {
      if (!this.util.m81() && this.f1.m6()) {
         String var2 = var1.m556().content().getString();
         if (var2.toLowerCase().contains("игроки рядом")) {
            this.f2.clear();
            this.f3 = null;
            this.f4 = false;
            this.enabled = true;
            this.f6 = 6;
         } else {
            if (this.enabled) {
               Matcher var3 = f7.matcher(var2);
               if (var3.find()) {
                  String var4 = var3.group(1);
                  if (!var4.equalsIgnoreCase(this.mc.player.getName().getString()) && !this.f2.contains(var4)) {
                     this.f2.add(var4);
                  }

                  this.f6 = 6;
               }
            }
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.f1.m6()) {
         if (this.enabled) {
            if (--this.f6 <= 0) {
               this.enabled = false;
               if (!this.f2.isEmpty()) {
                  this.f5 = 10;
               }
            }
         } else if (this.f5 > 0) {
            if (--this.f5 == 0 && !this.f2.isEmpty() && !this.f4) {
               this.m115();
            }
         } else {
            if (this.f4 && this.f3 != null && this.mc.currentScreen instanceof GenericContainerScreen var2) {
               boolean var8 = false;
               int var4 = ((GenericContainerScreenHandler)var2.getScreenHandler()).getRows() * 9;

               for (int var5 = 0; var5 < var4; var5++) {
                  Slot var6 = (Slot)((GenericContainerScreenHandler)var2.getScreenHandler()).slots.get(var5);
                  ItemStack var7 = var6.getStack();
                  if (var7.getItem() == Items.ELYTRA) {
                     var8 = true;
                     break;
                  }
               }

               if (var8) {
                  Daamky_2.m467(this.f3 + " имеет ЭЛИТРЫ!", Formatting.LIGHT_PURPLE);
               }

               this.mc.player.closeHandledScreen();
               this.f3 = null;
               this.f4 = false;
               if (!this.f2.isEmpty()) {
                  this.f5 = 6;
               }
            }
         }
      }
   }

   private void m115() {
      if (!this.f2.isEmpty() && this.mc.getNetworkHandler() != null) {
         this.f3 = this.f2.remove(0);
         this.f4 = true;
         this.mc.getNetworkHandler().sendChatCommand("invsee " + this.f3);
      }
   }
}
