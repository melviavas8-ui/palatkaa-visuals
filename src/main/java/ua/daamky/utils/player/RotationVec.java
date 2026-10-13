package ua.daamky.utils.player;

import net.minecraft.util.math.MathHelper;

public class RotationVec {
   public final float f1;
   public final float f2;
   public static final RotationVec f3 = new RotationVec(0.0F, 0.0F);

   public RotationVec(float var1, float var2) {
      this.f1 = var1;
      this.f2 = var2;
   }

   public float m402(RotationVec var1) {
      return Math.abs(MathHelper.wrapDegrees(var1.f1 - this.f1)) + Math.abs(MathHelper.wrapDegrees(var1.f2 - this.f2));
   }

   public float m329() {
      return this.f1;
   }

   public float m271() {
      return this.f2;
   }
}
