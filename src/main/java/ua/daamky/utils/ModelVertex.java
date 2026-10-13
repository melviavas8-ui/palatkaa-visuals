package ua.daamky.utils;

public class ModelVertex {
   public ModelVector3f f1;
   public float f2;
   public float f3;

   public ModelVertex(float var1, float var2, float var3, float var4, float var5) {
      this.f1 = new ModelVector3f(var1, var2, var3);
      this.f2 = var4;
      this.f3 = var5;
   }

   public ModelVertex(ModelVector3f var1, float var2, float var3) {
      this.f1 = var1;
      this.f2 = var2;
      this.f3 = var3;
   }
}
