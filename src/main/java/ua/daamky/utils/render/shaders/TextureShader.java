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
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
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

public class TextureShader {
   private static RenderPipeline f1;
   private static GpuBuffer f2;
   private static final int f3 = 256;

   public static void m63() {
      if (f1 == null) {
         f1 = RenderPipeline.builder(new Snippet[0])
            .withLocation(Identifier.of("daamky", "texture"))
            .withVertexShader(Identifier.of("daamky", "texture_vertex"))
            .withFragmentShader(Identifier.of("daamky", "texture_fragment"))
            .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
            .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
            .withSampler("Sampler0")
            .withBlend(BlendFunction.TRANSLUCENT)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withCull(false)
            .build();
         f2 = RenderSystem.getDevice().createBuffer(() -> "TexturePipeline Uniforms", 136, 256L);
      }
   }

   public static void m341(Matrix4f var0, float var1, float var2, float var3, float var4, GpuTextureView var5, int var6, float var7, float var8) {
      m345(var0, var1, var2, var3, var4, var5, var6, var7, var8, 0.0F, 0.0F, 1.0F, 1.0F);
   }

   public static void m342(Matrix4f var0, float var1, float var2, float var3, GpuTextureView var4, int var5, float var6, float var7) {
      m345(var0, var1, var2, var3, var3, var4, var5, var6, var7, 0.0F, 0.0F, 1.0F, 1.0F);
   }

   public static void m343(
      Matrix4f var0,
      float var1,
      float var2,
      float var3,
      GpuTextureView var4,
      int var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11
   ) {
      m346(var0, var1, var2, var3, var3, var4, var5, var6, var7, var8, var9, var10, var11, true);
   }

   public static void m344(
      Matrix4f var0,
      float var1,
      float var2,
      float var3,
      GpuTextureView var4,
      int var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      boolean var12
   ) {
      m346(var0, var1, var2, var3, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   public static void m345(
      Matrix4f var0,
      float var1,
      float var2,
      float var3,
      float var4,
      GpuTextureView var5,
      int var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12
   ) {
      m346(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, true);
   }

   public static void m346(
      Matrix4f var0,
      float var1,
      float var2,
      float var3,
      float var4,
      GpuTextureView var5,
      int var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      boolean var13
   ) {
      if (f1 == null) {
         m63();
      }

      if (f1 != null && f2 != null && var5 != null) {
         float var14 = (float)(var6 >> 16 & 0xFF) / 255.0F;
         float var15 = (float)(var6 >> 8 & 0xFF) / 255.0F;
         float var16 = (float)(var6 & 0xFF) / 255.0F;
         float var17 = (float)(var6 >> 24 & 0xFF) / 255.0F;
         ByteBuffer var18 = MemoryUtil.memAlloc(256);
         var18.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         var18.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         var18.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         var18.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         var18.position(64);
         var18.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var4);
         var18.putFloat(var14).putFloat(var15).putFloat(var16).putFloat(var17);
         var18.putFloat(var7).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
         var18.putFloat(var8).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
         var18.putFloat(var9).putFloat(var10).putFloat(var11).putFloat(var12);
         var18.flip();
         CommandEncoder var19 = RenderSystem.getDevice().createCommandEncoder();
         var19.writeToBuffer(f2.slice(), var18);
         MemoryUtil.memFree(var18);
         GpuSampler var20 = RenderSystem.getSamplerCache().get(var13 ? FilterMode.LINEAR : FilterMode.NEAREST);
         Framebuffer var21 = MinecraftClient.getInstance().getFramebuffer();
         RenderPass var22 = var19.createRenderPass(
            () -> "TexturePipeline",
            var21.getColorAttachmentView(),
            OptionalInt.empty(),
            var21.getDepthAttachmentView(),
            OptionalDouble.of(1.0)
         );

         try {
            var22.setPipeline(f1);
            var22.setUniform("Uniforms", f2);
            var22.bindTexture("Sampler0", var5, var20);
            var22.draw(0, 6);
         } catch (Throwable var26) {
            if (var22 != null) {
               try {
                  var22.close();
               } catch (Throwable var25) {
                  var26.addSuppressed(var25);
               }
            }

            throw var26;
         }

         if (var22 != null) {
            var22.close();
         }
      }
   }

   public static void m314() {
      if (f2 != null) {
         f2.close();
         f2 = null;
      }
   }
}
