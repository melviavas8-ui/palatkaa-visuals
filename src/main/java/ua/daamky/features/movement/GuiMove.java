package ua.daamky.features.movement;

import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.MovementUtil;
import ua.daamky.utils.SilentPacketUtil;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;

@NewFunction(
   I0 = "GuiMove",
   I00 = "Позволяет двигаться с открытым инвентарём",
   I000 = Category.MOVEMENT
)
public class GuiMove extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Mode",
      "Packet",
      "Grim full",
      "Funtime"
   );
   private final List<Packet<?>> f2 = new CopyOnWriteArrayList<>();
   private final Queue<Runnable> f3 = new LinkedList<>();
   private boolean f4 = false;
   private boolean enabled = false;
   private final Deque<Packet<?>> f5 = new ArrayDeque<>();
   private boolean f6 = false;

   public GuiMove() {
      this.addSettings(new Setting[]{this.f1});
   }

   private KeyBinding[] m1270() {
      return new KeyBinding[]{
         this.mc.options.forwardKey,
         this.mc.options.backKey,
         this.mc.options.rightKey,
         this.mc.options.leftKey,
         this.mc.options.jumpKey,
         this.mc.options.sprintKey
      };
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (this.mc.player == null) {
         this.m707();
      } else if (this.f1.m17("Funtime")) {
         this.m116();
      } else {
         if (!this.f3.isEmpty()) {
            Runnable var2 = this.f3.poll();
            if (var2 != null) {
               var2.run();
            }
         }

         if (!this.enabled) {
            if (this.mc.currentScreen != null
               && !(this.mc.currentScreen instanceof ChatScreen)
               && !(this.mc.currentScreen instanceof SignEditScreen)
               && !(this.mc.currentScreen instanceof AnvilScreen)) {
               for (KeyBinding var10 : this.m1270()) {
                  if (InputUtil.isKeyPressed(this.mc.getWindow(), var10.getDefaultKey().getCode())) {
                     var10.setPressed(true);
                  }
               }
            }
         } else {
            for (KeyBinding var5 : this.m1270()) {
               var5.setPressed(false);
            }
         }
      }
   }

   private void m116() {
      if (!this.f6
         && this.mc.currentScreen != null
         && !(this.mc.currentScreen instanceof ChatScreen)
         && !(this.mc.currentScreen instanceof SignEditScreen)
         && !(this.mc.currentScreen instanceof AnvilScreen)
         && !(this.mc.currentScreen instanceof CreativeInventoryScreen)) {
         for (KeyBinding var4 : this.m1270()) {
            var4.setPressed(InputUtil.isKeyPressed(this.mc.getWindow(), var4.getDefaultKey().getCode()));
         }
      }

      if (!MovementUtil.m91() && !this.f5.isEmpty()) {
         SilentPacketUtil.m94(this.f5.pollFirst());
         if (this.f5.isEmpty() && this.mc.currentScreen == null) {
            SilentPacketUtil.m94(new CloseHandledScreenC2SPacket(this.mc.player.currentScreenHandler.syncId));
         }
      }
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (this.mc.player != null && !SilentPacketUtil.f1) {
         if (this.f1.m17("Funtime")) {
            if (var1.m581() instanceof ClickSlotC2SPacket var5 && this.mc.currentScreen instanceof InventoryScreen && MovementUtil.m91()) {
               this.f5.add(var5);
               var1.m29();
            }

            if (var1.m581() instanceof CloseHandledScreenC2SPacket) {
               if (MovementUtil.m91() && this.mc.currentScreen instanceof InventoryScreen) {
                  var1.m29();
               }

               this.f6 = false;
            }
         } else if (!this.f1.m17("Packet")) {
            if (var1.m581() instanceof ClickSlotC2SPacket var2 && this.mc.currentScreen instanceof InventoryScreen && this.m534() && this.m706()) {
               this.f2.add(var2);
               var1.m29();
               return;
            }

            if (var1.m581() instanceof CloseHandledScreenC2SPacket var3 && var3.getSyncId() == 0 && !this.f2.isEmpty() && !this.f4) {
               this.f2.add(var3);
               var1.m29();
               this.m676();
            }
         }
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (this.mc.player != null && this.f1.m17("Funtime")) {
         if (var1.m581() instanceof OpenScreenS2CPacket) {
            this.f6 = true;
         }

         if (var1.m581() instanceof CloseScreenS2CPacket) {
            this.f6 = false;
         }
      }
   }

   private void m676() {
      this.f4 = true;
      if (this.f1.m17("Grim full")) {
         this.f3.add(() -> this.enabled = true);
         this.f3.add(() -> {
            for (Packet var2 : this.f2) {
               SilentPacketUtil.m94(var2);
            }

            this.f2.clear();
            this.f4 = false;
            this.enabled = false;
            this.m134();
         });
      }
   }

   private void m134() {
      if (this.mc.getWindow() != null) {
         for (KeyBinding var4 : this.m1270()) {
            var4.setPressed(InputUtil.isKeyPressed(this.mc.getWindow(), var4.getDefaultKey().getCode()));
         }
      }
   }

   private boolean m534() {
      return this.mc.player != null && this.mc.player.input != null && this.mc.player.input.getMovementInput().lengthSquared() > 1.0E-7F;
   }

   private boolean m706() {
      return this.mc.player != null && this.mc.player.currentScreenHandler != null && this.mc.player.currentScreenHandler.slots.size() >= 27;
   }

   private void m707() {
      this.f2.clear();
      this.f3.clear();
      this.f4 = false;
      this.enabled = false;
      this.f5.clear();
      this.f6 = false;
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.m707();
   }
}
