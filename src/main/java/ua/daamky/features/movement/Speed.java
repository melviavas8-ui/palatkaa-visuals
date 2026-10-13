package ua.daamky.features.movement;

import ua.daamky.events.MovementInputEvent;
import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.events.PacketSendEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.events.RenderEvent;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.NumberSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.ModuleManager;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.MovementUtil;
import ua.daamky.utils.SilentPacketUtil;
import ua.daamky.utils.render.RenderUtil;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

@NewFunction(
   I0 = "Speed",
   I00 = "Ускоряет игрока при столкновении с сущностями",
   I000 = Category.MOVEMENT
)
public class Speed extends Module {
   private static final String name = "ReallyWorld";
   private static final float f1 = 0.05F;
   private static final float f2 = 1.7F;
   private static final long f3 = 1250000000L;
   private static final long f4 = 2400000000L;
   private static final int f5 = 4;
   private final ModeSettingBase f6 = new ModeSettingBase(
      "Режим",
      "Grim",
      "MetaHVH",
      "Grim",
      "HolyWorld",
      "ReallyWorld"
   );
   private final NumberSetting f7 = new NumberSetting("Скорость", 1.5, 1.0, 5.0, 0.1);
   private Speed$1 f8 = Speed$1.f1;
   private boolean f9;
   private boolean f10;
   private int f11;
   private int f12;
   private long f13;
   private final Queue<CommonPongC2SPacket> f14 = new ConcurrentLinkedQueue<>();

   public Speed() {
      this.f6.m5(this::m135);
      this.m677();
      this.addSettings(new Setting[]{this.f6, this.f7});
   }

   public static Speed m1280() {
      return ModuleManager.getModule(Speed.class);
   }

   private void m135() {
      this.m677();
      if (!this.m752()) {
         this.m700();
      }
   }

   private void m677() {
      this.f7.setVisible(this.f6.m17("MetaHVH"));
   }

   private boolean m752() {
      return this.f6.m17("ReallyWorld");
   }

   public void m116() {
   }

   public void m676() {
   }

   public boolean m665() {
      return false;
   }

   public Vec3d m1048(float var1) {
      return this.mc.player.getCameraPosVec(var1);
   }

   public Vec3d m1281(float var1) {
      return this.mc.player.getLerpedPos(var1);
   }

   @Override
   public void onEnable() {
      this.m700();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.m700();
      super.onDisable();
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (this.m752()) {
            this.m708();
         } else if (MovementUtil.m91()) {
            if (this.f6.m17("MetaHVH")) {
               this.m607();
            } else if (this.f6.m17("Grim")) {
               this.m682();
            } else if (this.f6.m17("HolyWorld")) {
               this.m683();
            }
         }
      }
   }

   private void m607() {
      double var1 = this.f7.getValue() / 3.0;
      MovementUtil.m25(var1);
   }

   private void m682() {
      int var1 = 0;

      for (Entity var3 : this.mc.world.getEntities()) {
         if (this.m1040(var3) && this.m601(var3, 0.5)) {
            var1++;
         }
      }

      if (var1 > 0) {
         double var5 = 0.07 * (double)var1;
         double[] var4 = MovementUtil.m92(var5);
         this.mc.player.addVelocity(var4[0], 0.0, var4[1]);
      }
   }

   private void m683() {
      int var1 = 0;

      for (Entity var3 : this.mc.world.getEntities()) {
         if (this.m1040(var3) && this.m601(var3, 0.35)) {
            var1++;
         }
      }

      if (var1 > 0) {
         double var5 = 0.0205 * (double)var1;
         double[] var4 = MovementUtil.m92(var5);
         this.mc.player.addVelocity(var4[0], 0.0, var4[1]);
      }
   }

   private boolean m1040(Entity var1) {
      return var1 != null && var1 != this.mc.player && !(var1 instanceof ArmorStandEntity)
         ? var1 instanceof LivingEntity || var1.getType().toString().toLowerCase(Locale.ROOT).contains("boat")
         : false;
   }

   private boolean m601(Entity var1, double var2) {
      Box var4 = this.mc.player.getBoundingBox().expand(var2);
      Box var5 = var1.getBoundingBox();
      return var4.intersects(var5);
   }

   private void m708() {
      if (this.f9 && this.f8 == Speed$1.f2) {
         if (this.mc.player.verticalCollision) {
            if (this.f10) {
               this.f10 = false;
               this.f11++;
            }
         } else {
            this.f10 = true;
            if (this.f11 >= 4 && this.mc.player.getVelocity().y <= 0.0) {
               this.m698();
            }
         }
      }
   }

   @EventHandler
   public void m330(RenderEvent var1) {
      if (this.m752()) {
         if (!this.util.m81() && MovementUtil.m91()) {
            long var2 = System.nanoTime();
            if (!this.f9) {
               this.f9 = true;
               this.m698();
            } else {
               if (this.f8 == Speed$1.f1 && var2 - this.f13 >= 1250000000L) {
                  this.m699();
               } else if (this.f8 == Speed$1.f2 && var2 - this.f13 >= 2400000000L) {
                  this.m698();
               }
            }
         } else {
            this.m700();
         }
      }
   }

   @EventHandler
   public void m660(MovementInputEvent var1) {
      if (this.m752()) {
         if (!this.util.m81() && this.f8 == Speed$1.f2 && MovementUtil.m91()) {
            this.f12 = this.mc.player.verticalCollision ? this.f12 + 1 : 0;
            var1.m579(true);
            if (this.f12 > 0) {
               var1.m4(true);
            }
         } else {
            this.f12 = 0;
         }
      }
   }

   @EventHandler
   public void m870(PacketSendEvent var1) {
      if (this.m752()) {
         if (!SilentPacketUtil.f1) {
            if (this.f9 && var1.m581() instanceof CommonPongC2SPacket var2) {
               this.f14.add(var2);
               var1.m29();
            }
         }
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (this.m752()) {
         if (this.f9 && var1.m581() instanceof PlayerPositionLookS2CPacket) {
            this.m698();
         }
      }
   }

   private void m698() {
      this.m803();
      this.f8 = Speed$1.f1;
      this.f10 = false;
      this.f11 = 0;
      this.f12 = 0;
      this.f13 = System.nanoTime();
      RenderUtil.m348(0.05F);
   }

   private void m699() {
      this.f8 = Speed$1.f2;
      this.f10 = false;
      this.f11 = 0;
      this.f12 = 0;
      this.f13 = System.nanoTime();
      RenderUtil.m348(1.7F);
   }

   private void m700() {
      this.m803();
      this.f9 = false;
      this.f8 = Speed$1.f1;
      this.f10 = false;
      this.f11 = 0;
      this.f12 = 0;
      this.f13 = 0L;
      RenderUtil.m314();
   }

   private void m803() {
      CommonPongC2SPacket var1;
      while ((var1 = this.f14.poll()) != null) {
         SilentPacketUtil.m94(var1);
      }
   }
}
