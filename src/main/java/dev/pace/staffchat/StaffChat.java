package dev.pace.staffchat;

import com.google.common.collect.Maps;
import dev.pace.staffchat.commands.*;
import dev.pace.staffchat.metrics.Metrics;
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

public final class StaffChat extends JavaPlugin {

    public static StaffChat instance = null;
    public FileConfiguration config;

    public Map<UUID, Boolean> toggledSC = Maps.newConcurrentMap();
    public Map<UUID, Boolean> toggledAC = Maps.newConcurrentMap();
    public Map<UUID, Boolean> toggledDEV = Maps.newConcurrentMap();

    public static StaffChat getInstance() {
        return instance;
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public void reloadConfiguration() {
        this.reloadConfig();
        config = this.getConfig();
    }

    @Override
    public void onEnable() {
        StaffChat.instance = this;
        config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("developerchat-enabled", true);
        config.addDefault("adminchat-enabled", true);
        config.addDefault("update-checker", false);
        this.saveConfig();
        Logger logger = this.getLogger();
        int pluginId = 11633;
        Metrics metrics = new Metrics(this, 11633);
        getCommand("screload").setExecutor(new StaffChatReload());
        getCommand("staffchat").setExecutor(new dev.pace.staffchat.commands.StaffChat());
        getCommand("sc").setExecutor(new dev.pace.staffchat.commands.StaffChat());
        getCommand("sctoggle").setExecutor(new StaffChatToggle());
        if (config.getBoolean("adminchat-enabled")) {
            getCommand("adminchat").setExecutor(new AdminChat()); // aliases: asc ac adminstaffchat
            getCommand("actoggle").setExecutor(new AdminChatToggle());
            getCommand("adminchattoggle").setExecutor(new AdminChatToggle());
        }
        if (config.getBoolean("developerchat-enabled")) {
            getCommand("devchat").setExecutor(new DeveloperChat()); // aliases: developerchat
            getCommand("devchattoggle").setExecutor(new DeveloperChatToggle());
            getCommand("developerchattoggle").setExecutor(new DeveloperChatToggle());
        }
        getCommand("schelp").setExecutor(new StaffChatHelp());
        getCommand("staffchathelp").setExecutor(new StaffChatHelp());

        new UpdateChecker(this, 92585).getVersion(version -> {
            if (!config.getBoolean("update-checker")) return;
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("Staff Chat is up-to-date!");
            } else {
                logger.info("There is a new update available for Staff Chat. Download it here: https://www.spigotmc.org/resources/staff-chat.92585/");
            }
        });
    }

    @Override
    public void onDisable() {
    }
}
