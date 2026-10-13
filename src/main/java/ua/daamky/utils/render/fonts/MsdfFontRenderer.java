package ua.daamky.utils.render.fonts;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import ua.daamky.utils.FontGlyph;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

public class MsdfFontRenderer {
   private static final int f1 = 128;
   private static final int f2 = 128;
   private static final int f3 = 4096;
   private static RenderPipeline f4;
   private static GpuBuffer f5;
   private static GpuBuffer f6;
   private static ByteBuffer f7;
   private static ByteBuffer f8;
   private static boolean f9 = false;

   public static void m63() {
      if (!f9) {
         try {
            System.out.println("[MSDF] Initializing Msdf2D shaders and buffers...");
            f4 = RenderPipeline.builder(new Snippet[0])
               .withLocation(Identifier.of("daamky", "msdf"))
               .withVertexShader(Identifier.of("daamky", "msdf_vertex"))
               .withFragmentShader(Identifier.of("daamky", "msdf_fragment"))
               .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
               .withUniform("MsdfData", UniformType.UNIFORM_BUFFER)
               .withUniform("GlyphData", UniformType.UNIFORM_BUFFER)
               .withSampler("Sampler0")
               .withBlend(BlendFunction.TRANSLUCENT)
               .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
               .withCull(false)
               .build();
            f5 = RenderSystem.getDevice().createBuffer(() -> "MsdfPipeline MsdfData", 136, 128L);
            f6 = RenderSystem.getDevice().createBuffer(() -> "MsdfPipeline GlyphData", 136, 4096L);
            f7 = MemoryUtil.memAlloc(128);
            f8 = MemoryUtil.memAlloc(4096);
            f9 = true;
         } catch (Exception var1) {
            System.err.println("[MSDF] Failed to initialize Msdf2D: " + var1.getMessage());
            var1.printStackTrace();
         }
      }
   }

   public static void m321(Matrix4f var0, FontAtlas var1, String var2, float var3, float var4, float var5, int var6, float var7, float var8) {
      if (f9 && var1 != null && var1.m276() && !var2.isEmpty()) {
         float var9 = var5 / var1.m274();
         float var10 = var3;
         float var11 = var4 + var1.m272() * var9;
         float var12 = (float)(var6 >> 16 & 0xFF) / 255.0F;
         float var13 = (float)(var6 >> 8 & 0xFF) / 255.0F;
         float var14 = (float)(var6 & 0xFF) / 255.0F;
         float var15 = (float)(var6 >> 24 & 0xFF) / 255.0F;
         f7.clear();
         f7.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         f7.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         f7.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         f7.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         f7.putFloat(var12).putFloat(var13).putFloat(var14).putFloat(var15);
         f7.putFloat(var1.m273()).putFloat((float)Math.toRadians((double)var7)).putFloat(0.0F).putFloat(0.0F);
         f7.putFloat(var8).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
         f7.flip();
         f8.clear();
         int var16 = 0;

         for (int var17 = 0; var17 < var2.length() && var16 < 128; var17++) {
            char var18 = var2.charAt(var17);
            FontGlyph var19 = var1.m269(var18);
            if (var19 == null) {
               var19 = var1.m269(63);
               if (var19 == null) {
                  continue;
               }
            }

            if (var19.f5 > 0.0F && var19.f6 > 0.0F) {
               float var20 = var10 + var19.f11 * var9;
               float var21 = var11 - var19.f12 * var9;
               float var22 = var19.f5 * var9;
               float var23 = var19.f6 * var9;
               f8.putFloat(var20).putFloat(var21).putFloat(var22).putFloat(var23);
               f8.putFloat(var19.f7).putFloat(var19.f8);
               f8.putFloat(var19.f9 - var19.f7).putFloat(var19.f10 - var19.f8);
               var16++;
            }

            var10 += var19.f2 * var9;
         }

         if (var16 != 0) {
            f8.flip();
            m320(var1, var16);
         }
      }
   }

   public static void m322(Matrix4f var0, FontAtlas var1, String var2, float var3, float var4, float var5, int[] var6, float var7) {
      if (f9 && var1 != null && var1.m276() && !var2.isEmpty()) {
         float var8 = var5 / var1.m274();
         float var9 = var3;
         float var10 = var4 + var1.m272() * var8;

         for (int var11 = 0; var11 < var2.length(); var11++) {
            char var12 = var2.charAt(var11);
            FontGlyph var13 = var1.m269(var12);
            if (var13 == null) {
               var13 = var1.m269(63);
               if (var13 == null) {
                  continue;
               }
            }

            if (var13.f5 > 0.0F && var13.f6 > 0.0F) {
               int var14 = var6[var11 % var6.length];
               m323(var0, var1, var13, var9, var10, var8, var14, 0.0F, var7);
            }

            var9 += var13.f2 * var8;
         }
      }
   }

   public static float m323(Matrix4f var0, FontAtlas var1, FontGlyph var2, float var3, float var4, float var5, int var6, float var7, float var8) {
      if (f9 && var1 != null && var2 != null) {
         float var9 = (float)(var6 >> 16 & 0xFF) / 255.0F;
         float var10 = (float)(var6 >> 8 & 0xFF) / 255.0F;
         float var11 = (float)(var6 & 0xFF) / 255.0F;
         float var12 = (float)(var6 >> 24 & 0xFF) / 255.0F;
         f7.clear();
         f7.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         f7.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         f7.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         f7.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         f7.putFloat(var9).putFloat(var10).putFloat(var11).putFloat(var12);
         f7.putFloat(var1.m273()).putFloat((float)Math.toRadians((double)var7)).putFloat(0.0F).putFloat(0.0F);
         f7.putFloat(var8).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
         f7.flip();
         float var13 = var3 + var2.f11 * var5;
         float var14 = var4 - var2.f12 * var5;
         float var15 = var2.f5 * var5;
         float var16 = var2.f6 * var5;
         f8.clear();
         f8.putFloat(var13).putFloat(var14).putFloat(var15).putFloat(var16);
         f8.putFloat(var2.f7).putFloat(var2.f8);
         f8.putFloat(var2.f9 - var2.f7).putFloat(var2.f10 - var2.f8);
         f8.flip();
         m320(var1, 1);
         return var2.f2 * var5;
      } else {
         return 0.0F;
      }
   }

   private static void m320(FontAtlas var0, int var1) {
      GpuSampler var2 = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
      Framebuffer var3 = MinecraftClient.getInstance().getFramebuffer();
      CommandEncoder var4 = RenderSystem.getDevice().createCommandEncoder();
      var4.writeToBuffer(f5.slice(), f7);
      var4.writeToBuffer(f6.slice(), f8);
      RenderPass var5 = var4.createRenderPass(
         () -> "MsdfPipeline",
         var3.getColorAttachmentView(),
         OptionalInt.empty(),
         var3.getDepthAttachmentView(),
         OptionalDouble.of(1.0)
      );

      try {
         var5.setPipeline(f4);
         var5.setUniform("MsdfData", f5);
         var5.setUniform("GlyphData", f6);
         var5.bindTexture("Sampler0", var0.m270(), var2);
         var5.draw(0, var1 * 6);
      } catch (Throwable var9) {
         if (var5 != null) {
            try {
               var5.close();
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
            }
         }

         throw var9;
      }

      if (var5 != null) {
         var5.close();
      }
   }

   public static void m314() {
      if (f5 != null) {
         f5.close();
         f5 = null;
      }

      if (f6 != null) {
         f6.close();
         f6 = null;
      }

      if (f7 != null) {
         MemoryUtil.memFree(f7);
         f7 = null;
      }

      if (f8 != null) {
         MemoryUtil.memFree(f8);
         f8 = null;
      }

      f9 = false;
   }
}
