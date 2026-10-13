package ua.daamky.features.misc;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.Daamky_2;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.Formatting;

@NewFunction(
   I0 = "MineHelper",
   I00 = "Меняет кирку при низкой прочности",
   I000 = Category.MISC
)
public class MineHelper extends Module {
   private final NumberSetting f1 = new NumberSetting("Прочность %", 10.0, 1.0, 70.0, 1.0);
   private final BooleanSetting f2 = new BooleanSetting("Авто замена", true);
   private final BooleanSetting f3 = new BooleanSetting("Уведомления", true);
   private long f4;

   public MineHelper() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3});
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         ItemStack var2 = this.mc.player.getMainHandStack();
         if (this.m710(var2)) {
            if (!(this.m1314(var2) >= this.f1.getValue())) {
               if (!this.f2.m6() || !this.m689(var2)) {
                  if (this.f3.m6() && System.currentTimeMillis() - this.f4 > 1500L) {
                     Daamky_2.m467(
                        "Кирка почти сломана, замены нет!", Formatting.RED
                     );
                     this.f4 = System.currentTimeMillis();
                  }
               }
            }
         }
      }
   }

   private boolean m689(ItemStack var1) {
      int var2 = -1;
      double var3 = this.m1314(var1);

      for (int var5 = 0; var5 < 9; var5++) {
         ItemStack var6 = this.mc.player.getInventory().getStack(var5);
         if (this.m710(var6)) {
            double var7 = this.m1314(var6);
            if (var7 > var3) {
               var3 = var7;
               var2 = var5;
            }
         }
      }

      if (var2 == -1) {
         return false;
      } else {
         this.mc.player.getInventory().setSelectedSlot(var2);
         this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.mc.player.getInventory().getSelectedSlot()));
         if (this.f3.m6() && System.currentTimeMillis() - this.f4 > 1500L) {
            Daamky_2.m467("Заменил кирку", Formatting.GREEN);
            this.f4 = System.currentTimeMillis();
         }

         return true;
      }
   }

   private boolean m710(ItemStack var1) {
      return var1 != null
         && !var1.isEmpty()
         && var1.isDamageable()
         && Registries.ITEM.getId(var1.getItem()).getPath().endsWith("_pickaxe");
   }

   private double m1314(ItemStack var1) {
      return var1.getMaxDamage() <= 0 ? 100.0 : (double)(var1.getMaxDamage() - var1.getDamage()) / (double)var1.getMaxDamage() * 100.0;
   }
}
