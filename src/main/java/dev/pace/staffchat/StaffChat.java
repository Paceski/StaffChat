package dev.pace.staffchat;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.pace.staffchat.chat.AdminChatImpl;
import dev.pace.staffchat.chat.DeveloperChatImpl;
import dev.pace.staffchat.chat.StaffChatImpl;
import dev.pace.staffchat.chat.StaffChatType;
import dev.pace.staffchat.chat.executor.StaffChatExecutor;
import dev.pace.staffchat.chat.executor.StaffChatToggleExecutor;
import dev.pace.staffchat.commands.StaffChatHelp;
import dev.pace.staffchat.commands.StaffChatReload;
import dev.pace.staffchat.metrics.Metrics;
import dev.pace.staffchat.updatechecker.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public final class StaffChat extends JavaPlugin {

    public static StaffChat instance = null;
    public FileConfiguration config;

    public Table<UUID, String, Boolean> toggleTable = HashBasedTable.create();
    public AtomicBoolean papiEnabled = new AtomicBoolean(false);

    public static StaffChat getInstance() {
        return instance;
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public void reloadConfiguration() {
        this.reloadConfig();
        config = this.getConfig();
        papiEnabled.set(config.getBoolean("enable-placeholders"));
    }

    @Override
    public void onEnable() {
        StaffChat.instance = this;

        config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("staffchat-enabled", true);
        config.addDefault("developerchat-enabled", true);
        config.addDefault("adminchat-enabled", true);
        config.addDefault("update-checker", false);
        this.saveConfig();

        final Logger logger = this.getLogger();

        new Metrics(this, 11633);

        getCommand("screload").setExecutor(new StaffChatReload());
        getCommand("schelp").setExecutor(new StaffChatHelp());
        getCommand("staffchathelp").setExecutor(new StaffChatHelp());

        for (StaffChatType type : new StaffChatType[]{
                new StaffChatImpl(), new AdminChatImpl(), new DeveloperChatImpl()}) {
            if (config.getBoolean(type.getPrefix() + "-enabled")) {
                getCommand(type.getCommand()).setExecutor(new StaffChatExecutor(type));
                getCommand(type.getToggleCommand()).setExecutor(new StaffChatToggleExecutor(type));
            }
        }

        // Load PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            // place holder api is alive
            if (config.getBoolean("enable-placeholders")) {
                papiEnabled.set(true);
                getLogger().info("Hooked into PlaceholderAPI! If you encounter any bugs within config with placeholders make sure to report it to the plugin developer.");
            }
            getLogger().info("Hooked into PlaceholderAPI! But configuration disables it, it is still in beta testing so we do not recommend using it.");
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is optional.");
        }

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

    public AtomicBoolean getPapiEnabled() {
        return papiEnabled;
    }
}
