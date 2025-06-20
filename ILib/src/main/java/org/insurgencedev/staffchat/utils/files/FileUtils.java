package org.insurgencedev.staffchat.utils.files;

import org.insurgencedev.staffchat.utils.Common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    /**
     * Copies a resource to a file if the file does not already exist.
     * @param resourcePath path to the resource (e.g., "/config.yml")
     * @param path path to the target file
     * @param clazz class to use for resource loading (e.g., YourPlugin.class)
     * @throws IOException if an I/O error occurs
     */
    public static boolean copyResourceIfNotExists(String resourcePath, String path, Class<?> clazz) {
        Path targetPath = Path.of(path);
        String rPath = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
        if (Files.exists(targetPath)) {
            return true;
        }
        try (InputStream in = clazz.getResourceAsStream(rPath)) {
            if (in == null) {
                Common.log("Resource not found: " + rPath + ", file not created.");
                return false;
            }
            Files.createDirectories(targetPath.getParent());
            Files.copy(in, targetPath);
            if (Files.exists(targetPath)) {
                return true;
            } else {
                Common.log("File was not created at: " + targetPath);
                return false;
            }
        } catch (IOException e) {
            Common.log("Failed to copy resource", e);
            return false;
        }
    }
}