package ua.daamky.features.misc;

import com.mojang.authlib.GameProfile;
import ua.daamky.events.InteractEvent;
import ua.daamky.events.PacketReceiveEvent;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.UUID;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@NewFunction(
   I0 = "FakePlayer",
   I00 = "Спавнит фейкового игрока для тренировки",
   I000 = Category.MISC
)
public class FakePlayer extends Module {
   private static final float f1 = 20.0F;
   private static final long f2 = 500L;
   private OtherClientPlayerEntity f3;
   private float f4 = 20.0F;
   private long f5;

   @Override
   public void onEnable() {
      super.onEnable();
      this.m115();
   }

   @Override
   public void onDisable() {
      this.m116();
      super.onDisable();
   }

   private void m115() {
      if (!this.util.m81() && this.mc.getNetworkHandler() != null && this.f3 == null) {
         this.f4 = 20.0F;
         this.f5 = 0L;
         GameProfile var1 = new GameProfile(UUID.randomUUID(), this.mc.player.getName().getString());
         this.f3 = new OtherClientPlayerEntity(this.mc.world, var1);
         this.f3.copyFrom(this.mc.player);
         this.f3.setPos(this.mc.player.getX(), this.mc.player.getY(), this.mc.player.getZ());
         this.f3.setYaw(this.mc.player.getYaw());
         this.f3.setPitch(this.mc.player.getPitch());
         this.f3.setHealth(20.0F);
         this.mc.world.addEntity(this.f3);
      }
   }

   private void m116() {
      if (this.f3 != null) {
         if (this.mc.world != null) {
            this.mc.world.removeEntity(this.f3.getId(), RemovalReason.DISCARDED);
         }

         this.f3 = null;
      }

      this.f4 = 20.0F;
   }

   @EventHandler
   public void m771(InteractEvent var1) {
      if (this.f3 != null && var1.m553() == this.f3) {
         this.m846(var1.m552());
      }
   }

   @EventHandler
   public void m594(PacketReceiveEvent var1) {
      if (var1.m581() instanceof DisconnectS2CPacket || var1.m581() instanceof GameJoinS2CPacket || var1.m581() instanceof PlayerRespawnS2CPacket) {
         this.setEnabled(false);
      }
   }

   private void m846(PlayerEntity var1) {
      if (var1 != null && this.f3 != null && this.mc.world != null) {
         long var2 = System.currentTimeMillis();
         if (var2 - this.f5 >= 500L) {
            this.f5 = var2;
            float var4 = var1.getAttackCooldownProgress(0.5F);
            float var5 = (float)var1.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
            float var6 = var5 * (0.2F + var4 * var4 * 0.8F);
            boolean var7 = var4 > 0.9F;
            boolean var8 = var7
               && var1.fallDistance > 0.0
               && !var1.isOnGround()
               && !var1.isClimbing()
               && !var1.isTouchingWater()
               && !var1.hasStatusEffect(StatusEffects.BLINDNESS)
               && !var1.hasVehicle();
            if (var8) {
               var6 *= 1.5F;
            }

            boolean var9 = var1.getMainHandStack().hasEnchantments();
            boolean var10 = var7
               && !var8
               && var1.isOnGround()
               && !var1.isSprinting()
               && Registries.ITEM.getId(var1.getMainHandStack().getItem()).getPath().endsWith("_sword");
            this.m847(var1, var7, var8, var10, var6);
            this.m848(var1);
            if (var8) {
               var1.addCritParticles(this.f3);
            }

            if (var9) {
               var1.addEnchantedHitParticles(this.f3);
            }

            if (var10) {
               this.m849(var1);
            }

            if (this.f4 - var6 <= 0.0F) {
               this.m676();
               this.f4 = 20.0F;
            } else {
               this.f4 -= var6;
            }

            this.f3.setHealth(Math.max(this.f4, 1.0F));
         }
      }
   }

   private void m847(PlayerEntity var1, boolean var2, boolean var3, boolean var4, float var5) {
      SoundEvent var6;
      if (var4) {
         var6 = SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP;
      } else if (var3) {
         var6 = SoundEvents.ENTITY_PLAYER_ATTACK_CRIT;
      } else if (var2) {
         var6 = SoundEvents.ENTITY_PLAYER_ATTACK_STRONG;
      } else if (var5 <= 0.0F) {
         var6 = SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE;
      } else {
         var6 = SoundEvents.ENTITY_PLAYER_ATTACK_WEAK;
      }

      this.mc.world.playSound(null, var1.getX(), var1.getY(), var1.getZ(), var6, var1.getSoundCategory(), 1.0F, 1.0F);
   }

   private void m848(PlayerEntity var1) {
      double var2 = this.f3.getX() - var1.getX();
      double var4 = this.f3.getZ() - var1.getZ();
      float var6 = (float)(Math.toDegrees(Math.atan2(var4, var2)) - (double)var1.getYaw());
      this.f3.hurtTime = this.f3.maxHurtTime = 10;
      this.f3.animateDamage(var6);
      this.mc.world.playSound(null, this.f3.getX(), this.f3.getY(), this.f3.getZ(), SoundEvents.ENTITY_PLAYER_HURT, this.f3.getSoundCategory(), 1.0F, 1.0F);
   }

   private void m849(PlayerEntity var1) {
      double var2 = Math.toRadians((double)var1.getYaw());
      double var4 = -Math.sin(var2);
      double var6 = Math.cos(var2);
      this.mc.world.addParticleClient(ParticleTypes.SWEEP_ATTACK, this.f3.getX() + var4, this.f3.getBodyY(0.5), this.f3.getZ() + var6, var4, 0.0, var6);
   }

   private void m676() {
      for (int var1 = 0; var1 < 40; var1++) {
         this.mc
            .world
            .addParticleClient(
               ParticleTypes.TOTEM_OF_UNDYING,
               this.f3.getX(),
               this.f3.getBodyY(0.5),
               this.f3.getZ(),
               (this.mc.world.random.nextDouble() - 0.5) * 2.0,
               this.mc.world.random.nextDouble() * 0.5 + 0.5,
               (this.mc.world.random.nextDouble() - 0.5) * 2.0
            );
      }

      this.mc.world.playSound(null, this.f3.getX(), this.f3.getY(), this.f3.getZ(), SoundEvents.ITEM_TOTEM_USE, this.f3.getSoundCategory(), 1.0F, 1.0F);
   }
}
