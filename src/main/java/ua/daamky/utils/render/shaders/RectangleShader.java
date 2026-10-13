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

public class RectangleShader {
   private static RenderPipeline f1;
   private static GpuBuffer f2;
   private static final int f3 = 256;

   public static void m63() {
      if (f1 == null) {
         try {
            f1 = RenderPipeline.builder(new Snippet[0])
               .withLocation(Identifier.of("daamky", "rectangle"))
               .withVertexShader(Identifier.of("daamky", "rectangle_vertex"))
               .withFragmentShader(Identifier.of("daamky", "rectangle_fragment"))
               .withVertexFormat(VertexFormat.builder().build(), DrawMode.TRIANGLES)
               .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
               .withBlend(BlendFunction.TRANSLUCENT)
               .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
               .withCull(false)
               .build();
            f2 = RenderSystem.getDevice().createBuffer(() -> "RectanglePipeline Uniforms", 136, 256L);
         } catch (Exception var1) {
         }
      }
   }

   public static void m326(
      Matrix4f var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int... var10
   ) {
      if (f1 == null) {
         m63();
      }

      if (f1 != null && f2 != null) {
         int[] var11 = m325(var10);
         ByteBuffer var12 = MemoryUtil.memAlloc(256);
         var12.putFloat(var0.m00()).putFloat(var0.m01()).putFloat(var0.m02()).putFloat(var0.m03());
         var12.putFloat(var0.m10()).putFloat(var0.m11()).putFloat(var0.m12()).putFloat(var0.m13());
         var12.putFloat(var0.m20()).putFloat(var0.m21()).putFloat(var0.m22()).putFloat(var0.m23());
         var12.putFloat(var0.m30()).putFloat(var0.m31()).putFloat(var0.m32()).putFloat(var0.m33());
         var12.position(64);
         var12.putFloat(var1).putFloat(var2).putFloat(var3).putFloat(var4);
         var12.putFloat(var6).putFloat(var7).putFloat(var5).putFloat(var8);
         var12.putFloat(var9);
         var12.putFloat(0.0F).putFloat(0.0F).putFloat(0.0F);

         for (int var13 = 0; var13 < 9; var13++) {
            int var14 = var11[var13];
            var12.putFloat((float)(var14 >> 16 & 0xFF) / 255.0F);
            var12.putFloat((float)(var14 >> 8 & 0xFF) / 255.0F);
            var12.putFloat((float)(var14 & 0xFF) / 255.0F);
            var12.putFloat((float)(var14 >> 24 & 0xFF) / 255.0F);
         }

         var12.flip();
         CommandEncoder var20 = RenderSystem.getDevice().createCommandEncoder();
         var20.writeToBuffer(f2.slice(), var12);
         MemoryUtil.memFree(var12);
         Framebuffer var21 = MinecraftClient.getInstance().getFramebuffer();
         RenderPass var15 = var20.createRenderPass(
            () -> "RectanglePipeline",
            var21.getColorAttachmentView(),
            OptionalInt.empty(),
            var21.getDepthAttachmentView(),
            OptionalDouble.of(1.0)
         );

         try {
            var15.setPipeline(f1);
            var15.setUniform("Uniforms", f2);
            var15.draw(0, 6);
         } catch (Throwable var19) {
            if (var15 != null) {
               try {
                  var15.close();
               } catch (Throwable var18) {
                  var19.addSuppressed(var18);
               }
            }

            throw var19;
         }

         if (var15 != null) {
            var15.close();
         }
      }
   }

   private static int[] m325(int[] var0) {
      if (var0 != null && var0.length != 0) {
         if (var0.length == 1) {
            int var4 = var0[0];
            return new int[]{var4, var4, var4, var4, var4, var4, var4, var4, var4};
         } else if (var0.length >= 9) {
            return var0;
         } else {
            int[] var3 = new int[9];

            for (int var2 = 0; var2 < 9; var2++) {
               var3[var2] = var0[Math.min(var2, var0.length - 1)];
            }

            return var3;
         }
      } else {
         byte var1 = -1;
         return new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
      }
   }

   public static void m314() {
      if (f2 != null) {
         f2.close();
         f2 = null;
      }
   }
}
