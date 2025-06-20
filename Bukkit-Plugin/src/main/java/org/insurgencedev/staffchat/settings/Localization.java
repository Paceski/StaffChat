package org.insurgencedev.staffchat.settings;

import lombok.Getter;
import org.insurgencedev.staffchat.utils.files.LuaConfig;

@Getter
public class Localization extends LuaConfig {
    public Localization() {
        super("localization/messages_en.lua", "messages_en.lua", "");
    }
}
