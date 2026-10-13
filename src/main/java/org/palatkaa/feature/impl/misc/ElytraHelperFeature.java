package org.palatkaa.feature.impl.misc;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;
import org.palatkaa.event.EventTarget;
import org.palatkaa.event.events.game.GameTickEvent;
import org.palatkaa.feature.Feature;
import org.palatkaa.feature.FeatureCategory;
import org.palatkaa.feature.setting.BindSetting;
import org.palatkaa.feature.setting.InputBindSetting;
import org.palatkaa.menu.core.MenuOverlay;
import org.palatkaa.utils.network.PacketUtil;
import org.palatkaa.utils.text.ChatUtil;

/**
 * Manual elytra-firework helper.
 * A press of Firework Bind uses a rocket from the hotbar, or temporarily
 * swaps one from the main inventory into the selected hotbar slot and restores it.
 * It intentionally has no elytra swapping, auto-takeoff, auto-sprint, or auto-firework.
 */
public final class ElytraHelperFeature extends Feature {
    private final InputBindSetting fireworkBind = this.register(new InputBindSetting("Firework Bind", -1));
    private boolean wasFireworkPressed;
    private boolean busy;

    public ElytraHelperFeature() {
        super("Elytra Helper", "Manually use fireworks, including rockets stored in the inventory.", FeatureCategory.MISC, -1);
    }

    @Override
    protected void onDisable() {
        wasFireworkPressed = false;
        busy = false;
    }

    @EventTarget
    public void onTick(GameTickEvent event) {
        Minecraft mc = event.getClient();
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null || mc.gameMode == null || mc.getConnection() == null) {
            wasFireworkPressed = false;
            busy = false;
            return;
        }

        boolean pressed = isBindHeld(fireworkBind);
        if (mc.gui.screen() != null || MenuOverlay.isOpen()) {
            wasFireworkPressed = pressed;
            return;
        }

        if (pressed && !wasFireworkPressed && !busy) {
            useFireworkFromInventoryIfNeeded(mc, player);
        }
        wasFireworkPressed = pressed;
    }

    private void useFireworkFromInventoryIfNeeded(Minecraft mc, LocalPlayer player) {
        int selectedHotbarSlot = player.getInventory().getSelectedSlot();

        // Prefer rockets already in the hotbar, without changing the selected slot.
        for (int slot = 0; slot < 9; slot++) {
            if (player.getInventory().getItem(slot).is(Items.FIREWORK_ROCKET)) {
                useRocketInHotbar(slot, selectedHotbarSlot);
                return;
            }
        }

        // Main inventory indices 9..35 match the corresponding inventory-menu slots.
        for (int inventorySlot = 9; inventorySlot < 36; inventorySlot++) {
            if (player.getInventory().getItem(inventorySlot).is(Items.FIREWORK_ROCKET)) {
                busy = true;
                try {
                    clickSwap(mc, player, inventorySlot, selectedHotbarSlot);
                    PacketUtil.sendUseItem(InteractionHand.MAIN_HAND);
                    clickSwap(mc, player, inventorySlot, selectedHotbarSlot);
                } finally {
                    busy = false;
                }
                return;
            }
        }

        ChatUtil.info("No fireworks found in inventory.");
    }

    private void useRocketInHotbar(int rocketSlot, int selectedHotbarSlot) {
        if (rocketSlot == selectedHotbarSlot) {
            PacketUtil.sendUseItem(InteractionHand.MAIN_HAND);
            return;
        }

        // Select the rocket briefly, use it, then restore the player's selection.
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;
        busy = true;
        try {
            PacketUtil.sendHeldItemChange(rocketSlot);
            PacketUtil.sendUseItem(InteractionHand.MAIN_HAND);
            PacketUtil.sendHeldItemChange(selectedHotbarSlot);
        } finally {
            busy = false;
        }
    }

    private void clickSwap(Minecraft mc, LocalPlayer player, int inventoryMenuSlot, int hotbarButton) {
        mc.gameMode.handleContainerInput(
            player.inventoryMenu.containerId,
            inventoryMenuSlot,
            hotbarButton,
            ContainerInput.SWAP,
            player
        );
    }

    private boolean isBindHeld(InputBindSetting bind) {
        if (bind == null || !bind.isBound() || MenuOverlay.isOpen()) return false;
        Minecraft mc = Minecraft.getInstance();
        if (mc.getWindow() == null) return false;

        long window = mc.getWindow().handle();
        int code = bind.getValue();
        return BindSetting.isMouse(code)
            ? GLFW.glfwGetMouseButton(window, BindSetting.rawButton(code)) == GLFW.GLFW_PRESS
            : InputConstants.isKeyDown(mc.getWindow(), code);
    }
}
