package ua.daamky.features.movement;

import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.events.SlowdownEvent;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.SilentPacketUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

@NewFunction(
   I0 = "NoSlow",
   I00 = "Убирает замедления от использования предметов и инвентаря",
   I000 = Category.MOVEMENT
)
public class NoSlow extends Module {
   private static NoSlow f1;
   public static final ModeSettingBase f2 = new ModeSettingBase(
      "Режим",
      "Vanilla",
      "Grim",
      "ReallyWorld"
   );
   private int key = 0;
   private int f3 = 0;
   private boolean f4 = false;
   private boolean f5;
   private boolean f6;
   private static final long f7 = 15L;
   private static final long f8 = 90L;
   private static final long f9 = 40L;
   private static final long f10 = 10L;
   private static final float f11 = 0.09F;
   private static final float f12 = 0.04F;
   private static final float f13 = 20.0F;
   private static final float f14 = 1000.0F;
   private final List<Packet<?>> f15 = new ArrayList<>();
   private int f16;
   private long f17;
   private long f18;
   public boolean f19;

   public NoSlow() {
      this.addSettings(new Setting[]{f2});
      f1 = this;
   }

   private KeyBinding[] m1272() {
      return this.mc.options == null
         ? new KeyBinding[0]
         : new KeyBinding[]{
            this.mc.options.forwardKey,
            this.mc.options.backKey,
            this.mc.options.leftKey,
            this.mc.options.rightKey,
            this.mc.options.jumpKey,
            this.mc.options.sprintKey
         };
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         KeyBinding[] var2 = this.m1272();
         if (this.m752()) {
            this.m1275(var2);
            if (this.f16 > 0) {
               this.f16--;
            }
         } else if (System.currentTimeMillis() < this.f18) {
            this.m1275(var2);
         } else if (!(this.mc.currentScreen instanceof ChatScreen)
            && !(this.mc.currentScreen instanceof SignEditScreen)
            && !(this.mc.currentScreen instanceof AnvilScreen)) {
            if (f2.m17("Grim") && this.mc.currentScreen instanceof GenericContainerScreen) {
               this.m1275(var2);
            } else if (this.mc.currentScreen instanceof InventoryScreen && this.m665()) {
               this.m1274(var2);
            }
         } else {
            this.m1275(var2);
         }

         if (!f2.m17("Grim") && !f2.m17("ReallyWorld")) {
            if (this.mc.player.getActiveHand() != Hand.MAIN_HAND && this.mc.player.getActiveHand() != Hand.OFF_HAND) {
               this.key = 0;
            } else if (this.mc.player.isUsingItem()) {
               this.key++;
            } else {
               this.key = 0;
            }

            this.m683();
         } else {
            if (!this.mc.player.isUsingRiptide()) {
               if (this.mc.player.isUsingItem()) {
                  this.key++;
               } else {
                  this.key = 0;
                  this.f3 = 0;
               }
            }

            this.m682();
         }
      }
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (!this.util.m81()) {
         if (!SilentPacketUtil.f1) {
            Packet var2 = var1.m581();
            if (var2 instanceof ClickSlotC2SPacket && this.m534() && this.mc.currentScreen instanceof InventoryScreen && this.m665()) {
               this.f15.add(var2);
               var1.m29();
            } else if (var2 instanceof CloseHandledScreenC2SPacket
               && this.mc.currentScreen instanceof InventoryScreen
               && this.m534()
               && !this.f15.isEmpty()
               && this.m665()) {
               this.f19 = true;
               new Thread(this::m677, "NoSlow packet replay").start();
               var1.m29();
            }
         }
      }
   }

   @EventHandler
   public void m1273(SlowdownEvent var1) {
      if (!this.util.m81()) {
         if (f2.m17("Vanilla")) {
            var1.setCancelled(true);
         } else if (f2.m17("Grim") || f2.m17("ReallyWorld")) {
            int[] var2 = new int[]{2, 2, 3};
            int var3 = var2[this.f3 % 2];
            if (this.key >= var3) {
               var1.setCancelled(true);
               this.key = 0;
               this.f3++;
            }
         }
      }
   }

   private boolean m665() {
      return f2.m17("Grim") || f2.m17("ReallyWorld");
   }

   private boolean m534() {
      return this.mc.player != null && this.mc.player.input != null && this.mc.player.input.getMovementInput().lengthSquared() > 1.0E-7F;
   }

   private void m677() {
      this.f18 = System.currentTimeMillis() + 15L;
      m348(f2.m17("ReallyWorld") ? 0.09F : 0.04F);

      try {
         Thread.sleep(f2.m17("ReallyWorld") ? 90L : 40L);
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
      }

      for (Packet var2 : this.f15) {
         if (this.mc.player != null) {
            SilentPacketUtil.m94(var2);

            try {
               Thread.sleep(10L);
            } catch (InterruptedException var5) {
               Thread.currentThread().interrupt();
               break;
            }
         }
      }

      this.f15.clear();
      this.f19 = false;
      if (this.mc.player != null && this.mc.player.currentScreenHandler != null) {
         SilentPacketUtil.m94(new CloseHandledScreenC2SPacket(this.mc.player.currentScreenHandler.syncId));
      }
   }

   private void m1274(KeyBinding[] var1) {
      if (this.mc.getWindow() != null) {
         for (KeyBinding var5 : var1) {
            int var6 = var5.getDefaultKey().getCode();
            boolean var7 = var6 != InputUtil.UNKNOWN_KEY.getCode() && InputUtil.isKeyPressed(this.mc.getWindow(), var6);
            var5.setPressed(var7);
         }
      }
   }

   private void m1275(KeyBinding[] var1) {
      for (KeyBinding var5 : var1) {
         var5.setPressed(false);
      }
   }

   private boolean m752() {
      return this.f16 > 0 || System.currentTimeMillis() < this.f17;
   }

   public static void m348(float var0) {
      NoSlow var1 = f1;
      if (var1 != null && var1.isEnabled()) {
         int var2 = Math.max(1, (int)Math.ceil((double)(var0 * 20.0F)));
         var1.f16 = Math.max(var1.f16, var2);
         var1.f17 = Math.max(var1.f17, System.currentTimeMillis() + (long)(var0 * 1000.0F));
         var1.m1275(var1.m1272());
      } else {
         m115();
      }
   }

   public static void m115() {
      NoSlow var0 = f1;
      if (var0 != null && var0.mc != null && var0.mc.options != null) {
         KeyBinding[] var1 = new KeyBinding[]{
            var0.mc.options.forwardKey,
            var0.mc.options.backKey,
            var0.mc.options.leftKey,
            var0.mc.options.rightKey,
            var0.mc.options.jumpKey,
            var0.mc.options.sprintKey,
            var0.mc.options.sneakKey
         };

         for (KeyBinding var5 : var1) {
            var5.setPressed(false);
         }
      }
   }

   private boolean m791() {
      if (this.mc.player != null && this.mc.world != null) {
         BlockPos var1 = this.mc.player.getBlockPos();
         Block var2 = this.mc.world.getBlockState(var1).getBlock();
         return var2 == Blocks.SNOW
            || var2 == Blocks.WHITE_CARPET
            || var2 == Blocks.ORANGE_CARPET
            || var2 == Blocks.MAGENTA_CARPET
            || var2 == Blocks.LIGHT_BLUE_CARPET
            || var2 == Blocks.YELLOW_CARPET
            || var2 == Blocks.LIME_CARPET
            || var2 == Blocks.PINK_CARPET
            || var2 == Blocks.GRAY_CARPET
            || var2 == Blocks.LIGHT_GRAY_CARPET
            || var2 == Blocks.CYAN_CARPET
            || var2 == Blocks.PURPLE_CARPET
            || var2 == Blocks.BLUE_CARPET
            || var2 == Blocks.BROWN_CARPET
            || var2 == Blocks.GREEN_CARPET
            || var2 == Blocks.RED_CARPET
            || var2 == Blocks.BLACK_CARPET;
      } else {
         return false;
      }
   }

   private void m682() {
      if (this.mc.player != null && this.mc.options != null) {
         boolean var1 = this.mc.player.isUsingItem() && this.mc.player.getActiveItem().getUseAction() == UseAction.EAT;
         if (var1) {
            if (!this.f5) {
               this.f6 = this.mc.options.sprintKey.isPressed();
               this.f5 = true;
            }

            this.mc.options.sprintKey.setPressed(true);
            this.mc.player.setSprinting(true);
         } else {
            this.m683();
         }
      }
   }

   private void m683() {
      if (this.f5 && this.mc.options != null) {
         if (!this.f6) {
            this.mc.options.sprintKey.setPressed(false);
         }

         this.f5 = false;
         this.f6 = false;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.m683();
      this.key = 0;
      this.f3 = 0;
      this.f4 = false;
      this.f15.clear();
      this.f19 = false;
      this.f16 = 0;
      this.f17 = 0L;
      this.f18 = 0L;
      if (this.mc.options != null) {
         this.m1275(this.m1272());
      }
   }
   public static NoSlow m1276() {
      return f1;
   }
}
