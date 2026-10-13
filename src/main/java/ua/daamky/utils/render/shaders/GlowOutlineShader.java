package ua.daamky.utils.render.shaders;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

public class GlowOutlineShader {
   private static RenderPipeline f1;
   private static GpuBuffer f2;
   private static ByteBuffer f3;
   private static final int f4 = 128;

   public static void m63() {
      if (f1 == null) {
         try {
            f1 = RenderPipeline.builder(new Snippet[0])
               .withLocation(Identifier.of("daamky", "glow_outline"))
               .withVertexShader(Identifier.of("daamky", "glow_outline_vertex"))
               .withFragmentShader(Identifier.of("daamky", "glow_outline_fragment"))
               .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
               .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
               .withBlend(BlendFunction.TRANSLUCENT)
               .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
               .withCull(false)
               .build();
            f2 = RenderSystem.getDevice().createBuffer(() -> "GlowOutlinePipeline Uniforms", 136, 128L);
            f3 = MemoryUtil.memAlloc(128);
         } catch (Exception var1) {
         }
      }
   }

   public static void m316(
      Matrix4f var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      int var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12
   ) {
      if (f1 == null) {
         m63();
      }

      if (f1 != null && f2 != null && f3 != null) {
         float var13 = (float)(var6 >> 16 & 0xFF) / 255.0F;
         float var14 = (float)(var6 >> 8 & 0xFF) / 255.0F;
         float var15 = (float)(var6 & 0xFF) / 255.0F;
         float var16 = (float)(var6 >> 24 & 0xFF) / 255.0F;
         ByteBuffer var17 = f3;
         var17.clear();
         var17.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         var17.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         var17.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         var17.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         var17.position(64);
         var17.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var4);
         var17.putFloat(var7).putFloat(var5).putFloat(var9).putFloat(var11);
         var17.putFloat(var13).putFloat(var14).putFloat(var15).putFloat(var16);
         var17.putFloat(var12).putFloat(var8).putFloat(var10).putFloat(0.0F);
         var17.flip();
         CommandEncoder var18 = RenderSystem.getDevice().createCommandEncoder();
         var18.writeToBuffer(f2.slice(), var17);
         Framebuffer var19 = MinecraftClient.getInstance().getFramebuffer();
         RenderPass var20 = var18.createRenderPass(
            () -> "GlowOutlinePipeline",
            var19.getColorAttachmentView(),
            OptionalInt.empty(),
            var19.getDepthAttachmentView(),
            OptionalDouble.of(1.0)
         );

         try {
            var20.setPipeline(f1);
            var20.setUniform("Uniforms", f2);
            var20.draw(0, 6);
         } catch (Throwable var24) {
            if (var20 != null) {
               try {
                  var20.close();
               } catch (Throwable var23) {
                  var24.addSuppressed(var23);
               }
            }

            throw var24;
         }

         if (var20 != null) {
            var20.close();
         }
      }
   }

   public static void m314() {
      if (f2 != null) {
         f2.close();
         f2 = null;
      }

      if (f3 != null) {
         MemoryUtil.memFree(f3);
         f3 = null;
      }
   }
}
