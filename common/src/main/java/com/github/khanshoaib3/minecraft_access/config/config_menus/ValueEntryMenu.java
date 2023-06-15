package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

public class ValueEntryMenu extends BaseScreen {
    public enum CONFIG_TYPE {
        CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE,
        CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE,
        CAMERA_CONTROLS_DELAY,
        INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT,
        INVENTORY_CONTROLS_DELAY,
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

        switch (configType) {
            case CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getCameraControlsConfigMap().getNormalRotatingAngle());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getCameraControlsConfigMap().getModifiedRotatingAngle());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case CAMERA_CONTROLS_DELAY -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getCameraControlsConfigMap().getDelayInMilliseconds());
                this.valueType = VALUE_TYPE.INT;
            }
            case INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT -> {
                this.value = MainClass.config.getConfigMap().getInventoryControlsConfigMap().getRowAndColumnFormat();
                this.valueType = VALUE_TYPE.STRING;
            }
            case INVENTORY_CONTROLS_DELAY -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getInventoryControlsConfigMap().getDelayInMilliseconds());
                this.valueType = VALUE_TYPE.INT;
            }
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
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.close();
        } else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            this.updateConfig();
            this.close();
        } else if (valueType != VALUE_TYPE.STRING && ((keyCode >= GLFW.GLFW_KEY_0 && keyCode <= GLFW.GLFW_KEY_9)
                        || (keyCode >= GLFW.GLFW_KEY_KP_0 && keyCode <= GLFW.GLFW_KEY_KP_9)
                        || (valueType != VALUE_TYPE.INT && (keyCode == GLFW.GLFW_KEY_PERIOD || keyCode == GLFW.GLFW_KEY_KP_DECIMAL)))) {
            this.value += GLFW.glfwGetKeyName(keyCode, scanCode);
        } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE && this.value.length() > 0) {
            this.value = this.value.substring(0, this.value.length() - 1);
        } else if (valueType == VALUE_TYPE.STRING && GLFW.glfwGetKeyName(keyCode, scanCode) != null) {
            this.value += GLFW.glfwGetKeyName(keyCode, scanCode);
        } else {
            return false;
        }

        return true;
    }

    private void updateConfig() {
        try {
            ConfigMap configMap = MainClass.config.getConfigMap();
            switch (configType) {
                case CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE ->
                        configMap.getCameraControlsConfigMap().setNormalRotatingAngle(Float.parseFloat(value));
                case CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE ->
                        configMap.getCameraControlsConfigMap().setModifiedRotatingAngle(Float.parseFloat(value));
                case CAMERA_CONTROLS_DELAY ->
                        configMap.getCameraControlsConfigMap().setDelayInMilliseconds(Integer.parseInt(value));
                case INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT ->
                        configMap.getInventoryControlsConfigMap().setRowAndColumnFormat(value);
                case INVENTORY_CONTROLS_DELAY ->
                        configMap.getInventoryControlsConfigMap().setDelayInMilliseconds(Integer.parseInt(value));
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
