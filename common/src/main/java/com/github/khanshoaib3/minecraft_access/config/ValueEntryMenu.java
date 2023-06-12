package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class ValueEntryMenu extends BaseScreen {
    public enum CONFIG_TYPE {
        CameraControls_NormalRotatingAngle
    }

    public enum VALUE_TYPE {
        INT,
        FLOAT,
        STRING
    }

    String value;
    VALUE_TYPE valueType;
    CONFIG_TYPE configType;
    String previousValue;

    protected ValueEntryMenu(String title, CONFIG_TYPE configType, BaseScreen previousScreen) {
        super(title, previousScreen);
        this.configType = configType;
    }

    @Override
    protected void init() {
        super.init();

        if (configType == CONFIG_TYPE.CameraControls_NormalRotatingAngle) {
            this.value = String.valueOf(MainClass.config.getConfigMap().getCameraControlsConfigMap().getNormalRotatingAngle());
            this.valueType = VALUE_TYPE.FLOAT;
        }
        this.previousValue = this.value;
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.gui.value_entry_menu.info_text", this.value), true);
    }

    /**
     * Removes the default title narration on menu open.
     */
    @Override
    protected void addScreenNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            this.updateConfig();
            this.close();
        } else if ((keyCode >= GLFW.GLFW_KEY_0 && keyCode <= GLFW.GLFW_KEY_9)
                || (keyCode >= GLFW.GLFW_KEY_KP_0 && keyCode <= GLFW.GLFW_KEY_KP_9)
                || (valueType != VALUE_TYPE.INT && keyCode == GLFW.GLFW_KEY_PERIOD)) {
            this.value += GLFW.glfwGetKeyName(keyCode, scanCode);
        } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE && this.value.length() > 0) {
            this.value = this.value.substring(0, this.value.length() - 1);
        } else if (valueType == VALUE_TYPE.STRING) {
            this.value += GLFW.glfwGetKeyName(keyCode, scanCode);
        } else {
            return false;
        }

        return true;
    }

    private void updateConfig() {
        try {
            ConfigMap configMap = MainClass.config.getConfigMap();
            if (configType == CONFIG_TYPE.CameraControls_NormalRotatingAngle) {
                configMap.getCameraControlsConfigMap().setNormalRotatingAngle(Float.parseFloat(value));
            }
            MainClass.config.setConfigMap(configMap);
        } catch (Exception e) {
            MainClass.errorLog("Error occurred while updating the config. The user possibly entered wrong value type.");
            e.printStackTrace();
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.speakValue();
        this.renderBackground(matrices);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer, value, this.width / 2, this.height / 2, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void speakValue() {
        if (this.previousValue.equals(this.value)) return;
        this.previousValue = this.value;
        MainClass.speakWithNarrator(this.value, true);
    }
}
