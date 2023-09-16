package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.OtherConfigsMap;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.KeyUtils;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.PositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.stream.Stream;

/**
 * Adds key bindings to speak the player's position.<br><br>
 * Keybindings and combinations:<br>
 * 1. Speak Player Position Key (default: G) = Speaks the player's x y and z position.<br>
 * 2. Left Alt + X = Speaks only the x position.<br>
 * 3. Left Alt + C = Speaks only the y position.<br>
 * 4. Left Alt + Z = Speaks only the z position.<br>
 */
public class PositionNarrator {
    private static final PositionNarrator instance;
    public static String defaultFormat = "{x}x, {y}y, {z}z";
    public static Keystroke KeyX = new Keystroke(() -> KeyUtils.isAnyPressed(GLFW.GLFW_KEY_X));
    public static Keystroke KeyC = new Keystroke(() -> KeyUtils.isAnyPressed(GLFW.GLFW_KEY_C));
    public static Keystroke KeyZ = new Keystroke(() -> KeyUtils.isAnyPressed(GLFW.GLFW_KEY_Z));
    public static Keystroke positionNarrationKey;

    static {
        instance = new PositionNarrator();
        positionNarrationKey = new Keystroke(() -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().positionNarrationKey));
    }

    private PositionNarrator() {
    }

    public static PositionNarrator getInstance() {
        return instance;
    }

    public void update() {
        try {
            if (!OtherConfigsMap.getInstance().isPositionNarratorEnabled()) return;

            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            boolean isLeftAltPressed = KeyUtils.isLeftAltPressed();
            if (isLeftAltPressed) {
                if (KeyX.canBeTriggered()) {
                    MainClass.speakWithNarrator(new PlayerPositionUtils(minecraftClient).getNarratableXPos(), true);
                } else if (KeyC.canBeTriggered()) {
                    MainClass.speakWithNarrator(new PlayerPositionUtils(minecraftClient).getNarratableYPos(), true);
                } else if (KeyZ.canBeTriggered()) {
                    MainClass.speakWithNarrator(new PlayerPositionUtils(minecraftClient).getNarratableZPos(), true);
                }
            }

            if (positionNarrationKey.canBeTriggered()) {
                String posX = PositionUtils.getNarratableNumber(new PlayerPositionUtils(minecraftClient).getX());
                String posY = PositionUtils.getNarratableNumber(new PlayerPositionUtils(minecraftClient).getY());
                String posZ = PositionUtils.getNarratableNumber(new PlayerPositionUtils(minecraftClient).getZ());
                MainClass.speakWithNarrator(getNarrationText(posX, posY, posZ), true);
            }

            KeyX.updateStateForNextTick();
            KeyC.updateStateForNextTick();
            KeyZ.updateStateForNextTick();
            positionNarrationKey.updateStateForNextTick();

        } catch (Exception e) {
            MainClass.errorLog("An error occurred in PositionNarrator.", e);
        }
    }

    private String getNarrationText(String posX, String posY, String posZ) {
        String format = OtherConfigsMap.getInstance().getPositionNarratorFormat();

        // check if configured format is valid
        if (!Stream.of("{x}", "{y}", "{z}").allMatch(format::contains)) {
            format = defaultFormat;
        }

        return format.replace("{x}", posX).replace("{y}", posY).replace("{z}", posZ);
    }
}
