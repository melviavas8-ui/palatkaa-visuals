package ua.daamky.utils;

public class ModelFace {
   public ModelVertex[] f1;
   public ModelVector3f f2;

   public ModelFace(ModelVertex[] var1, ModelVector3f var2) {
      this.f1 = var1;
      this.f2 = var2;
   }

   public ModelFace(ModelVertex[] var1, float var2, float var3, float var4) {
      this.f1 = var1;
      this.f2 = new ModelVector3f(var2, var3, var4);
   }
}
