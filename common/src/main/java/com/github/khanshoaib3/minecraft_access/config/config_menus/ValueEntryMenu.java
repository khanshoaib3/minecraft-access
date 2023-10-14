package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.config.config_maps.*;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ValueEntryMenu extends BaseScreen {
    public enum CONFIG_TYPE {
        CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE(() -> CameraControlsConfigMap.getInstance().getNormalRotatingAngle(),
                (v) -> CameraControlsConfigMap.getInstance().setNormalRotatingAngle(Float.parseFloat(v)),
                ValueType.FLOAT),
        CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE(() -> CameraControlsConfigMap.getInstance().getModifiedRotatingAngle(),
                (v) -> CameraControlsConfigMap.getInstance().setModifiedRotatingAngle(Float.parseFloat(v)),
                ValueType.FLOAT),
        CAMERA_CONTROLS_DELAY(() -> CameraControlsConfigMap.getInstance().getDelayInMilliseconds(),
                (v) -> CameraControlsConfigMap.getInstance().setDelayInMilliseconds(Integer.parseInt(v)),
                ValueType.INT),
        INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT(() -> InventoryControlsConfigMap.getInstance().getRowAndColumnFormat(),
                (v) -> InventoryControlsConfigMap.getInstance().setRowAndColumnFormat(v),
                ValueType.STRING),
        INVENTORY_CONTROLS_DELAY(() -> InventoryControlsConfigMap.getInstance().getDelayInMilliseconds(),
                (v) -> InventoryControlsConfigMap.getInstance().setDelayInMilliseconds(Integer.parseInt(v)),
                ValueType.INT),
        MOUSE_SIMULATION_SCROLL_DELAY(() -> MouseSimulationConfigMap.getInstance().getScrollDelayInMilliseconds(),
                (v) -> MouseSimulationConfigMap.getInstance().setScrollDelayInMilliseconds(Integer.parseInt(v)),
                ValueType.INT),
        POI_BLOCKS_RANGE(() -> POIBlocksConfigMap.getInstance().getRange(),
                (v) -> POIBlocksConfigMap.getInstance().setRange(Integer.parseInt(v)),
                ValueType.INT),
        POI_BLOCKS_VOLUME(() -> POIBlocksConfigMap.getInstance().getVolume(),
                (v) -> POIBlocksConfigMap.getInstance().setVolume(Float.parseFloat(v)),
                ValueType.FLOAT),
        POI_BLOCKS_DELAY(() -> POIBlocksConfigMap.getInstance().getDelay(),
                (v) -> POIBlocksConfigMap.getInstance().setDelay(Integer.parseInt(v)),
                ValueType.INT),
        POI_ENTITIES_RANGE(() -> POIEntitiesConfigMap.getInstance().getRange(),
                (v) -> POIEntitiesConfigMap.getInstance().setRange(Integer.parseInt(v)),
                ValueType.INT),
        POI_ENTITIES_VOLUME(() -> POIEntitiesConfigMap.getInstance().getVolume(),
                (v) -> POIEntitiesConfigMap.getInstance().setVolume(Float.parseFloat(v)),
                ValueType.FLOAT),
        POI_ENTITIES_DELAY(() -> POIEntitiesConfigMap.getInstance().getDelay(),
                (v) -> POIEntitiesConfigMap.getInstance().setDelay(Integer.parseInt(v)),
                ValueType.INT),
        POI_LOCKING_DELAY(() -> POILockingConfigMap.getInstance().getDelay(),
                (v) -> POILockingConfigMap.getInstance().setDelay(Integer.parseInt(v)),
                ValueType.INT),
        PLAYER_WARNINGS_FIRST_HEALTH_THRESHOLD(() -> PlayerWarningConfigMap.getInstance().getFirstHealthThreshold(),
                (v) -> PlayerWarningConfigMap.getInstance().setFirstHealthThreshold(Float.parseFloat(v)),
                ValueType.FLOAT),
        PLAYER_WARNINGS_SECOND_HEALTH_THRESHOLD(() -> PlayerWarningConfigMap.getInstance().getSecondHealthThreshold(),
                (v) -> PlayerWarningConfigMap.getInstance().setSecondHealthThreshold(Float.parseFloat(v)),
                ValueType.FLOAT),
        PLAYER_WARNINGS_HUNGER_THRESHOLD(() -> PlayerWarningConfigMap.getInstance().getHungerThreshold(),
                (v) -> PlayerWarningConfigMap.getInstance().setHungerThreshold(Float.parseFloat(v)),
                ValueType.FLOAT),
        PLAYER_WARNINGS_AIR_THRESHOLD(() -> PlayerWarningConfigMap.getInstance().getAirThreshold(),
                (v) -> PlayerWarningConfigMap.getInstance().setAirThreshold(Float.parseFloat(v)),
                ValueType.FLOAT),
        FALL_DETECTOR_RANGE(() -> FallDetectorConfigMap.getInstance().getRange(),
                (v) -> FallDetectorConfigMap.getInstance().setRange(Integer.parseInt(v)),
                ValueType.INT),
        FALL_DETECTOR_DEPTH_THRESHOLD(() -> FallDetectorConfigMap.getInstance().getDepth(),
                (v) -> FallDetectorConfigMap.getInstance().setDepth(Integer.parseInt(v)),
                ValueType.INT),
        FALL_DETECTOR_VOLUME(() -> FallDetectorConfigMap.getInstance().getVolume(),
                (v) -> FallDetectorConfigMap.getInstance().setVolume(Float.parseFloat(v)),
                ValueType.FLOAT),
        FALL_DETECTOR_DELAY(() -> FallDetectorConfigMap.getInstance().getDelay(),
                (v) -> FallDetectorConfigMap.getInstance().setDelay(Integer.parseInt(v)),
                ValueType.INT),
        READ_CROSSHAIR_REPEAT_SPEAKING_INTERVAL(() -> ReadCrosshairConfigMap.getInstance().getRepeatSpeakingInterval(),
                (v) -> ReadCrosshairConfigMap.getInstance().setRepeatSpeakingInterval(Integer.parseInt(v)),
                ValueType.INT),
        NARRATOR_MENU_VOLUME(() -> FluidDetectorConfigMap.getInstance().getVolume(),
                (v) -> FluidDetectorConfigMap.getInstance().setVolume(Integer.parseInt(v)),
                ValueType.FLOAT),
        NARRATOR_MENU_RANGE(() -> FluidDetectorConfigMap.getInstance().getRange(),
                (v) -> FluidDetectorConfigMap.getInstance().setRange(Integer.parseInt(v)),
                ValueType.INT),
        OTHER_POSITION_NARRATOR_FORMAT(() -> OtherConfigsMap.getInstance().getPositionNarratorFormat(),
                (v) -> OtherConfigsMap.getInstance().setPositionNarratorFormat(v),
                ValueType.STRING),
        AREA_MAP_DELAY(() -> AreaMapConfigMap.getInstance().getDelayInMilliseconds(),
                (v) -> AreaMapConfigMap.getInstance().setDelayInMilliseconds(Integer.parseInt(v)),
                ValueType.INT),
        AREA_MAP_VERTICAL_BOUND(() -> AreaMapConfigMap.getInstance().getVerticalBound(),
                (v) -> AreaMapConfigMap.getInstance().setVerticalBound(Integer.parseInt(v)),
                ValueType.INT),
        AREA_MAP_HORIZONTAL_BOUND(() -> AreaMapConfigMap.getInstance().getHorizontalBound(),
                (v) -> AreaMapConfigMap.getInstance().setHorizontalBound(Integer.parseInt(v)),
                ValueType.INT);

        private final Supplier<Object> valueGetter;
        private final Consumer<String> valueSetter;
        private final ValueType valueType;

        CONFIG_TYPE(Supplier<Object> valueGetter, Consumer<String> valueSetter, ValueType valueType) {
            this.valueGetter = valueGetter;
            this.valueSetter = valueSetter;
            this.valueType = valueType;
        }

        public String buildButtonText(String fieldI18NKey) {
            return I18n.translate("minecraft_access.gui.common.button.button_with_string_value", I18n.translate(fieldI18NKey), this.valueGetter.get());
        }
    }

    private enum ValueType {
        INT,
        FLOAT,
        STRING
    }

    String value;
    CONFIG_TYPE configType;
    String previousValue;

    protected ValueEntryMenu(String title, CONFIG_TYPE configType, BaseScreen previousScreen) {
        super(title, previousScreen);
        this.configType = configType;
    }

    @Override
    protected void init() {
        super.init();
        this.value = String.valueOf(this.configType.valueGetter.get());
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
        } else if (this.configType.valueType != ValueType.STRING && ((keyCode >= GLFW.GLFW_KEY_0 && keyCode <= GLFW.GLFW_KEY_9)
                || (keyCode >= GLFW.GLFW_KEY_KP_0 && keyCode <= GLFW.GLFW_KEY_KP_9)
                || (this.configType.valueType != ValueType.INT && (keyCode == GLFW.GLFW_KEY_PERIOD || keyCode == GLFW.GLFW_KEY_KP_DECIMAL)))) {
            this.value += GLFW.glfwGetKeyName(keyCode, scanCode);
        } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !this.value.isEmpty()) {
            this.value = this.value.substring(0, this.value.length() - 1);
        } else if (this.configType.valueType == ValueType.STRING && GLFW.glfwGetKeyName(keyCode, scanCode) != null) {
            this.value += GLFW.glfwGetKeyName(keyCode, scanCode);
        } else {
            return false;
        }

        return true;
    }

    private void updateConfig() {
        try {
            this.configType.valueSetter.accept(this.value);
            Config.getInstance().writeJSON();

        } catch (Exception e) {
            MainClass.errorLog("Error occurred while updating the config. The user possibly entered wrong value type.", e);
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
