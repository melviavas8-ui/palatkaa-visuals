package ua.daamky.utils;

import ua.daamky.features.combat.TargetStrafe;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.utils.player.RotationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec2f;

public class MovementUtil {
   public static boolean m91() {
      MinecraftClient var0 = MinecraftClient.getInstance();
      return var0.player != null && var0.player.input.getMovementInput().lengthSquared() > 1.0E-7F;
   }

   public static double[] m92(double var0) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2.player == null) {
         return new double[]{0.0, 0.0};
      } else {
         Vec2f var3 = var2.player.input.getMovementInput();
         float var4 = var3.x;
         float var5 = var3.y;
         float var6 = var2.player.getYaw();
         TargetStrafe var7 = ModuleManager.getModule(TargetStrafe.class);
         if (var7 != null && var7.isEnabled() && var7.m770() != null) {
            var6 = RotationUtil.m417(var7.m770().getBoundingBox().getCenter()).m329();
            var5 = 1.0F;
            var4 = (float)((double)var7.m715() * var7.m774());
         }

         if (var5 != 0.0F) {
            if (var4 > 0.0F) {
               var6 += var5 > 0.0F ? -45.0F : 45.0F;
            } else if (var4 < 0.0F) {
               var6 += var5 > 0.0F ? 45.0F : -45.0F;
            }

            var4 = 0.0F;
            var5 = var5 > 0.0F ? 1.0F : -1.0F;
         }

         double var8 = Math.sin(Math.toRadians((double)(var6 + 90.0F)));
         double var10 = Math.cos(Math.toRadians((double)(var6 + 90.0F)));
         double var12 = (double)var5 * var0 * var10 + (double)var4 * var0 * var8;
         double var14 = (double)var5 * var0 * var8 - (double)var4 * var0 * var10;
         return new double[]{var12, var14};
      }
   }

   public static void m25(double var0) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2.player != null && m91()) {
         double[] var3 = m92(var0);
         var2.player.setVelocity(var3[0], var2.player.getVelocity().y, var3[1]);
      }
   }

   public static void m93(double var0, double var2) {
      MinecraftClient var4 = MinecraftClient.getInstance();
      if (var4.player != null && m91()) {
         double[] var5 = m92(var0);
         var4.player.setVelocity(var5[0], var2, var5[1]);
      }
   }

   public static void m26(double var0) {
      MinecraftClient var2 = MinecraftClient.getInstance();
      if (var2.player != null) {
         var2.player.setVelocity(var2.player.getVelocity().x, var0, var2.player.getVelocity().z);
      }
   }
}
