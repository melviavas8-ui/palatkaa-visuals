package ua.daamky.features.player;

import ua.daamky.events.KeyEvent;
import ua.daamky.events.MovementInputEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.MovementUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "ElytraHelper",
   I00 = "Помощник свапа элитры и полета",
   I000 = Category.PLAYER
)
public class ElytraHelper extends Module {
   private static final int f1 = 6;
   private final KeybindSetting f2 = new KeybindSetting("Кнопка свапа", 0);
   private final ModeSettingBase f3 = new ModeSettingBase(
      "Режим свапа", "Legit", "Packet"
   );
   private final BooleanSetting f4 = new BooleanSetting("Надеть когда падаешь", false);
   private final KeybindSetting f5 = new KeybindSetting("Кнопка феерверка", 0);
   private final BooleanSetting f8 = new BooleanSetting("Matrix bypass", true);
   private boolean f9;
   private boolean f10;
   private boolean f11;
   private boolean f12;
   private int f13 = -1;
   private int f14;
   private boolean f15;
   private int f16;

   public ElytraHelper() {
      this.addSettings(new Setting[]{this.f2, this.f3, this.f4, this.f5, this.f8});
      this.f4.m5(() -> this.f4.setVisible(this.f2.getKey() != 0));
      this.f3.m5(this::m698);
      this.m698();
   }

   @Override
   public void onDisable() {
      this.f9 = false;
      this.f13 = -1;
      this.f14 = 0;
      this.f15 = false;
      this.f10 = false;
      this.f11 = false;
      this.f12 = false;
      this.f16 = 0;
   }

   public boolean m687() {
      return this.f9 || this.f13 != -1;
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (var1.m189() == 1 && !this.util.m81() && this.mc.currentScreen == null && this.mc.interactionManager != null) {
         if (this.f2.m13(var1.m580().key())) {
            this.m116();
         }

         if (this.f5.m13(var1.m580().key())) {
            this.m676();
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.currentScreen == null && this.mc.interactionManager != null) {
         if (this.f2.m14(this.mc.getWindow().getHandle())) {
            this.m116();
         }

         if (this.f5.m14(this.mc.getWindow().getHandle())) {
            this.m676();
         }

         if (this.f16 > 0) {
            this.f16--;
         }

         if (this.f9 && this.m628()) {
            this.m707();
            this.f9 = false;
         }

         if (this.f13 != -1 && this.m628()) {
            this.m677();
         }

         if (this.mc.player.isGliding()) {
            this.f12 = true;
         }

         if (this.f4.m6()) {
            this.m607();
         }
      } else {
         this.f9 = false;
         this.m699();
      }
   }

   @EventHandler
   public void m660(MovementInputEvent var1) {
      if (this.m629() && this.f16 > 0) {
         var1.m348(0.0F);
         var1.m410(0.0F);
         var1.m4(false);
         var1.m300(false);
         var1.m579(false);
      }
   }

   public void m116() {
      PlayerInventory var1 = this.mc.player.getInventory();
      boolean var2 = this.m1312(var1, Items.ELYTRA) != -1;
      boolean var3 = this.m1309(var1) != -1;
      if (var2 || var3) {
         if (this.f3.m17("Packet")) {
            this.m707();
         } else {
            this.f16 = 2;
            this.f9 = true;
         }
      }
   }

   private boolean m665() {
      return this.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
   }

   public void m676() {
      if (!this.f9 && this.f13 == -1 && !(this.mc.player.getItemCooldownManager().getCooldownProgress(Items.FIREWORK_ROCKET.getDefaultStack(), 0.0F) > 0.0F)) {
         if (this.mc.player.getOffHandStack().isOf(Items.FIREWORK_ROCKET)) {
            this.f13 = 40;
            this.f14 = 0;
            this.f15 = true;
            this.f16 = 2;
         } else {
            PlayerInventory var1 = this.mc.player.getInventory();
            int var2 = this.m1312(var1, Items.FIREWORK_ROCKET);
            if (var2 != -1) {
               this.f13 = var2;
               this.f14 = var2 == var1.getSelectedSlot() ? 1 : 0;
               this.f15 = false;
               this.f16 = 2;
            }
         }
      }
   }

   private void m677() {
      if (this.f15) {
         this.mc.interactionManager.interactItem(this.mc.player, Hand.OFF_HAND);
         this.mc.player.swingHand(Hand.OFF_HAND);
         this.m699();
      } else {
         switch (this.f14) {
            case 0:
               this.m1311(this.m1009(this.f13), 0, SlotActionType.PICKUP);
               this.m1311(45, 0, SlotActionType.PICKUP);
               this.m1311(this.m1009(this.f13), 0, SlotActionType.PICKUP);
               this.f14 = 1;
               this.f16 = 2;
               break;
            case 1:
               this.mc.interactionManager.interactItem(this.mc.player, Hand.OFF_HAND);
               this.mc.player.swingHand(Hand.OFF_HAND);
               this.f14 = 2;
               this.f16 = 2;
               break;
            case 2:
               this.m1311(this.m1009(this.f13), 0, SlotActionType.PICKUP);
               this.m1311(45, 0, SlotActionType.PICKUP);
               this.m1311(this.m1009(this.f13), 0, SlotActionType.PICKUP);
               this.m699();
               break;
            default:
               this.m699();
         }
      }
   }

   private void m707() {
      if (this.mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
         PlayerInventory var1 = this.mc.player.getInventory();
         int var2 = this.m665() ? this.m1309(var1) : this.m1312(var1, Items.ELYTRA);
         if (var2 != -1) {
            this.m692(var2);
         }
      }
   }

   private void m607() {
      if (this.mc.player.isOnGround() && !this.f11 && this.f12 && this.m665()) {
         PlayerInventory var1 = this.mc.player.getInventory();
         int var2 = this.m1309(var1);
         if (var2 != -1) {
            this.m692(var2);
         } else {
            int var3 = this.m1313(var1);
            if (var3 != -1) {
               this.m693(var3);
            }
         }

         this.f12 = false;
      }

      this.f11 = this.mc.player.isOnGround();
   }

   private int m1309(PlayerInventory var1) {
      Item[] var2 = new Item[]{
         Items.NETHERITE_CHESTPLATE,
         Items.DIAMOND_CHESTPLATE,
         Items.IRON_CHESTPLATE,
         Items.CHAINMAIL_CHESTPLATE,
         Items.GOLDEN_CHESTPLATE,
         Items.LEATHER_CHESTPLATE
      };

      for (Item var6 : var2) {
         int var7 = this.m1312(var1, var6);
         if (var7 != -1) {
            return var7;
         }
      }

      return -1;
   }

   private int m1312(PlayerInventory var1, Item var2) {
      for (int var3 = 0; var3 < var1.getMainStacks().size(); var3++) {
         if (var1.getStack(var3).isOf(var2)) {
            return var3;
         }
      }

      return -1;
   }

   private int m1313(PlayerInventory var1) {
      for (int var2 = 0; var2 < var1.getMainStacks().size(); var2++) {
         if (var1.getStack(var2).isEmpty()) {
            return var2;
         }
      }

      return -1;
   }

   private int m1009(int var1) {
      return var1 < 9 ? var1 + 36 : var1;
   }

   private void m692(int var1) {
      int var2 = this.m1009(var1);
      this.m1311(var2, 0, SlotActionType.PICKUP);
      this.m1311(6, 0, SlotActionType.PICKUP);
      this.m1311(var2, 0, SlotActionType.PICKUP);
   }

   private void m693(int var1) {
      int var2 = this.m1009(var1);
      this.m1311(6, 0, SlotActionType.PICKUP);
      this.m1311(var2, 0, SlotActionType.PICKUP);
   }

   private void m1311(int var1, int var2, SlotActionType var3) {
      this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, var1, var2, var3, this.mc.player);
   }

   private boolean m628() {
      return !this.m629() || !MovementUtil.m91();
   }

   private boolean m629() {
      return this.f3.m17("Legit") && this.f8.m6();
   }

   private void m698() {
      this.f8.setVisible(this.f3.m17("Legit"));
   }

   private void m699() {
      this.f13 = -1;
      this.f14 = 0;
      this.f15 = false;
   }
}
