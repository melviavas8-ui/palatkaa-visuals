package ua.daamky.features.misc;

import ua.daamky.events.MovementInputEvent;
import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.RenderEvent;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.render.Render2DUtil;
import ua.daamky.utils.render.fonts.FontRenderUtil;
import java.awt.Color;
import java.util.Locale;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "FreeCam",
   I00 = "Позволяет свободно летать камерой по миру",
   I000 = Category.MISC
)
public class FreeCam extends Module {
   private final NumberSetting f1 = new NumberSetting("Скорость", 2.0, 0.5, 5.0, 0.1);
   private final BooleanSetting f2 = new BooleanSetting("Заморозка", false);
   private Vec3d f3 = Vec3d.ZERO;
   private Vec3d f4 = Vec3d.ZERO;
   private String f5 = "";
   private long f6;

   public FreeCam() {
      this.addSettings(new Setting[]{this.f1, this.f2});
   }

   public static FreeCam m869() {
      return ModuleManager.getModule(FreeCam.class);
   }

   @Override
   public void onEnable() {
      if (this.mc.player != null) {
         this.f3 = this.f4 = this.mc.player.getCameraPosVec(1.0F);
         this.f5 = this.m875(this.f3);
         this.f6 = System.currentTimeMillis();
         this.mc.chunkCullingEnabled = false;
      }
   }

   @Override
   public void onDisable() {
      this.mc.chunkCullingEnabled = true;
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (this.f2.m6() && var1.m581() instanceof PlayerMoveC2SPacket) {
         var1.m29();
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (var1.m581() instanceof PlayerRespawnS2CPacket || var1.m581() instanceof GameJoinS2CPacket) {
         this.toggle();
      }
   }

   @EventHandler
   public void m660(MovementInputEvent var1) {
      if (this.mc.player != null) {
         float var2 = var1.m271();
         float var3 = var1.m272();
         double var4 = this.f1.getValue();
         this.f4 = this.f3;
         Vec3d var6 = this.m874(var2, var3, this.mc.player.getYaw()).multiply(var4);
         double var7 = var1.m101() ? var4 : (var1.m31() ? -var4 : 0.0);
         this.f3 = this.f3.add(var6.x, var7, var6.z);
         var1.m348(0.0F);
         var1.m410(0.0F);
         var1.m4(false);
         var1.m300(false);
      }
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      if (var1.m583() != null && this.mc.player != null) {
         float var2 = var1.m582().getTickProgress(true);
         Vec3d var3 = new Vec3d(this.m871(var2), this.m872(var2), this.m873(var2));
         String var4 = this.m875(var3);
         if (!var4.equals(this.f5)) {
            this.f5 = var4;
            this.f6 = System.currentTimeMillis();
         }

         float var5 = Math.clamp((float)(System.currentTimeMillis() - this.f6) / 260.0F, 0.0F, 1.0F);
         float var6 = (float)Math.sin((double)var5 * Math.PI) * 2.2F;
         float var7 = 8.5F + var6;
         float var8 = (float)this.mc.getWindow().getScaledWidth() / 2.0F;
         float var9 = (float)this.mc.getWindow().getScaledHeight() / 2.0F + 13.0F;
         Render2DUtil.m219(
            var1.m583(), FontRenderUtil.f2, var8 + 1.0F, var9 + 1.0F, this.f5, var7, new Color(0, 0, 0, 140), "center"
         );
         Render2DUtil.m219(var1.m583(), FontRenderUtil.f2, var8, var9, this.f5, var7, new Color(245, 247, 250), "center");
      }
   }

   public double m871(float var1) {
      return MathHelper.lerp((double)var1, this.f4.x, this.f3.x);
   }

   public double m872(float var1) {
      return MathHelper.lerp((double)var1, this.f4.y, this.f3.y);
   }

   public double m873(float var1) {
      return MathHelper.lerp((double)var1, this.f4.z, this.f3.z);
   }

   public float m412(float var1) {
      return this.mc.player == null ? 0.0F : MathHelper.lerpAngleDegrees(var1, this.mc.player.lastYaw, this.mc.player.getYaw());
   }

   public float m413(float var1) {
      return this.mc.player == null ? 0.0F : MathHelper.lerp(var1, this.mc.player.lastPitch, this.mc.player.getPitch());
   }

   private Vec3d m874(float var1, float var2, float var3) {
      if (var1 == 0.0F && var2 == 0.0F) {
         return Vec3d.ZERO;
      } else {
         double var4 = Math.toRadians((double)var3);
         double var6 = Math.sin(var4);
         double var8 = Math.cos(var4);
         double var10 = (double)var2 * var8 - (double)var1 * var6;
         double var12 = (double)var1 * var8 + (double)var2 * var6;
         return new Vec3d(var10, 0.0, var12).normalize();
      }
   }

   private String m875(Vec3d var1) {
      return String.format(Locale.ROOT, "X %.1f  Z %.1f  Y %.1f", var1.x, var1.z, var1.y);
   }
}
