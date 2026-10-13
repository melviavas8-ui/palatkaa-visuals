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
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import ua.daamky.utils.render.Render2DUtil;
import java.awt.Color;
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

public class HudBlurShader {
   private static final int f1 = 256;
   private static RenderPipeline f2;
   private static GpuBuffer f3;
   // максимальный возраст копии кадра в мс (0 = копировать каждый вызов, как раньше)
   public static volatile long f8 = 0L;
   private static long f9 = 0L;
   private static GpuTexture f4;
   private static GpuTextureView f5;
   private static int f6;
   private static int f7;

   public static void m63() {
      if (f2 == null) {
         f2 = RenderPipeline.builder(new Snippet[0])
            .withLocation(Identifier.of("daamky", "hud_blur"))
            .withVertexShader(Identifier.of("daamky", "hud_blur_vertex"))
            .withFragmentShader(Identifier.of("daamky", "hud_blur_fragment"))
            .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
            .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
            .withSampler("Sampler0")
            .withBlend(BlendFunction.TRANSLUCENT)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withDepthWrite(false)
            .withCull(false)
            .build();
         f3 = RenderSystem.getDevice().createBuffer(() -> "HudBlurPipeline Uniforms", 136, 256L);
      }
   }

   public static void m318(
      Matrix4f var0,
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
      Color var11,
      float var12
   ) {
      MinecraftClient var13 = MinecraftClient.getInstance();
      Framebuffer var14 = var13.getFramebuffer();
      if (var14 != null && var14.getColorAttachment() != null) {
         if (f2 == null) {
            m63();
         }

         if (f2 != null && f3 != null) {
            int var15 = var14.textureWidth;
            int var16 = var14.textureHeight;
            int var17 = Render2DUtil.m113();
            int var18 = Render2DUtil.m189();
            m282(var15, var16);
            if (f4 != null && f5 != null) {
               float var19 = (float)var11.getRed() / 255.0F;
               float var20 = (float)var11.getGreen() / 255.0F;
               float var21 = (float)var11.getBlue() / 255.0F;
               float var22 = (float)var11.getAlpha() / 255.0F;
               float var23 = 1.0F;
               ByteBuffer var24 = MemoryUtil.memAlloc(256);
               var24.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
               var24.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
               var24.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
               var24.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
               var24.position(64);
               var24.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var4);
               var24.putFloat((float)var17).putFloat((float)var18).putFloat(0.0F).putFloat(0.0F);
               var24.putFloat(var6).putFloat(var7).putFloat(var5).putFloat(var8);
               var24.putFloat(var9).putFloat(var23).putFloat(Math.clamp(var10, 0.0F, 1.0F)).putFloat(var22);
               var24.putFloat(var19).putFloat(var20).putFloat(var21).putFloat(0.0F);
               var24.putFloat(var12).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
               var24.flip();
               CommandEncoder var25 = RenderSystem.getDevice().createCommandEncoder();
               long var40 = System.nanoTime();
               if (f8 <= 0L || f9 == 0L || (var40 - f9) > f8 * 1000000L) {
                  var25.copyTextureToTexture(var14.getColorAttachment(), f4, 0, 0, 0, 0, 0, var15, var16);
                  f9 = var40;
               }

               var25.writeToBuffer(f3.slice(), var24);
               MemoryUtil.memFree(var24);
               GpuSampler var26 = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
               RenderPass var27 = var25.createRenderPass(
                  () -> "HudBlurPipeline",
                  var14.getColorAttachmentView(),
                  OptionalInt.empty(),
                  var14.getDepthAttachmentView(),
                  OptionalDouble.of(1.0)
               );

               try {
                  var27.setPipeline(f2);
                  var27.setUniform("Uniforms", f3);
                  var27.bindTexture("Sampler0", f5, var26);
                  var27.draw(0, 6);
               } catch (Throwable var31) {
                  if (var27 != null) {
                     try {
                        var27.close();
                     } catch (Throwable var30) {
                        var31.addSuppressed(var30);
                     }
                  }

                  throw var31;
               }

               if (var27 != null) {
                  var27.close();
               }
            }
         }
      }
   }

   private static void m282(int var0, int var1) {
      if (f4 == null || var0 != f6 || var1 != f7) {
         if (f5 != null) {
            f5.close();
            f5 = null;
         }

         if (f4 != null) {
            f4.close();
            f4 = null;
         }

         f4 = RenderSystem.getDevice()
            .createTexture(() -> "daamky:hud_blur_copy", 5, TextureFormat.RGBA8, var0, var1, 1, 1);
         f5 = RenderSystem.getDevice().createTextureView(f4);
         f6 = var0;
         f7 = var1;
         f9 = 0L;
      }
   }

   public static void m314() {
      if (f3 != null) {
         f3.close();
         f3 = null;
      }

      if (f5 != null) {
         f5.close();
         f5 = null;
      }

      if (f4 != null) {
         f4.close();
         f4 = null;
      }

      f6 = 0;
      f7 = 0;
   }
}
