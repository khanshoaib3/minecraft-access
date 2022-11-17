package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPosition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Speaks the player facing direction on key press.
 */
public class FacingDirection {

    public final KeyBinding directionNarrationKey; //TODO create a separate class for initializing key binds

    public FacingDirection() {
        directionNarrationKey = new KeyBinding(
                "minecraft_access.keys.other.facing_direction_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "minecraft_access.keys.other.group_name"
        );
    }

    public void update() {
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            boolean isDirectionNarrationKeyPressed = InputUtil.isKeyPressed(
                    minecraftClient.getWindow().getHandle(),
                    InputUtil.fromTranslationKey(directionNarrationKey.getBoundKeyTranslationKey()).getCode()
            );
            if (!isDirectionNarrationKeyPressed) return;

            boolean isLeftAltPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(),
                    InputUtil.fromTranslationKey("key.keyboard.left.alt").getCode());

            String toSpeak;
            if (isLeftAltPressed) {
                int angle = new PlayerPosition(minecraftClient).getVerticalFacingDirection();
                toSpeak = PlayerPosition.getNarratableNumber(angle);
            } else {

                /* FIXME
                int angle = new PlayerPosition(minecraftClient).getHorizontalFacingDirectionInDegrees();
                if (Config.get(Config.getCardinal_to_Degrees_Key())) {
                    toSpeak += angle;
                } else {
                */
                String string = new PlayerPosition(minecraftClient).getHorizontalFacingDirectionInCardinal();
                toSpeak = I18n.translate("minecraft_access.other.facing_direction", string);
                // }
            }

            MainClass.speakWithNarrator(toSpeak, true);
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in DirectionNarrator.");
            e.printStackTrace();
        }
    }
}
