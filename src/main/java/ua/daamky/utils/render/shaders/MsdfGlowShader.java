package ua.daamky.utils.render.shaders;

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
import ua.daamky.utils.render.fonts.FontAtlas;
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

public class MsdfGlowShader {
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
            f4 = RenderPipeline.builder(new Snippet[0])
               .withLocation(Identifier.of("daamky", "msdf_glow"))
               .withVertexShader(Identifier.of("daamky", "msdf_glow_vertex"))
               .withFragmentShader(Identifier.of("daamky", "msdf_glow_fragment"))
               .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
               .withUniform("MsdfData", UniformType.UNIFORM_BUFFER)
               .withUniform("GlyphData", UniformType.UNIFORM_BUFFER)
               .withSampler("Sampler0")
               .withBlend(BlendFunction.TRANSLUCENT)
               .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
               .withCull(false)
               .build();
            f5 = RenderSystem.getDevice().createBuffer(() -> "MsdfGlowPipeline MsdfData", 136, 128L);
            f6 = RenderSystem.getDevice().createBuffer(() -> "MsdfGlowPipeline GlyphData", 136, 4096L);
            f7 = MemoryUtil.memAlloc(128);
            f8 = MemoryUtil.memAlloc(4096);
            f9 = true;
         } catch (Exception var1) {
            System.err.println("[MsdfGlowPipeline] Failed to init: " + var1.getMessage());
         }
      }
   }

   public static void m319(Matrix4f var0, FontAtlas var1, String var2, float var3, float var4, float var5, int var6, int var7, float var8, float var9) {
      if (f9 && var1 != null && var1.m276() && !var2.isEmpty()) {
         float var10 = var5 / var1.m274();
         float var11 = var3;
         float var12 = var4 + var1.m272() * var10;
         float var13 = (float)(var6 >> 16 & 0xFF) / 255.0F;
         float var14 = (float)(var6 >> 8 & 0xFF) / 255.0F;
         float var15 = (float)(var6 & 0xFF) / 255.0F;
         float var16 = (float)(var6 >> 24 & 0xFF) / 255.0F;
         float var17 = (float)(var7 >> 16 & 0xFF) / 255.0F;
         float var18 = (float)(var7 >> 8 & 0xFF) / 255.0F;
         float var19 = (float)(var7 & 0xFF) / 255.0F;
         float var20 = (float)(var7 >> 24 & 0xFF) / 255.0F;
         f7.clear();
         f7.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         f7.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         f7.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         f7.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         f7.putFloat(var13).putFloat(var14).putFloat(var15).putFloat(var16);
         f7.putFloat(var17).putFloat(var18).putFloat(var19).putFloat(var20);
         f7.putFloat(var1.m273()).putFloat(var8).putFloat(0.0F).putFloat(0.0F);
         f7.putFloat(var9).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
         f7.flip();
         f8.clear();
         int var21 = 0;

         for (int var22 = 0; var22 < var2.length() && var21 < 128; var22++) {
            char var23 = var2.charAt(var22);
            FontGlyph var24 = var1.m269(var23);
            if (var24 == null) {
               var24 = var1.m269(63);
               if (var24 == null) {
                  continue;
               }
            }

            if (var24.f5 > 0.0F && var24.f6 > 0.0F) {
               float var25 = var11 + var24.f11 * var10;
               float var26 = var12 - var24.f12 * var10;
               float var27 = var24.f5 * var10;
               float var28 = var24.f6 * var10;
               f8.putFloat(var25).putFloat(var26).putFloat(var27).putFloat(var28);
               f8.putFloat(var24.f7).putFloat(var24.f8);
               f8.putFloat(var24.f9 - var24.f7).putFloat(var24.f10 - var24.f8);
               var21++;
            }

            var11 += var24.f2 * var10;
         }

         if (var21 != 0) {
            f8.flip();
            m320(var1, var21);
         }
      }
   }

   private static void m320(FontAtlas var0, int var1) {
      GpuSampler var2 = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
      Framebuffer var3 = MinecraftClient.getInstance().getFramebuffer();
      CommandEncoder var4 = RenderSystem.getDevice().createCommandEncoder();
      var4.writeToBuffer(f5.slice(), f7);
      var4.writeToBuffer(f6.slice(), f8);
      RenderPass var5 = var4.createRenderPass(
         () -> "MsdfGlowPipeline",
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
