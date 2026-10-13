package ua.daamky.features.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import ua.daamky.gui.Daamky_chams_mask;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.mixins.interfaces.IModelPart;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.MultiChoiceSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.utils.player.RaytraceUtil;
import java.awt.Color;
import java.util.List;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPart.Cuboid;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

@NewFunction(
   I0 = "EntityESP",
   I00 = "Текстурированные чамсы для игроков и сущностей",
   I000 = Category.RENDER
)
public class EntityESP extends Module {
   public static EntityESP f1;
   public static Immediate f2;
   public static Immediate f3;
   public static Immediate f4;
   private static final Identifier f5 = Identifier.of(
      "minecraft", "textures/block/white_concrete.png"
   );
   private static final DepthTestFunction f27 = m1047();
   public static final RenderPipeline f6 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "entitychams_glow_box_lines"))
         .withBlend(BlendFunction.LIGHTNING)
         .withDepthTestFunction(EntityESP.f27)
         .withDepthWrite(false)
         .build()
   );
   public static final RenderLayer f7 = RenderLayer.of(
      "daamky_entitychams_glow_box_lines",
      RenderSetup.builder(f6).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   public static final RenderPipeline f8 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(
            Identifier.of("daamky", "entitychams_glow_box_lines_no_depth")
         )
         .withBlend(BlendFunction.LIGHTNING)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   public static final RenderLayer f9 = RenderLayer.of(
      "daamky_entitychams_glow_box_lines_no_depth",
      RenderSetup.builder(f8).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   private static final RenderPipeline f10 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "entityesp_shader"))
         .withVertexShader(Identifier.of("daamky", "block_overlay_shader_vertex"))
         .withFragmentShader(
            Identifier.of("daamky", "block_overlay_shader_fragment")
         )
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(EntityESP.f27)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   public static final RenderLayer f11 = RenderLayer.of(
      "daamky_entityesp_shader",
      RenderSetup.builder(f10)
         .texture("Sampler0", f5)
         .translucent()
         .outputTarget(OutputTarget.ITEM_ENTITY_TARGET)
         .expectedBufferSize(1536)
         .build()
   );
   private static final RenderPipeline f12 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("daamky", "entityesp_chams_fill"))
         .withVertexShader(Identifier.of("daamky", "block_overlay_shader_vertex"))
         .withFragmentShader(
            Identifier.of("daamky", "block_overlay_chams_fill_fragment")
         )
         .withUniform("Globals", UniformType.UNIFORM_BUFFER)
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(EntityESP.f27)
         .withDepthWrite(false)
         .withCull(false)
         .build()
   );
   public static final RenderLayer f13 = RenderLayer.of(
      "daamky_entityesp_chams_fill",
      RenderSetup.builder(f12)
         .texture("Sampler0", f5)
         .translucent()
         .outputTarget(OutputTarget.ITEM_ENTITY_TARGET)
         .expectedBufferSize(1536)
         .build()
   );
   private final MultiChoiceSettingBase f14 = new MultiChoiceSettingBase(
      "Цели", "Игроки", "Сущности"
   );
   private final MultiChoiceSettingBase f15 = new MultiChoiceSettingBase(
      "Эффекты",
      "Chams",
      "Box",
      "Skeleton",
      "Обводка"
   );
   private final BooleanSetting f16 = new BooleanSetting("Друзья", false);
   private final ColorSetting f17 = new ColorSetting("Цвет друзей", new Color(85, 255, 120));
   private final BooleanSetting f18 = new BooleanSetting("Цвет темы", true);
   private final ColorSetting f19 = new ColorSetting("Цвет", new Color(255, 255, 255));
   private final NumberSetting f20 = new NumberSetting(
      "Прозрачность заливки", 0.65, 0.05, 1.0, 0.05
   );
   private final ModeSettingBase f21 = new ModeSettingBase(
      "Тип заливки",
      "Обычная",
      "Full",
      "WebShader",
      "Plasma",
      "ChamsFill",
      "Waves"
   );
   private final BooleanSetting f22 = new BooleanSetting("Не видеть сквозь стены", true);
   private final BooleanSetting f23 = new BooleanSetting("Обводка", true);
   private final ModeSettingBase f24 = new ModeSettingBase(
      "Тип обводки",
      "Обычная",
      "Glow"
   );
   private final NumberSetting f25 = new NumberSetting("Ширина обводки", 2.0, 1.0, 8.0, 0.5);
   private final NumberSetting f26 = new NumberSetting("Сила свечения", 1.15, 0.2, 3.0, 0.05);

   public EntityESP() {
      f1 = this;
      this.addSettings(
         new Setting[]{this.f14, this.f15, this.f16, this.f17, this.f18, this.f19, this.f20, this.f21, this.f22, this.f23, this.f24, this.f25, this.f26}
      );
      this.f18.m5(this::m707);
      this.f16.m5(this::m707);
      this.f15.m5(this::m707);
      this.f24.m5(this::m707);
      this.m707();
   }

   public void m1035(Runnable var1, LivingEntityRenderState var2) {
      if (this.isEnabled()) {
         if (this.f15.m20("Обводка") && var2.entityType == EntityType.PLAYER) {
            boolean var3 = this.m1041(var2);
            if (var3) {
               Daamky_chams_mask.m296(var1, this.f22.m6());
            } else {
               Daamky_chams_mask.m295(var1, this.f22.m6());
            }
         } else {
            var1.run();
         }
      }
   }

   private int m751() {
      if (!this.f15.m20("Обводка")) {
         return 0;
      } else {
         return this.f24.m17("Glow") ? 2 : 1;
      }
   }

   public void m115() {
      if (this.isEnabled() && this.f15.m20("Обводка")) {
         int var1 = this.m751();
         if (var1 != 0) {
            if (Daamky_chams_mask.m101()) {
               Color var2 = this.m1042();
               Daamky_chams_mask.m303(var2, 0.0F, -1, var1, (float)this.f25.getValue(), (float)this.f26.getValue());
            }

            if (Daamky_chams_mask.m31()) {
               Color var3 = this.f17.m7();
               Daamky_chams_mask.m306(var3, 0.0F, -1, var1, (float)this.f25.getValue(), (float)this.f26.getValue());
            }
         }
      }
   }

   public static void m114(WorldRenderContext var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      Matrix4f var2 = var1.gameRenderer.getBasicProjectionMatrix(((Integer)var1.options.getFov().getValue()).floatValue());
      Matrix4f var3 = new Matrix4f(var2);
      var3.mul(var0.matrices().peek().getPositionMatrix());
      Daamky_chams_mask.f2 = var3.invert();
      EntityESP var4 = ModuleManager.getModule(EntityESP.class);
      if (var4 != null && var4.isEnabled()) {
         if (var4.f15.m20("Box")) {
            var4.m136(var0);
         }

         if (var4.f15.m20("Обводка") && Daamky_chams_mask.m6()) {
            var4.m115();
         }
      }

      if (f2 != null) {
         f2.draw();
      }

      if (f3 != null) {
         f3.draw();
      }

      if (f4 != null) {
         f4.draw();
      }

      Daamky_chams_mask.m299();
   }

   public boolean m1036(LivingEntityRenderState var1) {
      if (this.isEnabled() && var1 != null && !var1.invisible) {
         if (!this.f15.m20("Chams")
            && !this.f15.m20("Skeleton")
            && !this.f15.m20("Обводка")) {
            return false;
         } else if (var1.entityType != EntityType.PLAYER) {
            return this.f14.m20("Сущности");
         } else if (this.m1039(var1)) {
            return false;
         } else {
            return this.m1041(var1) ? this.f16.m6() : this.f14.m20("Игроки");
         }
      } else {
         return false;
      }
   }

   public boolean m1037(LivingEntityRenderState var1) {
      return this.isEnabled() && this.f15.m20("Chams") && this.m1036(var1);
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.world != null && this.mc.player != null) {
         Entry var2 = var1.matrices().peek();
         Vec3d var3 = var1.worldState().cameraRenderState.pos;
         float var4 = this.mc.getRenderTickCounter().getTickProgress(false);

         for (Entity var6 : this.mc.world.getEntities()) {
            LivingEntity var8;
            if (var6 instanceof LivingEntity && this.m586(var8 = (LivingEntity)var6)) {
               PlayerEntity var7;
               boolean var9 = var6 instanceof PlayerEntity && RaytraceUtil.m80((var7 = (PlayerEntity)var6).getName().getString());
               Color var10 = var9 ? this.f17.m7() : this.m1042();
               Vec3d var11 = var6.getLerpedPos(var4);
               Vec3d var12 = var11.subtract(var6.getEntityPos());
               Box var13 = var6.getBoundingBox().offset(var12).offset(-var3.x, -var3.y, -var3.z);
               this.m1038(var1.consumers(), var2, var13, var10, (float)this.f20.getValue());
            }
         }
      }
   }

   private void m1038(VertexConsumerProvider var1, Entry var2, Box var3, Color var4, float var5) {
      if (!(var5 <= 0.0F)) {
         VertexConsumer var6 = var1.getBuffer(RenderLayers.entityTranslucentEmissive(f5, false));
         this.m729(var6, var2, var3, this.m336(var4, var5 * 0.18F));
         VertexConsumer var7 = var1.getBuffer(this.f22.m6() ? f7 : f9);
         this.m138(var7, var2, var3, this.m336(var4, var5), 2.5F);
      }
   }

   private void m729(VertexConsumer var1, Entry var2, Box var3, Color var4) {
      this.m731(
         var1, var2, var4, var3.minX, var3.maxY, var3.maxZ, var3.maxX, var3.maxY, var3.maxZ, var3.maxX, var3.maxY, var3.minZ, var3.minX, var3.maxY, var3.minZ
      );
      this.m731(
         var1, var2, var4, var3.minX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.minY, var3.maxZ, var3.minX, var3.minY, var3.maxZ
      );
      this.m731(
         var1, var2, var4, var3.minX, var3.minY, var3.minZ, var3.minX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var3.maxX, var3.minY, var3.minZ
      );
      this.m731(
         var1, var2, var4, var3.maxX, var3.minY, var3.maxZ, var3.maxX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var3.minX, var3.minY, var3.maxZ
      );
      this.m731(
         var1, var2, var4, var3.minX, var3.minY, var3.maxZ, var3.minX, var3.maxY, var3.maxZ, var3.minX, var3.maxY, var3.minZ, var3.minX, var3.minY, var3.minZ
      );
      this.m731(
         var1, var2, var4, var3.maxX, var3.minY, var3.minZ, var3.maxX, var3.maxY, var3.minZ, var3.maxX, var3.maxY, var3.maxZ, var3.maxX, var3.minY, var3.maxZ
      );
   }

   private void m731(
      VertexConsumer var1,
      Entry var2,
      Color var3,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      double var18,
      double var20,
      double var22,
      double var24,
      double var26
   ) {
      this.m732(var1, var2, var3, var4, var6, var8, 0.0F, 1.0F);
      this.m732(var1, var2, var3, var10, var12, var14, 1.0F, 1.0F);
      this.m732(var1, var2, var3, var16, var18, var20, 1.0F, 0.0F);
      this.m732(var1, var2, var3, var22, var24, var26, 0.0F, 0.0F);
   }

   private void m732(VertexConsumer var1, Entry var2, Color var3, double var4, double var6, double var8, float var10, float var11) {
      var1.vertex(var2, (float)var4, (float)var6, (float)var8)
         .texture(var10, var11)
         .color(var3.getRed(), var3.getGreen(), var3.getBlue(), var3.getAlpha())
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(var2, 0.0F, 1.0F, 0.0F);
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
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), var3.getAlpha())
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
         var1.vertex(var2, (float)var10, (float)var12, (float)var14)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), var3.getAlpha())
            .normal(var2, var17 / var20, var18 / var20, var19 / var20)
            .lineWidth(var16);
      }
   }

   private Color m336(Color var1, float var2) {
      return new Color(var1.getRed(), var1.getGreen(), var1.getBlue(), (int)(Math.clamp(var2, 0.0F, 1.0F) * 255.0F));
   }

   private boolean m586(LivingEntity var1) {
      if (!var1.isAlive() || var1.isInvisible()) {
         return false;
      } else if (var1 == this.mc.player) {
         return false;
      } else if (var1 instanceof PlayerEntity var2) {
         return RaytraceUtil.m80(var2.getName().getString()) ? this.f16.m6() : this.f14.m20("Игроки");
      } else {
         return this.f14.m20("Сущности");
      }
   }

   private boolean m1039(LivingEntityRenderState var1) {
      if (!(this.mc.currentScreen instanceof InventoryScreen)) {
         return false;
      } else if (this.mc.player == null) {
         return false;
      } else {
         return !(var1 instanceof PlayerEntityRenderState var2) ? false : var2.id == this.mc.player.getId();
      }
   }

   private boolean m1040(Entity var1) {
      return this.mc.currentScreen instanceof InventoryScreen && this.mc.player != null && var1 == this.mc.player;
   }

   private void m707() {
      this.f19.setVisible(!this.f18.m6());
      this.f17.setVisible(this.f16.m6());
      this.f20.setVisible(this.f15.m20("Chams"));
      this.f21.setVisible(this.f15.m20("Chams"));
      this.f22.setVisible(this.f15.m20("Chams") || this.f15.m20("Обводка"));
      this.f23.setVisible(this.f15.m20("Chams"));
      boolean var1 = this.f15.m20("Обводка");
      this.f24.setVisible(var1);
      this.f25.setVisible(var1);
      this.f26.setVisible(var1 && this.f24.m17("Glow"));
   }

   private boolean m1041(LivingEntityRenderState var1) {
      if (!(var1 instanceof PlayerEntityRenderState var2) || this.mc.world == null) {
         return false;
      }

      if (!(this.mc.world.getEntityById(var2.id) instanceof PlayerEntity var4)) {
         return false;
      }

      return RaytraceUtil.m80(var4.getName().getString());
   }

   private Color m1042() {
      Color var1 = this.f18.m6() ? ThemeManager.m1379() : this.f19.m7();
      return !this.f18.m6() ? var1 : new Color(m1009(var1.getRed()), m1009(var1.getGreen()), m1009(var1.getBlue()));
   }

   private static int m1009(int var0) {
      return Math.clamp((long)((int)((float)var0 + (float)(255 - var0) * 0.45F)), 0, 255);
   }

   private int m753() {
      if (this.f21.m17("Обычная")) {
         return 10;
      } else if (this.f21.m17("Full")) {
         return 0;
      } else if (this.f21.m17("WebShader")) {
         return 2;
      } else if (this.f21.m17("Plasma")) {
         return 4;
      } else if (this.f21.m17("ChamsFill")) {
         return 6;
      } else {
         return this.f21.m17("Waves") ? 8 : 10;
      }
   }

   public boolean m1() {
      return this.isEnabled() && this.f15.m20("Chams");
   }

   public boolean m585() {
      return this.isEnabled() && this.f15.m20("Skeleton");
   }

   public boolean m665() {
      return this.isEnabled() && this.f15.m20("Обводка");
   }

   public boolean m534() {
      return this.f22.m6();
   }

   public void m1043(MatrixStack var1, VertexConsumerProvider var2, BipedEntityModel<?> var3, LivingEntityRenderState var4) {
      if (this.mc.player != null) {
         if (var4 instanceof PlayerEntityRenderState var6 && var6.id == this.mc.player.getId() && this.mc.options.getPerspective().isFirstPerson()) {
            return;
         }

         Color var20 = this.m1041(var4) ? this.f17.m7() : this.m1042();
         float var7 = (float)this.f20.getValue();
         if (!(var7 <= 0.0F)) {
            VertexConsumer var8 = var2.getBuffer(f9);
            float var9 = 2.0F;
            var1.push();
            var1.scale(0.0625F, 0.0625F, 0.0625F);
            Entry var10 = var1.peek();
            this.m1046(var8, var10, var20, 0.0, 0.0, 0.0, 0.0, 12.0, 0.0, var9, var7);
            float var11 = var3.rightArm.originX;
            float var12 = var3.rightArm.originY;
            float var13 = var3.rightArm.originZ;
            float var14 = var3.leftArm.originX;
            float var15 = var3.leftArm.originY;
            float var16 = var3.leftArm.originZ;
            float var17 = 1.0F;
            float var18 = var11 - 1.0F;
            float var19 = var14 + 1.0F;
            this.m1046(var8, var10, var20, (double)var18, (double)var17, (double)var13, (double)var19, (double)var17, (double)var16, var9, var7);
            this.m1046(
               var8, var10, var20, 0.0, 12.0, 0.0, (double)var3.rightLeg.originX, (double)var3.rightLeg.originY, (double)var3.rightLeg.originZ, var9, var7
            );
            this.m1046(var8, var10, var20, 0.0, 12.0, 0.0, (double)var3.leftLeg.originX, (double)var3.leftLeg.originY, (double)var3.leftLeg.originZ, var9, var7);
            var1.push();
            this.m1026(var1, var3.head);
            this.m1046(var8, var1.peek(), var20, 0.0, 0.0, 0.0, 0.0, -4.0, 0.0, var9, var7);
            var1.pop();
            var1.push();
            this.m1026(var1, var3.rightArm);
            this.m1046(var8, var1.peek(), var20, -1.0, -1.0, 0.0, -1.0, 10.0, 0.0, var9, var7);
            var1.pop();
            var1.push();
            this.m1026(var1, var3.leftArm);
            this.m1046(var8, var1.peek(), var20, 1.0, -1.0, 0.0, 1.0, 10.0, 0.0, var9, var7);
            var1.pop();
            var1.push();
            this.m1026(var1, var3.rightLeg);
            this.m1046(var8, var1.peek(), var20, 0.0, 0.0, 0.0, 0.0, 12.0, 0.0, var9, var7);
            var1.pop();
            var1.push();
            this.m1026(var1, var3.leftLeg);
            this.m1046(var8, var1.peek(), var20, 0.0, 0.0, 0.0, 0.0, 12.0, 0.0, var9, var7);
            var1.pop();
            var1.pop();
         }
      }
   }

   public void m1044(MatrixStack var1, VertexConsumerProvider var2, BipedEntityModel<?> var3, LivingEntityRenderState var4) {
      boolean var5 = this.f21.m17("ChamsFill");
      RenderLayer var6;
      if (this.f22.m6()) {
         var6 = var5 ? f13 : f11;
      } else {
         var6 = var5 ? BlockOverlay.f8 : BlockOverlay.f6;
      }

      VertexConsumer var7 = var2.getBuffer(var6);
      if (f3 == null) {
         f3 = VertexConsumerProvider.immediate(new BufferAllocator(786432));
      }

      VertexConsumer var8 = f3.getBuffer(this.f22.m6() ? f7 : f9);
      boolean var9 = this.m1041(var4);
      Color var10 = var9 ? this.f17.m7() : this.m1042();
      Color var11 = this.m1042();
      float var12 = (float)this.f20.getValue();
      float var13 = var5 ? 0.0F : (float)this.m753() * 2.0F;
      var1.push();
      var1.scale(0.0625F, 0.0625F, 0.0625F);
      this.m1045(var7, var8, var1, var3.body, var11, var10, var12, var13);
      this.m1045(var7, var8, var1, var3.head, var11, var10, var12, var13);
      this.m1045(var7, var8, var1, var3.rightArm, var11, var10, var12, var13);
      this.m1045(var7, var8, var1, var3.leftArm, var11, var10, var12, var13);
      this.m1045(var7, var8, var1, var3.rightLeg, var11, var10, var12, var13);
      this.m1045(var7, var8, var1, var3.leftLeg, var11, var10, var12, var13);
      var1.pop();
   }

   private void m1045(VertexConsumer var1, VertexConsumer var2, MatrixStack var3, ModelPart var4, Color var5, Color var6, float var7, float var8) {
      List<Cuboid> var9 = ((IModelPart)(Object)var4).getCuboids();
      if (var9 != null && !var9.isEmpty()) {
         var3.push();
         this.m1026(var3, var4);
         Entry var10 = var3.peek();

         for (Cuboid var12 : var9) {
            float var13 = 0.25F;
            float var14 = var12.minX - var13;
            float var15 = var12.minY - var13;
            float var16 = var12.minZ - var13;
            float var17 = var12.maxX + var13;
            float var18 = var12.maxY + var13;
            float var19 = var12.maxZ + var13;
            this.m338(var1, var10, var5, var14, var18, var19, var17, var18, var19, var17, var18, var16, var14, var18, var16, var7, var8);
            this.m338(var1, var10, var5, var14, var15, var16, var17, var15, var16, var17, var15, var19, var14, var15, var19, var7, var8);
            this.m338(var1, var10, var5, var14, var15, var16, var14, var18, var16, var17, var18, var16, var17, var15, var16, var7, var8);
            this.m338(var1, var10, var5, var17, var15, var19, var17, var18, var19, var14, var18, var19, var14, var15, var19, var7, var8);
            this.m338(var1, var10, var5, var14, var15, var19, var14, var18, var19, var14, var18, var16, var14, var15, var16, var7, var8);
            this.m338(var1, var10, var5, var17, var15, var16, var17, var18, var16, var17, var18, var19, var17, var15, var19, var7, var8);
            if (this.f23.m6()) {
               Box var20 = new Box((double)var14, (double)var15, (double)var16, (double)var17, (double)var18, (double)var19);
               this.m138(var2, var10, var20, var6, 2.0F);
            }
         }

         var3.pop();
      }
   }

   private void m338(
      VertexConsumer var1,
      Entry var2,
      Color var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16,
      float var17
   ) {
      this.m339(var1, var2, var3, var4, var5, var6, 0.0F, 1.0F + var17, var16);
      this.m339(var1, var2, var3, var7, var8, var9, 1.0F, 1.0F + var17, var16);
      this.m339(var1, var2, var3, var10, var11, var12, 1.0F, 0.0F + var17, var16);
      this.m339(var1, var2, var3, var13, var14, var15, 0.0F, 0.0F + var17, var16);
   }

   private void m339(VertexConsumer var1, Entry var2, Color var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      var1.vertex(var2, var4, var5, var6)
         .texture(var7, var8)
         .color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var9, 0.0F, 1.0F) * 255.0F));
   }

   private void m1026(MatrixStack var1, ModelPart var2) {
      var1.translate(var2.originX, var2.originY, var2.originZ);
      if (var2.roll != 0.0F) {
         var1.multiply(RotationAxis.POSITIVE_Z.rotation(var2.roll));
      }

      if (var2.yaw != 0.0F) {
         var1.multiply(RotationAxis.POSITIVE_Y.rotation(var2.yaw));
      }

      if (var2.pitch != 0.0F) {
         var1.multiply(RotationAxis.POSITIVE_X.rotation(var2.pitch));
      }
   }

   private void m1046(
      VertexConsumer var1, Entry var2, Color var3, double var4, double var6, double var8, double var10, double var12, double var14, float var16, float var17
   ) {
      float var18 = (float)(var10 - var4);
      float var19 = (float)(var12 - var6);
      float var20 = (float)(var14 - var8);
      float var21 = (float)Math.sqrt((double)(var18 * var18 + var19 * var19 + var20 * var20));
      if (!(var21 <= 1.0E-4F)) {
         var1.vertex(var2, (float)var4, (float)var6, (float)var8)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var17, 0.0F, 1.0F) * 255.0F))
            .normal(var2, var18 / var21, var19 / var21, var20 / var21)
            .lineWidth(var16);
         var1.vertex(var2, (float)var10, (float)var12, (float)var14)
            .color(var3.getRed(), var3.getGreen(), var3.getBlue(), (int)(Math.clamp(var17, 0.0F, 1.0F) * 255.0F))
            .normal(var2, var18 / var21, var19 / var21, var20 / var21)
            .lineWidth(var16);
      }
   }

   private static DepthTestFunction m1047() {
      return DepthTestFunction.LEQUAL_DEPTH_TEST;
   }
}
