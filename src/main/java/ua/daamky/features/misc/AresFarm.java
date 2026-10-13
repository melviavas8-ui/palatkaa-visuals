package ua.daamky.features.misc;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@NewFunction(
   I0 = "AresFarm",
   I00 = "Скрытая ферма бамбука и тростника с подсветкой блоков",
   I000 = Category.MISC
)
public class AresFarm extends Module {
   private static final RenderPipeline f1 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "aresfarm_lines"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderLayer f2 = RenderLayer.of(
      "daamky_aresfarm_lines",
      RenderSetup.builder(f1).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   public static AresFarm f3;
   private final ModeSettingBase f4 = new ModeSettingBase(
      "Preset", "Aresmine", "Свой"
   );
   private final NumberSetting f5 = new NumberSetting("Scan Range", 32.0, 4.0, 32.0, 1.0);
   private final NumberSetting f6 = new NumberSetting("Break Range", 3.0, 2.0, 5.0, 0.1);
   private final NumberSetting f7 = new NumberSetting("Y Range", 2.0, 2.0, 12.0, 1.0);
   private final NumberSetting f8 = new NumberSetting("Rotate Speed", 60.0, 20.0, 180.0, 5.0);
   private final NumberSetting f9 = new NumberSetting("Aim Delay", 1.0, 1.0, 10.0, 1.0);
   private final NumberSetting f10 = new NumberSetting("Break Delay", 0.0, 0.0, 15.0, 1.0);
   private final BooleanSetting f11 = new BooleanSetting("Keep Base", false);
   private final BooleanSetting f12 = new BooleanSetting("Render", true);
   private double f13;
   private double f14;
   private double f15;
   private float f16;
   private int f17;
   private int f18;
   private boolean f19;
   private boolean f20;
   private BlockPos f21;
   private BlockPos f22;
   private Vec3d f23;
   private int f24;
   private int f25;
   private int f26;
   private int f27;

   public AresFarm() {
      f3 = this;
      this.f4.m5(this::m115);
      this.addSettings(new Setting[]{this.f4, this.f5, this.f6, this.f7, this.f8, this.f9, this.f10, this.f11, this.f12});
   }

   @Override
   public void onDisable() {
      this.f21 = null;
      this.f22 = null;
      this.f23 = null;
      this.f24 = 0;
      this.f25 = 0;
      this.f26 = 0;
      this.f27 = 0;
      RotationUtil.m314();
      if (this.mc.options != null) {
         this.mc.options.attackKey.setPressed(false);
      }
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81() && this.mc.interactionManager != null) {
         this.m116();
         if (this.f27 > 0) {
            this.f27--;
         }

         if (!this.m122(this.f21)) {
            if (this.f21 != null) {
               this.f27 = this.f18;
            }

            this.f21 = null;
            this.f22 = null;
            this.f23 = null;
            this.f25 = 0;
            this.f26 = 0;
         }

         if (this.f21 == null || this.f24-- <= 0) {
            this.f21 = this.m119();
            this.f24 = 5;
         }

         if (this.f21 == null) {
            this.m134();
         } else if (!this.m126(this.f21)) {
            this.f23 = null;
            this.m135();
            RotationUtil.m314();
         } else {
            this.f23 = this.m127(this.f21);
            if (this.f23 == null) {
               this.m135();
               this.f26 = 0;
            } else {
               this.m117(this.f23);
               if (!this.m129(this.f23)) {
                  this.m135();
                  this.f26 = 0;
               } else {
                  this.f26++;
                  if (this.f27 == 0 && this.f26 >= this.f17) {
                     this.f22 = this.f21;
                     this.m118(this.f21);
                  }
               }
            }
         }
      } else {
         this.m134();
      }
   }

   public static void m114(WorldRenderContext var0) {
      if (f3 != null && f3.isEnabled() && f3.f20) {
         f3.m136(var0);
      }
   }

   private void m115() {
      boolean var1 = this.f4.m17("Свой");
      this.f5.setVisible(var1);
      this.f6.setVisible(var1);
      this.f7.setVisible(var1);
      this.f8.setVisible(var1);
      this.f9.setVisible(var1);
      this.f10.setVisible(var1);
      this.f11.setVisible(var1);
      this.f12.setVisible(var1);
      this.m116();
   }

   private void m116() {
      if (this.f4.m17("Свой")) {
         this.f13 = this.f5.getValue();
         this.f14 = this.f6.getValue();
         this.f15 = this.f7.getValue();
         this.f16 = (float)this.f8.getValue();
         this.f17 = (int)this.f9.getValue();
         this.f18 = (int)this.f10.getValue();
         this.f19 = this.f11.m6();
         this.f20 = this.f12.m6();
      } else {
         this.f13 = 32.0;
         this.f14 = 3.0;
         this.f15 = 2.0;
         this.f16 = 60.0F;
         this.f17 = 1;
         this.f18 = 0;
         this.f19 = false;
         this.f20 = true;
      }
   }

   private void m117(Vec3d var1) {
      RotationVec var2 = RotationUtil.m417(var1);
      RotationUtil.m406(var2, RotationMode.f3, this.f16, this.f16, 90.0F);
   }

   private void m118(BlockPos var1) {
      Direction var2 = this.m133(var1);
      if (this.f25 == 0) {
         this.mc.interactionManager.attackBlock(var1, var2);
      } else {
         this.mc.interactionManager.updateBlockBreakingProgress(var1, var2);
      }

      this.mc.player.swingHand(Hand.MAIN_HAND);
      this.f25++;
   }

   private BlockPos m119() {
      BlockPos var1 = this.mc.player.getBlockPos();
      int var2 = (int)this.f13;
      int var3 = (int)this.f15;
      double var4 = this.f13 * this.f13;
      List<BlockPos> var6 = new ArrayList<>();

      for (int var7 = -var2; var7 <= var2; var7++) {
         for (int var8 = -var3; var8 <= var3; var8++) {
            for (int var9 = -var2; var9 <= var2; var9++) {
               BlockPos var10 = var1.add(var7, var8, var9);
               if (!(this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var10)) > var4) && this.m121(var10)) {
                  var6.add(var10.toImmutable());
               }
            }
         }
      }

      return var6.stream().min(Comparator.comparingDouble(var2x -> this.m120(var2x, var1))).orElse(null);
   }

   private double m120(BlockPos var1, BlockPos var2) {
      return this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var1));
   }

   private boolean m121(BlockPos var1) {
      if (!this.m123(var1)) {
         return false;
      } else {
         BlockState var2 = this.mc.world.getBlockState(var1);
         boolean var3 = this.m124(var1.down(), var2);
         boolean var4 = this.m124(var1.down(2), var2);
         return !this.f19 ? !var3 : var3 && !var4;
      }
   }

   private boolean m122(BlockPos var1) {
      return var1 != null && this.mc.world != null && this.m121(var1) && this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var1)) <= this.f13 * this.f13;
   }

   private boolean m123(BlockPos var1) {
      if (var1 != null && this.mc.world != null) {
         BlockState var2 = this.mc.world.getBlockState(var1);
         return this.m125(var2);
      } else {
         return false;
      }
   }

   private boolean m124(BlockPos var1, BlockState var2) {
      if (var1 != null && this.mc.world != null) {
         BlockState var3 = this.mc.world.getBlockState(var1);
         return !var2.isOf(Blocks.BAMBOO) && !var2.isOf(Blocks.BAMBOO_SAPLING)
            ? var2.isOf(Blocks.SUGAR_CANE) && var3.isOf(Blocks.SUGAR_CANE)
            : var3.isOf(Blocks.BAMBOO) || var3.isOf(Blocks.BAMBOO_SAPLING);
      } else {
         return false;
      }
   }

   private boolean m125(BlockState var1) {
      return var1.isOf(Blocks.BAMBOO) || var1.isOf(Blocks.BAMBOO_SAPLING) || var1.isOf(Blocks.SUGAR_CANE);
   }

   private boolean m126(BlockPos var1) {
      Vec3d var2 = this.m131(var1);
      return this.mc.player.getEyePos().squaredDistanceTo(var2) <= this.f14 * this.f14;
   }

   private Vec3d m127(BlockPos var1) {
      for (Vec3d var3 : this.m130(var1)) {
         if (this.m128(var1, var3)) {
            return var3;
         }
      }

      return null;
   }

   private boolean m128(BlockPos var1, Vec3d var2) {
      BlockHitResult var3 = this.mc.world.raycast(new RaycastContext(this.mc.player.getEyePos(), var2, ShapeType.OUTLINE, FluidHandling.NONE, this.mc.player));
      return var3.getType() == Type.BLOCK && var3.getBlockPos().equals(var1);
   }

   private boolean m129(Vec3d var1) {
      RotationVec var2 = RotationUtil.m417(var1);
      RotationVec var3 = RotationUtil.m415();
      float var4 = Math.abs(MathHelper.wrapDegrees(var2.m329() - var3.m329()));
      float var5 = Math.abs(MathHelper.wrapDegrees(var2.m271() - var3.m271()));
      return var4 <= 6.0F && var5 <= 6.0F;
   }

   private List<Vec3d> m130(BlockPos var1) {
      Box var2 = this.m132(var1).expand(0.01);
      Vec3d var3 = this.mc.player.getEyePos();
      Vec3d var4 = var2.getCenter();
      Vec3d var5 = new Vec3d(
         MathHelper.clamp(var3.x, var2.minX, var2.maxX), MathHelper.clamp(var3.y, var2.minY, var2.maxY), MathHelper.clamp(var3.z, var2.minZ, var2.maxZ)
      );
      List<Vec3d> var6 = new ArrayList<>();
      var6.add(var5);
      var6.add(var4);
      var6.add(new Vec3d(var4.x, var2.minY + var2.getLengthY() * 0.25, var4.z));
      var6.add(new Vec3d(var4.x, var2.minY + var2.getLengthY() * 0.55, var4.z));
      var6.add(new Vec3d(var4.x, var2.minY + var2.getLengthY() * 0.85, var4.z));
      return var6;
   }

   private Vec3d m131(BlockPos var1) {
      Box var2 = this.m132(var1);
      Vec3d var3 = this.mc.player.getEyePos();
      return new Vec3d(
         MathHelper.clamp(var3.x, var2.minX, var2.maxX), MathHelper.clamp(var3.y, var2.minY, var2.maxY), MathHelper.clamp(var3.z, var2.minZ, var2.maxZ)
      );
   }

   private Box m132(BlockPos var1) {
      VoxelShape var2 = this.mc.world.getBlockState(var1).getOutlineShape(this.mc.world, var1);
      return var2.isEmpty() ? new Box(var1) : var2.getBoundingBox().offset(var1);
   }

   private Direction m133(BlockPos var1) {
      Vec3d var2 = this.mc.player.getEyePos();
      Vec3d var3 = Vec3d.ofCenter(var1);
      Vec3d var4 = var2.subtract(var3);
      return Direction.getFacing(var4.x, var4.y, var4.z);
   }

   private void m134() {
      this.f21 = null;
      this.f22 = null;
      this.f23 = null;
      this.f25 = 0;
      this.f26 = 0;
      this.f27 = 0;
      RotationUtil.m314();
      if (this.mc.options != null) {
         this.mc.options.attackKey.setPressed(false);
      }
   }

   private void m135() {
      if (this.f22 != null && this.f25 > 0 && this.mc.interactionManager != null) {
         this.mc.interactionManager.cancelBlockBreaking();
      }

      this.f22 = null;
      this.f23 = null;
      this.f25 = 0;
      this.mc.options.attackKey.setPressed(false);
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.world != null && this.mc.player != null) {
         Vec3d var2 = var1.worldState().cameraRenderState.pos;
         VertexConsumer var3 = var1.consumers().getBuffer(f2);
         Entry var4 = var1.matrices().peek();
         if (this.f21 != null) {
            this.m137(var3, var4, var1, this.f21, var2, new Color(60, 255, 90), 2.0F);
         }

         if (this.f22 != null) {
            this.m137(var3, var4, var1, this.f22, var2, new Color(255, 80, 80), 3.0F);
         }
      }
   }

   private void m137(VertexConsumer var1, Entry var2, WorldRenderContext var3, BlockPos var4, Vec3d var5, Color var6, float var7) {
      VoxelShape var8 = this.mc.world.getBlockState(var4).getOutlineShape(this.mc.world, var4);
      Box var9 = (var8.isEmpty() ? new Box(var4) : var8.getBoundingBox().offset(var4)).expand(0.002).offset(-var5.x, -var5.y, -var5.z);
      this.m138(var1, var2, var9, var6, var7);
   }

   private void m138(VertexConsumer var1, Entry var2, Box var3, Color var4, float var5) {
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.maxZ, var3.minX, var3.minY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.maxZ, var3.minX, var3.minY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.minZ, var3.minX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var5);
      this.m139(var1, var2, var4, var3.maxX, var3.minY, var3.maxZ, var3.maxX, var3.maxY, var3.maxZ, var5);
      this.m139(var1, var2, var4, var3.minX, var3.minY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var5);
   }

   private void m139(VertexConsumer var1, Entry var2, Color var3, double var4, double var6, double var8, double var10, double var12, double var14, float var16) {
      float var17 = (float)(var10 - var4);
      float var18 = (float)(var12 - var6);
      float var19 = (float)(var14 - var8);
      float var20 = (float)Math.sqrt((double)(var17 * var17 + var18 * var18 + var19 * var19));
      if (!(var20 <= 1.0E-4F)) {
         var1.vertex(var2, (float)var4, (float)var6, (float)var8)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), 255)
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
         var1.vertex(var2, (float)var10, (float)var12, (float)var14)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), 255)
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
      }
   }
}
