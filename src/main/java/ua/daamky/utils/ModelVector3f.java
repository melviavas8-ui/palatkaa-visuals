package ua.daamky.utils;

public class ModelVector3f {
   public static final ModelVector3f f1 = new ModelVector3f(0.0F, 0.0F, 0.0F);
   public float f2;
   public float f3;
   public float f4;

   public ModelVector3f(float var1, float var2, float var3) {
      this.f2 = var1;
      this.f3 = var2;
      this.f4 = var3;
   }

   public float m329() {
      return this.f2;
   }

   public float m271() {
      return this.f3;
   }

   public float m272() {
      return this.f4;
   }

   public void m348(float var1) {
      this.f2 = var1;
   }

   public void m410(float var1) {
      this.f3 = var1;
   }

   public void m519(float var1) {
      this.f4 = var1;
   }

   public void m548(float var1, float var2, float var3) {
      this.f2 = var1;
      this.f3 = var2;
      this.f4 = var3;
   }

   public ModelVector3f m558(ModelVector3f var1) {
      return new ModelVector3f(this.f3 * var1.f4 - this.f4 * var1.f3, this.f4 * var1.f2 - this.f2 * var1.f4, this.f2 * var1.f3 - this.f3 * var1.f2);
   }

   @Override
   public String toString() {
      return this.f2 + "," + this.f3 + "," + this.f4;
   }
}
