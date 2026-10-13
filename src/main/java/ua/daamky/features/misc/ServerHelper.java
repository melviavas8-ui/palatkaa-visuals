package ua.daamky.features.misc;

import ua.daamky.events.KeyEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.features.movement.Sprint;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;

@NewFunction(
   I0 = "ServerHelper",
   I00 = "Помощник для ReallyWorld: быстрый юз предметов",
   I000 = Category.MISC
)
public class ServerHelper extends Module {
   private final KeybindSetting f1 = new KeybindSetting("Анти-полёт", 71);
   private final KeybindSetting f2 = new KeybindSetting("Зелье Гринча", -1);
   private final KeybindSetting f3 = new KeybindSetting("AutoShift", -1);
   private final KeybindSetting f4 = new KeybindSetting("Снежок", -1);
   private final KeybindSetting f5 = new KeybindSetting("Трапка", -1);
   private final BooleanSetting f6 = new BooleanSetting("Auto /fix all", false);
   private boolean f7;
   private boolean f8;
   private boolean f9;
   private boolean f10;
   private boolean f11;
   private boolean f12;
   private int f13;

   public ServerHelper() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4, this.f5, this.f6});
   }

   @EventHandler
   public void m781(KeyEvent var1) {
      if (this.mc.currentScreen == null && var1.m189() == 1) {
         int var2 = var1.m580().key();
         if (var2 == this.f1.getKey()) {
            this.f7 = true;
         }

         if (var2 == this.f2.getKey()) {
            this.f8 = true;
         }

         if (var2 == this.f3.getKey()) {
            this.f9 = true;
         }

         if (var2 == this.f4.getKey()) {
            this.f10 = true;
         }

         if (var2 == this.f5.getKey()) {
            this.f11 = true;
         }
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (this.f7) {
            this.f7 = false;
            this.m1300(this::m809);
         }

         if (this.f8) {
            this.f8 = false;
            this.m1300(this::m689);
         }

         if (this.f10) {
            this.f10 = false;
            this.m1300(this::m710);
         }

         if (this.f9) {
            this.f9 = false;
            this.m676();
         }

         if (this.f11) {
            this.f11 = false;
            this.m134();
         }

         this.m115();
      }
   }

   private boolean m689(ItemStack var1) {
      return var1.isOf(Items.SPLASH_POTION) && var1.getName().getString().toLowerCase().contains("гринч");
   }

   private boolean m710(ItemStack var1) {
      return var1.isOf(Items.SPLASH_POTION) && var1.getName().getString().toLowerCase().contains("снежок");
   }

   private boolean m809(ItemStack var1) {
      return var1.isOf(Items.FIREWORK_STAR);
   }

   private boolean m810(ItemStack var1) {
      return var1.isOf(Items.HEART_OF_THE_SEA);
   }

   private int m1299(Predicate<ItemStack> var1, int var2, int var3) {
      for (int var4 = var2; var4 < var3; var4++) {
         if (var1.test(this.mc.player.getInventory().getStack(var4))) {
            return var4;
         }
      }

      return -1;
   }

   private void m115() {
      if (!this.f6.m6()) {
         this.f12 = this.m1();
      } else {
         boolean var1 = this.m1();
         if (this.f12 && !var1 && this.mc.player.age >= this.f13) {
            this.mc.player.networkHandler.sendChatCommand("fix all");
            this.f13 = this.mc.player.age + 40;
         }

         this.f12 = var1;
      }
   }

   private boolean m1() {
      return this.mc.world != null
         && this.mc.world.getPlayers().stream().filter(var1 -> var1 != this.mc.player).anyMatch(var1 -> var1.squaredDistanceTo(this.mc.player) < 100.0);
   }

   private void m676() {
      if (this.mc.options != null) {
         this.mc.options.sneakKey.setPressed(true);
         new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
               if (ServerHelper.this.mc.options != null) {
                  ServerHelper.this.mc.options.sneakKey.setPressed(false);
               }
            }
         }, 150L);
      }
   }

   private void m134() {
      if (this.m810(this.mc.player.getMainHandStack())) {
         this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
      } else if (this.m810(this.mc.player.getOffHandStack())) {
         this.mc.interactionManager.interactItem(this.mc.player, Hand.OFF_HAND);
      } else {
         int var1 = this.m1299(this::m810, 0, 9);
         if (var1 != -1) {
            int var2 = this.mc.player.getInventory().getSelectedSlot();
            this.mc.player.getInventory().setSelectedSlot(var1);
            this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(var1));
            this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
            this.mc.player.getInventory().setSelectedSlot(var2);
            this.mc.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(var2));
         }
      }
   }

   private void m1300(Predicate<ItemStack> var1) {
      if (this.mc.getNetworkHandler() != null && this.mc.interactionManager != null) {
         if (var1.test(this.mc.player.getOffHandStack())) {
            this.m135();
         } else {
            int var2 = this.m1299(var1, 0, 9);
            if (var2 != -1) {
               boolean var5 = this.m706();
               this.m1301(this.mc.player.currentScreenHandler.syncId, 45, var2);
               this.m135();
               this.m1301(this.mc.player.currentScreenHandler.syncId, 45, var2);
               if (var5) {
                  this.m707();
               }
            } else {
               int var3 = this.m1299(var1, 9, 45);
               if (var3 != -1) {
                  boolean var4 = this.m706();
                  this.m1301(0, var3, 40);
                  this.m135();
                  this.m1301(0, var3, 40);
                  if (var4) {
                     this.m707();
                  }
               }
            }
         }
      }
   }

   private void m135() {
      this.mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, true, false)));
      this.mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
   }

   private void m1301(int var1, int var2, int var3) {
      this.mc.interactionManager.clickSlot(var1, var2, var3, SlotActionType.SWAP, this.mc.player);
      this.mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
   }

   private boolean m706() {
      if (!this.mc.player.isSprinting()) {
         return false;
      } else {
         this.mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
         this.mc.player.setSprinting(false);
         this.mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(this.mc.player, Mode.STOP_SPRINTING));
         Sprint var1 = ModuleManager.getModule(Sprint.class);
         if ((var1 == null || !var1.isEnabled()) && this.mc.options != null) {
            this.mc.options.sprintKey.setPressed(false);
         }

         return true;
      }
   }

   private void m707() {
      this.mc.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(this.mc.player.input.playerInput));
   }
}
