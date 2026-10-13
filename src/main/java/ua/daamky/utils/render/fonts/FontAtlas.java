package ua.daamky.utils.render.fonts;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import ua.daamky.utils.FontGlyph;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class FontAtlas {
   private final String f1;
   private final Map<Integer, FontGlyph> f2 = new HashMap<>();
   private GpuTexture f3;
   private GpuTextureView f4;
   private float f5;
   private float f6;
   private float f7;
   private float f8;
   private int f9;
   private int f10;
   private float f11;
   private boolean f12 = false;

   public FontAtlas(String var1) {
      this.f1 = var1;
   }

   public void m266(Identifier var1, Identifier var2) {
      if (!this.f12) {
         try {
            this.m267(var1);
            this.m268(var2);
            this.f12 = true;
            System.out.println("[MSDF] Successfully loaded font: " + this.f1);
         } catch (Exception var4) {
            System.err.println("[MSDF] Failed to load font '" + this.f1 + "':");
            var4.printStackTrace();
         }
      }
   }

   private void m267(Identifier var1) throws Exception {
      Identifier var2 = Identifier.of(var1.getNamespace(), "msdf/" + var1.getPath());

      try (InputStream var3 = ((Resource)MinecraftClient.getInstance().getResourceManager().getResource(var2).get()).getInputStream()) {
         NativeImage var4 = NativeImage.read(var3);
         this.f9 = var4.getWidth();
         this.f10 = var4.getHeight();
         this.f3 = RenderSystem.getDevice().createTexture(() -> "msdf:" + this.f1, 5, TextureFormat.RGBA8, this.f9, this.f10, 1, 1);
         RenderSystem.getDevice().createCommandEncoder().writeToTexture(this.f3, var4);
         this.f4 = RenderSystem.getDevice().createTextureView(this.f3);
         var4.close();
      }
   }

   private void m268(Identifier var1) throws Exception {
      Identifier var2 = Identifier.of(var1.getNamespace(), "msdf/" + var1.getPath());

      try (InputStream var3 = ((Resource)MinecraftClient.getInstance().getResourceManager().getResource(var2).get()).getInputStream()) {
         String var4 = new String(var3.readAllBytes());
         JsonObject var5 = JsonParser.parseString(var4).getAsJsonObject();
         JsonObject var6 = var5.getAsJsonObject("atlas");
         this.f8 = var6.get("distanceRange").getAsFloat();
         boolean var7 = var6.has("yOrigin")
            && "bottom".equals(var6.get("yOrigin").getAsString());
         JsonObject var8 = var5.getAsJsonObject("metrics");
         this.f11 = var8.get("emSize").getAsFloat();
         this.f5 = var8.get("lineHeight").getAsFloat();
         this.f6 = var8.get("ascender").getAsFloat();
         this.f7 = var8.get("descender").getAsFloat();

         for (JsonElement var10 : var5.getAsJsonArray("glyphs")) {
            JsonObject var11 = var10.getAsJsonObject();
            int var12 = var11.get("unicode").getAsInt();
            float var13 = var11.has("advance") ? var11.get("advance").getAsFloat() : 0.0F;
            float var14 = 0.0F;
            float var15 = 0.0F;
            float var16 = 0.0F;
            float var17 = 0.0F;
            float var18 = 0.0F;
            float var19 = 0.0F;
            float var20 = 0.0F;
            float var21 = 0.0F;
            if (var11.has("atlasBounds")) {
               JsonObject var22 = var11.getAsJsonObject("atlasBounds");
               float var23 = var22.get("left").getAsFloat();
               float var24 = var22.get("bottom").getAsFloat();
               float var25 = var22.get("right").getAsFloat();
               float var26 = var22.get("top").getAsFloat();
               var16 = var23 / (float)this.f9;
               var18 = var25 / (float)this.f9;
               if (var7) {
                  var17 = 1.0F - var26 / (float)this.f10;
                  var19 = 1.0F - var24 / (float)this.f10;
               } else {
                  var17 = var26 / (float)this.f10;
                  var19 = var24 / (float)this.f10;
               }
            }

            if (var11.has("planeBounds")) {
               JsonObject var29 = var11.getAsJsonObject("planeBounds");
               float var30 = var29.get("left").getAsFloat();
               float var31 = var29.get("bottom").getAsFloat();
               float var32 = var29.get("right").getAsFloat();
               float var33 = var29.get("top").getAsFloat();
               var20 = var30;
               var21 = var33;
               var14 = var32 - var30;
               var15 = var33 - var31;
            }

            this.f2.put(var12, new FontGlyph(var12, var13, 0.0F, 0.0F, var14, var15, var16, var17, var18, var19, var20, var21));
         }

         System.out.println("[MSDF] Loaded font '" + this.f1 + "' with " + this.f2.size() + " glyphs");
      }
   }

   public FontGlyph m269(int var1) {
      return this.f2.get(var1);
   }

   public GpuTextureView m270() {
      return this.f4;
   }

   public float m271() {
      return this.f5;
   }

   public float m272() {
      return this.f6;
   }

   public float m192() {
      return this.f7;
   }

   public float m273() {
      return this.f8;
   }

   public float m274() {
      return this.f11;
   }

   public String m275() {
      return this.f1;
   }

   public boolean m276() {
      return this.f12;
   }

   public float m235(String var1, float var2) {
      float var3 = var2 / this.f11;
      float var4 = 0.0F;

      for (int var5 = 0; var5 < var1.length(); var5++) {
         FontGlyph var6 = this.f2.get(Integer.valueOf(var1.charAt(var5)));
         if (var6 != null) {
            var4 += var6.f2 * var3;
         }
      }

      return var4;
   }

   public void m277() {
      if (this.f4 != null) {
         this.f4.close();
         this.f4 = null;
      }

      if (this.f3 != null) {
         this.f3.close();
         this.f3 = null;
      }

      this.f2.clear();
      this.f12 = false;
   }
}
