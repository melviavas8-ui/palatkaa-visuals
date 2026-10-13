package ua.daamky.gui;

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
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
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
import org.lwjgl.system.MemoryUtil;

public class Daamky_chams_mask {
   private static final int f1 = 128;
   public static Matrix4f f2;
   private static final RenderPipeline f3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[0])
         .withLocation(Identifier.of("daamky", "chams_post"))
         .withVertexShader(Identifier.of("daamky", "chams_post_vertex"))
         .withFragmentShader(Identifier.of("daamky", "chams_post_fragment"))
         .withVertexFormat(VertexFormats.EMPTY, DrawMode.TRIANGLES)
         .withUniform("Uniforms", UniformType.UNIFORM_BUFFER)
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withSampler("Sampler0")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   private static SimpleFramebuffer f4;
   private static SimpleFramebuffer f5;
   private static GpuBuffer f6;
   private static GpuBuffer f7;
   private static boolean f8;
   private static boolean f9;
   private static boolean f10;
   private static boolean f11;

   private Daamky_chams_mask() {
   }

   public static Framebuffer m291() {
      return m292(false);
   }

   private static Framebuffer m292(boolean var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      Framebuffer var2 = var1.getFramebuffer();
      if (var2 != null && var2.getColorAttachmentView() != null) {
         int var3 = var2.textureWidth;
         int var4 = var2.textureHeight;
         SimpleFramebuffer var5 = var0 ? f5 : f4;
         if (f4 == null) {
            f4 = new SimpleFramebuffer("daamky_chams_mask", var3, var4, true);
         }

         if (f5 == null) {
            f5 = new SimpleFramebuffer("daamky_chams_friend_mask", var3, var4, true);
         }

         var5 = var0 ? f5 : f4;
         if (var5.textureWidth != var3 || var5.textureHeight != var4) {
            var5.resize(var3, var4);
         }

         return var5;
      } else {
         return null;
      }
   }

   private static void m293() {
      m4(false);
   }

   private static void m4(boolean var0) {
      Framebuffer var1 = m292(var0);
      if (var1 != null && var1.getColorAttachment() != null) {
         CommandEncoder var2 = RenderSystem.getDevice().createCommandEncoder();
         var2.clearColorTexture(var1.getColorAttachment(), 0);
         if (var1.getDepthAttachment() != null) {
            var2.clearDepthTexture(var1.getDepthAttachment(), 1.0);
         }
      }
   }

   public static void m294(Runnable var0, boolean var1) {
      m293();
      if (var1) {
         m277();
      }

      m5(var0);
      f10 = true;
   }

   public static void m295(Runnable var0, boolean var1) {
      m297(var0, var1, false);
   }

   public static void m296(Runnable var0, boolean var1) {
      m297(var0, var1, true);
   }

   private static void m297(Runnable var0, boolean var1, boolean var2) {
      if (!f9) {
         m4(false);
         m4(true);
         if (var1) {
            m300(false);
            m300(true);
         }

         f9 = true;
      }

      m298(var0, var2);
      if (var2) {
         f11 = true;
      } else {
         f10 = true;
      }
   }

   private static void m5(Runnable var0) {
      m298(var0, false);
   }

   private static void m298(Runnable var0, boolean var1) {
      Framebuffer var2 = m292(var1);
      if (var2 != null && var2.getColorAttachmentView() != null) {
         GpuTextureView var3 = RenderSystem.outputColorTextureOverride;
         GpuTextureView var4 = RenderSystem.outputDepthTextureOverride;

         try {
            f8 = true;
            RenderSystem.outputColorTextureOverride = var2.getColorAttachmentView();
            RenderSystem.outputDepthTextureOverride = var2.getDepthAttachmentView();
            var0.run();
         } finally {
            RenderSystem.outputColorTextureOverride = var3;
            RenderSystem.outputDepthTextureOverride = var4;
            f8 = false;
         }
      }
   }

   public static boolean m81() {
      return f8;
   }

   public static boolean m6() {
      return f10 || f11;
   }

   public static boolean m101() {
      return f10;
   }

   public static boolean m31() {
      return f11;
   }

   public static void m299() {
      f9 = false;
      f10 = false;
      f11 = false;
   }

   private static void m277() {
      m300(false);
   }

   private static void m300(boolean var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      Framebuffer var2 = var1.getFramebuffer();
      Framebuffer var3 = m292(var0);
      if (var2 != null && var3 != null && var2.getDepthAttachment() != null && var3.getDepthAttachment() != null) {
         var3.copyDepthFrom(var2);
      }
   }

   public static void m301(Color var0, float var1, int var2) {
      m307(var0, var1, var2, 0, 0.0F, 0.0F, false, false);
   }

   public static void m302(Color var0, float var1, int var2, boolean var3) {
      m307(var0, var1, var2, 0, 0.0F, 0.0F, false, var3);
   }

   public static void m303(Color var0, float var1, int var2, int var3, float var4, float var5) {
      m307(var0, var1, var2, var3, var4, var5, false, false);
   }

   public static void m304(Color var0, float var1, int var2) {
      m307(var0, var1, var2, 0, 0.0F, 0.0F, true, false);
   }

   public static void m305(Color var0, float var1, int var2, boolean var3) {
      m307(var0, var1, var2, 0, 0.0F, 0.0F, true, var3);
   }

   public static void m306(Color var0, float var1, int var2, int var3, float var4, float var5) {
      m307(var0, var1, var2, var3, var4, var5, true, false);
   }

   private static void m307(Color var0, float var1, int var2, int var3, float var4, float var5, boolean var6, boolean var7) {
      MinecraftClient var8 = MinecraftClient.getInstance();
      Framebuffer var9 = var8.getFramebuffer();
      Framebuffer var10 = m292(var6);
      if (var9 != null && var10 != null && var9.getColorAttachmentView() != null && var10.getColorAttachmentView() != null) {
         boolean var11 = var2 >= 0;
         m115();
         if (f6 != null && f7 != null) {
            ByteBuffer var12 = MemoryUtil.memAlloc(128);
            var12.putFloat((float)var9.textureWidth).putFloat((float)var9.textureHeight).putFloat(0.0F).putFloat(0.0F);
            var12.putFloat((float)var0.getRed() / 255.0F).putFloat((float)var0.getGreen() / 255.0F).putFloat((float)var0.getBlue() / 255.0F).putFloat(1.0F);
            var12.putFloat(Math.clamp(var1, 0.0F, 1.0F))
               .putFloat(var7 ? 1.0F : 0.0F)
               .putFloat(var11 ? 1.0F : 0.0F)
               .putFloat((float)(System.currentTimeMillis() % 100000L) / 1000.0F);
            var12.putFloat((float)var2).putFloat((float)var3).putFloat(Math.clamp(var4, 0.0F, 16.0F));
            var12.putFloat(Math.clamp(var5, 0.0F, 4.0F));
            if (f2 != null) {
               float[] var13 = new float[16];
               f2.get(var13);

               for (float var17 : var13) {
                  var12.putFloat(var17);
               }
            } else {
               for (int var20 = 0; var20 < 16; var20++) {
                  var12.putFloat(0.0F);
               }
            }

            var12.flip();
            CommandEncoder var21 = RenderSystem.getDevice().createCommandEncoder();
            var21.writeToBuffer(f6.slice(), var12);
            MemoryUtil.memFree(var12);
            GpuSampler var22 = RenderSystem.getSamplerCache().get(FilterMode.LINEAR);
            RenderPass var23 = var21.createRenderPass(
               () -> "daamky:chams_post",
               var9.getColorAttachmentView(),
               OptionalInt.empty(),
               var9.getDepthAttachmentView(),
               OptionalDouble.empty()
            );

            try {
               var23.setPipeline(f3);
               var23.setVertexBuffer(0, f7);
               var23.setUniform("Uniforms", f6);
               var23.bindTexture("Sampler0", var10.getColorAttachmentView(), var22);
               var23.bindTexture("Sampler1", var9.getDepthAttachmentView(), var22);
               var23.draw(0, 3);
            } catch (Throwable var19) {
               if (var23 != null) {
                  try {
                     var23.close();
                  } catch (Throwable var18) {
                     var19.addSuppressed(var18);
                  }
               }

               throw var19;
            }

            if (var23 != null) {
               var23.close();
            }
         }
      }
   }

   private static void m115() {
      if (f6 == null) {
         f6 = RenderSystem.getDevice().createBuffer(() -> "daamky:chams_uniforms", 136, 128L);
      }

      if (f7 == null) {
         ByteBuffer var0 = MemoryUtil.memAlloc(4);
         var0.putInt(0);
         var0.flip();
         f7 = RenderSystem.getDevice().createBuffer(() -> "daamky:chams_dummy_vertex", 32, var0);
         MemoryUtil.memFree(var0);
      }
   }

   public static void m308() {
      if (f4 != null) {
         f4.delete();
         f4 = null;
      }

      if (f5 != null) {
         f5.delete();
         f5 = null;
      }

      if (f6 != null) {
         f6.close();
         f6 = null;
      }

      if (f7 != null) {
         f7.close();
         f7 = null;
      }
   }
}
