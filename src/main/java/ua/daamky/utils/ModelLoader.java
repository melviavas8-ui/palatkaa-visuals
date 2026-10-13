package ua.daamky.utils;

public class ModelLoader {
   public CustomModel m505(CosmeticModelItem var1) {
      try {
         String var2 = var1.m30();
         if (var2 == null) {
            return null;
         } else {
            CustomModel var3 = BedrockModelParser.m535(var2);
            return var3 == null ? null : var3;
         }
      } catch (Exception var4) {
         return null;
      }
   }
}
