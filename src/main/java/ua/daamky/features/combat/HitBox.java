package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.features.render.EntityESP;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "HitBox",
   I00 = "Увеличивает визуальный хитбокс с сайлент-атакой",
   I000 = Category.COMBAT
)
public class HitBox extends Module {
   private final NumberSetting f1 = new NumberSetting("Размер", 0.5, 0.1, 3.0, 0.1);
   private final BooleanSetting f2 = new BooleanSetting("Цвет темы", true);
   private final BooleanSetting f3 = new BooleanSetting("Заливка", true);
   private final NumberSetting f4 = new NumberSetting("Альфа заливки", 50.0, 0.0, 255.0, 1.0);
   private final BooleanSetting f5 = new BooleanSetting("Обводка", true);
   private final ColorSetting f6 = new ColorSetting("Цвет", new Color(255, 255, 255, 100));
   private final NumberSetting f7 = new NumberSetting("Альфа при ударе", 200.0, 0.0, 255.0, 1.0);
   private final NumberSetting f8 = new NumberSetting("Скорость анимации", 10.0, 1.0, 30.0, 1.0);
   private final NumberSetting f9 = new NumberSetting("Скорость ротации", 45.0, 10.0, 180.0, 5.0);
   private final MultiChoiceSetting f10 = new MultiChoiceSetting(
      "Цели",
      "Игроки",
      "Животные",
      "Мобы",
      "Невидимые"
   );
   private final Map<Integer, Double> f11 = new HashMap<>();
   private long f12 = 0L;
   private Entity f13 = null;
   private boolean f14 = false;
   private boolean f15 = false;
   private final Random f16 = new Random();
   private boolean f17 = false;
   private double f18 = 0.5;
   private double f19 = 0.8;
   private double f20 = 0.5;

   public HitBox() {
      this.addSettings(new Setting[]{this.f10, this.f1, this.f2, this.f3, this.f4, this.f5, this.f6, this.f7, this.f8, this.f9});
      WorldRenderEvents.AFTER_ENTITIES.register(this::m114);
      AttackBlockCallback.EVENT.register((AttackBlockCallback)(var1, var2, var3, var4, var5) -> {
         if (this.f14) {
            return ActionResult.FAIL;
         } else {
            return (ActionResult)(this.isEnabled() && var1 == this.mc.player && this.m687() ? ActionResult.FAIL : ActionResult.PASS);
         }
      });
      AttackEntityCallback.EVENT.register((AttackEntityCallback)(var1, var2, var3, var4, var5) -> {
         if (this.f15) {
            return ActionResult.PASS;
         } else if (this.f14) {
            return ActionResult.FAIL;
         } else {
            return (ActionResult)(this.isEnabled() && var1 == this.mc.player && this.m687() ? ActionResult.FAIL : ActionResult.PASS);
         }
      });
   }

   @Override
   public void onEnable() {
      this.f11.clear();
      this.f12 = System.nanoTime();
      this.f13 = null;
      this.f14 = false;
      this.f17 = false;
   }

   @Override
   public void onDisable() {
      if (this.f14) {
         RotationUtil.m314();
      }

      this.f13 = null;
      this.f14 = false;
      this.f15 = false;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         boolean var2 = this.mc.options.attackKey.isPressed();
         if (this.f14 && this.f13 != null) {
            float var3 = (float)this.f9.getValue();
            Box var4 = this.f13.getBoundingBox();
            double var5 = var4.minX + (var4.maxX - var4.minX) * this.f18;
            double var7 = var4.minY + (var4.maxY - var4.minY) * this.f19;
            double var9 = var4.minZ + (var4.maxZ - var4.minZ) * this.f20;
            RotationVec var11 = RotationUtil.m417(new Vec3d(var5, var7, var9));
            RotationUtil.m406(var11, RotationMode.f3, var3, var3, var3);
            RotationVec var12 = RotationUtil.m415();
            if (this.m727(var11, var12)) {
               if (!this.mc.player.isOnGround() && this.mc.player.getVelocity().y >= 0.0) {
                  return;
               }

               this.f15 = true;
               this.mc.interactionManager.attackEntity(this.mc.player, this.f13);
               this.f15 = false;
               this.mc.player.swingHand(Hand.MAIN_HAND);
               this.f11.put(this.f13.getId(), 1.0);
               RotationUtil.m410(var3);
               this.f14 = false;
               this.f13 = null;
            }

            this.f17 = var2;
         } else {
            if (var2 && !this.f17) {
               this.m687();
            }

            this.f17 = var2;
         }
      }
   }

   private boolean m687() {
      if (this.f14) {
         return false;
      } else {
         Vec3d var1 = this.mc.player.getCameraPosVec(1.0F);
         Vec3d var2 = this.mc.player.getRotationVec(1.0F);
         Vec3d var3 = var1.add(var2.multiply(4.5));
         double var4 = Double.MAX_VALUE;
         Entity var6 = null;
         Entity var7 = null;

         for (Entity var9 : this.mc.world.getEntities()) {
            if (this.m728(var9)) {
               Box var10 = var9.getBoundingBox();
               Optional var11 = var10.raycast(var1, var3);
               if (var11.isPresent()) {
                  var7 = var9;
               }

               Box var12 = var10.expand(this.f1.getValue(), 0.0, this.f1.getValue());
               Optional var13 = var12.raycast(var1, var3);
               if (var13.isPresent()) {
                  double var14 = var1.squaredDistanceTo((Vec3d)var13.get());
                  if (var14 < var4) {
                     var4 = var14;
                     var6 = var9;
                  }
               }
            }
         }

         if (var7 != null) {
            return false;
         } else if (var6 != null) {
            this.f14 = true;
            this.f13 = var6;
            this.f18 = this.f16.nextDouble();
            this.f19 = 0.3 + this.f16.nextDouble() * 0.7;
            this.f20 = this.f16.nextDouble();
            this.mc.options.attackKey.setPressed(false);
            return true;
         } else {
            return false;
         }
      }
   }

   private RotationVec m726(Entity var1) {
      Box var2 = var1.getBoundingBox();
      double var3 = var2.minX + (var2.maxX - var2.minX) * this.f16.nextDouble();
      double var5 = var2.minY + (var2.maxY - var2.minY) * (0.3 + this.f16.nextDouble() * 0.7);
      double var7 = var2.minZ + (var2.maxZ - var2.minZ) * this.f16.nextDouble();
      return RotationUtil.m417(new Vec3d(var3, var5, var7));
   }

   private boolean m727(RotationVec var1, RotationVec var2) {
      float var3 = Math.abs(MathHelper.wrapDegrees(var1.m329() - var2.m329()));
      float var4 = Math.abs(var1.m271() - var2.m271());
      return var3 < 6.0F && var4 < 6.0F;
   }

   private boolean m728(Entity var1) {
      if (var1 == this.mc.player || !(var1 instanceof LivingEntity)) {
         return false;
      } else if (var1.isAlive() && var1.isAttackable()) {
         if (var1.isInvisible() && !this.f10.m20("Невидимые")) {
            return false;
         } else {
            if (var1 instanceof PlayerEntity) {
               AntiBot var2 = ModuleManager.getModule(AntiBot.class);
               if (var2 != null && var2.isEnabled() && var2.m595((PlayerEntity)var1)) {
                  return false;
               }

               if (NoFriendDamage.m595((PlayerEntity)var1)) {
                  return false;
               }

               if (!this.f10.m20("Игроки")) {
                  return false;
               }
            } else {
               if (var1 instanceof AnimalEntity && !this.f10.m20("Животные")) {
                  return false;
               }

               if (var1 instanceof MobEntity && !this.f10.m20("Мобы")) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void m114(WorldRenderContext var1) {
      if (this.isEnabled() && this.mc.world != null && this.mc.player != null) {
         long var2 = System.nanoTime();
         double var4 = this.f12 == 0L ? 0.0 : Math.min((double)(var2 - this.f12) / 1.0E9, 0.05);
         this.f12 = var2;
         Vec3d var6 = var1.worldState().cameraRenderState.pos;
         MatrixStack var7 = var1.matrices();
         Color var8 = this.f2.m6() ? ThemeManager.m1379() : this.f6.m7();
         double var9 = this.f1.getValue();
         double var11 = this.f8.getValue();
         float var13 = this.mc.getRenderTickCounter().getTickProgress(false);

         for (Entity var15 : this.mc.world.getEntities()) {
            if (this.m728(var15)) {
               int var16 = var15.getId();
               double var17 = this.f11.getOrDefault(var16, 0.0);
               if (var17 > 0.0) {
                  var17 -= var4 * var11;
                  if (var17 <= 0.0) {
                     var17 = 0.0;
                     this.f11.remove(var16);
                  } else {
                     this.f11.put(var16, var17);
                  }
               }

               double var19 = 1.0 - Math.pow(1.0 - var17, 3.0);
               int var21 = var8.getRed();
               int var22 = var8.getGreen();
               int var23 = var8.getBlue();
               int var24 = var8.getAlpha();
               int var25 = (int)this.f7.getValue();
               int var26 = (int)((double)var24 + (double)(var25 - var24) * var19);
               int var27 = (int)this.f4.getValue();
               int var28 = (int)((double)var27 + (double)(var25 - var27) * var19);
               Color var29 = new Color(
                  Math.clamp((long)var21, 0, 255), Math.clamp((long)var22, 0, 255), Math.clamp((long)var23, 0, 255), Math.clamp((long)var26, 0, 255)
               );
               Color var30 = new Color(
                  Math.clamp((long)var21, 0, 255), Math.clamp((long)var22, 0, 255), Math.clamp((long)var23, 0, 255), Math.clamp((long)var28, 0, 255)
               );
               Vec3d var31 = var15.getLerpedPos(var13);
               Vec3d var32 = var31.subtract(var15.getEntityPos());
               Box var33 = var15.getBoundingBox().expand(var9, 0.0, var9).offset(var32);
               Box var34 = var33.offset(-var6.x, -var6.y, -var6.z);
               if (this.f3.m6()) {
                  VertexConsumer var35 = var1.consumers()
                     .getBuffer(
                        RenderLayers.entityTranslucentEmissive(
                           Identifier.of(
                              "minecraft", "textures/block/white_concrete.png"
                           ),
                           false
                        )
                     );
                  this.m730(var35, var7.peek(), var34, var30);
               }

               if (this.f5.m6()) {
                  VertexConsumer var36 = var1.consumers().getBuffer(EntityESP.f9);
                  this.m729(var36, var7.peek(), var34, var29);
               }
            }
         }
      }
   }

   private void m729(VertexConsumer var1, Entry var2, Box var3, Color var4) {
      float var5 = 2.0F;
      double var6 = var3.minX;
      double var8 = var3.minY;
      double var10 = var3.minZ;
      double var12 = var3.maxX;
      double var14 = var3.maxY;
      double var16 = var3.maxZ;
      this.m139(var1, var2, var4, var6, var8, var10, var12, var8, var10, var5);
      this.m139(var1, var2, var4, var12, var8, var10, var12, var8, var16, var5);
      this.m139(var1, var2, var4, var12, var8, var16, var6, var8, var16, var5);
      this.m139(var1, var2, var4, var6, var8, var16, var6, var8, var10, var5);
      this.m139(var1, var2, var4, var6, var14, var10, var12, var14, var10, var5);
      this.m139(var1, var2, var4, var12, var14, var10, var12, var14, var16, var5);
      this.m139(var1, var2, var4, var12, var14, var16, var6, var14, var16, var5);
      this.m139(var1, var2, var4, var6, var14, var16, var6, var14, var10, var5);
      this.m139(var1, var2, var4, var6, var8, var10, var6, var14, var10, var5);
      this.m139(var1, var2, var4, var12, var8, var10, var12, var14, var10, var5);
      this.m139(var1, var2, var4, var12, var8, var16, var12, var14, var16, var5);
      this.m139(var1, var2, var4, var6, var8, var16, var6, var14, var16, var5);
   }

   private void m730(VertexConsumer var1, Entry var2, Box var3, Color var4) {
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
}
