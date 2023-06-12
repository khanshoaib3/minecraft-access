package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ValueEntryMenu extends Screen {
    public static enum TYPE {
        CameraControls_NormalRotatingAngle
    }
    Screen previousScreen;
    String value;
    TYPE type;

    protected ValueEntryMenu(String title, TYPE type, Screen previousScreen) {
        super(Text.of(title));
        this.type = type;
        this.previousScreen = previousScreen;
    }

    @Override
    protected void init() {
        super.init();

        if (type == TYPE.CameraControls_NormalRotatingAngle) {
            value = String.valueOf(MainClass.config.getConfigMap().getCameraControlsConfigMap().getNormalRotatingAngle());
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            this.updateConfig();
            this.close();
        } else if (keyCode >= GLFW.GLFW_KEY_0 && keyCode <= GLFW.GLFW_KEY_9) {
            this.value += String.valueOf(keyCode - GLFW.GLFW_KEY_0);
        } else if (keyCode >= GLFW.GLFW_KEY_KP_0 && keyCode <= GLFW.GLFW_KEY_KP_9) {
            this.value += String.valueOf(keyCode - GLFW.GLFW_KEY_KP_0);
        } else if (keyCode == GLFW.GLFW_KEY_PERIOD) {
            this.value += ".";
        } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            this.value = this.value.substring(0, this.value.length() - 1);
        } else {
            return false;
        }

        return true;
    }

    private void updateConfig() {
        ConfigMap configMap = MainClass.config.getConfigMap();
        if (type == TYPE.CameraControls_NormalRotatingAngle) {
            configMap.getCameraControlsConfigMap().setNormalRotatingAngle(Float.parseFloat(value));
        }
        MainClass.config.setConfigMap(configMap);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer, value, this.width / 2, this.height / 2, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if (this.client != null) this.client.setScreen(previousScreen);
    }
}
