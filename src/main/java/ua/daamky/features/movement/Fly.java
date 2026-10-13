package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PiercingWeaponComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "Fly",
   I00 = "Свапает на копье и делает выпад",
   I000 = Category.MOVEMENT
)
public class Fly extends Module {
   private static final long f1 = 200L;
   private final ModeSettingBase f2 = new ModeSettingBase(
      "Режим", "Полёт на копье"
   );
   private int f3 = -1;
   private long f4;

   public Fly() {
      this.addSettings(new Setting[]{this.f2});
   }

   @Override
   public void onEnable() {
      this.f3 = this.mc.player != null ? this.mc.player.getInventory().getSelectedSlot() : -1;
      this.f4 = 0L;
   }

   @Override
   public void onDisable() {
      if (this.mc.player != null && this.f3 >= 0 && this.f3 < 9) {
         this.mc.player.getInventory().setSelectedSlot(this.f3);
      }

      this.f3 = -1;
      this.f4 = 0L;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.interactionManager != null && this.mc.currentScreen == null) {
         if (this.f2.m17("Полёт на копье") && !this.mc.player.isUsingItem()) {
            if (System.currentTimeMillis() - this.f4 >= 200L) {
               int var2 = this.m107();
               if (var2 != -1) {
                  ItemStack var3 = this.mc.player.getInventory().getStack(var2);
                  PiercingWeaponComponent var4 = (PiercingWeaponComponent)var3.get(DataComponentTypes.PIERCING_WEAPON);
                  if (var4 != null) {
                     int var5 = this.mc.player.getInventory().getSelectedSlot();
                     this.mc.player.getInventory().setSelectedSlot(var2);
                     this.mc.interactionManager.attackWithPiercingWeapon(var4);
                     this.mc.player.swingHand(Hand.MAIN_HAND);
                     if (var5 != var2) {
                        this.mc.player.getInventory().setSelectedSlot(var5);
                     }

                     this.f4 = System.currentTimeMillis();
                  }
               }
            }
         }
      }
   }

   private int m107() {
      for (int var1 = 0; var1 < 9; var1++) {
         ItemStack var2 = this.mc.player.getInventory().getStack(var1);
         if (var2.isIn(ItemTags.SPEARS)) {
            return var1;
         }
      }

      return -1;
   }
}
