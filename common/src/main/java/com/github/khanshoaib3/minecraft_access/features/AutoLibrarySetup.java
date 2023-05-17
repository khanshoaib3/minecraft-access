package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.UnzipUtility;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        if (checkInstalled()) return;

        try {
            downloadAndInstall();
        } catch (IOException e) {
            MainClass.errorLog("An error occurred while downloading library.");
        }
    }

    private void downloadAndInstall() throws IOException {
        if (SystemUtils.IS_OS_WINDOWS) {
            MainClass.infoLog("Downloading latest tolk build...");
            File tolkLatestBuildZip = new File(Paths.get("tolk-latest-build.zip").toAbsolutePath().toString());
            FileUtils.copyURLToFile(new URL("https://github.com/ndarilek/tolk/releases/download/refs%2Fheads%2Fmaster/tolk.zip"), tolkLatestBuildZip);

            UnzipUtility unzipUtility = new UnzipUtility();
            File tempDirectoryPath = Paths.get("temp-tolk-latest").toFile();
            try {
                unzipUtility.unzip(tolkLatestBuildZip.getAbsolutePath(), tempDirectoryPath.getAbsolutePath());
            } catch (Exception e) {
                MainClass.errorLog("An error occurred while extracting tolk-latest-build.zip");
                e.printStackTrace();
            }

            MainClass.infoLog("Deleting temp files.");
            FileUtils.delete(tolkLatestBuildZip);
            FileUtils.deleteDirectory(tempDirectoryPath);
        } else if (SystemUtils.IS_OS_LINUX) {
            MainClass.infoLog("Downloading libspeechdwrapper.so ...");
            FileUtils.copyURLToFile(new URL("https://github.com/khanshoaib3/libspeechdwrapper/releases/download/v1.0.0/libspeechdwrapper.so"), new File(Paths.get("libspeechdwrapper.so").toAbsolutePath().toString()));
            MainClass.infoLog("libspeechdwrapper.so downloaded and installed.");
        }
    }

    /**
     * Checks whether all the library files are installed or not
     *
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
     *
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
