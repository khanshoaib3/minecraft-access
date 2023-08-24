package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class InventoryControlsConfigMap {

    private static InventoryControlsConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Auto Open Recipe Book (in creative/survival and crafting inventory)")
    private boolean autoOpenRecipeBook;
    @SerializedName("Row and Column Format in Crafting Input Slots")
    private String rowAndColumnFormat;
    @SerializedName("Delay (in milliseconds)")
    private int delayInMilliseconds;

    private InventoryControlsConfigMap() {
    }

    public static InventoryControlsConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(InventoryControlsConfigMap map) {
        instance = map;
    }

    public static InventoryControlsConfigMap buildDefault() {
        InventoryControlsConfigMap defaultInventoryControlsConfigMap = new InventoryControlsConfigMap();
        defaultInventoryControlsConfigMap.setEnabled(true);
        defaultInventoryControlsConfigMap.setAutoOpenRecipeBook(true);
        defaultInventoryControlsConfigMap.setRowAndColumnFormat("%dx%d");
        defaultInventoryControlsConfigMap.setDelayInMilliseconds(150);

        setInstance(defaultInventoryControlsConfigMap);
        return defaultInventoryControlsConfigMap;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAutoOpenRecipeBook() {
        return autoOpenRecipeBook;
    }

    public void setAutoOpenRecipeBook(boolean autoOpenRecipeBook) {
        this.autoOpenRecipeBook = autoOpenRecipeBook;
    }

    public String getRowAndColumnFormat() {
        return rowAndColumnFormat;
    }

    public void setRowAndColumnFormat(String rowAndColumnFormat) {
        this.rowAndColumnFormat = rowAndColumnFormat;
    }

    public int getDelayInMilliseconds() {
        return delayInMilliseconds;
    }

    public void setDelayInMilliseconds(int delayInMilliseconds) {
        this.delayInMilliseconds = delayInMilliseconds;
    }
}
