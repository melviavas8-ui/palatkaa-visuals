package ua.daamky.utils.render.shaders;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import java.util.function.Function;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Sampler0 {
   private static final RenderPipeline f1 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.ENTITY_EMISSIVE_SNIPPET})
         .withLocation(Identifier.of("daamky", "additive_textured_billboard"))
         .withShaderDefine("PER_FACE_LIGHTING")
         .withSampler("Sampler1")
         .withBlend(BlendFunction.LIGHTNING)
         .withCull(false)
         .withDepthWrite(false)
         .build()
   );
   public static final Function<Identifier, RenderLayer> f2 = Util.memoize(
      var0 -> RenderLayer.of(
            "daamky_additive_textured_billboard",
            RenderSetup.builder(f1).texture("Sampler0", var0).useOverlay().translucent().expectedBufferSize(1536).build()
         )
   );
   private static final RenderPipeline f3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.ENTITY_EMISSIVE_SNIPPET})
         .withLocation(
            Identifier.of("daamky", "additive_textured_billboard_no_depth")
         )
         .withShaderDefine("PER_FACE_LIGHTING")
         .withSampler("Sampler1")
         .withBlend(BlendFunction.LIGHTNING)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   public static final Function<Identifier, RenderLayer> f4 = Util.memoize(
      var0 -> RenderLayer.of(
            "daamky_additive_textured_billboard_no_depth",
            RenderSetup.builder(f3).texture("Sampler0", var0).useOverlay().translucent().expectedBufferSize(1536).build()
         )
   );
   private static final RenderPipeline f5 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.ENTITY_EMISSIVE_SNIPPET})
         .withLocation(Identifier.of("daamky", "textured_billboard_no_depth"))
         .withShaderDefine("PER_FACE_LIGHTING")
         .withSampler("Sampler1")
         .withBlend(BlendFunction.TRANSLUCENT)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   private static final Function<Identifier, RenderLayer> f6 = Util.memoize(
      var0 -> RenderLayer.of(
            "daamky_textured_billboard_no_depth",
            RenderSetup.builder(f5).texture("Sampler0", var0).useOverlay().translucent().expectedBufferSize(1536).build()
         )
   );

   private Sampler0() {
   }

   public static Sampler0$2 m173(WorldRenderContext var0, Identifier var1) {
      return m177(var0, RenderLayers.entityTranslucentEmissive(var1, false));
   }

   public static Sampler0$2 m174(WorldRenderContext var0, Identifier var1) {
      return m177(var0, f2.apply(var1));
   }

   public static Sampler0$2 m175(WorldRenderContext var0, Identifier var1) {
      return m177(var0, f4.apply(var1));
   }

   public static Sampler0$2 m176(WorldRenderContext var0, Identifier var1) {
      return m177(var0, f6.apply(var1));
   }

   public static Sampler0$2 m177(WorldRenderContext var0, RenderLayer var1) {
      Vec3d var2 = var0.worldState().cameraRenderState.pos;
      Quaternionf var3 = var0.worldState().cameraRenderState.orientation;
      Vector3f var4 = var3.transform(new Vector3f(1.0F, 0.0F, 0.0F));
      Vector3f var5 = var3.transform(new Vector3f(0.0F, 1.0F, 0.0F));
      VertexConsumer var6 = var0.consumers().getBuffer(var1);
      return new Sampler0$2(var6, var2, var4, var5);
   }

   public static Sampler0$4 m178(WorldRenderContext var0, Identifier var1) {
      Vec3d var2 = var0.worldState().cameraRenderState.pos;
      VertexConsumer var3 = var0.consumers().getBuffer(RenderLayers.entityTranslucentEmissive(var1, false));
      return new Sampler0$4(var3, var2);
   }

   public static Sampler0$4 m179(WorldRenderContext var0, Identifier var1) {
      Vec3d var2 = var0.worldState().cameraRenderState.pos;
      VertexConsumer var3 = var0.consumers().getBuffer(f2.apply(var1));
      return new Sampler0$4(var3, var2);
   }

   public static void m180(WorldRenderContext var0, Identifier var1, Vec3d var2, float var3, float var4, float var5) {
      m173(var0, var1).m162(var2, var3, var4, var5);
   }

   public static void m181(WorldRenderContext var0, Identifier var1, Vec3d var2, float var3, float var4, float var5) {
      m174(var0, var1).m162(var2, var3, var4, var5);
   }

   public static void m182(WorldRenderContext var0, Identifier var1, Vec3d var2, float var3, float var4, float var5, float var6, float var7) {
      m174(var0, var1).m164(var2, var3, var4, var5, var6, var7);
   }

   public static void m183(WorldRenderContext var0, Identifier var1, Identifier var2, Vec3d var3, float var4, float var5, float var6, float var7, float var8) {
      m174(var0, var2).m162(var3, var4 * var7, var5, var6 * var8);
      m174(var0, var1).m162(var3, var4, var5, var6);
   }

   public static Sampler0$3 m184(WorldRenderContext var0, Identifier var1) {
      Vec3d var2 = var0.worldState().cameraRenderState.pos;
      VertexConsumer var3 = var0.consumers().getBuffer(f4.apply(var1));
      return new Sampler0$3(var3, var2);
   }

   public static Sampler0$1 m185(WorldRenderContext var0, Identifier var1) {
      Vec3d var2 = var0.worldState().cameraRenderState.pos;
      VertexConsumer var3 = var0.consumers().getBuffer(f4.apply(var1));
      return new Sampler0$1(var3, var2);
   }
}
