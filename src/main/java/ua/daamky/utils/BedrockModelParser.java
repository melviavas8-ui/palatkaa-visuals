package ua.daamky.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;

public class BedrockModelParser {
   public static CustomModel m535(String var0) {
      try {
         JsonObject var1 = JsonParser.parseString(var0).getAsJsonObject();
         return m536(var1);
      } catch (Exception var2) {
         return null;
      }
   }

   private static CustomModel m536(JsonObject var0) {
      JsonArray var1 = var0.getAsJsonArray("minecraft:geometry");
      if (var1 != null && !var1.isEmpty()) {
         JsonObject var2 = var1.get(0).getAsJsonObject();
         JsonObject var3 = var2.getAsJsonObject("description");
         int var4 = var3.has("texture_width")
            ? var3.get("texture_width").getAsInt()
            : 64;
         int var5 = var3.has("texture_height")
            ? var3.get("texture_height").getAsInt()
            : 64;
         CustomModel var6 = new CustomModel();
         var6.f2 = var4;
         var6.f3 = var5;
         JsonArray var7 = var2.getAsJsonArray("bones");
         if (var7 != null) {
            HashMap var8 = new HashMap();

            for (JsonElement var10 : var7) {
               JsonObject var11 = var10.getAsJsonObject();
               ModelPart3D var12 = m537(var11, var4, var5);
               var8.put(var12.f4, var12);
            }

            for (JsonElement var17 : var7) {
               JsonObject var18 = var17.getAsJsonObject();
               String var19 = var18.get("name").getAsString();
               ModelPart3D var13 = (ModelPart3D)var8.get(var19);
               if (var18.has("parent")) {
                  String var14 = var18.get("parent").getAsString();
                  ModelPart3D var15 = (ModelPart3D)var8.get(var14);
                  if (var15 != null) {
                     var15.f2.add(var13);
                     var13.f1 = var15;
                  }
               } else {
                  var6.f1.add(var13);
               }
            }
         }

         return var6;
      } else {
         return null;
      }
   }

   private static ModelPart3D m537(JsonObject var0, int var1, int var2) {
      String var3 = var0.get("name").getAsString();
      ModelPart3D var4 = new ModelPart3D(var3);
      if (var0.has("pivot")) {
         JsonArray var5 = var0.getAsJsonArray("pivot");
         var4.f6 = -var5.get(0).getAsFloat();
         var4.f7 = var5.get(1).getAsFloat();
         var4.f8 = var5.get(2).getAsFloat();
      }

      if (var0.has("rotation")) {
         JsonArray var9 = var0.getAsJsonArray("rotation");
         var4.m348((float)Math.toRadians((double)(-var9.get(0).getAsFloat())));
         var4.m410((float)Math.toRadians((double)(-var9.get(1).getAsFloat())));
         var4.m519((float)Math.toRadians((double)var9.get(2).getAsFloat()));
      }

      if (var0.has("cubes")) {
         for (JsonElement var6 : var0.getAsJsonArray("cubes")) {
            JsonObject var7 = var6.getAsJsonObject();
            ModelCube var8 = m538(var7, var1, var2);
            var4.f3.add(var8);
         }
      }

      return var4;
   }

   private static ModelCube m538(JsonObject var0, int var1, int var2) {
      float[] var3 = m543(var0, "origin", new float[]{0.0F, 0.0F, 0.0F});
      float[] var4 = m543(var0, "size", new float[]{1.0F, 1.0F, 1.0F});
      float[] var5 = m543(var0, "pivot", (float[])var3.clone());
      float[] var6 = m543(var0, "rotation", new float[]{0.0F, 0.0F, 0.0F});
      float var7 = var0.has("inflate") ? var0.get("inflate").getAsFloat() : 0.0F;
      boolean var8 = var0.has("mirror") && var0.get("mirror").getAsBoolean();
      ModelCube var9 = new ModelCube(var4[0], var4[1], var4[2]);
      var9.f3 = new ModelVector3f(-var5[0], var5[1], var5[2]);
      var9.f4 = new ModelVector3f((float)Math.toRadians((double)(-var6[0])), (float)Math.toRadians((double)(-var6[1])), (float)Math.toRadians((double)var6[2]));
      var9.f5 = var7;
      var9.f6 = var8;
      m539(var9, var3, var4, var7, var8, var0, var1, var2);
      return var9;
   }

   private static void m539(ModelCube var0, float[] var1, float[] var2, float var3, boolean var4, JsonObject var5, int var6, int var7) {
      float var8 = var1[0];
      float var9 = var1[1];
      float var10 = var1[2];
      float var11 = var2[0];
      float var12 = var2[1];
      float var13 = var2[2];
      var8 -= var3;
      var9 -= var3;
      var10 -= var3;
      var11 += var3 * 2.0F;
      var12 += var3 * 2.0F;
      var13 += var3 * 2.0F;
      float var14 = 0.0F;
      float var15 = 0.0F;
      boolean var16 = false;
      JsonObject var17 = null;
      if (var5.has("uv")) {
         JsonElement var18 = var5.get("uv");
         if (var18.isJsonArray()) {
            JsonArray var19 = var18.getAsJsonArray();
            var14 = var19.get(0).getAsFloat();
            var15 = var19.get(1).getAsFloat();
         } else if (var18.isJsonObject()) {
            var16 = true;
            var17 = var18.getAsJsonObject();
         }
      }

      float var32 = -(var8 + var11) / 16.0F;
      float var33 = var9 / 16.0F;
      float var20 = var10 / 16.0F;
      float var21 = -var8 / 16.0F;
      float var22 = (var9 + var12) / 16.0F;
      float var23 = (var10 + var13) / 16.0F;
      if (var16 && var17 != null) {
         var0.f1[0] = m540(var17, "west", var32, var33, var20, var32, var22, var23, -1.0F, 0.0F, 0.0F, var6, var7);
         var0.f1[1] = m540(var17, "east", var21, var33, var20, var21, var22, var23, 1.0F, 0.0F, 0.0F, var6, var7);
         var0.f1[2] = m540(var17, "down", var32, var33, var20, var21, var33, var23, 0.0F, -1.0F, 0.0F, var6, var7);
         var0.f1[3] = m540(var17, "up", var32, var22, var20, var21, var22, var23, 0.0F, 1.0F, 0.0F, var6, var7);
         var0.f1[4] = m540(var17, "north", var32, var33, var20, var21, var22, var20, 0.0F, 0.0F, -1.0F, var6, var7);
         var0.f1[5] = m540(var17, "south", var32, var33, var23, var21, var22, var23, 0.0F, 0.0F, 1.0F, var6, var7);
      } else {
         float var24 = (float)var6;
         float var25 = (float)var7;
         var0.f1[0] = m541(
            var32,
            var33,
            var20,
            var32,
            var22,
            var23,
            -1.0F,
            0.0F,
            0.0F,
            var14,
            var15,
            var13,
            var12,
            var11,
            var24,
            var25,
            "west"
         );
         var0.f1[1] = m541(
            var21,
            var33,
            var20,
            var21,
            var22,
            var23,
            1.0F,
            0.0F,
            0.0F,
            var14,
            var15,
            var13,
            var12,
            var11,
            var24,
            var25,
            "east"
         );
         var0.f1[2] = m541(
            var32,
            var33,
            var20,
            var21,
            var33,
            var23,
            0.0F,
            -1.0F,
            0.0F,
            var14,
            var15,
            var13,
            var12,
            var11,
            var24,
            var25,
            "down"
         );
         var0.f1[3] = m541(
            var32, var22, var20, var21, var22, var23, 0.0F, 1.0F, 0.0F, var14, var15, var13, var12, var11, var24, var25, "up"
         );
         var0.f1[4] = m541(
            var32,
            var33,
            var20,
            var21,
            var22,
            var20,
            0.0F,
            0.0F,
            -1.0F,
            var14,
            var15,
            var13,
            var12,
            var11,
            var24,
            var25,
            "north"
         );
         var0.f1[5] = m541(
            var32,
            var33,
            var23,
            var21,
            var22,
            var23,
            0.0F,
            0.0F,
            1.0F,
            var14,
            var15,
            var13,
            var12,
            var11,
            var24,
            var25,
            "south"
         );
      }
   }

   private static ModelFace m540(
      JsonObject var0,
      String var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      int var11,
      int var12
   ) {
      if (!var0.has(var1)) {
         return null;
      } else {
         JsonObject var13 = var0.getAsJsonObject(var1);
         JsonArray var14 = var13.getAsJsonArray("uv");
         JsonArray var15 = var13.getAsJsonArray("uv_size");
         float var16 = var14.get(0).getAsFloat() / (float)var11;
         float var17 = var14.get(1).getAsFloat() / (float)var12;
         float var18 = var15.get(0).getAsFloat() / (float)var11;
         float var19 = var15.get(1).getAsFloat() / (float)var12;
         ModelVertex[] var20 = m542(var1, var2, var3, var4, var5, var6, var7, var16, var17, var18, var19);
         return new ModelFace(var20, var8, var9, var10);
      }
   }

   private static ModelFace m541(
      float var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      String var16
   ) {
      float var17;
      float var18;
      float var19;
      float var20;
      switch (var16) {
         case "north":
            var17 = (var9 + var11 + var13) / var14;
            var18 = (var10 + var11) / var15;
            var19 = var13 / var14;
            var20 = var12 / var15;
            break;
         case "south":
            var17 = (var9 + var11 + var13 + var11) / var14;
            var18 = (var10 + var11) / var15;
            var19 = var13 / var14;
            var20 = var12 / var15;
            break;
         case "east":
            var17 = var9 / var14;
            var18 = (var10 + var11) / var15;
            var19 = var11 / var14;
            var20 = var12 / var15;
            break;
         case "west":
            var17 = (var9 + var11 + var13) / var14;
            var18 = (var10 + var11) / var15;
            var19 = var11 / var14;
            var20 = var12 / var15;
            break;
         case "up":
            var17 = (var9 + var11) / var14;
            var18 = var10 / var15;
            var19 = var13 / var14;
            var20 = var11 / var15;
            break;
         case "down":
            var17 = (var9 + var11 + var13) / var14;
            var18 = var10 / var15;
            var19 = var13 / var14;
            var20 = var11 / var15;
            break;
         default:
            var17 = 0.0F;
            var18 = 0.0F;
            var19 = 0.0F;
            var20 = 0.0F;
      }

      ModelVertex[] var21 = m542(var16, var0, var1, var2, var3, var4, var5, var17, var18, var19, var20);
      return new ModelFace(var21, var6, var7, var8);
   }

   private static ModelVertex[] m542(
      String var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10
   ) {
      ModelVertex[] var11 = new ModelVertex[4];
      float var12 = var7 + var9;
      float var13 = var8 + var10;
      switch (var0) {
         case "north":
            var11[0] = new ModelVertex(var4, var5, var3, var7, var8);
            var11[1] = new ModelVertex(var1, var5, var3, var12, var8);
            var11[2] = new ModelVertex(var1, var2, var3, var12, var13);
            var11[3] = new ModelVertex(var4, var2, var3, var7, var13);
            break;
         case "south":
            var11[0] = new ModelVertex(var1, var5, var6, var7, var8);
            var11[1] = new ModelVertex(var4, var5, var6, var12, var8);
            var11[2] = new ModelVertex(var4, var2, var6, var12, var13);
            var11[3] = new ModelVertex(var1, var2, var6, var7, var13);
            break;
         case "east":
            var11[0] = new ModelVertex(var4, var5, var6, var7, var8);
            var11[1] = new ModelVertex(var4, var5, var3, var12, var8);
            var11[2] = new ModelVertex(var4, var2, var3, var12, var13);
            var11[3] = new ModelVertex(var4, var2, var6, var7, var13);
            break;
         case "west":
            var11[0] = new ModelVertex(var1, var5, var3, var7, var8);
            var11[1] = new ModelVertex(var1, var5, var6, var12, var8);
            var11[2] = new ModelVertex(var1, var2, var6, var12, var13);
            var11[3] = new ModelVertex(var1, var2, var3, var7, var13);
            break;
         case "up":
            var11[0] = new ModelVertex(var1, var5, var3, var7, var8);
            var11[1] = new ModelVertex(var1, var5, var6, var7, var13);
            var11[2] = new ModelVertex(var4, var5, var6, var12, var13);
            var11[3] = new ModelVertex(var4, var5, var3, var12, var8);
            break;
         case "down":
            var11[0] = new ModelVertex(var4, var2, var3, var7, var8);
            var11[1] = new ModelVertex(var4, var2, var6, var7, var13);
            var11[2] = new ModelVertex(var1, var2, var6, var12, var13);
            var11[3] = new ModelVertex(var1, var2, var3, var12, var8);
      }

      return var11;
   }

   private static float[] m543(JsonObject var0, String var1, float[] var2) {
      if (!var0.has(var1)) {
         return var2;
      } else {
         JsonArray var3 = var0.getAsJsonArray(var1);
         float[] var4 = new float[var3.size()];

         for (int var5 = 0; var5 < var3.size(); var5++) {
            var4[var5] = var3.get(var5).getAsFloat();
         }

         return var4;
      }
   }
}
