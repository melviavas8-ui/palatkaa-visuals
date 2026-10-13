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

public class FluidGlassShader {
   private static final int f1 = 256;
   private static RenderPipeline f2;
   private static GpuBuffer f3;
   private static GpuTexture f4;
   private static GpuTextureView f5;
   private static int f6;
   private static int f7;

   public static void m63() {
      if (f2 == null) {
         f2 = RenderPipeline.builder(new Snippet[0])
            .withLocation(Identifier.of("daamky", "fluid_glass"))
            .withVertexShader(Identifier.of("daamky", "liquid_glass_vertex"))
            .withFragmentShader(Identifier.of("daamky", "fluid_glass_fragment"))
            .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
            .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
            .withSampler("Sampler0")
            .withBlend(BlendFunction.TRANSLUCENT)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withDepthWrite(false)
            .withCull(false)
            .build();
         f3 = RenderSystem.getDevice().createBuffer(() -> "FluidGlassPipeline Uniforms", 136, 256L);
      }
   }

   public static void m315(Matrix4f var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8, float var9) {
      MinecraftClient var10 = MinecraftClient.getInstance();
      Framebuffer var11 = var10.getFramebuffer();
      if (var11 != null && var11.getColorAttachment() != null) {
         if (f2 == null) {
            m63();
         }

         if (f2 != null && f3 != null) {
            int var12 = var11.textureWidth;
            int var13 = var11.textureHeight;
            int var14 = Render2DUtil.m113();
            int var15 = Render2DUtil.m189();
            m282(var12, var13);
            if (f4 != null && f5 != null) {
               float var16 = (float)(var8 >> 16 & 0xFF) / 255.0F;
               float var17 = (float)(var8 >> 8 & 0xFF) / 255.0F;
               float var18 = (float)(var8 & 0xFF) / 255.0F;
               float var19 = (float)(var8 >> 24 & 0xFF) / 255.0F;
               float var20 = (float)(System.currentTimeMillis() % 120000L) / 1000.0F;
               ByteBuffer var21 = MemoryUtil.memAlloc(256);
               var21.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
               var21.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
               var21.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
               var21.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
               var21.position(64);
               var21.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var4);
               var21.putFloat((float)var14).putFloat((float)var15).putFloat(0.0F).putFloat(0.0F);
               var21.putFloat(var5).putFloat(var5).putFloat(var5).putFloat(var5);
               var21.putFloat(var20).putFloat(Math.clamp(var6, 0.0F, 1.0F)).putFloat(Math.clamp(var7, 0.0F, 1.0F)).putFloat(var19);
               var21.putFloat(var16).putFloat(var17).putFloat(var18).putFloat(0.0F);
               var21.putFloat(var9).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
               var21.flip();
               CommandEncoder var22 = RenderSystem.getDevice().createCommandEncoder();
               var22.copyTextureToTexture(var11.getColorAttachment(), f4, 0, 0, 0, 0, 0, var12, var13);
               var22.writeToBuffer(f3.slice(), var21);
               MemoryUtil.memFree(var21);
               GpuSampler var23 = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
               RenderPass var24 = var22.createRenderPass(
                  () -> "FluidGlassPipeline",
                  var11.getColorAttachmentView(),
                  OptionalInt.empty(),
                  var11.getDepthAttachmentView(),
                  OptionalDouble.of(1.0)
               );

               try {
                  var24.setPipeline(f2);
                  var24.setUniform("Uniforms", f3);
                  var24.bindTexture("Sampler0", f5, var23);
                  var24.draw(0, 6);
               } catch (Throwable var28) {
                  if (var24 != null) {
                     try {
                        var24.close();
                     } catch (Throwable var27) {
                        var28.addSuppressed(var27);
                     }
                  }

                  throw var28;
               }

               if (var24 != null) {
                  var24.close();
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
            .createTexture(() -> "daamky:fluid_glass_copy", 5, TextureFormat.RGBA8, var0, var1, 1, 1);
         f5 = RenderSystem.getDevice().createTextureView(f4);
         f6 = var0;
         f7 = var1;
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
