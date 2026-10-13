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

public class SweepShader {
   private static RenderPipeline f1;
   private static GpuBuffer f2;
   private static final int f3 = 128;

   public static void m63() {
      if (f1 == null) {
         try {
            f1 = RenderPipeline.builder(new Snippet[0])
               .withLocation(Identifier.of("daamky", "sweep"))
               .withVertexShader(Identifier.of("daamky", "sweep_vertex"))
               .withFragmentShader(Identifier.of("daamky", "sweep_fragment"))
               .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
               .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
               .withBlend(BlendFunction.TRANSLUCENT)
               .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
               .withCull(false)
               .build();
            f2 = RenderSystem.getDevice().createBuffer(() -> "SweepPipeline Uniforms", 136, 128L);
         } catch (Exception var1) {
         }
      }
   }

   public static void m328(
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
      float var11,
      float var12
   ) {
      if (f1 == null) {
         m63();
      }

      if (f1 != null && f2 != null) {
         ByteBuffer var13 = MemoryUtil.memAlloc(128);
         var13.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         var13.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         var13.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         var13.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         var13.position(64);
         var13.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var4);
         var13.putFloat(var6).putFloat(var7).putFloat(var5).putFloat(var8);
         var13.putFloat(var9);
         var13.putFloat(var10);
         var13.putFloat(var11);
         var13.putFloat(var12);
         var13.putFloat(0.0F).putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);
         var13.flip();
         CommandEncoder var14 = RenderSystem.getDevice().createCommandEncoder();
         var14.writeToBuffer(f2.slice(), var13);
         MemoryUtil.memFree(var13);
         Framebuffer var15 = MinecraftClient.getInstance().getFramebuffer();
         RenderPass var16 = var14.createRenderPass(
            () -> "SweepPipeline",
            var15.getColorAttachmentView(),
            OptionalInt.empty(),
            var15.getDepthAttachmentView(),
            OptionalDouble.of(1.0)
         );

         try {
            var16.setPipeline(f1);
            var16.setUniform("Uniforms", f2);
            var16.draw(0, 6);
         } catch (Throwable var20) {
            if (var16 != null) {
               try {
                  var16.close();
               } catch (Throwable var19) {
                  var20.addSuppressed(var19);
               }
            }

            throw var20;
         }

         if (var16 != null) {
            var16.close();
         }
      }
   }
}
