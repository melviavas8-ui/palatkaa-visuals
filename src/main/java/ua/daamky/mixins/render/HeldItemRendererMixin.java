package ua.daamky.mixins.render;

import ua.daamky.features.render.BeautifulHands;
import ua.daamky.features.render.Hands;
import ua.daamky.features.render.SwingAnimations;
import ua.daamky.features.render.ViewModel;
import ua.daamky.gui.Daamky_chams_mask;
import ua.daamky.system.api.ModuleManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.SwingAnimationType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HeldItemRenderer.class})
public abstract class HeldItemRendererMixin {
   @Shadow
   private float equipProgressMainHand;
   @Shadow
   private float lastEquipProgressMainHand;
   @Unique
   private boolean daamky$overrideSwing;
   @Unique
   private float daamky$tickProgress;
   @Unique
   private float daamky$equipProgress;

   @Shadow
   private void applyEquipOffset(MatrixStack var1, Arm var2, float var3) {
   }

   @Shadow
   private void swingArm(float var1, MatrixStack var2, int var3, Arm var4) {
   }

   @Shadow
   private void renderArmHoldingItem(MatrixStack var1, OrderedRenderCommandQueue var2, int var3, float var4, float var5, Arm var6) {
   }

   @Shadow
   public abstract void renderItem(LivingEntity var1, ItemStack var2, ItemDisplayContext var3, MatrixStack var4, OrderedRenderCommandQueue var5, int var6);

   @Redirect(
      method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionfc;)V",
         ordinal = 0
      )
   )
   private void daamky$skipViewBobPitch(MatrixStack var1, Quaternionfc var2) {
      ViewModel var3 = ModuleManager.getModule(ViewModel.class);
      if (var3 == null || !var3.m687()) {
         var1.multiply(var2);
      }
   }

   @Redirect(
      method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lorg/joml/Quaternionfc;)V",
         ordinal = 1
      )
   )
   private void daamky$skipViewBobYaw(MatrixStack var1, Quaternionfc var2) {
      ViewModel var3 = ModuleManager.getModule(ViewModel.class);
      if (var3 == null || !var3.m687()) {
         var1.multiply(var2);
      }
   }

   @Inject(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;push()V",
         shift = Shift.AFTER
      )}
   )
   private void daamky$applyViewModel(
      AbstractClientPlayerEntity var1,
      float var2,
      float var3,
      Hand var4,
      float var5,
      ItemStack var6,
      float var7,
      MatrixStack var8,
      OrderedRenderCommandQueue var9,
      int var10,
      CallbackInfo var11
   ) {
      ViewModel var12 = ModuleManager.getModule(ViewModel.class);
      if (var12 != null) {
         boolean var13 = var4 == Hand.MAIN_HAND;
         Arm var14 = var13 ? var1.getMainArm() : var1.getMainArm().getOpposite();
         var12.m1011(var8, var14);
      }
   }

   @Inject(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void daamky$captureSwingState(
      AbstractClientPlayerEntity var1,
      float var2,
      float var3,
      Hand var4,
      float var5,
      ItemStack var6,
      float var7,
      MatrixStack var8,
      OrderedRenderCommandQueue var9,
      int var10,
      CallbackInfo var11
   ) {
      Hands var12 = ModuleManager.getModule(Hands.class);
      if (var12 != null && var12.m585() && !Daamky_chams_mask.m81()) {
         this.daamky$overrideSwing = false;
         var11.cancel();
      } else if (this.daamky$renderBeautifulHandsCustom(var1, var2, var4, var5, var6, var7, var8, var9, var10)) {
         this.daamky$overrideSwing = false;
         var11.cancel();
      } else {
         this.daamky$tickProgress = var2;
         this.daamky$equipProgress = var7;
         this.daamky$overrideSwing = this.daamky$shouldOverride(var1, var4, var6);
      }
   }

   @Inject(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"},
      at = {@At("RETURN")}
   )
   private void daamky$clearSwingState(
      AbstractClientPlayerEntity var1,
      float var2,
      float var3,
      Hand var4,
      float var5,
      ItemStack var6,
      float var7,
      MatrixStack var8,
      OrderedRenderCommandQueue var9,
      int var10,
      CallbackInfo var11
   ) {
      this.daamky$overrideSwing = false;
   }

   @Redirect(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V"
      )
   )
   private void daamky$redirectEquipOffset(HeldItemRenderer var1, MatrixStack var2, Arm var3, float var4) {
      if (!this.daamky$overrideSwing) {
         this.applyEquipOffset(var2, var3, var4);
      }
   }

   @Redirect(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;swingArm(FLnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/util/Arm;)V"
      )
   )
   private void daamky$redirectSwingArm(HeldItemRenderer var1, float var2, MatrixStack var3, int var4, Arm var5) {
      SwingAnimations var6 = ModuleManager.getModule(SwingAnimations.class);
      if (this.daamky$overrideSwing && var6 != null) {
         var6.m1265(var3, var2, this.daamky$equipProgress, var5, this.daamky$tickProgress, this.lastEquipProgressMainHand, this.equipProgressMainHand);
      } else {
         this.swingArm(var2, var3, var4, var5);
      }
   }

   @Redirect(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"
      )
   )
   private void daamky$redirectRenderItem(
      HeldItemRenderer var1, LivingEntity var2, ItemStack var3, ItemDisplayContext var4, MatrixStack var5, OrderedRenderCommandQueue var6, int var7
   ) {
      this.daamky$applyViewModelRotation(var5, this.daamky$armFromRenderMode(var4));
      this.renderItem(var2, var3, var4, var5, var6, var7);
   }

   @Unique
   private boolean daamky$shouldOverride(AbstractClientPlayerEntity var1, Hand var2, ItemStack var3) {
      SwingAnimations var4 = ModuleManager.getModule(SwingAnimations.class);
      return var4 != null
         && var4.m687()
         && var2 == Hand.MAIN_HAND
         && !var3.isEmpty()
         && !var3.contains(DataComponentTypes.MAP_ID)
         && !var3.isOf(Items.CROSSBOW)
         && !var1.isUsingSpyglass()
         && !var1.isUsingRiptide()
         && (!var1.isUsingItem() || var1.getItemUseTimeLeft() <= 0 || var1.getActiveHand() != var2)
         && var3.getSwingAnimation().type() == SwingAnimationType.WHACK;
   }

   @Unique
   private boolean daamky$renderBeautifulHandsCustom(
      AbstractClientPlayerEntity var1,
      float var2,
      Hand var3,
      float var4,
      ItemStack var5,
      float var6,
      MatrixStack var7,
      OrderedRenderCommandQueue var8,
      int var9
   ) {
      BeautifulHands var10 = ModuleManager.getModule(BeautifulHands.class);
      if (var10 == null || !var10.isEnabled() || var5.contains(DataComponentTypes.MAP_ID) || var1.isUsingSpyglass() || var1.isUsingRiptide()) {
         return false;
      } else if (var5.isEmpty() && !var10.m1()) {
         return false;
      } else {
         Arm var11 = var3 == Hand.MAIN_HAND ? var1.getMainArm() : var1.getMainArm().getOpposite();
         int var12 = var11 == Arm.RIGHT ? 1 : -1;
         boolean var13 = var11 == Arm.RIGHT;
         double var14 = var10.m724();
         Vec3d var16 = var1.getVelocity();
         double var17 = Math.sqrt(var16.x * var16.x + var16.z * var16.z);
         float var19 = var1.lastYaw - var1.getYaw();
         float var20 = var1.lastPitch - var1.getPitch();
         var7.push();
         ViewModel var21 = ModuleManager.getModule(ViewModel.class);
         if (var21 != null) {
            var21.m1011(var7, var11);
         }

         var10.m1011(var7, var11);
         var10.m1013(var7, var11, var17, var16.y, var19, var20, var4, var14);
         if (var5.isEmpty()) {
            this.daamky$applyViewModelRotation(var7, var11);
            this.renderArmHoldingItem(var7, var8, var9, var6, var10.m3(var4), var11);
            var7.pop();
            return true;
         } else {
            float var22 = var10.m411(var4);
            float var23 = var10.m151(MathHelper.sin((double)(var4 * (float) Math.PI)));
            boolean var24 = var10.m1012(this.daamky$isAttackPressed(), var4);
            this.daamky$applyHoldMyItemsSwing(var7, var10, var5, var11, var12, var23, var22, var24);
            var7.push();
            this.daamky$applyHeldArmPose(var7, var5, var11, var12, var6, var4);
            var7.push();
            this.daamky$applyViewModelRotation(var7, var11);
            this.renderArmHoldingItem(var7, var8, var9, 0.0F, var10.m3(var4), var11);
            var7.pop();
            this.daamky$applyHeldItemPose(var7, var10, var1, var5, var11, var12, var6, var23, var22, var17, var14);
            this.daamky$applyViewModelRotation(var7, var11);
            this.renderItem(var1, var5, var13 ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, var7, var8, var9);
            var7.pop();
            var7.pop();
            return true;
         }
      }
   }

   @Unique
   private Arm daamky$armFromRenderMode(ItemDisplayContext var1) {
      return var1 == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ? Arm.LEFT : Arm.RIGHT;
   }

   @Unique
   private void daamky$applyViewModelRotation(MatrixStack var1, Arm var2) {
      ViewModel var3 = ModuleManager.getModule(ViewModel.class);
      if (var3 != null) {
         var3.m1230(var1, var2);
      }
   }

   @Unique
   private void daamky$applyHoldMyItemsSwing(MatrixStack var1, BeautifulHands var2, ItemStack var3, Arm var4, int var5, float var6, float var7, boolean var8) {
      if (!var2.m665()) {
         boolean var9 = var8
            || var3.isIn(ItemTags.AXES)
            || var3.getUseAction() == UseAction.SPEAR
            || var3.getUseAction() == UseAction.TRIDENT
            || var3.getUseAction() == UseAction.BLOCK;
         if (var3.isIn(ItemTags.SHOVELS)) {
            var1.translate(0.0F, 0.15F * var7, -0.25F * var7 - 0.2F * var6);
            var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(15.0F * var7));
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-35.0F * var7 + 30.0F * var6));
         } else {
            if (var9 && var3.isIn(ItemTags.SWORDS)) {
               var1.translate(0.8F * (float)var5 * var7, 0.3F * var7, -0.5F * var6);
               var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(15.0F * var7 * (float)var5));
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-20.0F * var7));
               var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-70.0F * var7 * (float)var5));
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * var6));
            } else if (!var9 && var3.isIn(ItemTags.SWORDS)) {
               var1.translate(-0.55F * (float)var5 * var7, -0.8F * var7, -0.77F * var6);
               var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(5.0F * var7 * (float)var5));
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * var7));
               var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(70.0F * var7 * (float)var5));
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(50.0F * var6));
            } else if (this.daamky$isToolLike(var3)) {
               var1.translate(0.1F * (float)var5 * var7, 0.1F * var7, -0.5F * var6);
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * var7));
               var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F * var7 * (float)var5));
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * var6));
            } else {
               var1.translate(0.1F * (float)var5 * var7, 0.1F * var7, -0.1F * var6);
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * var7));
               var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-10.0F * var7 * (float)var5));
               var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * var6));
               var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(10.0F * var6 * (float)var5));
            }
         }
      }
   }

   @Unique
   private void daamky$applyHeldArmPose(MatrixStack var1, ItemStack var2, Arm var3, int var4, float var5, float var6) {
      if (var2.getUseAction() == UseAction.BLOCK) {
         var1.translate(0.0F, -0.2F, 0.0F);
      } else if (var2.isIn(ItemTags.LANTERNS) || var2.isIn(ItemTags.HANGING_SIGNS)) {
         var1.translate(0.1F * (float)var4, 0.0F, -0.1F);
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(10.0F));
      }

      var1.translate((float)var4, -var5 * 0.3F, 0.3F);
      var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * (float)var4));
      var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-40.0F * (float)var4));
      var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
      this.daamky$applyAltSwing(var1, var3, var6);
      var1.scale(0.9F, 0.9F, 0.9F);
      if (this.daamky$isThrowable(var2)) {
         var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-25.0F * (float)var4));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-10.0F));
         var1.translate(-0.15F * (float)var4, 0.1F, 0.1F);
      } else {
         if (var2.getUseAction() == UseAction.BLOCK) {
            var1.translate((float)var4 * 0.22F, -0.04F, -0.06F);
            var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(18.0F * (float)var4));
         } else if (var2.getUseAction() != UseAction.BOW && !var2.isOf(Items.CROSSBOW)) {
            if (var2.getUseAction() != UseAction.TRIDENT && var2.getUseAction() != UseAction.SPEAR) {
               var1.translate((float)var4 * 0.08F, 0.02F, -0.02F);
            } else {
               var1.translate((float)var4 * -0.18F, 0.0F, -0.15F);
               var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(20.0F * (float)var4));
            }
         } else {
            var1.translate((float)var4 * -0.05F, -0.05F, -0.18F);
            var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-18.0F));
         }
      }
   }

   @Unique
   private void daamky$applyHeldItemPose(
      MatrixStack var1,
      BeautifulHands var2,
      AbstractClientPlayerEntity var3,
      ItemStack var4,
      Arm var5,
      int var6,
      float var7,
      float var8,
      float var9,
      double var10,
      double var12
   ) {
      var1.translate(0.0F, -0.5F, -0.1F);
      var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-65.0F * (float)var6));
      var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(10.0F));
      UseAction var14 = var4.getUseAction();
      if (var14 == UseAction.BLOCK) {
         var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(160.0F * (float)var6));
         var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-60.0F * (float)var6));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-70.0F));
         var1.scale(0.75F, 0.75F, 0.75F);
         var1.translate(0.32F * (float)var6, 0.35F, 0.15F);
         var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F * (float)var6));
      } else if (var14 == UseAction.BOW || var4.isOf(Items.CROSSBOW)) {
         var1.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(75.0F * (float)var6));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(60.0F));
         var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45.0F * (float)var6));
         var1.translate(-0.1F * (float)var6, -0.2F, 0.0F);
         var1.scale(1.12F, 1.12F, 1.12F);
      } else if (var14 == UseAction.TRIDENT || var14 == UseAction.SPEAR) {
         var1.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(75.0F * (float)var6));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F - 40.0F * var9));
         var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45.0F * (float)var6));
         var1.translate(-0.3F * (float)var6, 0.1F * var9, -0.1F * var9);
         var1.scale(1.15F, 1.15F, 1.15F);
      } else if (var4.isIn(ItemTags.SHOVELS)) {
         var1.translate(0.07F * (float)var6, 0.0F, 0.05F);
         var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F * (float)var6));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-15.0F - 80.0F * var9 + 30.0F * var8));
      } else if (this.daamky$isSimpleHeldItem(var4)) {
         var1.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(5.0F * (float)var6));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(15.0F));
         var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(75.0F * (float)var6));
         var1.translate(0.0F, -0.05F, -0.1F);
         var1.scale(0.72F, 0.72F, 0.72F);
         if (this.daamky$isSoftItem(var4)) {
            var2.m1014(var1, var8, var10, var12);
         }
      } else {
         var1.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(10.0F));
         var1.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-65.0F * (float)var6));
         var1.translate(0.0F, 0.5F, 0.1F);
         var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(92.0F));
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F));
         var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-41.0F * (float)var6));
         var1.translate((float)var6 * -0.075F, -0.5375F, 0.5125F);
         var1.translate(var2.m564() * (float)var6, var2.m565(), var2.m566());
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var2.m1015()));
         var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var2.m1016() * (float)var6));
         var1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var2.m1017() * (float)var6));
         if (var14 != UseAction.BLOCK) {
            var1.scale(1.2F, 1.2F, 1.2F);
         }
      }

      if (var4.isOf(Items.NETHER_STAR) || var4.isOf(Items.END_CRYSTAL)) {
         float var15 = (float)((double)System.nanoTime() / 1.0E9);
         var1.translate(0.0F, 0.25F + 0.02F * MathHelper.sin((double)(var15 * 3.0F)), 0.0F);
         var1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(3.0F * MathHelper.sin((double)(var15 * 6.0F))));
         float var16 = 1.0F + 0.01F * MathHelper.sin((double)(var15 * 12.0F));
         var1.scale(var16, var16, var16);
      }
   }

   @Unique
   private void daamky$applyAltSwing(MatrixStack var1, Arm var2, float var3) {
      int var4 = var2 == Arm.RIGHT ? 1 : -1;
      float var5 = MathHelper.sin((double)(var3 * (float) Math.PI));
      var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var4 * (45.0F + var5 * 0.0F)));
      var1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var4 * -45.0F));
   }

   @Unique
   private boolean daamky$isAttackPressed() {
      return MinecraftClient.getInstance().options.attackKey.isPressed();
   }

   @Unique
   private boolean daamky$isThrowable(ItemStack var1) {
      return var1.isOf(Items.EXPERIENCE_BOTTLE)
         || var1.isOf(Items.EGG)
         || var1.isOf(Items.ENDER_EYE)
         || var1.isOf(Items.SNOWBALL)
         || var1.isOf(Items.ENDER_PEARL)
         || var1.isOf(Items.SPLASH_POTION)
         || var1.isOf(Items.LINGERING_POTION);
   }

   @Unique
   private boolean daamky$isToolLike(ItemStack var1) {
      return var1.isIn(ItemTags.SWORDS)
         || var1.isIn(ItemTags.AXES)
         || var1.isIn(ItemTags.PICKAXES)
         || var1.isIn(ItemTags.HOES)
         || var1.isIn(ItemTags.SHOVELS)
         || var1.isIn(ItemTags.MELEE_WEAPON_ENCHANTABLE)
         || var1.isIn(ItemTags.MINING_ENCHANTABLE)
         || var1.getItem() instanceof FishingRodItem
         || var1.isOf(Items.SHEARS)
         || var1.isOf(Items.CARROT_ON_A_STICK)
         || var1.isOf(Items.WARPED_FUNGUS_ON_A_STICK);
   }

   @Unique
   private boolean daamky$isSimpleHeldItem(ItemStack var1) {
      Block var2 = Block.getBlockFromItem(var1.getItem());
      boolean var3 = var2 != Blocks.AIR
         && !var2.getDefaultState().isIn(BlockTags.RAILS)
         && !var2.getDefaultState().isIn(BlockTags.CLIMBABLE)
         && !var2.getDefaultState().isIn(BlockTags.COMBINATION_STEP_SOUND_BLOCKS);
      return var3
         || var1.getUseAction() == UseAction.EAT
         || var1.getUseAction() == UseAction.DRINK
         || var1.getUseAction() == UseAction.BRUSH
         || var1.isIn(ItemTags.BANNERS)
         || var1.isIn(ItemTags.LANTERNS)
         || var1.isOf(Items.STRING)
         || var1.isOf(Items.REDSTONE)
         || var1.isOf(Items.LEVER)
         || var1.isOf(Items.TRIPWIRE_HOOK);
   }

   @Unique
   private boolean daamky$isSoftItem(ItemStack var1) {
      Block var2 = Block.getBlockFromItem(var1.getItem());
      return var1.isOf(Items.FEATHER)
         || var1.isOf(Items.SLIME_BALL)
         || var1.isOf(Items.PUFFERFISH)
         || var1.isOf(Items.SLIME_BLOCK)
         || var1.isOf(Items.HONEY_BLOCK)
         || var2.getDefaultState().isIn(BlockTags.FLOWERS)
         || var2.getDefaultState().isIn(BlockTags.LEAVES)
         || var2.getDefaultState().isIn(BlockTags.SAPLINGS)
         || var2.getDefaultState().isIn(BlockTags.SWORD_EFFICIENT);
   }
}
