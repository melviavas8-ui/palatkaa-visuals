package ua.daamky.features.player;

import ua.daamky.events.KeyEvent;
import ua.daamky.events.MovementInputEvent;
import ua.daamky.events.PostMotionEvent;

import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

@NewFunction(
   I0 = "ClickAction",
   I00 = "Использование эндер перла по бинду",
   I000 = Category.PLAYER
)
public class ClickAction extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Тип", "Обычный", "Легитный"
   );
   private final KeybindSetting f2 = new KeybindSetting("Кнопка эндер-жемчуга", 0);
   private int f3 = -1;
   private int key = -1;
   private ClickAction$1 f4 = ClickAction$1.f2;
   private long f5;
   private long f6;
   private boolean f7;
   private boolean f8;
   private boolean f9;
   private boolean f10;
   private boolean f11;
   private boolean f12;
   private boolean f13;
   private boolean f14;

   public ClickAction() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   @Override
   public void onDisable() {
      this.m707();
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (var1.m189() == 1 && !this.util.m81() && this.mc.currentScreen == null && this.mc.interactionManager != null) {
         if (this.f2.m13(var1.m580().key())) {
            this.m115();
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.currentScreen == null && this.mc.interactionManager != null) {
         if (this.f2.m14(this.mc.getWindow().getHandle())) {
            this.m115();
         }

         if (this.f3 != -1) {
            this.m116();
         }
      } else {
         this.m707();
      }
   }

   @EventHandler
   public void m660(MovementInputEvent var1) {
      if (this.f8) {
         var1.m348(0.0F);
         var1.m410(0.0F);
         var1.m4(false);
         var1.m300(false);
         var1.m579(false);
      }
   }

   private void m115() {
      if (this.f3 == -1
         && this.mc.player.currentScreenHandler.getCursorStack().isEmpty()
         && !(this.mc.player.getItemCooldownManager().getCooldownProgress(Items.ENDER_PEARL.getDefaultStack(), 0.0F) > 0.0F)) {
         this.m676();
         if (this.mc.player.getOffHandStack().isOf(Items.ENDER_PEARL)) {
            if (this.f1.m17("Обычный")) {
               this.m1310(Hand.OFF_HAND);
            } else {
               this.m692(40);
            }
         } else {
            PlayerInventory var1 = this.mc.player.getInventory();
            int var2 = this.m1309(var1);
            if (var2 != -1) {
               if (this.f1.m17("Обычный")) {
                  this.m691(var2);
               } else {
                  this.m692(var2);
               }
            }
         }
      }
   }

   private void m691(int var1) {
      PlayerInventory var2 = this.mc.player.getInventory();
      int var3 = var2.getSelectedSlot();
      if (var1 != var3) {
         if (var1 < 9) {
            this.m694(var1);
         } else {
            this.m1311(this.m826(var1), var3, SlotActionType.SWAP);
         }
      }

      this.m676();
      this.m1310(Hand.MAIN_HAND);
      if (var1 != var3) {
         if (var1 < 9) {
            this.m694(var3);
         } else {
            this.m1311(this.m826(var1), var3, SlotActionType.SWAP);
         }
      }
   }

   private void m692(int var1) {
      this.f3 = var1;
      this.key = this.mc.player.getInventory().getSelectedSlot();
      this.f4 = ClickAction$1.f1;
      this.f5 = System.currentTimeMillis();
      this.f6 = this.f5;
      this.f7 = false;
      this.m134();
      this.m135();
      this.f8 = true;
      this.f9 = true;
   }

   private void m116() {
      Hand var1 = this.f3 == 40 ? Hand.OFF_HAND : Hand.MAIN_HAND;
      switch (this.f4) {
         case f1:
            this.m135();
            if (this.mc.player.isSprinting()) {
               this.mc.player.setSprinting(false);
            }

            if (System.currentTimeMillis() - this.f5 >= 50L) {
               this.f4 = ClickAction$1.f2;
               this.f5 = System.currentTimeMillis();
            }
            break;
         case f2:
            if (var1 != Hand.OFF_HAND && this.f3 != this.key) {
               if (this.f3 < 9) {
                  this.m694(this.f3);
               } else {
                  this.m1311(this.m826(this.f3), this.key, SlotActionType.SWAP);
                  this.f7 = true;
               }
            }

            this.f4 = ClickAction$1.f3;
            this.f5 = System.currentTimeMillis();
            break;
         case f3:
            if (System.currentTimeMillis() - this.f5 >= 50L) {
               this.f4 = ClickAction$1.f4;
            }
            break;
         case f4:
            this.m676();
            this.m1310(var1);
            this.f4 = ClickAction$1.f5;
            this.f6 = System.currentTimeMillis();
            break;
         case f5:
            if (System.currentTimeMillis() - this.f6 < 300L) {
               return;
            }

            if (var1 != Hand.OFF_HAND && this.f3 != this.key) {
               if (this.f7) {
                  this.m1311(this.m826(this.f3), this.key, SlotActionType.SWAP);
               } else {
                  this.m694(this.key);
               }
            }

            this.f4 = ClickAction$1.f6;
            this.f5 = System.currentTimeMillis();
            break;
         case f6:
            this.m677();
            this.m707();
            break;
         default:
            this.m707();
      }
   }

   private int m1309(PlayerInventory var1) {
      for (int var2 = 0; var2 < var1.getMainStacks().size(); var2++) {
         if (var1.getStack(var2).isOf(Items.ENDER_PEARL)) {
            return var2;
         }
      }

      return -1;
   }

   private int m826(int var1) {
      return var1 < 9 ? var1 + 36 : var1;
   }

   private void m694(int var1) {
      if (var1 >= 0 && var1 < 9 && var1 != this.mc.player.getInventory().getSelectedSlot()) {
         this.mc.player.getInventory().setSelectedSlot(var1);
      }
   }

   private void m1310(Hand var1) {
      this.mc.interactionManager.interactItem(this.mc.player, var1);
      this.mc.player.swingHand(var1);
   }

   private void m676() {
      // AttackAura removed — no forced look packet
   }

   private void m1311(int var1, int var2, SlotActionType var3) {
      this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, var1, var2, var3, this.mc.player);
   }

   private void m134() {
      Window var1 = this.mc.getWindow();
      this.f10 = InputUtil.isKeyPressed(var1, this.mc.options.forwardKey.getDefaultKey().getCode());
      this.f11 = InputUtil.isKeyPressed(var1, this.mc.options.backKey.getDefaultKey().getCode());
      this.f12 = InputUtil.isKeyPressed(var1, this.mc.options.leftKey.getDefaultKey().getCode());
      this.f13 = InputUtil.isKeyPressed(var1, this.mc.options.rightKey.getDefaultKey().getCode());
      this.f14 = InputUtil.isKeyPressed(var1, this.mc.options.jumpKey.getDefaultKey().getCode());
   }

   private void m135() {
      this.mc.options.forwardKey.setPressed(false);
      this.mc.options.backKey.setPressed(false);
      this.mc.options.leftKey.setPressed(false);
      this.mc.options.rightKey.setPressed(false);
      this.mc.options.jumpKey.setPressed(false);
   }

   private void m677() {
      if (this.f9 && this.mc.getWindow() != null) {
         Window var1 = this.mc.getWindow();
         boolean var2 = InputUtil.isKeyPressed(var1, this.mc.options.forwardKey.getDefaultKey().getCode());
         boolean var3 = InputUtil.isKeyPressed(var1, this.mc.options.backKey.getDefaultKey().getCode());
         boolean var4 = InputUtil.isKeyPressed(var1, this.mc.options.leftKey.getDefaultKey().getCode());
         boolean var5 = InputUtil.isKeyPressed(var1, this.mc.options.rightKey.getDefaultKey().getCode());
         boolean var6 = InputUtil.isKeyPressed(var1, this.mc.options.jumpKey.getDefaultKey().getCode());
         this.mc.options.forwardKey.setPressed(this.f10 && var2);
         this.mc.options.backKey.setPressed(this.f11 && var3);
         this.mc.options.leftKey.setPressed(this.f12 && var4);
         this.mc.options.rightKey.setPressed(this.f13 && var5);
         this.mc.options.jumpKey.setPressed(this.f14 && var6);
         this.f9 = false;
      }
   }

   private void m707() {
      if (this.f9) {
         this.m677();
      }

      this.f3 = -1;
      this.key = -1;
      this.f4 = ClickAction$1.f2;
      this.f5 = 0L;
      this.f6 = 0L;
      this.f7 = false;
      this.f8 = false;
      this.f9 = false;
   }
}
