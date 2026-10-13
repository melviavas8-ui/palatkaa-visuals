package ua.daamky.utils;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class SkyShaderRenderer {
   private static final int f1 = 112;
   private static final RenderPipeline f2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[0])
         .withLocation(Identifier.of("daamky", "shader_sky"))
         .withVertexShader(Identifier.of("daamky", "shader_sky_vertex"))
         .withFragmentShader(Identifier.of("daamky", "shader_sky_fragment"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static GpuBuffer f3;
   private static GpuBuffer f4;

   private SkyShaderRenderer() {
   }

   public static void m327(Color var0, float var1, float var2, int var3, Vector3f var4, Vector3f var5, Vector3f var6) {
      MinecraftClient var7 = MinecraftClient.getInstance();
      Framebuffer var8 = var7.getFramebuffer();
      if (var8 != null && var8.getColorAttachmentView() != null) {
         m314();
         if (f3 != null && f4 != null) {
            ByteBuffer var9 = MemoryUtil.memAlloc(112);
            var9.putFloat((float)var8.textureWidth).putFloat((float)var8.textureHeight).putFloat(0.0F).putFloat(0.0F);
            var9.putFloat((float)var0.getRed() / 255.0F).putFloat((float)var0.getGreen() / 255.0F).putFloat((float)var0.getBlue() / 255.0F).putFloat(1.0F);
            var9.putFloat(Math.clamp(var1, 0.0F, 1.0F))
               .putFloat(Math.max(0.01F, var2))
               .putFloat(0.0F)
               .putFloat((float)(System.currentTimeMillis() % 100000L) / 1000.0F);
            var9.putFloat((float)var3).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
            var9.putFloat(var4.x).putFloat(var4.y).putFloat(var4.z).putFloat(0.0F);
            var9.putFloat(var5.x).putFloat(var5.y).putFloat(var5.z).putFloat(0.0F);
            var9.putFloat(var6.x).putFloat(var6.y).putFloat(var6.z).putFloat(0.0F);
            var9.flip();
            CommandEncoder var10 = RenderSystem.getDevice().createCommandEncoder();
            var10.writeToBuffer(f3.slice(), var9);
            MemoryUtil.memFree(var9);
            RenderPass var11 = var10.createRenderPass(
               () -> "daamky:shader_sky",
               var8.getColorAttachmentView(),
               OptionalInt.empty(),
               var8.getDepthAttachmentView(),
               OptionalDouble.empty()
            );

            try {
               var11.setPipeline(f2);
               var11.setVertexBuffer(0, f4);
               var11.setUniform("Uniforms", f3);
               var11.draw(0, 3);
            } catch (Throwable var15) {
               if (var11 != null) {
                  try {
                     var11.close();
                  } catch (Throwable var14) {
                     var15.addSuppressed(var14);
                  }
               }

               throw var15;
            }

            if (var11 != null) {
               var11.close();
            }
         }
      }
   }

   private static void m314() {
      if (f3 == null) {
         f3 = RenderSystem.getDevice().createBuffer(() -> "daamky:shader_sky_uniforms", 136, 112L);
      }

      if (f4 == null) {
         ByteBuffer var0 = MemoryUtil.memAlloc(4);
         var0.putInt(0);
         var0.flip();
         f4 = RenderSystem.getDevice().createBuffer(() -> "daamky:shader_sky_dummy_vertex", 32, var0);
         MemoryUtil.memFree(var0);
      }
   }

   public static void m63() {
      if (f3 != null) {
         f3.close();
         f3 = null;
      }

      if (f4 != null) {
         f4.close();
         f4 = null;
      }
   }
}
