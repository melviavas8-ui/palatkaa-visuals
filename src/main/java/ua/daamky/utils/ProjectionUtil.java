package ua.daamky.utils;

import ua.daamky.utils.render.Render2DUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class ProjectionUtil {
   private static final MinecraftClient f1 = MinecraftClient.getInstance();

   private ProjectionUtil() {
   }

   public static float[] m224(Vec3d var0) {
      Camera var1 = f1.gameRenderer.getCamera();
      if (var1 != null && f1.player != null) {
         Vec3d var2 = var1.getCameraPos();
         Vector3f var3 = new Vector3f((float)(var0.x - var2.x), (float)(var0.y - var2.y), (float)(var0.z - var2.z));
         Quaternionf var4 = new Quaternionf(var1.getRotation()).conjugate();
         var3.rotate(var4);
         if (var3.z >= 0.0F) {
            return null;
         } else {
            double var5 = (double)((Integer)f1.options.getFov().getValue()).intValue();
            int var7 = Render2DUtil.m113();
            int var8 = Render2DUtil.m189();
            float var9 = (float)var7 * 0.5F;
            float var10 = (float)var8 * 0.5F;
            float var11 = (float)((double)var10 / Math.tan(Math.toRadians(var5) * 0.5)) / -var3.z;
            float var12 = var9 + var3.x * var11;
            float var13 = var10 - var3.y * var11;
            return new float[]{var12, var13, var11};
         }
      } else {
         return null;
      }
   }

   public static Vec3d m225(Entity var0, float var1) {
      return var0.getLerpedPos(var1);
   }
}
