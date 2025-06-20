package org.insurgencedev.staffchat.utils;

import org.insurgencedev.staffchat.utils.plugin.IPlugin;

public class Common {

    public static void log(String message) {
        IPlugin.getIPluginInstance().getLogger().info(message);
    }
    public static void log(String message, Exception exception) {
        IPlugin.getIPluginInstance().getLogger().info(message);
        exception.printStackTrace();
    }

}
