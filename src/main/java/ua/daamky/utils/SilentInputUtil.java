package ua.daamky.utils;

import ua.daamky.features.movement.Sprint;
import ua.daamky.system.api.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.PlayerInput;

public class SilentInputUtil {
   public static final SilentInputUtil f1 = new SilentInputUtil();
   private static final MinecraftClient f2 = MinecraftClient.getInstance();

   public static SilentInputUtil m89() {
      return f1;
   }

   public static void m5(Runnable var0) {
      if (f2.player != null && f2.getNetworkHandler() != null) {
         try {
            f2.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
            if (f2.player.isSprinting()) {
               f2.player.setSprinting(false);
               f2.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(f2.player, Mode.STOP_SPRINTING));
               Sprint var1 = ModuleManager.getModule(Sprint.class);
               if (var1 == null || !var1.isEnabled()) {
                  f2.options.sprintKey.setPressed(false);
               }
            }

            var0.run();
            SilentPacketUtil.m94(new CloseHandledScreenC2SPacket(0));
            f2.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(f2.player.input.playerInput));
         } catch (Exception var2) {
            var2.printStackTrace();
         }
      }
   }
}
