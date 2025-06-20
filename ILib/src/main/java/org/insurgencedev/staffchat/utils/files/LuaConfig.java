package org.insurgencedev.staffchat.utils.files;

import org.insurgencedev.staffchat.utils.Common;
import org.insurgencedev.staffchat.utils.LuaUtils;
import org.insurgencedev.staffchat.utils.plugin.IPlugin;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class LuaConfig {

    private Globals globals;
    File file;

    public LuaConfig(String resourcePath, String fileName, String destination) {
        this.globals = LuaUtils.getGlobals();
        Common.log("Resource path: " + resourcePath);
        Common.log("File name: " + fileName);
        Common.log("Destination: " + destination);
        String creationPath = destination.isEmpty() ? fileName : destination + File.separator + fileName;
        File targetFile = new File(IPlugin.getIPluginInstance().getDataFolder(), creationPath);
// Ensure parent directories exist
        targetFile.getParentFile().mkdirs();
        if (FileUtils.copyResourceIfNotExists(resourcePath, targetFile.getPath(), IPlugin.getIPluginInstance().getClass())) {
            this.file = targetFile;
            try (FileReader reader = new FileReader(this.file)) {
                LuaValue chunk = globals.load(reader, fileName);
                chunk.call();
            } catch (IOException e) {
                Common.log("Failed to load Lua file: ", e);
            }
        } else {
            Common.log("There was an error copying the Lua file. Please contact the plugin developer.");
        }

    }

    public LuaValue get(String key) {
        LuaValue value = globals.get(key);
        if (value.isnil()) {
            Common.log("Key '" + key + "' not found in Lua config: " + file.getName());
            return LuaValue.NIL;
        }
        return value;
    }
}
