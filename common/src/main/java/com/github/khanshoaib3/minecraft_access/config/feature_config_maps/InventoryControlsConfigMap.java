package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

//import com.fasterxml.jackson.annotation.JsonProperty;

public class InventoryControlsConfigMap {
    private boolean enabled;
    private boolean autoOpenRecipeBook;
    private String rowAndColumnFormat;
    private int delayInMilliseconds;

//    @JsonProperty("Enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    @JsonProperty("Auto Open Recipe Book (in creative/survival and crafting inventory)")
    public boolean isAutoOpenRecipeBook() {
        return autoOpenRecipeBook;
    }

    public void setAutoOpenRecipeBook(boolean autoOpenRecipeBook) {
        this.autoOpenRecipeBook = autoOpenRecipeBook;
    }

//    @JsonProperty("Row and Column Format in Crafting Input Slots")
    public String getRowAndColumnFormat() {
        return rowAndColumnFormat;
    }

    public void setRowAndColumnFormat(String rowAndColumnFormat) {
        this.rowAndColumnFormat = rowAndColumnFormat;
    }

//    @JsonProperty("Delay (in milliseconds)")
    public int getDelayInMilliseconds() {
        return delayInMilliseconds;
    }

    public void setDelayInMilliseconds(int delayInMilliseconds) {
        this.delayInMilliseconds = delayInMilliseconds;
    }
}
