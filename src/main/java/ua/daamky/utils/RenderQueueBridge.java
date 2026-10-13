package ua.daamky.utils;

import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.block.MovingBlockRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.RenderCommandQueue;
import net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand;
import net.minecraft.client.render.command.OrderedRenderCommandQueue.Custom;
import net.minecraft.client.render.command.OrderedRenderCommandQueue.LayeredCustom;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState.LeashData;
import net.minecraft.client.render.entity.state.EntityRenderState.ShadowPiece;
import net.minecraft.client.render.item.ItemRenderState.Glint;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BlockStateModel;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

public class RenderQueueBridge implements OrderedRenderCommandQueue, RenderCommandQueue  {
   private final Immediate f1;

   public RenderQueueBridge(Immediate var1) {
      this.f1 = var1;
   }

   public RenderCommandQueue getBatchingQueue(int order) {
      return this;
   }

   public <T> void submitModel(
      Model<? super T> model,
      T state,
      MatrixStack matrices,
      RenderLayer renderLayer,
      int light,
      int overlay,
      int tintedColor,
      Sprite sprite,
      int outlineColor,
      CrumblingOverlayCommand crumblingOverlay
   ) {
      model.setAngles(state);
      model.render(matrices, this.f1.getBuffer(renderLayer), light, overlay, tintedColor);
   }

   public void submitModelPart(
      ModelPart part,
      MatrixStack matrices,
      RenderLayer renderLayer,
      int light,
      int overlay,
      Sprite sprite,
      boolean sheeted,
      boolean hasGlint,
      int tintedColor,
      CrumblingOverlayCommand crumblingOverlay,
      int var11
   ) {
      part.render(matrices, this.f1.getBuffer(renderLayer), light, overlay, tintedColor);
   }

   public void submitShadowPieces(MatrixStack matrices, float shadowRadius, List<ShadowPiece> shadowPieces) {
   }

   public void submitLabel(
      MatrixStack matrices,
      Vec3d nameLabelPos,
      int y,
      Text label,
      boolean notSneaking,
      int light,
      double squaredDistanceToCamera,
      CameraRenderState cameraState
   ) {
   }

   public void submitText(
      MatrixStack matrices,
      float x,
      float y,
      OrderedText text,
      boolean dropShadow,
      TextLayerType layerType,
      int light,
      int color,
      int backgroundColor,
      int outlineColor
   ) {
   }

   public void submitFire(MatrixStack matrices, EntityRenderState renderState, Quaternionf rotation) {
   }

   public void submitLeash(MatrixStack matrices, LeashData leashData) {
   }

   public void submitBlock(MatrixStack matrices, BlockState state, int light, int overlay, int outlineColor) {
   }

   public void submitMovingBlock(MatrixStack matrices, MovingBlockRenderState state) {
   }

   public void submitBlockStateModel(
      MatrixStack matrices, RenderLayer renderLayer, BlockStateModel model, float r, float g, float b, int light, int overlay, int outlineColor
   ) {
   }

   public void submitItem(
      MatrixStack matrices,
      ItemDisplayContext displayContext,
      int light,
      int overlay,
      int outlineColors,
      int[] tintLayers,
      List<BakedQuad> quads,
      RenderLayer renderLayer,
      Glint glintType
   ) {
   }

   public void submitCustom(MatrixStack matrices, RenderLayer renderLayer, Custom customRenderer) {
   }

   public void submitCustom(LayeredCustom customRenderer) {
   }

   public Immediate m158() {
      return this.f1;
   }
}
