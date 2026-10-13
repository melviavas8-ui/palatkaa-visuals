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
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public final class Sampler0_3 {
   private static final int f1 = 256;
   private static final RenderPipeline f2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[0])
         .withLocation(Identifier.of("daamky", "wet_world_copy"))
         .withVertexShader(Identifier.of("daamky", "wet_world_vertex"))
         .withFragmentShader(Identifier.of("daamky", "wet_world_copy_fragment"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withSampler("Sampler0")
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static final RenderPipeline f3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[0])
         .withLocation(Identifier.of("daamky", "wet_world"))
         .withVertexShader(Identifier.of("daamky", "wet_world_vertex"))
         .withFragmentShader(Identifier.of("daamky", "wet_world_fragment"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withSampler("Sampler1")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static SimpleFramebuffer f4;
   private static GpuBuffer f5;
   private static GpuBuffer f6;

   private Sampler0_3() {
   }

   public static void m349(
      Matrix4f var0, Vector3f var1, Vector3f var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, int var11
   ) {
      MinecraftClient var12 = MinecraftClient.getInstance();
      Framebuffer var13 = var12.getFramebuffer();
      if (var13 != null && var13.getColorAttachmentView() != null && var13.getDepthAttachmentView() != null) {
         Framebuffer var14 = m350(var13);
         if (var14 != null) {
            m314();
            if (f5 != null && f6 != null) {
               CommandEncoder var15 = RenderSystem.getDevice().createCommandEncoder();
               GpuSampler var16 = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
               GpuSampler var17 = RenderSystem.getSamplerCache().get(FilterMode.NEAREST);
               if (var13.getDepthAttachment() != null && var14.getDepthAttachment() != null) {
                  var14.copyDepthFrom(var13);
               }

               RenderPass var18 = var15.createRenderPass(
                  () -> "daamky:wet_world_copy",
                  var14.getColorAttachmentView(),
                  OptionalInt.empty(),
                  var14.getDepthAttachmentView(),
                  OptionalDouble.empty()
               );

               try {
                  var18.setPipeline(f2);
                  var18.setVertexBuffer(0, f6);
                  var18.bindTexture("Sampler0", var13.getColorAttachmentView(), var17);
                  var18.draw(0, 3);
               } catch (Throwable var28) {
                  if (var18 != null) {
                     try {
                        var18.close();
                     } catch (Throwable var26) {
                        var28.addSuppressed(var26);
                     }
                  }

                  throw var28;
               }

               if (var18 != null) {
                  var18.close();
               }

               Matrix4f var29 = new Matrix4f(var0).invert();
               ByteBuffer var19 = MemoryUtil.memAlloc(256);
               var19.putFloat((float)var13.textureWidth)
                  .putFloat((float)var13.textureHeight)
                  .putFloat((float)(System.currentTimeMillis() % 1000000L) / 1000.0F)
                  .putFloat((float)var11);
               var19.putFloat(var6).putFloat(var7).putFloat(var8).putFloat(var9);
               var19.putFloat(var1.x).putFloat(var1.y).putFloat(var1.z).putFloat(var10);
               var19.putFloat(var3).putFloat(var4).putFloat(var5).putFloat(0.0F);
               var19.putFloat(var2.x).putFloat(var2.y).putFloat(var2.z).putFloat(0.0F);
               float[] var20 = new float[16];
               var0.get(var20);

               for (float var24 : var20) {
                  var19.putFloat(var24);
               }

               var29.get(var20);

               for (float var34 : var20) {
                  var19.putFloat(var34);
               }

               while (var19.hasRemaining()) {
                  var19.putFloat(0.0F);
               }

               var19.flip();
               var15.writeToBuffer(f5.slice(), var19);
               MemoryUtil.memFree(var19);
               RenderPass var31 = var15.createRenderPass(
                  () -> "daamky:wet_world",
                  var13.getColorAttachmentView(),
                  OptionalInt.empty(),
                  var13.getDepthAttachmentView(),
                  OptionalDouble.empty()
               );

               try {
                  var31.setPipeline(f3);
                  var31.setVertexBuffer(0, f6);
                  var31.setUniform("Uniforms", f5);
                  var31.bindTexture("Sampler0", var14.getColorAttachmentView(), var16);
                  var31.bindTexture("Sampler1", var14.getDepthAttachmentView(), var17);
                  var31.draw(0, 3);
               } catch (Throwable var27) {
                  if (var31 != null) {
                     try {
                        var31.close();
                     } catch (Throwable var25) {
                        var27.addSuppressed(var25);
                     }
                  }

                  throw var27;
               }

               if (var31 != null) {
                  var31.close();
               }
            }
         }
      }
   }

   private static Framebuffer m350(Framebuffer var0) {
      int var1 = var0.textureWidth;
      int var2 = var0.textureHeight;
      if (var1 > 0 && var2 > 0) {
         if (f4 == null) {
            f4 = new SimpleFramebuffer("daamky_wet_world_scene", var1, var2, true);
         } else if (f4.textureWidth != var1 || f4.textureHeight != var2) {
            f4.resize(var1, var2);
         }

         return f4.getColorAttachmentView() == null ? null : f4;
      } else {
         return null;
      }
   }

   private static void m314() {
      if (f5 == null) {
         f5 = RenderSystem.getDevice().createBuffer(() -> "daamky:wet_world_uniforms", 136, 256L);
      }

      if (f6 == null) {
         ByteBuffer var0 = MemoryUtil.memAlloc(4);
         var0.putInt(0);
         var0.flip();
         f6 = RenderSystem.getDevice().createBuffer(() -> "daamky:wet_world_dummy_vertex", 32, var0);
         MemoryUtil.memFree(var0);
      }
   }

   public static void m63() {
      if (f4 != null) {
         f4.delete();
         f4 = null;
      }

      if (f5 != null) {
         f5.close();
         f5 = null;
      }

      if (f6 != null) {
         f6.close();
         f6 = null;
      }
   }
}
