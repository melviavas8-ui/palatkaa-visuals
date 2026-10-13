package ua.daamky.utils;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class MatrixStackHelper {
   private MatrixStack f1;

   public void m546(MatrixStack var1) {
      this.f1 = var1;
   }

   public void m63() {
      this.f1.push();
   }

   public void m314() {
      this.f1.pop();
   }

   public MatrixStack m547() {
      return this.f1;
   }

   public void m548(float var1, float var2, float var3) {
      this.f1.translate(var1, var2, var3);
   }

   public void m549(float var1, float var2, float var3) {
      this.f1.scale(var1, var2, var3);
   }

   public void m348(float var1) {
      this.f1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var1));
   }

   public void m410(float var1) {
      this.f1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var1));
   }

   public void m519(float var1) {
      this.f1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var1));
   }

   public void m550(float var1, float var2, float var3) {
      if (var3 != 0.0F) {
         this.m519(var3);
      }

      if (var2 != 0.0F) {
         this.m410(var2);
      }

      if (var1 != 0.0F) {
         this.m348(var1);
      }
   }

   public void m551(float var1, float var2, float var3) {
      this.m550(var1, var2, var3);
   }

   public void m520(float var1) {
      this.m348(var1);
   }

   public void m521(float var1) {
      this.m410(var1);
   }

   public void m522(float var1) {
      this.m519(var1);
   }
}
