package ua.daamky.utils.render.shaders;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
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
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import ua.daamky.utils.render.Render2DUtil;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

public class Sampler0_2 {
   private static final int f1 = 5;
   private static final int f2 = 2;
   private static final int f3 = 256;
   private static final RenderPipeline f4 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(Identifier.of("daamky", "blur_pass"))
         .withVertexShader(Identifier.of("daamky", "blur_pass_vertex"))
         .withFragmentShader(Identifier.of("daamky", "blur_pass_fragment"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("BlurData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final RenderPipeline f5 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET})
         .withLocation(Identifier.of("daamky", "blur_final"))
         .withVertexShader(Identifier.of("daamky", "blur_final_vertex"))
         .withFragmentShader(Identifier.of("daamky", "blur_final_fragment"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("BlurData", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final Vector4f f6 = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
   private static final Vector3f f7 = new Vector3f(0.0F, 0.0F, 0.0F);
   private static final Matrix4f f8 = new Matrix4f();
   private static GpuBuffer f9;
   private static GpuBuffer f10;
   private static ByteBuffer f11;
   private static GpuTexture f12;
   private static GpuTextureView f13;
   private static GpuTexture[] f14 = new GpuTexture[2];
   private static GpuTextureView[] f15 = new GpuTextureView[2];
   private static int f16 = 0;
   private static int f17 = 0;
   private static boolean f18 = false;
   private static long f19 = -1L;
   private static int f20 = 0;
   private static float f21 = 0.0F;

   public static void m63() {
      if (!f18) {
         f11 = MemoryUtil.memAlloc(256);
         ByteBuffer var0 = MemoryUtil.memAlloc(4);
         var0.putInt(0);
         var0.flip();
         f10 = RenderSystem.getDevice().createBuffer(() -> "daamky:blur_dummy_vertex", 32, var0);
         MemoryUtil.memFree(var0);
         f18 = true;
      }
   }

   private static void m282(int var0, int var1) {
      int var2 = var0 / 2;
      int var3 = var1 / 2;
      if (f12 == null || var0 != f16 || var1 != f17) {
         if (f13 != null) {
            f13.close();
            f13 = null;
         }

         if (f12 != null) {
            f12.close();
            f12 = null;
         }

         f12 = RenderSystem.getDevice().createTexture(() -> "daamky:blur_copy", 5, TextureFormat.RGBA8, var0, var1, 1, 1);
         f13 = RenderSystem.getDevice().createTextureView(f12);

         for (int var4 = 0; var4 < 2; var4++) {
            if (f15[var4] != null) {
               f15[var4].close();
               f15[var4] = null;
            }

            if (f14[var4] != null) {
               f14[var4].close();
               f14[var4] = null;
            }

            int var5 = var4;
            f14[var4] = RenderSystem.getDevice().createTexture(() -> "daamky:blur_pp_" + var5, 13, TextureFormat.RGBA8, var2, var3, 1, 1);
            f15[var4] = RenderSystem.getDevice().createTextureView(f14[var4]);
         }

         f16 = var0;
         f17 = var1;
         f19 = -1L;
      }
   }

   public static void m283(Matrix4f var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      MinecraftClient var8 = MinecraftClient.getInstance();
      if (var8.getFramebuffer() != null) {
         if (var8.getFramebuffer().getColorAttachment() != null) {
            m63();
            int var9 = var8.getFramebuffer().textureWidth;
            int var10 = var8.getFramebuffer().textureHeight;
            int var11 = var9 / 2;
            int var12 = var10 / 2;
            m282(var9, var10);
            long var13 = System.nanoTime() / 16666666L;
            boolean var15 = var13 != f19 || Math.abs(var6 - f21) > 0.01F;
            GpuSampler var16 = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
            GpuBufferSlice var17 = RenderSystem.getDynamicUniforms().write(RenderSystem.getModelViewMatrix(), f6, f7, f8);
            CommandEncoder var18 = RenderSystem.getDevice().createCommandEncoder();
            if (var15) {
               var18.copyTextureToTexture(var8.getFramebuffer().getColorAttachment(), f12, 0, 0, 0, 0, 0, var9, var10);
               m284(var9, var10, var11, var12, 1.0F, var6);
               var18.writeToBuffer(f9.slice(), f11);
               RenderPass var19 = var18.createRenderPass(
                  () -> "daamky:blur_downsample", f15[0], OptionalInt.empty(), null, OptionalDouble.empty()
               );

               try {
                  var19.setPipeline(f4);
                  var19.setVertexBuffer(0, f10);
                  var19.bindTexture("Sampler0", f13, var16);
                  RenderSystem.bindDefaultUniforms(var19);
                  var19.setUniform("DynamicTransforms", var17);
                  var19.setUniform("BlurData", f9);
                  var19.draw(0, 6);
               } catch (Throwable var34) {
                  if (var19 != null) {
                     try {
                        var19.close();
                     } catch (Throwable var31) {
                        var34.addSuppressed(var31);
                     }
                  }

                  throw var34;
               }

               if (var19 != null) {
                  var19.close();
               }

               int var35 = Math.max(2, (int)(5.0F * var6));
               float[] var20 = new float[]{1.0F, 2.0F, 2.0F, 3.0F};

               for (int var21 = 0; var21 < var35; var21++) {
                  int var22 = var21 % 2;
                  int var23 = (var21 + 1) % 2;
                  float var24 = var21 < var20.length ? var20[var21] : 3.0F;
                  int var25 = var21;
                  m284(var11, var12, var11, var12, var24, 1.0F);
                  var18.writeToBuffer(f9.slice(), f11);
                  RenderPass var26 = var18.createRenderPass(() -> "daamky:blur_" + var25, f15[var23], OptionalInt.empty(), null, OptionalDouble.empty());

                  try {
                     var26.setPipeline(f4);
                     var26.setVertexBuffer(0, f10);
                     var26.bindTexture("Sampler0", f15[var22], var16);
                     RenderSystem.bindDefaultUniforms(var26);
                     var26.setUniform("DynamicTransforms", var17);
                     var26.setUniform("BlurData", f9);
                     var26.draw(0, 6);
                  } catch (Throwable var33) {
                     if (var26 != null) {
                        try {
                           var26.close();
                        } catch (Throwable var30) {
                           var33.addSuppressed(var30);
                        }
                     }

                     throw var33;
                  }

                  if (var26 != null) {
                     var26.close();
                  }
               }

               f20 = var35 % 2;
               f19 = var13;
               f21 = var6;
            }

            float[] var36 = new float[]{var5, var5, var5, var5};
            int var37 = Render2DUtil.m113();
            int var38 = Render2DUtil.m189();
            m285(var0, var1, var2, var3, var4, var37, var38, var36, var7);
            var18.writeToBuffer(f9.slice(), f11);
            RenderPass var39 = var18.createRenderPass(
               () -> "daamky:blur_final",
               var8.getFramebuffer().getColorAttachmentView(),
               OptionalInt.empty(),
               var8.getFramebuffer().getDepthAttachmentView(),
               OptionalDouble.of(1.0)
            );

            try {
               var39.setPipeline(f5);
               var39.setVertexBuffer(0, f10);
               var39.bindTexture("Sampler0", f15[f20], var16);
               RenderSystem.bindDefaultUniforms(var39);
               var39.setUniform("DynamicTransforms", var17);
               var39.setUniform("BlurData", f9);
               var39.draw(0, 6);
            } catch (Throwable var32) {
               if (var39 != null) {
                  try {
                     var39.close();
                  } catch (Throwable var29) {
                     var32.addSuppressed(var29);
                  }
               }

               throw var32;
            }

            if (var39 != null) {
               var39.close();
            }
         }
      }
   }

   private static void m284(int var0, int var1, int var2, int var3, float var4, float var5) {
      f11.clear();

      for (int var6 = 0; var6 < 16; var6++) {
         f11.putFloat(0.0F);
      }

      f11.putFloat(0.0F).putFloat(0.0F).putFloat((float)var0).putFloat((float)var1);
      f11.putFloat((float)var0).putFloat((float)var1).putFloat(1.0F).putFloat(var4);
      f11.putFloat((float)var2).putFloat((float)var3).putFloat(1.0F).putFloat(var5);
      f11.putFloat(0.0F).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
      f11.putFloat(0.0F).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
      f11.flip();
      m286();
   }

   private static void m285(Matrix4f var0, float var1, float var2, float var3, float var4, int var5, int var6, float[] var7, float var8) {
      f11.clear();
      f11.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
      f11.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
      f11.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
      f11.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
      f11.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var4);
      f11.putFloat((float)var5).putFloat((float)var6).putFloat(0.0F).putFloat(0.0F);
      f11.putFloat((float)var5).putFloat((float)var6).putFloat(1.0F).putFloat(0.0F);
      f11.putFloat(var7[0]).putFloat(var7[1]).putFloat(var7[2]).putFloat(var7[3]);
      f11.putFloat(var8).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
      f11.flip();
      m286();
   }

   private static void m286() {
      int var0 = f11.remaining();
      if (f9 == null || f9.size() < (long)var0) {
         if (f9 != null) {
            f9.close();
         }

         f9 = RenderSystem.getDevice().createBuffer(() -> "daamky:blur_uniform", 136, (long)var0);
      }
   }

   public static GpuTextureView m287() {
      return f18 && f15[f20] != null ? f15[f20] : null;
   }

   public static void m29() {
      if (f9 != null) {
         f9.close();
         f9 = null;
      }

      if (f10 != null) {
         f10.close();
         f10 = null;
      }

      if (f11 != null) {
         MemoryUtil.memFree(f11);
         f11 = null;
      }

      if (f13 != null) {
         f13.close();
         f13 = null;
      }

      if (f12 != null) {
         f12.close();
         f12 = null;
      }

      for (int var0 = 0; var0 < 2; var0++) {
         if (f15[var0] != null) {
            f15[var0].close();
            f15[var0] = null;
         }

         if (f14[var0] != null) {
            f14[var0].close();
            f14[var0] = null;
         }
      }

      f16 = 0;
      f17 = 0;
      f18 = false;
      f19 = -1L;
   }
}
