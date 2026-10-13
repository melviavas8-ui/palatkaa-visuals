package ua.daamky.features.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import ua.daamky.events.RenderEvent;
import ua.daamky.gui.theme.ThemeManager;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ColorSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.ProjectionUtil;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.LayeringTransform;
import net.minecraft.client.render.OutputTarget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

@NewFunction(
   I0 = "Predictions",
   I00 = "Показывает шейдерную траекторию полета снарядов",
   I000 = Category.RENDER
)
public class Predictions extends Module {
   private static final int f1 = 2;
   private static final double f2 = 4.0E-4;
   private static final RenderPipeline f3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("daamky", "predictions_lines"))
         .withBlend(BlendFunction.TRANSLUCENT)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderLayer f4 = RenderLayer.of(
      "daamky_predictions_lines",
      RenderSetup.builder(f3).layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).outputTarget(OutputTarget.ITEM_ENTITY_TARGET).build()
   );
   public static Predictions f5;
   private static final double f6 = 14.0;
   private List<Box> f7 = new ArrayList<>();
   private long f8;
   private final NumberSetting f9 = new NumberSetting("Line Width", 2.2, 0.8, 6.0, 0.1);
   private final NumberSetting f10 = new NumberSetting("Prediction Ticks", 80.0, 10.0, 180.0, 5.0);
   private final BooleanSetting f11 = new BooleanSetting("Only Mine", true);
   private final BooleanSetting f12 = new BooleanSetting("Theme Color", true);
   private final ColorSetting f13 = new ColorSetting("Color", new Color(120, 210, 255));
   private final List<Predictions$1> f14 = new ArrayList<>();

   public Predictions() {
      f5 = this;
      this.addSettings(new Setting[]{this.f9, this.f10, this.f11, this.f12, this.f13});
      this.f12.m5(this::m115);
   }

   @Override
   public void onDisable() {
      this.f7.clear();
      this.f8 = 0L;
   }

   public static void m114(WorldRenderContext var0) {
      if (f5 != null && f5.isEnabled()) {
         f5.m136(var0);
      }
   }

   private void m136(WorldRenderContext var1) {
      if (this.mc.world != null && this.mc.player != null) {
         Color var2 = this.f12.m6() ? ThemeManager.m1379() : this.f13.m7();
         Vec3d var3 = var1.worldState().cameraRenderState.pos;
         float var4 = this.mc.getRenderTickCounter().getTickProgress(false);
         this.f14.clear();
         boolean var5 = this.mc.player.getMainHandStack().getItem() == Items.ENDER_PEARL || this.mc.player.getOffHandStack().getItem() == Items.ENDER_PEARL;
         boolean var6 = this.mc.player.getMainHandStack().getItem() instanceof BowItem || this.mc.player.getOffHandStack().getItem() instanceof BowItem;
         boolean var7 = this.mc.player.getMainHandStack().getItem() instanceof CrossbowItem
            || this.mc.player.getOffHandStack().getItem() instanceof CrossbowItem;
         ArrayList var8 = new ArrayList();
         if (var5 || var6 || var7) {
            float var9 = this.mc.player.getPitch();
            float var10 = this.mc.player.getYaw();
            float var11 = 1.5F;
            double var12 = 0.03;
            boolean var14 = false;
            if (var6) {
               var12 = 0.05;
               if (this.mc.player.isUsingItem() && this.mc.player.getActiveItem().getItem() instanceof BowItem) {
                  int var32 = this.mc.player.getItemUseTime();
                  float var16 = BowItem.getPullProgress(var32);
                  var11 = var16 * 3.0F;
                  if (var11 < 0.15F) {
                     var11 = 0.15F;
                  }
               } else {
                  var11 = 3.0F;
               }
            } else if (var7) {
               var12 = 0.05;
               var11 = 3.15F;
               ItemStack var15 = this.mc.player.getMainHandStack().getItem() instanceof CrossbowItem
                  ? this.mc.player.getMainHandStack()
                  : this.mc.player.getOffHandStack();
               if (var15 != null && var15.getEnchantments().toString().contains("multishot")) {
                  var14 = true;
               }
            }

            List<Vec3d> var33 = new ArrayList<>();
            if (var14) {
               var33.add(this.m1249(var10 - 10.0F, var9).multiply((double)var11));
               var33.add(this.m1249(var10, var9).multiply((double)var11));
               var33.add(this.m1249(var10 + 10.0F, var9).multiply((double)var11));
            } else {
               var33.add(this.m1249(var10, var9).multiply((double)var11));
            }

            double var35 = this.mc.player.getEyePos().y - this.mc.player.getY();
            Vec3d var18 = this.mc.player.getLerpedPos(var4).add(0.0, var35 - 0.1, 0.0);

            for (Vec3d var20 : var33) {
               Predictions$2 var21 = this.m1255(var18, var20, null, 0.99, var12, this.mc.player);
               if (var21.f1.size() >= 2 && var21.f2 != null) {
                  Box var22 = this.m1251(var21.f2);
                  if (var22 != null) {
                     var8.add(var22);
                  }
               }
            }
         }

         for (Entity var25 : this.mc.world.getEntities()) {
            if (var25 instanceof ProjectileEntity) {
               ProjectileEntity var27 = (ProjectileEntity)var25;
               if (this.m1254(var27)) {
                  Predictions$2 var29 = this.m1256(var27, var4);
                  List var13 = var29.f1;
                  if (var13.size() >= 2) {
                     this.m1250(var13, var1, var3, var2);
                     if (var29.f2 != null) {
                        Box var30 = this.m1251(var29.f2);
                        if (var30 != null) {
                           var8.add(var30);
                        }
                     }

                     Vec3d var31 = (Vec3d)var13.get(var13.size() - 1);
                     int var34 = var13.size() - 1;
                     double var36 = (double)var34 / 20.0;
                     String var37 = String.format("%.1f s", var36);
                     this.f14.add(new Predictions$1(var31, this.m1253(var27), var37));
                  }
               }
            }
         }

         this.m1252(var8);

         for (Box var26 : this.f7) {
            Box var28 = var26.offset(-var3.x, -var3.y, -var3.z);
            this.m729(var1.consumers().getBuffer(f4), var1.matrices().peek(), var28, var2);
         }
      }
   }

   private Vec3d m1249(float var1, float var2) {
      float var3 = -MathHelper.sin((double)(var1 * (float) (Math.PI / 180.0))) * MathHelper.cos((double)(var2 * (float) (Math.PI / 180.0)));
      float var4 = -MathHelper.sin((double)(var2 * (float) (Math.PI / 180.0)));
      float var5 = MathHelper.cos((double)(var1 * (float) (Math.PI / 180.0))) * MathHelper.cos((double)(var2 * (float) (Math.PI / 180.0)));
      return new Vec3d((double)var3, (double)var4, (double)var5).normalize();
   }

   private void m1250(List<Vec3d> var1, WorldRenderContext var2, Vec3d var3, Color var4) {
      this.m1259(var2.consumers().getBuffer(f4), var2.matrices().peek(), var1, var3, var4);
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      if (this.mc.world != null && this.mc.player != null) {
         DrawContext var2 = var1.m583();
         if (var2 != null) {
            for (Predictions$1 var4 : this.f14) {
               float[] var5 = ProjectionUtil.m224(var4.f1);
               if (var5 != null) {
                  String var6 = var4.f2.getName().getString() + " " + var4.f3;
                  float var7 = FontRenderUtil.m237(FontRenderUtil.f2, var6, 7.5F);
                  float var8 = FontRenderUtil.m239(FontRenderUtil.f2, 7.5F);
                  float var9 = 5.0F;
                  float var10 = 4.0F;
                  float var11 = var7 + var9 * 2.0F;
                  float var12 = var8 + var10 * 2.0F;
                  float var13 = var5[0] - var11 / 2.0F;
                  float var14 = var5[1] - var12 / 2.0F;
                  Render2DUtil.m198(var13, var14, var11, var12, 3.0F, 25.0F, 1.0F, new Color(4, 4, 6, 180));
                  float var15 = var13 + var9;
                  float var16 = var14 + var10 - 0.5F;
                  Render2DUtil.m208(var2, FontRenderUtil.f2, var15, var16, var6, 7.5F, new Color(230, 230, 235));
               }
            }
         }
      }
   }

   private Box m1251(HitResult var1) {
      if (var1 instanceof BlockHitResult var2) {
         BlockPos var4 = var2.getBlockPos();
         if (this.mc.world != null) {
            VoxelShape var5 = this.mc.world.getBlockState(var4).getOutlineShape(this.mc.world, var4);
            if (!var5.isEmpty()) {
               return var5.getBoundingBox().offset(var4);
            }
         }
      } else if (var1 instanceof EntityHitResult var3) {
         Entity var9 = var3.getEntity();
         float var10 = this.mc.getRenderTickCounter().getTickProgress(false);
         Vec3d var6 = var9.getLerpedPos(var10);
         double var7 = (double)var9.getWidth() / 2.0;
         return new Box(var6.x - var7, var6.y, var6.z - var7, var6.x + var7, var6.y + (double)var9.getHeight(), var6.z + var7);
      }

      return null;
   }

   private void m1252(List<Box> var1) {
      long var2 = System.nanoTime();
      double var4 = this.f8 == 0L ? 1.0 : Math.min((double)(var2 - this.f8) / 1.0E9, 0.05);
      this.f8 = var2;
      double var6 = 1.0 - Math.exp(-14.0 * var4);
      if (var1.isEmpty()) {
         for (int var14 = 0; var14 < this.f7.size(); var14++) {
            Box var15 = this.f7.get(var14);
            Vec3d var16 = var15.getCenter();
            Box var17 = new Box(var16, var16);
            Box var12 = new Box(
               this.m154(var15.minX, var17.minX, var6),
               this.m154(var15.minY, var17.minY, var6),
               this.m154(var15.minZ, var17.minZ, var6),
               this.m154(var15.maxX, var17.maxX, var6),
               this.m154(var15.maxY, var17.maxY, var6),
               this.m154(var15.maxZ, var17.maxZ, var6)
            );
            this.f7.set(var14, var12);
         }

         this.f7.removeIf(var0 -> var0.maxX - var0.minX < 0.01);
         if (this.f7.isEmpty()) {
            this.f8 = 0L;
         }
      } else {
         while (this.f7.size() < var1.size()) {
            Vec3d var8 = ((Box)var1.get(this.f7.size())).getCenter();
            this.f7.add(new Box(var8, var8));
         }

         while (this.f7.size() > var1.size()) {
            this.f7.remove(this.f7.size() - 1);
         }

         for (int var13 = 0; var13 < var1.size(); var13++) {
            Box var9 = (Box)var1.get(var13);
            Box var10 = this.f7.get(var13);
            Box var11 = new Box(
               this.m154(var10.minX, var9.minX, var6),
               this.m154(var10.minY, var9.minY, var6),
               this.m154(var10.minZ, var9.minZ, var6),
               this.m154(var10.maxX, var9.maxX, var6),
               this.m154(var10.maxY, var9.maxY, var6),
               this.m154(var10.maxZ, var9.maxZ, var6)
            );
            this.f7.set(var13, var11);
         }
      }
   }

   private double m154(double var1, double var3, double var5) {
      return var1 + (var3 - var1) * var5;
   }

   private ItemStack m1253(ProjectileEntity var1) {
      if (var1 instanceof ThrownItemEntity var2) {
         return var2.getStack();
      } else if (var1 instanceof TridentEntity) {
         return new ItemStack(Items.TRIDENT);
      } else {
         return var1 instanceof PersistentProjectileEntity ? new ItemStack(Items.ARROW) : new ItemStack(Items.ENDER_PEARL);
      }
   }

   private boolean m1254(ProjectileEntity var1) {
      if (!var1.isAlive() || var1.getVelocity().lengthSquared() < 4.0E-4) {
         return false;
      } else if (this.f11.m6() && var1.getOwner() != this.mc.player) {
         return false;
      } else if (var1 instanceof FishingBobberEntity) {
         return false;
      } else {
         if (var1 instanceof PersistentProjectileEntity var2 && !var2.canHit()) {
            return false;
         }

         return var1 instanceof ThrownEntity
            || var1 instanceof PersistentProjectileEntity
            || var1 instanceof TridentEntity
            || var1 instanceof FireworkRocketEntity
            || var1 instanceof AbstractFireballEntity
            || var1 instanceof AbstractWindChargeEntity;
      }
   }

   private Predictions$2 m1255(Vec3d var1, Vec3d var2, ProjectileEntity var3, double var4, double var6, Entity var8) {
      ArrayList var9 = new ArrayList();
      var9.add(var1);
      Object var10 = null;
      int var11 = (int)this.f10.getValue();

      for (int var12 = 0; var12 < var11 && !(var2.lengthSquared() < 4.0E-4); var12++) {
         Vec3d var13 = var1.add(var2);
         HitResult var14 = this.mc.world.raycast(new RaycastContext(var1, var13, ShapeType.COLLIDER, FluidHandling.NONE, var8));
         double var15 = var14.getType() != Type.MISS ? var1.squaredDistanceTo(var14.getPos()) : Double.MAX_VALUE;
         EntityHitResult var17 = null;
         Box var18 = new Box(var1, var13).expand(1.0);

         for (Entity var20 : this.mc.world.getOtherEntities(var8, var18)) {
            if (var20.canHit() || var20 instanceof LivingEntity) {
               Box var21 = var20.getBoundingBox().expand(0.3);
               Optional var22 = var21.raycast(var1, var13);
               if (var22.isPresent()) {
                  double var23 = var1.squaredDistanceTo((Vec3d)var22.get());
                  if (var23 < var15) {
                     var15 = var23;
                     var17 = new EntityHitResult(var20, (Vec3d)var22.get());
                  }
               }
            }
         }

         if (var17 != null) {
            var14 = var17;
         }

         if (var14 != null && var14.getType() != Type.MISS) {
            var9.add(var14.getPos());
            var10 = var14;
            break;
         }

         var9.add(var13);
         var1 = var13;
         double var25;
         if (var3 != null) {
            var25 = this.m1257(var3, var13);
         } else {
            boolean var26 = !this.mc.world.getFluidState(BlockPos.ofFloored(var13)).isEmpty();
            var25 = var26 ? 0.8 : var4;
         }

         var2 = var2.multiply(var25);
         if (var6 > 0.0) {
            var2 = var2.add(0.0, -var6, 0.0);
         }
      }

      return new Predictions$2(var9, (HitResult)var10);
   }

   private Predictions$2 m1256(ProjectileEntity var1, float var2) {
      double var3 = var1.hasNoGravity() ? 0.0 : this.m1258(var1);
      Predictions$2 var5 = this.m1255(var1.getLerpedPos(1.0F), var1.getVelocity(), var1, 0.99, var3, var1);
      if (!var5.f1.isEmpty()) {
         var5.f1.set(0, var1.getLerpedPos(var2));
      }

      return var5;
   }

   private double m1257(ProjectileEntity var1, Vec3d var2) {
      boolean var3 = !this.mc.world.getFluidState(BlockPos.ofFloored(var2)).isEmpty();
      if (var1 instanceof TridentEntity) {
         return var3 ? 0.99 : 0.99;
      } else if (var1 instanceof PersistentProjectileEntity) {
         return var3 ? 0.6 : 0.99;
      } else if (var1 instanceof ThrownEntity) {
         return var3 ? 0.8 : 0.99;
      } else {
         return !(var1 instanceof AbstractFireballEntity) && !(var1 instanceof AbstractWindChargeEntity) ? 0.99 : 0.95;
      }
   }

   private double m1258(ProjectileEntity var1) {
      EntityType var2 = var1.getType();
      if (var2 == EntityType.FIREBALL || var2 == EntityType.SMALL_FIREBALL || var2 == EntityType.DRAGON_FIREBALL || var2 == EntityType.WITHER_SKULL) {
         return 0.0;
      } else if (var1 instanceof PersistentProjectileEntity) {
         return 0.05;
      } else if (var1 instanceof ThrownEntity) {
         return 0.03;
      } else if (var1 instanceof AbstractWindChargeEntity) {
         return 0.0;
      } else {
         return var1 instanceof FireworkRocketEntity ? 0.0 : 0.03;
      }
   }

   private void m1259(VertexConsumer var1, Entry var2, List<Vec3d> var3, Vec3d var4, Color var5) {
      float var6 = (float)this.f9.getValue();

      for (int var7 = 0; var7 < var3.size() - 1; var7++) {
         Vec3d var8 = ((Vec3d)var3.get(var7)).subtract(var4);
         Vec3d var9 = ((Vec3d)var3.get(var7 + 1)).subtract(var4);
         this.m139(var1, var2, var5, var8.x, var8.y, var8.z, var9.x, var9.y, var9.z, var6);
      }
   }

   private void m729(VertexConsumer var1, Entry var2, Box var3, Color var4) {
      float var5 = (float)this.f9.getValue();
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

   private void m115() {
      this.f13.setVisible(!this.f12.m6());
   }
}
