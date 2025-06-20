package org.insurgencedev.staffchat.settings;

import lombok.Getter;
import org.insurgencedev.staffchat.utils.files.LuaConfig;

@Getter
public class Settings extends LuaConfig {
    public Settings() {
        super("Settings.lua", "Settings.lua", "");
    }
}
