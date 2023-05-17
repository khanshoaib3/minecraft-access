package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import org.apache.commons.lang3.SystemUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Automatically installs the required libraries for client's operating system.
 */
public class AutoLibrarySetup {
    public AutoLibrarySetup() {

    }

    public void initialize() {
        checkInstalled();
    }

    /**
     * Checks whether all the library files are installed or not
     * @return Returns true if all files are installed otherwise false.
     */
    private boolean checkInstalled() {
        MainClass.infoLog("Checking for installed files...");

        for (String libraryName : getRequiredLibraryNames()) {
            MainClass.infoLog("Checking for " + libraryName);
            if (!Files.exists(Paths.get(libraryName).toAbsolutePath())) {
                MainClass.infoLog(libraryName + " file not found.");
                return false;
            }
        }

        MainClass.infoLog("All files are installed.");
        return true;
    }

    /**
     * Returns the list of libraries required based on the operating system.
     * @return The list of required libraries
     */
    private List<String> getRequiredLibraryNames() {
        List<String> requiredFiles = new ArrayList<>();

        if (SystemUtils.IS_OS_WINDOWS) {
            requiredFiles.add("Tolk.dll");
            if (SystemUtils.OS_ARCH.equalsIgnoreCase("X86")) {
                requiredFiles.add("nvdaControllerClient32.dll");
                requiredFiles.add("SAAPI32.dll");
                requiredFiles.add("dolapi32.dll");
            } else {
                requiredFiles.add("nvdaControllerClient64.dll");
                requiredFiles.add("SAAPI64.dll");
            }
        } else if (SystemUtils.IS_OS_LINUX) {
            requiredFiles.add("libspeechdwrapper.so");
        }

        return requiredFiles;
    }
}
