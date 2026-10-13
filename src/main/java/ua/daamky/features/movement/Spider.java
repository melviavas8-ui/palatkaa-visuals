package ua.daamky.features.movement;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.ModeSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "Spider",
   I00 = "Позволяет ползать по стенам",
   I000 = Category.MOVEMENT
)
public class Spider extends Module {
   private final ModeSetting f1 = new ModeSetting(
      "Мод", "Vanilla", "РВ вода"
   );

   public Spider() {
      this.addSettings(new Setting[]{this.f1});
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (this.mc.player.horizontalCollision) {
            if (this.f1.m17("Vanilla")) {
               this.mc.player.setVelocity(this.mc.player.getVelocity().x, 0.2, this.mc.player.getVelocity().z);
            } else if (this.f1.m17("РВ вода") && this.mc.player.age % 3 == 0) {
               int var2 = this.m107();
               if (var2 != -1) {
                  int var3 = this.mc.player.getInventory().getSelectedSlot();
                  this.mc.player.getInventory().setSelectedSlot(var2);
                  float var4 = this.mc.player.getPitch();
                  this.mc.player.setPitch(90.0F);
                  this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
                  this.mc.player.swingHand(Hand.MAIN_HAND);
                  this.mc.player.setPitch(var4);
                  this.mc.player.getInventory().setSelectedSlot(var3);
                  this.mc.player.setVelocity(this.mc.player.getVelocity().x, 0.4, this.mc.player.getVelocity().z);
               }
            }
         }
      }
   }

   private int m107() {
      for (int var1 = 0; var1 < 9; var1++) {
         if (this.mc.player.getInventory().getStack(var1).getItem() == Items.WATER_BUCKET) {
            return var1;
         }
      }

      return -1;
   }
}
