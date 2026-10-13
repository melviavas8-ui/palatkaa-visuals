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

public class GlowShader {
   private static RenderPipeline f1;
   private static GpuBuffer f2;
   private static final int f3 = 128;

   public static void m63() {
      if (f1 == null) {
         try {
            f1 = RenderPipeline.builder(new Snippet[0])
               .withLocation(Identifier.of("daamky", "glow"))
               .withVertexShader(Identifier.of("daamky", "glow_vertex"))
               .withFragmentShader(Identifier.of("daamky", "glow_fragment"))
               .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
               .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
               .withBlend(BlendFunction.TRANSLUCENT)
               .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
               .withCull(false)
               .build();
            f2 = RenderSystem.getDevice().createBuffer(() -> "GlowPipeline Uniforms", 136, 128L);
         } catch (Exception var1) {
         }
      }
   }

   public static void m317(Matrix4f var0, float var1, float var2, float var3, int var4, float var5) {
      if (f1 == null) {
         m63();
      }

      if (f1 != null && f2 != null) {
         float var6 = (float)(var4 >> 16 & 0xFF) / 255.0F;
         float var7 = (float)(var4 >> 8 & 0xFF) / 255.0F;
         float var8 = (float)(var4 & 0xFF) / 255.0F;
         float var9 = (float)(var4 >> 24 & 0xFF) / 255.0F;
         ByteBuffer var10 = MemoryUtil.memAlloc(128);
         var10.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         var10.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         var10.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         var10.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         var10.position(64);
         var10.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var5);
         var10.putFloat(var6).putFloat(var7).putFloat(var8).putFloat(var9);
         var10.flip();
         CommandEncoder var11 = RenderSystem.getDevice().createCommandEncoder();
         var11.writeToBuffer(f2.slice(), var10);
         MemoryUtil.memFree(var10);
         Framebuffer var12 = MinecraftClient.getInstance().getFramebuffer();
         RenderPass var13 = var11.createRenderPass(
            () -> "GlowPipeline",
            var12.getColorAttachmentView(),
            OptionalInt.empty(),
            var12.getDepthAttachmentView(),
            OptionalDouble.of(1.0)
         );

         try {
            var13.setPipeline(f1);
            var13.setUniform("Uniforms", f2);
            var13.draw(0, 6);
         } catch (Throwable var17) {
            if (var13 != null) {
               try {
                  var13.close();
               } catch (Throwable var16) {
                  var17.addSuppressed(var16);
               }
            }

            throw var17;
         }

         if (var13 != null) {
            var13.close();
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
