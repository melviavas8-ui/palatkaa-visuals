package ua.daamky.utils;

public class ModelCube {
   public ModelFace[] f1 = new ModelFace[6];
   public ModelVector3f f2;
   public ModelVector3f f3;
   public ModelVector3f f4;
   public float f5;
   public boolean f6;

   public ModelCube(float var1, float var2, float var3) {
      this.f2 = new ModelVector3f(var1, var2, var3);
      this.f3 = new ModelVector3f(0.0F, 0.0F, 0.0F);
      this.f4 = new ModelVector3f(0.0F, 0.0F, 0.0F);
      this.f5 = 0.0F;
      this.f6 = false;
   }
}
