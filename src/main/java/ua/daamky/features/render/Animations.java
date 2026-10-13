package ua.daamky.features.render;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ua.daamky.utils.ModelVertex;
import ua.daamky.utils.CosmeticModelItem;
import ua.daamky.utils.ModelTransformUtil;
import ua.daamky.utils.ModelLoader;
import ua.daamky.utils.ModelPart3D;
import ua.daamky.utils.ModelCube;
import ua.daamky.utils.CustomModel;
import ua.daamky.utils.ModelFace;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Animations {
   private static Animations f1;
   private final Map<Integer, CustomModel> f2 = new ConcurrentHashMap<>();
   private final Map<Integer, Animations$2> f3 = new ConcurrentHashMap<>();
   private final Set<Integer> f4 = ConcurrentHashMap.newKeySet();
   private final Map<Integer, Long> f5 = new ConcurrentHashMap<>();
   private final Map<Integer, Map<String, float[]>> f6 = new ConcurrentHashMap<>();
   private final ModelLoader f7 = new ModelLoader();

   public static Animations m502() {
      if (f1 == null) {
         f1 = new Animations();
      }

      return f1;
   }

   public void m503(CosmeticModelItem var1, MatrixStack var2, VertexConsumerProvider var3, int var4) {
      if (var1 != null && var1.m560() != null) {
         CustomModel var5 = this.m505(var1);
         if (var5 != null) {
            Animations$2 var6 = this.m510(var1);
            if (var6 != null) {
               this.m513(var5, var6, var1.m189());
            }

            Identifier var7 = var1.m560();
            RenderLayer var8 = RenderLayers.entityCutoutNoCull(var7);
            VertexConsumer var9 = var3.getBuffer(var8);

            for (ModelPart3D var11 : var5.f1) {
               this.m508(var11, var2, var9, var4, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   public void m504(CosmeticModelItem var1, MatrixStack var2, VertexConsumer var3, int var4) {
      if (var1 != null && var1.m560() != null && var3 != null) {
         CustomModel var5 = this.m505(var1);
         if (var5 != null) {
            Animations$2 var6 = this.m510(var1);
            if (var6 != null) {
               this.m513(var5, var6, var1.m189());
            }

            for (ModelPart3D var8 : var5.f1) {
               this.m508(var8, var2, var3, var4, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   private CustomModel m505(CosmeticModelItem var1) {
      int var2 = var1.m189();
      if (this.f2.containsKey(var2)) {
         return this.f2.get(var2);
      } else {
         CustomModel var3 = this.f7.m505(var1);
         if (var3 != null) {
            this.f2.put(var2, var3);
            this.m506(var2, var3);
         }

         return var3;
      }
   }

   private void m506(int var1, CustomModel var2) {
      HashMap var3 = new HashMap();

      for (ModelPart3D var5 : var2.f1) {
         this.m507(var5, var3);
      }

      this.f6.put(var1, var3);
   }

   private void m507(ModelPart3D var1, Map<String, float[]> var2) {
      var2.put(var1.f4, new float[]{var1.m329(), var1.m271(), var1.m272(), var1.m192(), var1.m273(), var1.m274(), var1.m523(), var1.m524(), var1.m525()});

      for (ModelPart3D var4 : var1.f2) {
         this.m507(var4, var2);
      }
   }

   private void m508(ModelPart3D var1, MatrixStack var2, VertexConsumer var3, int var4, int var5, float var6, float var7, float var8, float var9) {
      if (!var1.f5) {
         var2.push();
         ModelTransformUtil.m490(var1, var2);
         ModelTransformUtil.m491(var1, var2);
         ModelTransformUtil.m493(var1, var2);
         ModelTransformUtil.m494(var1, var2);
         ModelTransformUtil.m492(var1, var2);

         for (ModelCube var11 : var1.f3) {
            this.m509(var11, var2, var3, var4, var5, var6, var7, var8, var9);
         }

         for (ModelPart3D var13 : var1.f2) {
            this.m508(var13, var2, var3, var4, var5, var6, var7, var8, var9);
         }

         var2.pop();
      }
   }

   private void m509(ModelCube var1, MatrixStack var2, VertexConsumer var3, int var4, int var5, float var6, float var7, float var8, float var9) {
      var2.push();
      ModelTransformUtil.m495(var1, var2);
      ModelTransformUtil.m497(var1, var2);
      ModelTransformUtil.m496(var1, var2);
      Matrix4f var10 = var2.peek().getPositionMatrix();
      Matrix3f var11 = var2.peek().getNormalMatrix();

      for (ModelFace var15 : var1.f1) {
         if (var15 != null) {
            Vector3f var16 = new Vector3f(var15.f2.m329(), var15.f2.m271(), var15.f2.m272());
            var11.transform(var16);
            float var17 = var16.x();
            float var18 = var16.y();
            float var19 = var16.z();
            if ((var1.f2.m271() == 0.0F || var1.f2.m272() == 0.0F) && var17 < 0.0F) {
               var17 = -var17;
            }

            if ((var1.f2.m329() == 0.0F || var1.f2.m272() == 0.0F) && var18 < 0.0F) {
               var18 = -var18;
            }

            if ((var1.f2.m329() == 0.0F || var1.f2.m271() == 0.0F) && var19 < 0.0F) {
               var19 = -var19;
            }

            for (ModelVertex var23 : var15.f1) {
               var3.vertex(var10, var23.f1.m329(), var23.f1.m271(), var23.f1.m272())
                  .color(var6, var7, var8, var9)
                  .texture(var23.f2, var23.f3)
                  .overlay(var5)
                  .light(var4)
                  .normal(var17, var18, var19);
            }
         }
      }

      var2.pop();
   }

   private Animations$2 m510(CosmeticModelItem var1) {
      int var2 = var1.m189();
      if (this.f3.containsKey(var2)) {
         return this.f3.get(var2);
      } else if (this.f4.contains(var2)) {
         return null;
      } else {
         JsonObject var3 = var1.m567();
         if (var3 == null) {
            this.f4.add(var2);
            return null;
         } else {
            try {
               Animations$2 var4 = this.m511(var3);
               if (var4 != null) {
                  this.f3.put(var2, var4);
               } else {
                  this.f4.add(var2);
               }

               return var4;
            } catch (Exception var5) {
               this.f4.add(var2);
               return null;
            }
         }
      }
   }

   private Animations$2 m511(JsonObject var1) {
      Animations$2 var2 = new Animations$2();
      if (!var1.has("animations")) {
         return null;
      } else {
         JsonObject var3 = var1.getAsJsonObject("animations");
         Iterator var4 = var3.entrySet().iterator();
         if (var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            String var6 = (String)var5.getKey();
            JsonObject var7 = ((JsonElement)var5.getValue()).getAsJsonObject();
            var2.f1 = var6;
            var2.f2 = var7.has("loop") && var7.get("loop").getAsBoolean();
            var2.f3 = var7.has("animation_length")
               ? var7.get("animation_length").getAsFloat()
               : 1.0F;
            if (var7.has("bones")) {
               JsonObject var8 = var7.getAsJsonObject("bones");

               for (Entry var10 : var8.entrySet()) {
                  String var11 = (String)var10.getKey();
                  JsonObject var12 = ((JsonElement)var10.getValue()).getAsJsonObject();
                  Animations$1 var13 = new Animations$1();
                  if (var12.has("rotation")) {
                     var13.f1 = this.m512(var12.get("rotation"));
                  }

                  if (var12.has("position")) {
                     var13.f2 = this.m512(var12.get("position"));
                  }

                  if (var12.has("scale")) {
                     var13.f3 = this.m512(var12.get("scale"));
                  }

                  var2.f4.put(var11, var13);
               }
            }
         }

         return var2;
      }
   }

   private Map<Float, float[]> m512(JsonElement var1) {
      HashMap var2 = new HashMap();
      if (var1.isJsonObject()) {
         JsonObject var3 = var1.getAsJsonObject();

         for (Entry var5 : var3.entrySet()) {
            try {
               float var6 = Float.parseFloat((String)var5.getKey());
               JsonElement var7 = (JsonElement)var5.getValue();
               float[] var8 = new float[3];
               if (var7.isJsonObject()) {
                  JsonObject var9 = var7.getAsJsonObject();
                  if (var9.has("vector")) {
                     JsonArray var10 = var9.getAsJsonArray("vector");
                     var8[0] = var10.get(0).getAsFloat();
                     var8[1] = var10.get(1).getAsFloat();
                     var8[2] = var10.get(2).getAsFloat();
                  }
               } else if (var7.isJsonArray()) {
                  JsonArray var12 = var7.getAsJsonArray();
                  var8[0] = var12.get(0).getAsFloat();
                  var8[1] = var12.get(1).getAsFloat();
                  var8[2] = var12.get(2).getAsFloat();
               }

               var2.put(var6, var8);
            } catch (NumberFormatException var11) {
            }
         }
      }

      return var2;
   }

   private void m513(CustomModel var1, Animations$2 var2, int var3) {
      Map var4 = this.f6.get(var3);
      if (var4 != null) {
         long var5 = this.f5.computeIfAbsent(var3, var0 -> System.currentTimeMillis());
         float var7 = (float)(System.currentTimeMillis() - var5) / 1000.0F;
         float var8;
         if (var2.f2 && var2.f3 > 0.0F) {
            var8 = var7 % var2.f3;
         } else {
            var8 = Math.min(var7, var2.f3);
         }

         for (ModelPart3D var10 : var1.f1) {
            this.m514(var10, var4);
         }

         for (Entry var17 : var2.f4.entrySet()) {
            String var11 = (String)var17.getKey();
            Animations$1 var12 = (Animations$1)var17.getValue();
            ModelPart3D var13 = this.m515(var1, var11);
            if (var13 != null) {
               float[] var14 = (float[])var4.get(var11);
               if (var14 == null) {
                  var14 = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F};
               }

               if (!var12.f1.isEmpty()) {
                  float[] var15 = this.m517(var12.f1, var8);
                  var13.m348(var14[0] + (float)Math.toRadians((double)(-var15[0])));
                  var13.m410(var14[1] + (float)Math.toRadians((double)(-var15[1])));
                  var13.m519(var14[2] + (float)Math.toRadians((double)var15[2]));
               }

               if (!var12.f2.isEmpty()) {
                  float[] var18 = this.m517(var12.f2, var8);
                  var13.m520(var14[3] + var18[0]);
                  var13.m521(var14[4] + var18[1]);
                  var13.m522(var14[5] + var18[2]);
               }

               if (!var12.f3.isEmpty()) {
                  float[] var19 = this.m517(var12.f3, var8);
                  var13.m526(var14[6] * var19[0]);
                  var13.m527(var14[7] * var19[1]);
                  var13.m528(var14[8] * var19[2]);
               }
            }
         }
      }
   }

   private void m514(ModelPart3D var1, Map<String, float[]> var2) {
      float[] var3 = (float[])var2.get(var1.f4);
      if (var3 != null) {
         var1.m348(var3[0]);
         var1.m410(var3[1]);
         var1.m519(var3[2]);
         var1.m520(var3[3]);
         var1.m521(var3[4]);
         var1.m522(var3[5]);
         var1.m526(var3[6]);
         var1.m527(var3[7]);
         var1.m528(var3[8]);
      }

      for (ModelPart3D var5 : var1.f2) {
         this.m514(var5, var2);
      }
   }

   private ModelPart3D m515(CustomModel var1, String var2) {
      for (ModelPart3D var4 : var1.f1) {
         ModelPart3D var5 = this.m516(var4, var2);
         if (var5 != null) {
            return var5;
         }
      }

      return null;
   }

   private ModelPart3D m516(ModelPart3D var1, String var2) {
      if (var1.f4.equals(var2)) {
         return var1;
      } else {
         for (ModelPart3D var4 : var1.f2) {
            ModelPart3D var5 = this.m516(var4, var2);
            if (var5 != null) {
               return var5;
            }
         }

         return null;
      }
   }

   private float[] m517(Map<Float, float[]> var1, float var2) {
      if (var1.isEmpty()) {
         return new float[]{0.0F, 0.0F, 0.0F};
      } else {
         Float var3 = null;
         Float var4 = null;
         float[] var5 = null;
         float[] var6 = null;

         for (Entry var8 : var1.entrySet()) {
            float var9 = (Float)var8.getKey();
            if (var9 <= var2 && (var3 == null || var9 > var3)) {
               var3 = var9;
               var5 = (float[])var8.getValue();
            }

            if (var9 >= var2 && (var4 == null || var9 < var4)) {
               var4 = var9;
               var6 = (float[])var8.getValue();
            }
         }

         if (var5 == null && var6 == null) {
            return new float[]{0.0F, 0.0F, 0.0F};
         } else if (var5 == null) {
            return var6;
         } else if (var6 == null) {
            return var5;
         } else if (var3.equals(var4)) {
            return var5;
         } else {
            float var10 = (var2 - var3) / (var4 - var3);
            return new float[]{var5[0] + var10 * (var6[0] - var5[0]), var5[1] + var10 * (var6[1] - var5[1]), var5[2] + var10 * (var6[2] - var5[2])};
         }
      }
   }
}
