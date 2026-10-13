package ua.daamky.features.misc;

import ua.daamky.events.ChatMessageEvent;
import ua.daamky.events.MovementInputEvent;
import ua.daamky.events.PostMotionEvent;
import ua.daamky.gui.Daamky_2;
import ua.daamky.mixins.interfaces.IBossBarHud;
import ua.daamky.mixins.interfaces.IPlayerListHud;
import ua.daamky.mixins.interfaces.IWorld;
import ua.daamky.settings.BooleanSetting;
import ua.daamky.settings.ModeSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import ua.daamky.utils.player.RotationUtil;
import ua.daamky.utils.player.RotationMode;
import ua.daamky.utils.player.RotationVec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction.Type;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import net.minecraft.world.chunk.BlockEntityTickInvoker;

@NewFunction(
   I0 = "AutoWarden",
   I00 = "Автоматизирует фарм варденов на анархии",
   I000 = Category.MISC
)
public class AutoWarden extends Module {
   private static final double f1 = -2070.0;
   private static final double f2 = -1921.0;
   private static final double f3 = -2076.0;
   private static final double f4 = -1929.0;
   private static final Pattern f5 = Pattern.compile("(\\d{1,2}):(\\d{2})");
   private final BooleanSetting f6 = new BooleanSetting("Использовать скорость", false);
   private final BooleanSetting f7 = new BooleanSetting("Репортить обидчиков", false);
   private final ModeSetting f8 = new ModeSetting(
      "Приоритеты лута",
      "Средний",
      "Низкий",
      "Высокий"
   );
   private int f9;
   private boolean f10;
   private Box f11;
   private BlockPos f12;
   private String f13;
   private final List<Integer> f14 = new ArrayList<>();
   private final Map<BlockPos, Integer> f15 = new HashMap<>();
   private final Map<BlockPos, Integer> f16 = new HashMap<>();
   private int f17 = 1;
   private long f18;
   private AutoWarden$2 f19 = AutoWarden$2.f1;
   private final Map<BlockPos, AutoWarden$1> f20 = new HashMap<>();

   public AutoWarden() {
      this.addSettings(new Setting[]{this.f6, this.f7, this.f8});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      int var1 = this.m828();
      if (var1 >= 0) {
         this.f14.remove(Integer.valueOf(var1));
         this.f14.add(0, var1);
      }

      this.f17 = 1;
      this.f19 = AutoWarden$2.f3;
      this.f16.clear();
      Daamky_2.m467(
         "Shift + Пробел — быстрое выключение функции",
         Formatting.GRAY
      );
      // Baritone removed
      this.updateToggled(true);
   }

   @Override
   public void onDisable() {
      // Baritone removed
      this.m700();
      this.updateToggled(false);
      super.onDisable();
   }

   private void updateToggled(boolean var1) {
      // Baritone removed — no blocksToAvoid
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      if (!this.util.m81()) {
         if (this.mc.currentScreen instanceof DeathScreen && this.mc.player.deathTime >= 5) {
            this.mc.player.requestRespawn();
         }

         if (this.mc.options.sneakKey.isPressed() && this.mc.options.jumpKey.isPressed()) {
            this.setEnabled(false);
         } else {
            this.m682();
            this.m683();
            if (this.f13 != null) {
               if (this.mc.player.age >= 20 && this.mc.player.age < 30) {
                  this.mc.player.networkHandler.sendChatMessage("/report " + this.f13 + " чит");
                  this.f13 = null;
               }
            } else if (this.mc.player.age < 5) {
               this.f9 = 0;
               this.f15.clear();
            } else if (this.m828() < 0) {
               if (this.mc.player.age % 100 == 0 && this.m816() >= 0 && this.mc.player.age > 300) {
                  this.mc.player.networkHandler.sendChatCommand("an" + this.m816());
               }

               this.f19 = AutoWarden$2.f1;
            } else {
               if (this.mc.player.age % 100 == 0 && this.m654() && (this.f11 == null || !this.m655())) {
                  this.f11 = new Box(
                     -2070.0, (double)this.mc.player.getBlockPos().getY(), -2076.0, -1921.0, (double)this.mc.player.getBlockPos().getY(), -1929.0
                  );
               }

               if (this.mc.player.hasStatusEffect(StatusEffects.GLOWING) && this.m821(32.0)) {
                  this.m300(false);
               } else {
                  switch (this.f19) {
                     case f1:
                        this.m115();
                        break;
                     case f2:
                        this.m116();
                        break;
                     case f3:
                        this.m676();
                        break;
                     case f4:
                        this.m135();
                  }

                  if (this.m646() && this.mc.player.age % 15 == 0) {
                     this.m700();
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void m780(ChatMessageEvent var1) {
      GameMessageS2CPacket var2 = var1.m556();
      if (var2 != null) {
         String var3 = var2.content().getString();
         if (var3.contains("Помянем. Вы погибли")) {
            this.f10 = true;
            if (this.f7.m6()
               && this.mc.player != null
               && var3.contains("Вас убил")
               && !this.mc.player.hasStatusEffect(StatusEffects.GLOWING)
               && !this.m822(2.0)) {
               try {
                  this.f13 = var3.split("Вас убил ")[1].split(",")[0].trim();
               } catch (Exception var5) {
               }
            }
         }
      }
   }

   @EventHandler
   public void m660(MovementInputEvent var1) {
      if (this.mc.player != null) {
         if (!this.mc.player.isOnGround() && !this.mc.player.isClimbing()) {
            var1.m4(false);
         }

         if (this.m646() && this.mc.player.getMainHandStack().isEmpty() && !this.m822(3.0)) {
            int var2 = (float)(this.mc.player.age % 10) <= this.m614(3.0F, 8.0F) ? -1 : 1;
            var1.m348((float)var2);
            var1.m410((float)var2);
         }
      }
   }

   private void m115() {
      if (this.m816() >= 0 && this.m828() != this.m816() && !this.m830() && this.mc.player.age % 5 == 0 && this.mc.player.age > 5) {
         this.mc.player.networkHandler.sendChatCommand("an" + this.m816());
      }

      if (this.m653()) {
         this.m805();
         this.m787(this.m644(), true, AutoWarden$2.f2);
      }
   }

   private void m116() {
      if (this.f10 && this.f14.size() > 1) {
         this.f17++;
         if (this.f17 >= this.f14.size()) {
            this.f17 = 1;
         }

         this.f10 = false;
      }

      this.f9 = 0;
      this.f15.clear();
      if (this.m653()) {
         this.m787(this.m643() || this.m642(), false, AutoWarden$2.f3);
      }
   }

   private void m676() {
      if (!this.m752()) {
         if (this.f14.size() <= 1) {
            if (this.mc.player.age % 20 == 0) {
               Daamky_2.m467("ОШИБКА -> список анархий пуст", Formatting.RED);
            }
         } else {
            if (this.f17 >= this.f14.size()) {
               this.f17 = 1;
            }

            int var1 = this.f14.get(this.f17);
            if (this.m828() != var1) {
               if (this.mc.player.age % 10 == 0 && this.mc.player.age > 10) {
                  this.mc.player.networkHandler.sendChatCommand("an" + var1);
               }
            } else {
               if (this.mc.player.age > 5) {
                  this.m134();
               }
            }
         }
      }
   }

   private void m134() {
      StatusEffectInstance var1 = this.mc.player.getStatusEffect(StatusEffects.INVISIBILITY);
      boolean var2 = this.mc.player.hasStatusEffect(StatusEffects.GLOWING) || var1 != null && var1.getDuration() >= 400;
      if (!var2 && var1 == null && this.m741() < 1 && this.mc.player.age % 5 == 0 && !this.m830()) {
         this.f19 = AutoWarden$2.f4;
      } else {
         if (!var2) {
            this.m803();
         }

         if (!this.m654()) {
            if (this.mc.player.age % 50 == 0) {
               this.mc.player.networkHandler.sendChatCommand("home");
            }
         } else if (var2) {
            int var3 = this.f6.m6() && this.mc.player.getStatusEffect(StatusEffects.SPEED) == null ? this.m804(this::m809) : -1;
            if (var3 < 0) {
               this.m677();
            } else {
               this.m691(var3);
            }
         }
      }
   }

   private void m135() {
      if (this.m653()) {
         this.f19 = AutoWarden$2.f1;
      } else if (this.m791() && this.m830()) {
         this.m300(true);
      } else {
         BlockPos var1 = this.m798();
         if (!(this.mc.currentScreen instanceof GenericContainerScreen)
            && (var1 == null || this.m796(var1) >= 0L || !(this.mc.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(var1)) <= 16.0))) {
            if (!this.m830()) {
               this.f19 = AutoWarden$2.f1;
            } else {
               if (this.m814() < 23 && !this.m821(2.0) && this.m831() > 16 && var1 != null) {
                  this.m677();
               } else {
                  this.m300(true);
               }
            }
         } else {
            this.m677();
         }
      }
   }

   private void m677() {
      if (this.mc.currentScreen instanceof GenericContainerScreen var8) {
         this.m788(var8);
      } else {
         BlockPos var1 = this.m798();
         if (var1 == null) {
            var1 = this.m797();
         }

         boolean var9 = var1 != null && this.f12 != null && !var1.equals(this.f12) && this.m796(this.f12) > 25000L;
         if (!var9) {
            this.f18 = System.currentTimeMillis();
         }

         if (!var9 || System.currentTimeMillis() - this.f18 > 1000L) {
            this.f12 = var1;
         }

         BlockPos var3 = this.f12;
         if (var3 == null && this.mc.player.age % 40 == 0) {
            this.f19 = AutoWarden$2.f4;
            this.f10 = true;
         }

         if (var3 != null) {
            long var4 = this.m796(var3);
            if (var4 > 1000L && this.m820(var3, 7.0)) {
               BlockPos var10 = this.m800(var3);
               if (var10 != null) {
                  if (this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var10)) > 2.0) {
                     this.m722(var10);
                  } else {
                     this.m700();
                  }
               }
            } else if (var4 > 6000L) {
               this.m722(this.m799(var3));
            } else {
               double var6 = this.mc.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(var3));
               if (var6 <= 20.0) {
                  if (this.f15.getOrDefault(var3, 0) < (var4 >= 0L ? 1 : 3) && this.m786(var3, var4 >= 0L ? 6 : 1)) {
                     this.f15.merge(var3, 1, Integer::sum);
                  }
               } else {
                  if (var6 > 10.0) {
                     this.m805();
                  }

                  this.m722(this.m800(var3));
               }
            }
         }
      }
   }

   private boolean m786(BlockPos var1, int var2) {
      if (var1 != null && !(this.mc.currentScreen instanceof GenericContainerScreen)) {
         Vec3d var3 = this.mc.player.getEyePos();
         Vec3d var4 = this.m680(var3, var1);
         if (var4 == null) {
            return false;
         } else {
            RotationVec var5 = RotationUtil.m417(var4);
            float var6 = (float)this.mc.player.age + this.mc.getRenderTickCounter().getTickProgress(false);
            float var7 = (float)(
               (Math.sin((double)(var6 * 0.31F)) * 0.5 + Math.sin((double)(var6 * 0.73F + 1.1F)) * 0.3 + Math.sin((double)(var6 * 1.7F + 2.6F)) * 0.2) * 8.0
            );
            RotationUtil.m406(
               new RotationVec(var5.m329() + var7, MathHelper.clamp(var5.m271() + var7 / 4.0F, -90.0F, 90.0F)), RotationMode.f1, 120.0F, 120.0F, 120.0F
            );
            if (this.mc.player.age % var2 == 0 && !(this.m823(var5) > 5.0)) {
               BlockHitResult var8 = this.mc.world.raycast(new RaycastContext(var3, var4, ShapeType.COLLIDER, FluidHandling.NONE, this.mc.player));
               if (!var8.getBlockPos().equals(var1)) {
                  return false;
               } else {
                  this.mc.interactionManager.interactBlock(this.mc.player, Hand.MAIN_HAND, var8);
                  this.mc.player.swingHand(Hand.MAIN_HAND);
                  return true;
               }
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private void m787(boolean var1, boolean var2, AutoWarden$2 var3) {
      if (!var1) {
         if (this.m806()) {
            this.f19 = var3;
         }
      } else if (this.mc.currentScreen instanceof GenericContainerScreen var4) {
         if (var2) {
            this.m790(var4);
         } else {
            this.m789(var4);
         }
      } else {
         this.m786(this.m802(var2), 2);
      }
   }

   private void m788(GenericContainerScreen var1) {
      if (this.m649()) {
         this.m700();
      } else if (this.mc.player.age % 2 == 0) {
         Slot var2 = this.m807(var1, false, var1x -> !var1x.isEmpty() && !this.m810(var1x));
         if (var2 == null) {
            this.m806();
         } else {
            this.m808(var1, var2, 0, SlotActionType.QUICK_MOVE);
         }
      }
   }

   private void m789(GenericContainerScreen var1) {
      if (this.m649()) {
         this.m700();
      } else if (this.mc.player.age % 2 == 0) {
         ItemStack var2 = ((GenericContainerScreenHandler)var1.getScreenHandler()).getCursorStack();
         if (!var2.isEmpty()) {
            Predicate<ItemStack> var3 = var1x -> var1x.isEmpty() || ItemStack.areItemsAndComponentsEqual(var1x, var2);
            if (!this.m689(var2)) {
               this.m808(var1, this.m807(var1, false, var3), 0, SlotActionType.PICKUP);
            } else {
               this.m808(var1, this.m807(var1, true, var3), 1, SlotActionType.PICKUP);
            }
         } else {
            this.m808(var1, this.m807(var1, false, this::m689), 0, SlotActionType.PICKUP);
         }
      }
   }

   private void m790(GenericContainerScreen var1) {
      if (this.m649()) {
         this.m700();
      } else if (this.mc.player.age % 2 == 0) {
         boolean var2 = false;
         boolean var3 = false;
         int var4 = 0;

         for (Slot var6 : ((GenericContainerScreenHandler)var1.getScreenHandler()).slots) {
            if (var4 >= 4) {
               return;
            }

            ItemStack var7 = var6.getStack();
            if (var6.inventory == this.mc.player.getInventory() && !var7.isEmpty() && (!this.f6.m6() || !this.m809(var7))) {
               if (!var2 && this.m710(var7)) {
                  var2 = true;
               } else if (!var3 && var7.isOf(Items.GOLDEN_CARROT)) {
                  var3 = true;
               } else {
                  this.m808(var1, var6, 0, SlotActionType.QUICK_MOVE);
                  var4++;
               }
            }
         }
      }
   }

   private boolean m752() {
      boolean var1 = this.m791();
      if ((
            var1
               || this.m814() > this.m11(20)
               || this.mc.player.getHungerManager().getFoodLevel() < 8
               || this.f15.values().stream().filter(var0 -> var0 >= 2).count() >= 3L && this.mc.player.age % 30 == 0
         )
         && this.mc.player.age > 100) {
         if (var1) {
            this.f10 = true;
         }

         this.f19 = AutoWarden$2.f4;
         return true;
      } else if (!this.m830() && this.m814() > this.m11(8)) {
         this.f19 = AutoWarden$2.f4;
         return true;
      } else {
         int var2 = this.m831();
         if (var2 >= 0 && var2 < 7 && !this.m821(14.0) && this.m814() > this.m11(7)) {
            this.f19 = AutoWarden$2.f4;
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean m791() {
      if (this.mc.player.age >= this.f9) {
         return false;
      } else {
         for (Entity var2 : this.mc.world.getEntities()) {
            if (var2 instanceof WardenEntity var3) {
               double var4 = this.mc.player.squaredDistanceTo(var3);
               if (var4 < 900.0 && this.m793(var3) && (var4 < 16.0 || this.m792(var3))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean m792(WardenEntity var1) {
      return (this.mc.player.getX() - var1.getX()) * (var1.getX() - var1.lastX) + (this.mc.player.getZ() - var1.getZ()) * (var1.getZ() - var1.lastZ) > 0.01;
   }

   private boolean m793(WardenEntity var1) {
      double var2 = Math.toDegrees(Math.atan2(-(this.mc.player.getX() - var1.getX()), this.mc.player.getZ() - var1.getZ()));
      return Math.abs((((double)var1.getBodyYaw() - var2) % 360.0 + 540.0) % 360.0 - 180.0) < 10.0;
   }

   private void m300(boolean var1) {
      this.m806();
      this.m805();
      BlockPos var2 = null;
      double var3 = -1.0;
      int var5 = this.mc.player.getBlockPos().getY();

      for (byte var6 = 0; var6 < 360; var6 += 30) {
         int var7 = this.m826((int)(this.mc.player.getX() + Math.cos(Math.toRadians((double)var6)) * 25.0));
         int var8 = this.m827((int)(this.mc.player.getZ() + Math.sin(Math.toRadians((double)var6)) * 25.0));
         double var9 = this.m794(var7, var8, var1);
         if (var9 > var3) {
            var3 = var9;
            var2 = new BlockPos(var7, var5, var8);
         }
      }

      this.m722(var2);
   }

   private double m794(int var1, int var2, boolean var3) {
      double var4 = Double.MAX_VALUE;

      for (Entity var7 : this.mc.world.getEntities()) {
         if (var7 != this.mc.player && (var7 instanceof PlayerEntity || var3 && var7 instanceof WardenEntity)) {
            var4 = Math.min(var4, Math.hypot(var7.getX() - (double)var1, var7.getZ() - (double)var2));
         }
      }

      return var4;
   }

   private void m682() {
      for (Entity var2 : this.mc.world.getEntitiesByClass(WardenEntity.class, this.mc.player.getBoundingBox().expand(256.0), var0 -> true)) {
         this.f16.put(var2.getBlockPos(), this.mc.player.age + 100);
         if (this.mc.player.squaredDistanceTo(var2) < 576.0) {
            this.f9 = this.mc.player.age + 100;
         }
      }

      this.f16.values().removeIf(var1 -> this.mc.player.age > var1);
   }

   private void m683() {
      int var1 = this.m828();
      long var2 = System.currentTimeMillis();

      for (Entity var5 : this.mc.world.getEntitiesByClass(ArmorStandEntity.class, this.mc.player.getBoundingBox().expand(256.0), var0 -> true)) {
         Matcher var6 = f5.matcher(var5.getName().getString());
         if (var6.find()) {
            BlockPos var7 = this.m824(var5.getBlockPos());
            if (var7 != null) {
               long var8 = ((long)Integer.parseInt(var6.group(1)) * 60L + (long)Integer.parseInt(var6.group(2))) * 1000L;
               AutoWarden$1 var10 = this.f20.get(var7);
               if (var10 == null || var10.f3 != var1 || Math.abs(var8 / 1000L - var10.m782(var2) / 1000L) > 5L) {
                  this.f20.put(var7, new AutoWarden$1(var8, var2, var1));
               }
            }
         }
      }

      for (BlockEntityTickInvoker var12 : ((IWorld)this.mc.world).getBlockEntityTickers()) {
         if (!var12.isRemoved()) {
            BlockPos var13 = var12.getPos();
            if (this.m825(var13) && (this.mc.world.getBlockState(var13).isOf(Blocks.CHEST) || this.mc.world.getBlockState(var13).isOf(Blocks.TRAPPED_CHEST))) {
               AutoWarden$1 var14 = this.f20.get(var13);
               if (var14 == null || var14.f3 != var1) {
                  this.f20.put(var13, new AutoWarden$1(-1L, var2, var1));
               }
            }
         }
      }
   }

   private List<BlockPos> m795() {
      int var1 = this.m828();
      ArrayList var2 = new ArrayList();

      for (Entry var4 : this.f20.entrySet()) {
         if (((AutoWarden$1)var4.getValue()).f3 == var1) {
            var2.add((BlockPos)var4.getKey());
         }
      }

      return var2;
   }

   private long m796(BlockPos var1) {
      AutoWarden$1 var2 = this.f20.get(var1);
      return var2 != null && var2.f3 == this.m828() ? var2.m782(System.currentTimeMillis()) : -1L;
   }

   private BlockPos m797() {
      BlockPos var1 = null;
      long var2 = 45000L;

      for (BlockPos var5 : this.m795()) {
         long var6 = this.m796(var5);
         if (var6 >= 0L && var6 < var2 && this.m817(var5) && !this.m818(var5) && !this.m819(var5)) {
            var2 = var6;
            var1 = var5;
         }
      }

      return var1;
   }

   private BlockPos m798() {
      BlockPos var1 = null;
      byte var2 = 99;
      double var3 = Double.MAX_VALUE;

      for (BlockPos var6 : this.m795()) {
         if (this.m817(var6) && !this.m819(var6)) {
            double var7 = this.mc.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(var6));
            long var9 = this.m796(var6);
            if ((!this.m818(var6) || var9 < 0L && var7 <= 16.0) && (var9 >= 0L || this.f15.getOrDefault(var6, 0) < 3)) {
               byte var11 = -1;
               if (var9 < 0L && var7 <= 25.0) {
                  var11 = 0;
               } else if (var9 >= 0L && var9 <= 5000L && var7 <= 144.0) {
                  var11 = 1;
               } else if (var9 < 0L && var7 <= 144.0) {
                  var11 = 2;
               } else if (var9 >= 0L && var9 <= 15000L && var7 <= 625.0) {
                  var11 = 3;
               } else if (var9 < 0L) {
                  var11 = 4;
               }

               if (var11 >= 0) {
                  double var12 = Vec3d.ofCenter(var6).y - this.mc.player.getEyeY();
                  double var14 = (double)var6.getX() + 0.5 - this.mc.player.getX();
                  double var16 = (double)var6.getZ() + 0.5 - this.mc.player.getZ();
                  double var18 = var14 * var14 + var16 * var16 + (double)(var12 > 0.0 ? 2 : 1) * var12 * var12;
                  if (var11 < var2 || var11 == var2 && var18 < var3) {
                     var2 = var11;
                     var3 = var18;
                     var1 = var6;
                  }
               }
            }
         }
      }

      return var1;
   }

   private void m722(BlockPos var1) {
      // Baritone removed — pathing disabled
   }

   private void m700() {
      // Baritone removed
   }

   private BlockPos m799(BlockPos var1) {
      double var2 = (double)this.mc.player.age / 40.0 * 2.4;
      return new BlockPos(this.m826(var1.getX() + (int)(Math.cos(var2) * 10.0)), var1.getY(), this.m827(var1.getZ() + (int)(Math.sin(var2) * 10.0)));
   }

   private BlockPos m800(BlockPos var1) {
      for (int var2 = -1; var2 <= 1; var2++) {
         for (int var3 = -1; var3 <= 1; var3++) {
            if (var2 != 0 || var3 != 0) {
               BlockPos var4 = var1.add(var2, 0, var3);
               if (this.mc.world.getBlockState(var4).isAir()
                  && this.mc.world.getBlockState(var4.up()).isAir()
                  && !this.mc.world.getBlockState(var4.down()).isAir()
                  && this.m801(var4, var1)) {
                  return var4;
               }
            }
         }
      }

      return this.mc.world.getBlockState(var1.up()).isAir() && this.mc.world.getBlockState(var1.up().up()).isAir() && this.m801(var1.up(), var1)
         ? var1.up()
         : null;
   }

   private boolean m801(BlockPos var1, BlockPos var2) {
      return this.m680(Vec3d.ofCenter(var1).add(0.0, (double)this.mc.player.getEyeHeight(this.mc.player.getPose()) - 0.5, 0.0), var2) != null;
   }

   private Vec3d m680(Vec3d var1, BlockPos var2) {
      Vec3d var3 = Vec3d.ofCenter(var2);
      Vec3d var4 = null;
      double var5 = Double.MAX_VALUE;

      for (double var7 = -0.4; var7 <= 0.41; var7 += 0.4) {
         for (double var9 = -0.4; var9 <= 0.41; var9 += 0.4) {
            for (double var11 = -0.4; var11 <= 0.41; var11 += 0.4) {
               Vec3d var13 = var3.add(var7, var9, var11);
               double var14 = var13.squaredDistanceTo(var3);
               if (var14 < var5
                  && this.mc.world.raycast(new RaycastContext(var1, var13, ShapeType.COLLIDER, FluidHandling.NONE, this.mc.player)).getBlockPos().equals(var2)) {
                  var5 = var14;
                  var4 = var13;
               }
            }
         }
      }

      return var4;
   }

   private BlockPos m802(boolean var1) {
      BlockPos var2 = this.mc.player.getBlockPos();
      Mutable var3 = new Mutable();

      for (int var4 = -4; var4 <= 4; var4++) {
         for (int var5 = -4; var5 <= 4; var5++) {
            for (int var6 = -4; var6 <= 4; var6++) {
               var3.set(var2.getX() + var4, var2.getY() + var5, var2.getZ() + var6);
               if (this.mc.world.getBlockState(var3).isOf(Blocks.CHEST)
                  && this.m126(var3) == var1
                  && this.mc.world.getBlockState(var3.up()).isAir()
                  && this.mc
                     .world
                     .raycast(new RaycastContext(this.mc.player.getEyePos(), Vec3d.ofCenter(var3), ShapeType.COLLIDER, FluidHandling.NONE, this.mc.player))
                     .getBlockPos()
                     .equals(var3)) {
                  return var3.toImmutable();
               }
            }
         }
      }

      return null;
   }

   private boolean m126(BlockPos var1) {
      if (this.mc.world.getBlockState(var1.down()).isOf(Blocks.HOPPER)) {
         return true;
      } else {
         BlockState var2 = this.mc.world.getBlockState(var1);
         if (var2.get(Properties.CHEST_TYPE) == ChestType.SINGLE) {
            return false;
         } else {
            for (Direction var4 : Type.HORIZONTAL) {
               BlockPos var5 = var1.offset(var4);
               BlockState var6 = this.mc.world.getBlockState(var5);
               if (var6.isOf(Blocks.CHEST)
                  && var6.get(Properties.CHEST_TYPE) != ChestType.SINGLE
                  && var6.get(Properties.CHEST_TYPE) != var2.get(Properties.CHEST_TYPE)
                  && var6.get(Properties.HORIZONTAL_FACING) == var2.get(Properties.HORIZONTAL_FACING)
                  && this.mc.world.getBlockState(var5.down()).isOf(Blocks.HOPPER)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private void m803() {
      int var1 = this.m804(this::m710);
      if (var1 >= 0 && this.mc.player.age > 20) {
         this.m691(var1);
      }
   }

   private void m691(int var1) {
      if (var1 >= 0) {
         if (var1 < 9) {
            this.mc.player.getInventory().setSelectedSlot(var1);
         } else {
            int var2 = this.mc.player.playerScreenHandler.syncId;
            this.mc.interactionManager.clickSlot(var2, var1, 8, SlotActionType.SWAP, this.mc.player);
            this.mc.player.getInventory().setSelectedSlot(8);
         }

         this.mc.interactionManager.interactItem(this.mc.player, Hand.MAIN_HAND);
      }
   }

   private int m804(Predicate<ItemStack> var1) {
      for (int var2 = 0; var2 < 36; var2++) {
         if (var1.test(this.mc.player.getInventory().getStack(var2))) {
            return var2;
         }
      }

      return -1;
   }

   private void m805() {
      if (!this.mc.player.getMainHandStack().isEmpty()) {
         for (int var1 = 0; var1 < 9; var1++) {
            if (this.mc.player.getInventory().getStack(var1).isEmpty()) {
               this.mc.player.getInventory().setSelectedSlot(var1);
               return;
            }
         }
      }
   }

   private boolean m806() {
      if (this.mc.currentScreen instanceof GenericContainerScreen && this.mc.player.age % 2 == 0) {
         this.mc.player.closeHandledScreen();
      }

      return !(this.mc.currentScreen instanceof GenericContainerScreen);
   }

   private Slot m807(GenericContainerScreen var1, boolean var2, Predicate<ItemStack> var3) {
      for (Slot var5 : ((GenericContainerScreenHandler)var1.getScreenHandler()).slots) {
         if (var5.inventory == this.mc.player.getInventory() == var2 && var3.test(var5.getStack())) {
            return var5;
         }
      }

      return null;
   }

   private void m808(GenericContainerScreen var1, Slot var2, int var3, SlotActionType var4) {
      if (var2 != null) {
         this.mc.interactionManager.clickSlot(((GenericContainerScreenHandler)var1.getScreenHandler()).syncId, var2.id, var3, var4, this.mc.player);
      }
   }

   private boolean m642() {
      if (this.mc.currentScreen instanceof GenericContainerScreen var1 && !((GenericContainerScreenHandler)var1.getScreenHandler()).getCursorStack().isEmpty()) {
         return true;
      }

      return false;
   }

   private boolean m689(ItemStack var1) {
      if (var1.isEmpty()) {
         return false;
      } else if (this.m710(var1) && this.m741() < 1) {
         return true;
      } else {
         return var1.isOf(Items.GOLDEN_CARROT) && this.m696(Items.GOLDEN_CARROT) < 3 ? true : this.f6.m6() && this.m809(var1) && this.m804(this::m809) < 0;
      }
   }

   private boolean m643() {
      return this.m741() < 1 || this.m696(Items.GOLDEN_CARROT) < 3 || this.f6.m6() && this.m804(this::m809) < 0;
   }

   private boolean m644() {
      boolean var1 = false;
      boolean var2 = false;

      for (ItemStack var4 : this.m815()) {
         if (!var4.isEmpty() && (!this.f6.m6() || !this.m809(var4))) {
            if (var1 || !this.m710(var4)) {
               if (var2 || !var4.isOf(Items.GOLDEN_CARROT)) {
                  return true;
               }

               var2 = true;
            } else {
               var1 = true;
            }
         }
      }

      return false;
   }

   private int m741() {
      int var1 = 0;

      for (ItemStack var3 : this.m815()) {
         if (this.m710(var3)) {
            var1++;
         }
      }

      return var1;
   }

   private int m696(Item var1) {
      int var2 = 0;

      for (ItemStack var4 : this.m815()) {
         if (var4.isOf(var1)) {
            var2 += var4.getCount();
         }
      }

      return var2;
   }

   private boolean m710(ItemStack var1) {
      RegistryEntry var2 = (RegistryEntry)((PotionContentsComponent)var1.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT))
         .potion()
         .orElse(null);
      return var2 != null && (var2.equals(Potions.INVISIBILITY) || var2.equals(Potions.LONG_INVISIBILITY));
   }

   private boolean m809(ItemStack var1) {
      if (!var1.isOf(Items.POTION)) {
         return false;
      } else {
         for (StatusEffectInstance var3 : ((PotionContentsComponent)var1.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT))
            .getEffects()) {
            if (var3.getEffectType().equals(StatusEffects.SPEED)) {
               return true;
            }
         }

         return false;
      }
   }

   private int m11(int var1) {
      double var2 = this.f8.m17("Низкий")
         ? 1.5
         : (this.f8.m17("Высокий") ? 0.8 : 1.0);
      return (int)((double)var1 * var2);
   }

   private boolean m810(ItemStack var1) {
      return this.f8.m17("Низкий")
         ? false
         : this.m813(var1) || this.f8.m17("Высокий") && this.m812(var1);
   }

   private boolean m811(ItemStack var1) {
      EquippableComponent var2 = (EquippableComponent)var1.get(DataComponentTypes.EQUIPPABLE);
      return var2 != null && var2.slot().isArmorSlot();
   }

   private boolean m812(ItemStack var1) {
      Item var2 = var1.getItem();
      return var2 instanceof ArrowItem
         || var1.isIn(ItemTags.PICKAXES)
         || var2 instanceof AxeItem
         || var1.isOf(Items.CHORUS_FRUIT)
         || var1.isOf(Items.DISC_FRAGMENT_5)
         || var1.isOf(Items.NAUTILUS_SHELL)
         || var1.isOf(Items.BOOKSHELF)
         || var1.isOf(Items.COOKED_MUTTON)
         || var1.isOf(Items.SKELETON_SPAWN_EGG)
         || var1.isOf(Items.CREEPER_SPAWN_EGG)
         || var1.isOf(Items.ZOMBIE_SPAWN_EGG)
         || var1.isOf(Items.VINDICATOR_SPAWN_EGG)
         || var1.isOf(Items.PIGLIN_SPAWN_EGG)
         || var1.isOf(Items.FIRE_CHARGE)
         || var1.isOf(Items.LEATHER)
         || var1.isOf(Items.SHULKER_SHELL)
         || var1.isOf(Items.EXPERIENCE_BOTTLE)
         || var1.isOf(Items.WITHER_ROSE)
         || var1.isOf(Items.EMERALD)
         || var1.isOf(Items.SUGAR)
         || var1.contains(DataComponentTypes.JUKEBOX_PLAYABLE)
         || var1.isOf(Items.GHAST_TEAR)
         || var1.isOf(Items.DRAGON_BREATH)
         || var1.isOf(Items.VEX_SPAWN_EGG)
         || var1.isOf(Items.ENDERMITE_SPAWN_EGG)
         || var1.isOf(Items.CAT_SPAWN_EGG)
         || var1.isOf(Items.ENCHANTING_TABLE)
         || var1.isOf(Items.DIAMOND_HELMET)
         || var1.isOf(Items.DIAMOND_CHESTPLATE)
         || var1.isOf(Items.DIAMOND_LEGGINGS)
         || var1.isOf(Items.DIAMOND_BOOTS);
   }

   private boolean m813(ItemStack var1) {
      Item var2 = var1.getItem();
      return var2 instanceof ShovelItem
         || var2 instanceof AxeItem
         || var2 instanceof BannerItem
         || var2 instanceof SmithingTemplateItem && !var1.isOf(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
         || var1.isOf(Items.BLAZE_ROD)
         || var1.isOf(Items.ENCHANTED_BOOK)
         || var1.isOf(Items.TRIDENT)
         || var1.isOf(Items.NAME_TAG)
         || var1.isOf(Items.SCULK)
         || var1.isOf(Items.SCULK_SENSOR)
         || var1.isOf(Items.ENDER_CHEST)
         || var1.isOf(Items.REINFORCED_DEEPSLATE)
         || var1.isOf(Items.PUFFERFISH)
         || var1.isOf(Items.HONEY_BOTTLE)
         || var1.isOf(Items.FERMENTED_SPIDER_EYE)
         || var1.isOf(Items.ANVIL)
         || var1.isOf(Items.COOKED_PORKCHOP);
   }

   private boolean m646() {
      BlockState var1 = this.mc.world.getBlockState(this.mc.player.getBlockPos());
      BlockState var2 = this.mc.world.getBlockState(this.mc.player.getBlockPos().down());
      return !var1.isIn(BlockTags.CANDLES) && !var2.isIn(BlockTags.CANDLES)
         ? !this.m649() && this.f19 == AutoWarden$2.f3 && this.m654() && this.mc.currentScreen == null && !this.m648() && this.m647()
         : true;
   }

   private boolean m647() {
      Box var1 = this.mc.player.getBoundingBox().expand(0.05, 0.0, 0.05);

      for (BlockPos var3 : BlockPos.iterate(BlockPos.ofFloored(var1.minX, var1.minY, var1.minZ), BlockPos.ofFloored(var1.maxX, var1.maxY, var1.maxZ))) {
         if (!this.mc.world.getBlockState(var3).isAir()) {
            return true;
         }
      }

      return false;
   }

   private boolean m648() {
      return false; // Baritone removed
   }

   private boolean m649() {
      return this.mc.player.getVelocity().horizontalLengthSquared() > 0.0025;
   }

   private int m814() {
      int var1 = 0;

      for (ItemStack var3 : this.m815()) {
         if (!var3.isEmpty()) {
            var1++;
         }
      }

      return var1;
   }

   private List<ItemStack> m815() {
      ArrayList var1 = new ArrayList(36);

      for (int var2 = 0; var2 < 36; var2++) {
         var1.add(this.mc.player.getInventory().getStack(var2));
      }

      return var1;
   }

   private int m816() {
      return this.f14.isEmpty() ? -1 : this.f14.get(0);
   }

   private boolean m653() {
      return this.m816() >= 0 && this.m816() == this.m828();
   }

   private boolean m817(BlockPos var1) {
      return this.m800(var1) != null;
   }

   private boolean m818(BlockPos var1) {
      for (BlockPos var3 : this.f16.keySet()) {
         if (var3.getSquaredDistance(var1) < 25.0) {
            return true;
         }
      }

      return false;
   }

   private boolean m819(BlockPos var1) {
      for (Entity var3 : this.mc.world.getEntities()) {
         if (var3 instanceof PlayerEntity) {
            PlayerEntity var4 = (PlayerEntity)var3;
            if (var4 != this.mc.player && var4.getEntityPos().squaredDistanceTo(Vec3d.ofCenter(var1)) < 20.0) {
               for (EquipmentSlot var8 : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
                  if (this.m811(var4.getEquippedStack(var8))) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean m820(BlockPos var1, double var2) {
      for (Entity var5 : this.mc.world.getEntities()) {
         if (var5 instanceof PlayerEntity var6 && var6 != this.mc.player && var6.getEntityPos().squaredDistanceTo(Vec3d.ofCenter(var1)) < var2 * var2) {
            return true;
         }
      }

      return false;
   }

   private boolean m821(double var1) {
      for (Entity var4 : this.mc.world.getEntities()) {
         if (var4 instanceof PlayerEntity var5 && var5 != this.mc.player && this.mc.player.squaredDistanceTo(var5) < var1 * var1) {
            return true;
         }
      }

      return false;
   }

   private boolean m822(double var1) {
      for (BlockPos var4 : this.m795()) {
         if (this.mc.player.squaredDistanceTo(Vec3d.ofCenter(var4)) <= var1 * var1) {
            return true;
         }
      }

      return false;
   }

   private double m823(RotationVec var1) {
      float var2 = MathHelper.wrapDegrees(var1.m329() - this.mc.player.getYaw());
      float var3 = MathHelper.wrapDegrees(var1.m271() - this.mc.player.getPitch());
      return Math.hypot((double)Math.abs(var2), (double)Math.abs(var3));
   }

   private BlockPos m824(BlockPos var1) {
      for (int var2 = 1; var2 <= 3; var2++) {
         BlockPos var3 = var1.down(var2);
         if (this.mc.world.getBlockState(var3).isOf(Blocks.CHEST) || this.mc.world.getBlockState(var3).isOf(Blocks.TRAPPED_CHEST)) {
            return var3;
         }
      }

      return null;
   }

   private boolean m654() {
      return this.mc.world.getRegistryKey().getValue().toString().equals("minecraft:overworld")
         && this.mc.player.getX() <= -1921.0
         && this.mc.player.getX() >= -2070.0
         && this.mc.player.getZ() <= -1929.0
         && this.mc.player.getZ() >= -2076.0;
   }

   private boolean m825(BlockPos var1) {
      return (double)var1.getX() >= -2070.0 && (double)var1.getX() <= -1921.0 && (double)var1.getZ() >= -2076.0 && (double)var1.getZ() <= -1929.0;
   }

   private boolean m655() {
      return this.f11 != null
         && this.mc.player.getX() >= this.f11.minX
         && this.mc.player.getX() <= this.f11.maxX
         && this.mc.player.getZ() >= this.f11.minZ
         && this.mc.player.getZ() <= this.f11.maxZ;
   }

   private int m826(int var1) {
      return this.f11 == null ? var1 : (int)Math.max(this.f11.minX + 10.0, Math.min(this.f11.maxX - 10.0, (double)var1));
   }

   private int m827(int var1) {
      return this.f11 == null ? var1 : (int)Math.max(this.f11.minZ + 10.0, Math.min(this.f11.maxZ - 10.0, (double)var1));
   }

   private float m614(float var1, float var2) {
      return (float)(Math.random() * (double)(var2 - var1) + (double)var1);
   }

   private int m828() {
      String var1 = this.m829();
      if (var1 != null && var1.contains("Анархия-")) {
         try {
            return Integer.parseInt(var1.split("Анархия-")[1].trim().split("\\s")[0]);
         } catch (Exception var3) {
            return -1;
         }
      } else {
         return -1;
      }
   }

   private String m829() {
      if (this.mc.inGameHud == null) {
         return null;
      } else {
         PlayerListHud var1 = this.mc.inGameHud.getPlayerListHud();
         if (var1 == null) {
            return null;
         } else {
            Text var2 = ((IPlayerListHud)var1).getHeader();
            return var2 == null ? null : var2.getString();
         }
      }
   }

   private boolean m830() {
      if (this.mc.inGameHud == null) {
         return false;
      } else {
         for (ClientBossBar var2 : ((IBossBarHud)this.mc.inGameHud.getBossBarHud()).getBossBars().values()) {
            String var3 = var2.getName().getString().toLowerCase(Locale.ROOT);
            if (var3.contains("pvp")
               || var3.contains("пвп")
               || var3.contains("дуэль")) {
               return true;
            }
         }

         return false;
      }
   }

   private int m831() {
      if (this.mc.inGameHud == null) {
         return -1;
      } else {
         for (ClientBossBar var2 : ((IBossBarHud)this.mc.inGameHud.getBossBarHud()).getBossBars().values()) {
            String var3 = var2.getName().getString().toLowerCase(Locale.ROOT);
            if (var3.contains("pvp") || var3.contains("пвп")) {
               Matcher var4 = f5.matcher(var3);
               if (var4.find()) {
                  return Integer.parseInt(var4.group(1)) * 60 + Integer.parseInt(var4.group(2));
               }

               Matcher var5 = Pattern.compile("(\\d+)").matcher(var3);
               if (var5.find()) {
                  return Integer.parseInt(var5.group(1));
               }
            }
         }

         return -1;
      }
   }
}
