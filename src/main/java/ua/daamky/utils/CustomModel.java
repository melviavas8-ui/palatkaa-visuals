package ua.daamky.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomModel {
   public List<ModelPart3D> f1 = new ArrayList<>();
   public int f2 = 64;
   public int f3 = 64;

   public Optional<ModelPart3D> m544(String var1) {
      for (ModelPart3D var3 : this.f1) {
         ModelPart3D var4 = this.m545(var1, var3);
         if (var4 != null) {
            return Optional.of(var4);
         }
      }

      return Optional.empty();
   }

   private ModelPart3D m545(String var1, ModelPart3D var2) {
      if (var2.f4.equals(var1)) {
         return var2;
      } else {
         for (ModelPart3D var4 : var2.f2) {
            ModelPart3D var5 = this.m545(var1, var4);
            if (var5 != null) {
               return var5;
            }
         }

         return null;
      }
   }
}
