package ua.daamky.utils.render;

import ua.daamky.features.render.Animations;
import ua.daamky.utils.CosmeticModelItem;
import ua.daamky.utils.MatrixStackHelper;
import ua.daamky.utils.CosmeticAttachPoint;
import java.util.Locale;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MaskUtil {
   private static MaskUtil f1;
   private final Animations f2 = Animations.m502();
   private final MatrixStackHelper f3 = new MatrixStackHelper();
   private static final float f4 = 180.0F / (float)Math.PI;

   public static MaskUtil m569() {
      if (f1 == null) {
         f1 = new MaskUtil();
      }

      return f1;
   }

   public void m570(CosmeticModelItem var1, AbstractClientPlayerEntity var2, MatrixStack var3, VertexConsumerProvider var4, int var5, PlayerEntityModel var6, float var7) {
      if (var1 != null && var1.m560() != null) {
         this.f3.m546(var3);
         this.f3.m63();
         float var8 = this.m572(var1, var6);
         this.f3.m522(180.0F);
         this.f3.m548(var1.m524(), var1.m525() + var8, var1.m2());
         this.f3.m521(var1.m529());
         this.f3.m520(var1.m530());
         this.f3.m522(var1.m563());
         this.f3.m549(var1.m523(), var1.m523(), var1.m523());
         this.f2.m503(var1, var3, var4, var5);
         this.f3.m314();
      }
   }

   public void m571(CosmeticModelItem var1, AbstractClientPlayerEntity var2, MatrixStack var3, VertexConsumer var4, int var5, PlayerEntityModel var6, float var7) {
      if (var1 != null && var1.m560() != null && var4 != null) {
         this.f3.m546(var3);
         this.f3.m63();
         float var8 = this.m572(var1, var6);
         this.f3.m522(180.0F);
         this.f3.m548(var1.m524(), var1.m525() + var8, var1.m2());
         this.f3.m521(var1.m529());
         this.f3.m520(var1.m530());
         this.f3.m522(var1.m563());
         this.f3.m549(var1.m523(), var1.m523(), var1.m523());
         this.f2.m504(var1, var3, var4, var5);
         this.f3.m314();
      }
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private float m572(CosmeticModelItem var1, PlayerEntityModel var2) {
      float var3 = 0.0F;
      CosmeticAttachPoint var4 = var1.m561();
      if (var2 == null) {
         return var3;
      } else {
         switch (var4) {
            case f3:
               this.m574(var2.head);
               var3 = -0.035F;
               break;
            case f4:
               var3 = 0.75F;
               break;
            case f2:
               this.m574(var2.body);
               var3 = -0.3F;
               break;
            case f5:
               this.m574(var2.rightArm);
               var3 = -0.25F;
               break;
            case f6:
               this.m574(var2.leftArm);
               var3 = -0.25F;
               break;
            case f7:
               this.m574(var2.rightLeg);
               var3 = -0.35F;
               break;
            case f8:
               this.m574(var2.leftLeg);
               var3 = -0.35F;
            case f1:
         }

         return var3;
      }
   }

   private boolean m573(CosmeticModelItem var1) {
      String var2 = var1.m37();
      return var2 != null && var2.toLowerCase(Locale.ROOT).contains("mask");
   }

   private void m574(ModelPart var1) {
      this.f3.m548(var1.originX * 0.0625F, var1.originY * 0.0625F, var1.originZ * 0.0625F);
      this.f3.m551(var1.pitch * (180.0F / (float)Math.PI), var1.yaw * (180.0F / (float)Math.PI), var1.roll * (180.0F / (float)Math.PI));
   }
}
