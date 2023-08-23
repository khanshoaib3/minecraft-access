package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.*;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.resource.language.I18n;
import org.lwjgl.glfw.GLFW;

public class ValueEntryMenu extends BaseScreen {
    public enum CONFIG_TYPE {
        CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE,
        CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE,
        CAMERA_CONTROLS_DELAY,
        INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT,
        INVENTORY_CONTROLS_DELAY,
        MOUSE_SIMULATION_SCROLL_DELAY,
        POI_BLOCKS_RANGE,
        POI_BLOCKS_VOLUME,
        POI_BLOCKS_DELAY,
        POI_ENTITIES_RANGE,
        POI_ENTITIES_VOLUME,
        POI_ENTITIES_DELAY,
        POI_LOCKING_DELAY,
        PLAYER_WARNINGS_FIRST_HEALTH_THRESHOLD,
        PLAYER_WARNINGS_SECOND_HEALTH_THRESHOLD,
        PLAYER_WARNINGS_HUNGER_THRESHOLD,
        PLAYER_WARNINGS_AIR_THRESHOLD,
        FALL_DETECTOR_RANGE,
        FALL_DETECTOR_DEPTH_THRESHOLD,
        FALL_DETECTOR_VOLUME,
        FALL_DETECTOR_DELAY,
        READ_CROSSHAIR_REPEAT_SPEAKING_INTERVAL,
        NARRATOR_MENU_VOLUME,
        NARRATOR_MENU_RANGE,
        OTHER_POSITION_NARRATOR_FORMAT,
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

        final CameraControlsConfigMap cameraControlsConfigMap = CameraControlsConfigMap.getInstance();
        final FallDetectorConfigMap fallDetectorConfigMap = FallDetectorConfigMap.getInstance();
        final InventoryControlsConfigMap inventoryControlsConfigMap = InventoryControlsConfigMap.getInstance();

        switch (configType) {
            case CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE -> {
                this.value = String.valueOf(cameraControlsConfigMap.getNormalRotatingAngle());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE -> {
                this.value = String.valueOf(cameraControlsConfigMap.getModifiedRotatingAngle());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case CAMERA_CONTROLS_DELAY -> {
                this.value = String.valueOf(cameraControlsConfigMap.getDelayInMilliseconds());
                this.valueType = VALUE_TYPE.INT;
            }
            case INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT -> {
                this.value = inventoryControlsConfigMap.getRowAndColumnFormat();
                this.valueType = VALUE_TYPE.STRING;
            }
            case INVENTORY_CONTROLS_DELAY -> {
                this.value = String.valueOf(inventoryControlsConfigMap.getDelayInMilliseconds());
                this.valueType = VALUE_TYPE.INT;
            }
            case MOUSE_SIMULATION_SCROLL_DELAY -> {
                this.value = String.valueOf(MouseSimulationConfigMap.getInstance().getScrollDelayInMilliseconds());
                this.valueType = VALUE_TYPE.INT;
            }
            case POI_BLOCKS_RANGE -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPoiConfigMap().getBlocksConfigMap().getRange());
                this.valueType = VALUE_TYPE.INT;
            }
            case POI_BLOCKS_VOLUME -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPoiConfigMap().getBlocksConfigMap().getVolume());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case POI_BLOCKS_DELAY -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPoiConfigMap().getBlocksConfigMap().getDelay());
                this.valueType = VALUE_TYPE.INT;
            }
            case POI_ENTITIES_RANGE -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPoiConfigMap().getEntitiesConfigMap().getRange());
                this.valueType = VALUE_TYPE.INT;
            }
            case POI_ENTITIES_VOLUME -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPoiConfigMap().getEntitiesConfigMap().getVolume());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case POI_ENTITIES_DELAY -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPoiConfigMap().getEntitiesConfigMap().getDelay());
                this.valueType = VALUE_TYPE.INT;
            }
            case POI_LOCKING_DELAY -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().getDelay());
                this.valueType = VALUE_TYPE.INT;
            }
            case PLAYER_WARNINGS_FIRST_HEALTH_THRESHOLD -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPlayerWarningConfigMap().getFirstHealthThreshold());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case PLAYER_WARNINGS_SECOND_HEALTH_THRESHOLD -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPlayerWarningConfigMap().getSecondHealthThreshold());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case PLAYER_WARNINGS_HUNGER_THRESHOLD -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPlayerWarningConfigMap().getHungerThreshold());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case PLAYER_WARNINGS_AIR_THRESHOLD -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getPlayerWarningConfigMap().getAirThreshold());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case FALL_DETECTOR_RANGE -> {
                this.value = String.valueOf(fallDetectorConfigMap.getRange());
                this.valueType = VALUE_TYPE.INT;
            }
            case FALL_DETECTOR_DEPTH_THRESHOLD -> {
                this.value = String.valueOf(fallDetectorConfigMap.getDepth());
                this.valueType = VALUE_TYPE.INT;
            }
            case FALL_DETECTOR_VOLUME -> {
                this.value = String.valueOf(fallDetectorConfigMap.getVolume());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case FALL_DETECTOR_DELAY -> {
                this.value = String.valueOf(fallDetectorConfigMap.getDelay());
                this.valueType = VALUE_TYPE.INT;
            }
            case READ_CROSSHAIR_REPEAT_SPEAKING_INTERVAL -> {
                this.value = String.valueOf(ReadCrosshairConfigMap.getInstance().getRepeatSpeakingInterval());
                this.valueType = VALUE_TYPE.INT;
            }
            case NARRATOR_MENU_VOLUME -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getNarratorMenuConfigMap().getFluidDetectorConfigMap().getVolume());
                this.valueType = VALUE_TYPE.FLOAT;
            }
            case NARRATOR_MENU_RANGE -> {
                this.value = String.valueOf(MainClass.config.getConfigMap().getNarratorMenuConfigMap().getFluidDetectorConfigMap().getRange());
                this.valueType = VALUE_TYPE.INT;
            }
            case OTHER_POSITION_NARRATOR_FORMAT -> {
                this.value = MainClass.config.getConfigMap().getOtherConfigsMap().getPositionNarratorFormat();
                this.valueType = VALUE_TYPE.STRING;
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
            final CameraControlsConfigMap cameraControlsConfigMap = CameraControlsConfigMap.getInstance();
            final FallDetectorConfigMap fallDetectorConfigMap = FallDetectorConfigMap.getInstance();
            final InventoryControlsConfigMap inventoryControlsConfigMap = InventoryControlsConfigMap.getInstance();

            switch (configType) {
                case CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE ->
                        cameraControlsConfigMap.setNormalRotatingAngle(Float.parseFloat(value));
                case CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE ->
                        cameraControlsConfigMap.setModifiedRotatingAngle(Float.parseFloat(value));
                case CAMERA_CONTROLS_DELAY ->
                        cameraControlsConfigMap.setDelayInMilliseconds(Integer.parseInt(value));
                case INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT ->
                        inventoryControlsConfigMap.setRowAndColumnFormat(value);
                case INVENTORY_CONTROLS_DELAY ->
                        inventoryControlsConfigMap.setDelayInMilliseconds(Integer.parseInt(value));
                case MOUSE_SIMULATION_SCROLL_DELAY ->
                        MouseSimulationConfigMap.getInstance().setScrollDelayInMilliseconds(Integer.parseInt(value));
                case POI_BLOCKS_RANGE ->
                        configMap.getPoiConfigMap().getBlocksConfigMap().setRange(Integer.parseInt(value));
                case POI_BLOCKS_VOLUME ->
                        configMap.getPoiConfigMap().getBlocksConfigMap().setVolume(Float.parseFloat(value));
                case POI_BLOCKS_DELAY ->
                        configMap.getPoiConfigMap().getBlocksConfigMap().setDelay(Integer.parseInt(value));
                case POI_ENTITIES_RANGE ->
                        configMap.getPoiConfigMap().getEntitiesConfigMap().setRange(Integer.parseInt(value));
                case POI_ENTITIES_VOLUME ->
                        configMap.getPoiConfigMap().getEntitiesConfigMap().setVolume(Float.parseFloat(value));
                case POI_ENTITIES_DELAY ->
                        configMap.getPoiConfigMap().getEntitiesConfigMap().setDelay(Integer.parseInt(value));
                case POI_LOCKING_DELAY ->
                        configMap.getPoiConfigMap().getLockingConfigMap().setDelay(Integer.parseInt(value));
                case PLAYER_WARNINGS_FIRST_HEALTH_THRESHOLD ->
                        configMap.getPlayerWarningConfigMap().setFirstHealthThreshold(Double.parseDouble(value));
                case PLAYER_WARNINGS_SECOND_HEALTH_THRESHOLD ->
                        configMap.getPlayerWarningConfigMap().setSecondHealthThreshold(Double.parseDouble(value));
                case PLAYER_WARNINGS_HUNGER_THRESHOLD ->
                        configMap.getPlayerWarningConfigMap().setHungerThreshold(Double.parseDouble(value));
                case PLAYER_WARNINGS_AIR_THRESHOLD ->
                        configMap.getPlayerWarningConfigMap().setAirThreshold(Double.parseDouble(value));
                case FALL_DETECTOR_RANGE -> fallDetectorConfigMap.setRange(Integer.parseInt(value));
                case FALL_DETECTOR_DEPTH_THRESHOLD -> fallDetectorConfigMap.setDepth(Integer.parseInt(value));
                case FALL_DETECTOR_VOLUME -> fallDetectorConfigMap.setVolume(Float.parseFloat(value));
                case FALL_DETECTOR_DELAY -> fallDetectorConfigMap.setDelay(Integer.parseInt(value));
                case READ_CROSSHAIR_REPEAT_SPEAKING_INTERVAL ->
                        ReadCrosshairConfigMap.getInstance().setRepeatSpeakingInterval(Long.parseLong(value));
                case NARRATOR_MENU_VOLUME ->
                        configMap.getNarratorMenuConfigMap().getFluidDetectorConfigMap().setVolume(Float.parseFloat(value));
                case NARRATOR_MENU_RANGE ->
                        configMap.getNarratorMenuConfigMap().getFluidDetectorConfigMap().setRange(Integer.parseInt(value));
                case OTHER_POSITION_NARRATOR_FORMAT ->
                        configMap.getOtherConfigsMap().setPositionNarratorFormat(value);
            }
            MainClass.config.setConfigMap(configMap);
        } catch (Exception e) {
            MainClass.errorLog("Error occurred while updating the config. The user possibly entered wrong value type.");
            e.printStackTrace();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.speakValue();
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);
        context.drawCenteredTextWithShadow(this.textRenderer, value, this.width / 2, this.height / 2, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    /* Pre 1.20.x
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.speakValue();
        this.renderBackground(matrices);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer, value, this.width / 2, this.height / 2, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
    */

    private void speakValue() {
        if (this.previousValue.equals(this.value)) return;
        this.previousValue = this.value;
        MainClass.speakWithNarrator(this.value, true);
    }
}
