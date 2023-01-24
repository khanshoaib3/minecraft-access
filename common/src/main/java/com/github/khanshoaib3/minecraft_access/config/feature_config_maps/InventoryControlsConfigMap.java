package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.google.gson.annotations.SerializedName;

public class InventoryControlsConfigMap {
    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Auto Open Recipe Book (in creative/survival and crafting inventory)")
    private boolean autoOpenRecipeBook;
    @SerializedName("Row and Column Format in Crafting Input Slots")
    private String rowAndColumnFormat;
    @SerializedName("Delay (in milliseconds)")
    private int delayInMilliseconds;

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
