package ua.daamky.features.combat;

import ua.daamky.events.PostMotionEvent;
import ua.daamky.settings.KeybindSetting;
import ua.daamky.settings.ModeSettingBase;
import ua.daamky.settings.MultiChoiceSetting;
import ua.daamky.settings.Setting;
import ua.daamky.system.api.Category;
import ua.daamky.system.api.Module;
import ua.daamky.system.api.NewFunction;
import ua.daamky.system.events.EventHandler;
import java.util.Set;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import org.lwjgl.glfw.GLFW;

@NewFunction(
   I0 = "AutoSwap",
   I00 = "Свап предметов в оффхенд (Packet/Legit) + АвтоЦербер / Авто ярость-каратель",
   I000 = Category.COMBAT
)
public class AutoSwap extends Module {
   private final ModeSettingBase f1 = new ModeSettingBase(
      "Режим", "Packet", "Legit"
   );
   private final ModeSettingBase f2 = new ModeSettingBase(
      "Первый предмет",
      "Щит",
      "Геплы",
      "Тотем",
      "Шар"
   );
   private final ModeSettingBase f3 = new ModeSettingBase(
      "Второй предмет",
      "Щит",
      "Геплы",
      "Тотем",
      "Шар"
   );
   private final KeybindSetting f4 = new KeybindSetting("Кнопка", -1);
   private final ModeSettingBase f5 = new ModeSettingBase(
      "Сервер", "HW", "VonTam"
   );
   private final KeybindSetting f6 = new KeybindSetting("Авто Цербер", -1);
   private final KeybindSetting f7 = new KeybindSetting("Авто ярость/каратель", -1);
   private final MultiChoiceSetting f8 = new MultiChoiceSetting(
      "Брать Цербер",
      "Всегда",
      "Когда без меча",
      "Когда ливает",
      "Когда в элитре",
      "Если тебя не бьют"
   );
   private final MultiChoiceSetting f9 = new MultiChoiceSetting(
      "Брать ярость/карателя",
      "Всегда",
      "Когда без меча",
      "Когда ливает",
      "Когда в элитре"
   );
   private boolean f10 = false;
   private boolean f11 = false;
   private int f12 = -1;
   private boolean f13 = false;
   private float f14 = -1.0F;
   private long f15 = 0L;
   private long f16 = 0L;
   private AutoSwap$1 f17 = AutoSwap$1.f1;
   private boolean f18 = false;
   private int f19 = -1;
   private boolean f20;
   private boolean f21;
   private boolean f22;
   private boolean f23;
   private boolean f24;
   private boolean f25 = false;

   public AutoSwap() {
      this.addSettings(new Setting[]{this.f1, this.f2, this.f3, this.f4, this.f5, this.f6, this.f7, this.f8, this.f9});
      this.f8
         .m22(
            Set.of(
               "Когда без меча",
               "Когда ливает",
               "Когда в элитре"
            )
         );
      this.f9
         .m22(
            Set.of(
               "Когда без меча",
               "Когда ливает",
               "Когда в элитре"
            )
         );
      this.m134();
   }

   @Override
   public void onEnable() {
      this.m700();
      this.f18 = false;
      this.f10 = false;
      this.f11 = false;
      this.f12 = -1;
      this.f14 = -1.0F;
   }

   @Override
   public void onDisable() {
      this.m700();
      this.f18 = false;
      this.m699();
      if (this.f11 && this.f12 != -1 && this.mc.player != null && this.mc.interactionManager != null) {
         this.m691(this.f12);
      }

      this.f10 = false;
      this.f11 = false;
      this.f12 = -1;
   }

   @EventHandler
   public void m67(PostMotionEvent var1) {
      this.m134();
      if (!this.util.m81() && this.mc.interactionManager != null && this.mc.currentScreen == null) {
         this.m682();
         if (this.f1.m18().equals("Legit") && this.f17 != AutoSwap$1.f1) {
            this.m683();
         }

         this.m135();
      } else {
         this.m700();
      }
   }

   private boolean m585() {
      return this.f5.m18().equals("HW");
   }

   public boolean m687() {
      return this.f10;
   }

   public String m309() {
      return this.m585()
         ? "АвтоЦербер"
         : "Авто ярость/каратель";
   }

   private void m134() {
      boolean var1 = this.m585();
      this.f6.setVisible(var1);
      this.f8.setVisible(var1);
      this.f7.setVisible(!var1);
      this.f9.setVisible(!var1);
   }

   private void m135() {
      boolean var1 = this.m585();
      KeybindSetting var2 = var1 ? this.f6 : this.f7;
      int var3 = var2.getKey();
      boolean var4 = var3 > 0
         && (
            KeybindSetting.m10(var3)
               ? GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), KeybindSetting.m11(var3)) == 1
               : GLFW.glfwGetKey(this.mc.getWindow().getHandle(), var3) == 1
         );
      if (var4 && !this.f13) {
         this.f10 = !this.f10;
         if (!this.f10 && this.f11 && this.f12 != -1) {
            this.m691(this.f12);
            this.f11 = false;
            this.f12 = -1;
         }
      }

      this.f13 = var4;
      this.m677();
      LivingEntity var5 = this.m688();
      boolean var6 = this.m689(this.mc.player.getOffHandStack());
      if (!var6) {
         this.f11 = false;
      }

      if (this.f11 && this.f12 != -1 && !this.m586(var5)) {
         this.m691(this.f12);
         this.f12 = -1;
         this.f11 = false;
      } else if (this.f10 && var5 != null) {
         if (this.m586(var5) && !this.f11 && System.currentTimeMillis() - this.f16 > 200L) {
            int var7 = this.m681();
            if (var7 != -1) {
               this.f12 = var7;
               this.m691(var7);
               this.f11 = true;
               this.f16 = System.currentTimeMillis();
            }
         }
      }
   }

   private boolean m586(LivingEntity var1) {
      if (!this.f10) {
         return false;
      } else {
         boolean var2 = this.m585();
         MultiChoiceSetting var3 = var2 ? this.f8 : this.f9;
         if (var2 && var3.m20("Если тебя не бьют") && System.currentTimeMillis() - this.f15 < 1000L) {
            return false;
         } else if (var3.m20("Всегда")) {
            return true;
         } else if (this.mc.player.hurtTime <= 0 && var1 != null) {
            if (var3.m20("Когда без меча") && !var1.getMainHandStack().isIn(ItemTags.SWORDS)) {
               return true;
            } else {
               return var3.m20("Когда ливает") && var1.isGliding() && !this.mc.player.isTouchingWater()
                  ? true
                  : var3.m20("Когда в элитре")
                     && var1 instanceof PlayerEntity
                     && var1.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
            }
         } else {
            return false;
         }
      }
   }

   private void m677() {
      float var1 = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
      if (this.f14 >= 0.0F && this.mc.player.hurtTime > 0 && this.f14 - var1 >= 2.0F) {
         this.f15 = System.currentTimeMillis();
      }

      this.f14 = var1;
   }

   private LivingEntity m688() {
      if (this.mc.crosshairTarget instanceof EntityHitResult var1 && var1.getType() == Type.ENTITY) {
         Entity var4 = var1.getEntity();
         if (var4 instanceof LivingEntity) {
            return (LivingEntity)var4;
         }
      }

      return null;
   }

   private boolean m689(ItemStack var1) {
      if (var1 != null && !var1.isEmpty()) {
         String var2 = this.m690(var1);
         return this.m585()
            ? var1.isOf(Items.PLAYER_HEAD)
               && (var2.contains("cerber") || var2.contains("цербер"))
            : var2.contains("ярост") || var2.contains("карател");
      } else {
         return false;
      }
   }

   private String m690(ItemStack var1) {
      StringBuilder var2 = new StringBuilder(var1.getName().getString());
      LoreComponent var3 = (LoreComponent)var1.get(DataComponentTypes.LORE);
      if (var3 != null) {
         for (Text var5 : var3.lines()) {
            var2.append(' ').append(var5.getString());
         }
      }

      return var2.toString().toLowerCase();
   }

   private int m681() {
      for (int var1 = 0; var1 <= 35; var1++) {
         if (this.m689(this.mc.player.getInventory().getStack(var1))) {
            return this.m697(var1);
         }
      }

      return -1;
   }

   private void m691(int var1) {
      if (var1 != -1 && this.mc.player != null && this.mc.interactionManager != null) {
         this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, var1, 40, SlotActionType.SWAP, this.mc.player);
      }
   }

   private void m682() {
      int var1 = this.f4.getKey();
      if (var1 > 0) {
         boolean var2 = KeybindSetting.m10(var1) ? this.f4.m14(this.mc.getWindow().getHandle()) : GLFW.glfwGetKey(this.mc.getWindow().getHandle(), var1) == 1;
         if (var2 && (!this.f18 || KeybindSetting.m10(var1)) && this.f17 == AutoSwap$1.f1) {
            this.f19 = this.m695();
            if (this.f19 == -1) {
               this.f18 = var2;
               return;
            }

            if (this.f1.m18().equals("Packet")) {
               this.m693(this.f19);
               this.m700();
            } else {
               this.m692(this.f19);
            }
         }

         this.f18 = var2;
      }
   }

   private void m692(int var1) {
      this.f19 = var1;
      Window var2 = this.mc.getWindow();
      this.f20 = InputUtil.isKeyPressed(var2, this.mc.options.forwardKey.getDefaultKey().getCode());
      this.f21 = InputUtil.isKeyPressed(var2, this.mc.options.backKey.getDefaultKey().getCode());
      this.f22 = InputUtil.isKeyPressed(var2, this.mc.options.leftKey.getDefaultKey().getCode());
      this.f23 = InputUtil.isKeyPressed(var2, this.mc.options.rightKey.getDefaultKey().getCode());
      this.f24 = InputUtil.isKeyPressed(var2, this.mc.options.jumpKey.getDefaultKey().getCode());
      this.f25 = false;
      this.f17 = AutoSwap$1.f2;
   }

   private void m683() {
      switch (this.f17) {
         case f1:
         default:
            break;
         case f2:
            this.m698();
            this.f25 = true;
            if (this.mc.player.isSprinting()) {
               this.mc.player.setSprinting(false);
            }

            this.f17 = AutoSwap$1.f3;
            break;
         case f3:
            this.m694(this.f19);
            this.f17 = AutoSwap$1.f4;
            break;
         case f4:
            this.m694(45);
            this.f17 = AutoSwap$1.f5;
            break;
         case f5:
            this.m694(this.f19);
            this.f17 = AutoSwap$1.f6;
            this.m699();
            break;
         case f6:
            this.m699();
            this.f17 = AutoSwap$1.f7;
            break;
         case f7:
            this.m700();
      }
   }

   private void m693(int var1) {
      this.m694(var1);
      this.m694(45);
      this.m694(var1);
   }

   private void m694(int var1) {
      if (var1 != -1 && this.mc.player != null && this.mc.interactionManager != null) {
         this.mc.interactionManager.clickSlot(this.mc.player.currentScreenHandler.syncId, var1, 0, SlotActionType.PICKUP, this.mc.player);
      }
   }

   private int m695() {
      Item var1 = this.m701(this.f2.m18());
      Item var2 = this.m701(this.f3.m18());
      Item var3 = this.mc.player.getOffHandStack().getItem();
      if (var3 == var1) {
         int var4 = this.m696(var2);
         if (var4 != -1) {
            return this.m697(var4);
         }
      }

      if (var3 == var2) {
         int var5 = this.m696(var1);
         if (var5 != -1) {
            return this.m697(var5);
         }
      }

      if (var3 != var1 && var3 != var2) {
         int var6 = this.m696(var1);
         if (var6 != -1) {
            return this.m697(var6);
         }

         var6 = this.m696(var2);
         if (var6 != -1) {
            return this.m697(var6);
         }
      }

      return -1;
   }

   private int m696(Item var1) {
      if (var1 == Items.AIR) {
         return -1;
      } else {
         for (int var2 = 35; var2 >= 0; var2--) {
            ItemStack var3 = this.mc.player.getInventory().getStack(var2);
            if (var3.isOf(var1)) {
               return var2;
            }
         }

         return -1;
      }
   }

   private int m697(int var1) {
      return var1 < 9 ? var1 + 36 : var1;
   }

   private void m698() {
      this.mc.options.forwardKey.setPressed(false);
      this.mc.options.backKey.setPressed(false);
      this.mc.options.leftKey.setPressed(false);
      this.mc.options.rightKey.setPressed(false);
      this.mc.options.jumpKey.setPressed(false);
   }

   private void m699() {
      if (this.f25 && this.mc.getWindow() != null) {
         Window var1 = this.mc.getWindow();
         boolean var2 = InputUtil.isKeyPressed(var1, this.mc.options.forwardKey.getDefaultKey().getCode());
         boolean var3 = InputUtil.isKeyPressed(var1, this.mc.options.backKey.getDefaultKey().getCode());
         boolean var4 = InputUtil.isKeyPressed(var1, this.mc.options.leftKey.getDefaultKey().getCode());
         boolean var5 = InputUtil.isKeyPressed(var1, this.mc.options.rightKey.getDefaultKey().getCode());
         boolean var6 = InputUtil.isKeyPressed(var1, this.mc.options.jumpKey.getDefaultKey().getCode());
         this.mc.options.forwardKey.setPressed(this.f20 && var2);
         this.mc.options.backKey.setPressed(this.f21 && var3);
         this.mc.options.leftKey.setPressed(this.f22 && var4);
         this.mc.options.rightKey.setPressed(this.f23 && var5);
         this.mc.options.jumpKey.setPressed(this.f24 && var6);
         this.f25 = false;
      }
   }

   private void m700() {
      if (this.f25) {
         this.m699();
      }

      this.f17 = AutoSwap$1.f1;
      this.f19 = -1;
      this.f25 = false;
   }

   private Item m701(String var1) {
      return switch (var1) {
         case "Щит" -> Items.SHIELD;
         case "Тотем" -> Items.TOTEM_OF_UNDYING;
         case "Геплы" -> Items.GOLDEN_APPLE;
         case "Шар" -> Items.PLAYER_HEAD;
         default -> Items.AIR;
      };
   }
}
