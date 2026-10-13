package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "AutoPotion",
   I00 = "Кидает взрывные зелья под себя",
   I000 = Category.COMBAT
)
public class AutoPotion extends Module {
   private final MultiChoiceSetting f1 = new MultiChoiceSetting(
      "Бафать",
      "Силу",
      "Скорость",
      "Огнестойкость"
   );
   private final List<Integer> f2 = new ArrayList<>();
   private final List<RegistryEntry<StatusEffect>> f3 = new ArrayList<>();
   private boolean f4 = false;
   private long f5 = 0L;
   private long f6 = 0L;

   public AutoPotion() {
      this.addSettings(new Setting[]{this.f1});
   }

   @Override
   public void onDisable() {
      this.f2.clear();
      this.f3.clear();
      this.f4 = false;
      RotationUtil.m314();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player != null && this.mc.world != null) {
         if (this.mc.currentScreen != null) {
            String var2 = this.mc.currentScreen.getClass().getSimpleName();
            if (var2.contains("Downloading")
               || var2.contains("Receiving")
               || var2.contains("GenericContainerScreen")) {
               return;
            }
         }

         if (!this.mc.player.isGliding() && (!this.mc.player.isUsingItem() || this.mc.player.getActiveItem().getItem() == Items.SHIELD)) {
            if (this.f2.isEmpty() && !this.f4) {
               if (System.currentTimeMillis() - this.f6 < 1000L) {
                  return;
               }

               for (AutoPotion$1 var5 : AutoPotion$1.m667()) {
                  if (this.m671(var5)) {
                     int var6 = this.m672(var5.m669());
                     if (var6 != -1 && !this.f2.contains(var6)) {
                        this.f2.add(var6);
                        this.f3.add(var5.m669());
                     }
                  }
               }
            }

            if (!this.f2.isEmpty()) {
               float var9 = RotationUtil.m411(this.mc.player.getYaw());
               if (RotationUtil.m412(this.mc.player.getPitch()) >= 85.0F) {
                  int var11 = this.f2.remove(0);
                  int var13 = this.mc.player.getInventory().getSelectedSlot();
                  float var15 = this.mc.player.getPitch();
                  float var16 = this.mc.player.getYaw();
                  this.mc.player.getInventory().setSelectedSlot(var11);
                  this.mc.player.setPitch(90.0F);
                  this.mc.player.setYaw(var9);
                  this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
                  this.mc.player.swingHand(Hand.MAIN_HAND);
                  this.mc.player.getInventory().setSelectedSlot(var13);
                  this.mc.player.setPitch(var15);
                  this.mc.player.setYaw(var16);
                  if (this.f2.isEmpty()) {
                     this.f4 = true;
                     this.f5 = System.currentTimeMillis();
                  }
               }

               RotationUtil.m406(new RotationVec(var9, 90.0F), RotationMode.f3, 360.0F, 360.0F, 360.0F);
            } else {
               if (this.f4) {
                  float var8 = RotationUtil.m411(this.mc.player.getYaw());
                  RotationUtil.m406(new RotationVec(var8, 90.0F), RotationMode.f3, 360.0F, 360.0F, 360.0F);
                  boolean var10 = true;

                  for (RegistryEntry var14 : this.f3) {
                     if (!this.mc.player.hasStatusEffect(var14)) {
                        var10 = false;
                        break;
                     }
                  }

                  if (var10 || System.currentTimeMillis() - this.f5 > 1000L) {
                     this.f4 = false;
                     this.f3.clear();
                     this.f6 = System.currentTimeMillis();
                     RotationUtil.m314();
                  }
               }
            }
         }
      }
   }

   private boolean m671(AutoPotion$1 var1) {
      return this.f1.m20(var1.m18()) && !this.mc.player.hasStatusEffect(var1.m669());
   }

   private int m672(RegistryEntry<StatusEffect> var1) {
      for (int var2 = 0; var2 < 9; var2++) {
         ItemStack var3 = this.mc.player.getInventory().getStack(var2);
         if (var3.getItem() == Items.SPLASH_POTION) {
            PotionContentsComponent var4 = (PotionContentsComponent)var3.get(DataComponentTypes.POTION_CONTENTS);
            if (var4 != null) {
               for (StatusEffectInstance var6 : var4.getEffects()) {
                  if (var6.getEffectType() == var1) {
                     return var2;
                  }
               }
            }
         }
      }

      return -1;
   }
}
