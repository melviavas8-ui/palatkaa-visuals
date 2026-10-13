package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.Box;

@NewFunction(
   I0 = "AutoTotem",
   I00 = "автататем))",
   I000 = Category.COMBAT
)
public class AutoTotem extends Module {
   private final NumberSetting f1 = new NumberSetting("Порог здоровья", 4.0, 2.0, 8.0, 0.1);
   private final BooleanSetting f2 = new BooleanSetting("Кристаллы", true);
   private final BooleanSetting f3 = new BooleanSetting("TNT", true);
   private final NumberSetting f4 = new NumberSetting("Дистанция до TNT", 8.0, 0.0, 50.0, 0.1);
   private long f5;
   private AutoTotem$1 f6 = AutoTotem$1.f1;
   private boolean f7;
   private boolean f8;
   private boolean f9;
   private boolean f10;
   private boolean f11;
   private boolean f12 = false;
   private int f13 = -1;

   public AutoTotem() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4});
      this.f3.m5(this::m115);
      this.m115();
   }

   @Override
   public void onEnable() {
      this.m708();
      this.f5 = 0L;
   }

   @Override
   public void onDisable() {
      this.m708();
      this.f5 = 0L;
      this.m683();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      this.m115();
      if (this.util.m81() || this.mc.interactionManager == null || this.mc.world == null) {
         this.m708();
      } else if (this.mc.currentScreen != null) {
         this.m708();
      } else {
         if (this.f6 == AutoTotem$1.f1) {
            if (!this.mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
               return;
            }

            if (!this.m1()) {
               return;
            }

            ItemStack var2 = this.mc.player.getOffHandStack();
            int var3 = this.m705();
            if (var3 == -1) {
               return;
            }

            ItemStack var4 = this.mc.player.getInventory().getStack(var3);
            boolean var5 = !var2.isOf(Items.TOTEM_OF_UNDYING) || var2.hasEnchantments() && !var4.hasEnchantments();
            if (var5 && this.m706()) {
               this.m607();
               this.f13 = var3;
               this.f6 = AutoTotem$1.f2;
            }
         } else {
            this.m707();
         }
      }
   }

   private void m115() {
      this.f4.setVisible(this.f3.m6());
   }

   private boolean m1() {
      if (this.mc.player.getItemCooldownManager().isCoolingDown(Items.TOTEM_OF_UNDYING.getDefaultStack())) {
         return false;
      } else {
         float var1 = this.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA) ? 4.0F : 0.0F;
         float var2 = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
         if ((double)var2 < this.f1.getValue() + (double)var1) {
            return true;
         } else {
            return this.f2.m6() && this.m585() ? true : this.f3.m6() && this.m665();
         }
      }
   }

   private boolean m585() {
      Box var1 = this.mc.player.getBoundingBox().expand(5.0);
      List var2 = this.mc
         .world
         .getEntitiesByClass(EndCrystalEntity.class, var1, var1x -> this.mc.player.distanceTo(var1x) < 5.0F && var1x.getY() > this.mc.player.getEyeY());
      return !var2.isEmpty();
   }

   private boolean m665() {
      Box var1 = this.mc.player.getBoundingBox().expand(this.f4.getValue());
      List var2 = this.mc.world.getEntitiesByClass(TntEntity.class, var1, var1x -> (double)this.mc.player.distanceTo(var1x) < this.f4.getValue());
      return !var2.isEmpty();
   }

   private int m705() {
      return this.mc
         .player
         .getInventory()
         .getMainStacks()
         .stream()
         .filter(var0 -> var0.isOf(Items.TOTEM_OF_UNDYING))
         .min(Comparator.comparing(ItemStack::hasEnchantments))
         .map(var1 -> this.mc.player.getInventory().getMainStacks().indexOf(var1))
         .orElse(-1);
   }

   private void m691(int var1) {
      int var2 = this.m11(var1);
      this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, var2, 40, SlotActionType.SWAP, this.mc.player);
   }

   private int m11(int var1) {
      return var1 < 9 ? var1 + 36 : var1;
   }

   private boolean m706() {
      return System.currentTimeMillis() - this.f5 >= 200L;
   }

   private void m707() {
      switch (this.f6) {
         case f1:
         default:
            break;
         case f2:
            this.m682();
            this.f12 = true;
            if (this.mc.player.isSprinting()) {
               this.mc.player.setSprinting(false);
            }

            this.f6 = AutoTotem$1.f3;
            break;
         case f3:
            this.m691(this.f13);
            this.f5 = System.currentTimeMillis();
            this.f6 = AutoTotem$1.f4;
            break;
         case f4:
            this.m683();
            this.f6 = AutoTotem$1.f5;
            break;
         case f5:
            this.m708();
      }
   }

   private void m607() {
      Window var1 = this.mc.getWindow();
      this.f7 = InputUtil.isKeyPressed(var1, this.mc.options.forwardKey.getDefaultKey().getCode());
      this.f8 = InputUtil.isKeyPressed(var1, this.mc.options.backKey.getDefaultKey().getCode());
      this.f9 = InputUtil.isKeyPressed(var1, this.mc.options.leftKey.getDefaultKey().getCode());
      this.f10 = InputUtil.isKeyPressed(var1, this.mc.options.rightKey.getDefaultKey().getCode());
      this.f11 = InputUtil.isKeyPressed(var1, this.mc.options.jumpKey.getDefaultKey().getCode());
      this.f12 = false;
   }

   private void m682() {
      this.mc.options.forwardKey.setPressed(false);
      this.mc.options.backKey.setPressed(false);
      this.mc.options.leftKey.setPressed(false);
      this.mc.options.rightKey.setPressed(false);
      this.mc.options.jumpKey.setPressed(false);
   }

   private void m683() {
      if (this.f12 && this.mc.getWindow() != null) {
         Window var1 = this.mc.getWindow();
         boolean var2 = InputUtil.isKeyPressed(var1, this.mc.options.forwardKey.getDefaultKey().getCode());
         boolean var3 = InputUtil.isKeyPressed(var1, this.mc.options.backKey.getDefaultKey().getCode());
         boolean var4 = InputUtil.isKeyPressed(var1, this.mc.options.leftKey.getDefaultKey().getCode());
         boolean var5 = InputUtil.isKeyPressed(var1, this.mc.options.rightKey.getDefaultKey().getCode());
         boolean var6 = InputUtil.isKeyPressed(var1, this.mc.options.jumpKey.getDefaultKey().getCode());
         this.mc.options.forwardKey.setPressed(this.f7 && var2);
         this.mc.options.backKey.setPressed(this.f8 && var3);
         this.mc.options.leftKey.setPressed(this.f9 && var4);
         this.mc.options.rightKey.setPressed(this.f10 && var5);
         this.mc.options.jumpKey.setPressed(this.f11 && var6);
         this.f12 = false;
      }
   }

   private void m708() {
      if (this.f12) {
         this.m683();
      }

      this.f6 = AutoTotem$1.f1;
      this.f13 = -1;
      this.f12 = false;
   }
}
