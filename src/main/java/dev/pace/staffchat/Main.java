package dev.pace.staffchat;

import com.google.common.collect.Maps;
import dev.pace.staffchat.commands.*;
import dev.pace.staffchat.updatechecker.UpdateChecker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public final class Main extends JavaPlugin {

    public static Main instance = null;
    public FileConfiguration config;

    public Map<UUID, Boolean> toggledSC = Maps.newConcurrentMap();
    public Map<UUID, Boolean> toggledAC = Maps.newConcurrentMap();

    public static Main getInstance() {
        return instance;
    }

    public void reloadConfiguration() {
        this.reloadConfig();
        config = this.getConfig();
    }

    @Override
    public void onEnable() {
        Main.instance = this;
        config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("adminchat-enabled", true);
        config.addDefault("update-checker", false);
        this.saveConfig();
        Logger logger = this.getLogger();
        getCommand("screload").setExecutor(new StaffChatReload());
        getCommand("staffchat").setExecutor(new StaffChat());
        getCommand("sc").setExecutor(new StaffChat());
        getCommand("sctoggle").setExecutor(new StaffChatToggle());
        getCommand("adminchat").setExecutor(new AdminChat());
        if (config.getBoolean("adminchat-enabled")) getCommand("asc").setExecutor(new AdminChat());
        getCommand("ac").setExecutor(new AdminChat());
        getCommand("adminstaffchat").setExecutor(new AdminChat());
        getCommand("actoggle").setExecutor(new AdminChatToggle());
        getCommand("adminchattoggle").setExecutor(new AdminChatToggle());
        getCommand("schelp").setExecutor(new StaffChatHelp(this));
        getCommand("staffchathelp").setExecutor(new StaffChatHelp(this));

        new UpdateChecker(this, 92585).getVersion(version -> {
            if (!config.getBoolean("update-checker")) return;
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("Staff Chat is up-to-date!");
            } else {
                logger.info("There is a new update available for Staff Chat. https://www.spigotmc.org/resources/staff-chat.92585/");
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}