package com.github.khanshoaib3.minecraft_access.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.github.khanshoaib3.minecraft_access.MainClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Config {
    private static final String CONFIG_FILE_PATH = Paths.get("config", "minecraft-access", "config.json").toString();
    private ConfigMap configMap = null;

    /**
     * Returns the main config map, if not already initialized, initializes it.
     * @return The main config map
     */
    public ConfigMap getConfigMap(){
        if(configMap == null) loadConfig();
        return configMap;
    }

    /**
     * Loads the configurations from the config.json<br>
     * The path for the file is: config/minecraft_access/config.json
     * If the json format is wrong or an error occurs, the config.json is reset to default
     */
    public void loadConfig() {
        try {
            createEmptyConfigFileIfNotExist();
            configMap = readJSON();
        } catch (UnrecognizedPropertyException e) {
            MainClass.errorLog("Unsupported config.json file format, resetting to default.");
            e.printStackTrace();
            resetToDefault();
        } catch (Exception e){
            MainClass.errorLog("An error occurred while reading config.json file, resetting to default");
            e.printStackTrace();
            resetToDefault();
        } finally {
            MainClass.infoLog("Loaded configurations from config.json");
        }
    }

    /**
     * Resets the config.json to default
     */
    private void resetToDefault() {
        try {
            configMap = new ConfigMap();
            configMap.setDefaultCameraControlsConfigMap();
            configMap.setDefaultInventoryControlsConfigMap();
            configMap.setDefaultPoiConfigMap();
            configMap.setDefaultPlayerWarningConfigMap();
            configMap.setDefaultReadCrosshairConfigMap();
            configMap.setDefaultOtherConfigsMap();
            writeJSON(configMap);
        } catch (IOException e) {
            MainClass.errorLog("An error occurred while resetting config.json file to default.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createEmptyConfigFileIfNotExist() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (configFile.exists()) return;

        try {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
            MainClass.infoLog("Created an empty config.json file at: %s".formatted(configFile.getAbsolutePath()));
        } catch (IOException e) {
            MainClass.errorLog("An error occurred while creating empty config.json file.");
            e.printStackTrace();
        }
    }

    private void writeJSON(ConfigMap configMap) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(CONFIG_FILE_PATH), configMap);
    }

    private ConfigMap readJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(CONFIG_FILE_PATH), ConfigMap.class);
    }
}