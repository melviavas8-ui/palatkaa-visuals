package ua.daamky.utils;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ModelTransformUtil {
   private static final Vector3f f1 = new Vector3f(1.0F, 0.0F, 0.0F);
   private static final Vector3f f2 = new Vector3f(0.0F, 1.0F, 0.0F);
   private static final Vector3f f3 = new Vector3f(0.0F, 0.0F, 1.0F);

   public static void m490(ModelPart3D var0, MatrixStack var1) {
      var1.translate(-var0.m192() / 16.0F, var0.m273() / 16.0F, var0.m274() / 16.0F);
   }

   public static void m491(ModelPart3D var0, MatrixStack var1) {
      var1.translate(var0.m2() / 16.0F, var0.m529() / 16.0F, var0.m530() / 16.0F);
   }

   public static void m492(ModelPart3D var0, MatrixStack var1) {
      var1.translate(-var0.m2() / 16.0F, -var0.m529() / 16.0F, -var0.m530() / 16.0F);
   }

   public static void m493(ModelPart3D var0, MatrixStack var1) {
      if (var0.m272() != 0.0F) {
         var1.multiply(m498(f3, var0.m272()));
      }

      if (var0.m271() != 0.0F) {
         var1.multiply(m498(f2, var0.m271()));
      }

      if (var0.m329() != 0.0F) {
         var1.multiply(m498(f1, var0.m329()));
      }
   }

   public static void m494(ModelPart3D var0, MatrixStack var1) {
      var1.scale(var0.m523(), var0.m524(), var0.m525());
   }

   public static void m495(ModelCube var0, MatrixStack var1) {
      var1.translate(var0.f3.m329() / 16.0F, var0.f3.m271() / 16.0F, var0.f3.m272() / 16.0F);
   }

   public static void m496(ModelCube var0, MatrixStack var1) {
      var1.translate(-var0.f3.m329() / 16.0F, -var0.f3.m271() / 16.0F, -var0.f3.m272() / 16.0F);
   }

   public static void m497(ModelCube var0, MatrixStack var1) {
      if (var0.f4.m272() != 0.0F) {
         var1.multiply(m498(f3, var0.f4.m272()));
      }

      if (var0.f4.m271() != 0.0F) {
         var1.multiply(m498(f2, var0.f4.m271()));
      }

      if (var0.f4.m329() != 0.0F) {
         var1.multiply(m498(f1, var0.f4.m329()));
      }
   }

   public static Quaternionf m498(Vector3f var0, float var1) {
      float var2 = (float)Math.sin((double)(var1 / 2.0F));
      float var3 = var0.x() * var2;
      float var4 = var0.y() * var2;
      float var5 = var0.z() * var2;
      float var6 = (float)Math.cos((double)(var1 / 2.0F));
      return new Quaternionf(var3, var4, var5, var6);
   }
}
