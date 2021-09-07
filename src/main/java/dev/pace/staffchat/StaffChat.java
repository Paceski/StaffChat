package dev.pace.staffchat;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.pace.staffchat.chat.AdminChatImpl;
import dev.pace.staffchat.chat.DeveloperChatImpl;
import dev.pace.staffchat.chat.StaffChatImpl;
import dev.pace.staffchat.chat.StaffChatType;
import dev.pace.staffchat.chat.executor.StaffChatExecutor;
import dev.pace.staffchat.chat.executor.StaffChatToggleExecutor;
import dev.pace.staffchat.chat.executor.StaffChatLockExecutor;
import dev.pace.staffchat.chat.listener.ChatListener;
import dev.pace.staffchat.commands.StaffChatHelp;
import dev.pace.staffchat.commands.StaffChatReload;
import dev.pace.staffchat.metrics.Metrics;
import dev.pace.staffchat.updatechecker.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public final class StaffChat extends JavaPlugin {

    public static StaffChat instance;
    public FileConfiguration config;

    public Table<UUID, String, Boolean> toggleTable = HashBasedTable.create();
    public HashMap<UUID, String> lockMap = new HashMap<>();
    public AtomicBoolean papiEnabled = new AtomicBoolean(false);

    public ArrayList<StaffChatType> channels = new ArrayList<>();

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
        instance = this;
        config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("staffchat-enabled", true);
        config.addDefault("developerchat-enabled", true);
        config.addDefault("adminchat-enabled", true);
        config.addDefault("update-checker", false);
        this.saveConfig();

        channels.add(new AdminChatImpl());
        channels.add(new DeveloperChatImpl());
        channels.add(new StaffChatImpl());

        final Logger logger = this.getLogger();

        new Metrics(this, 11633);

        getCommand("screload").setExecutor(new StaffChatReload());
        getCommand("schelp").setExecutor(new StaffChatHelp());
        getCommand("staffchathelp").setExecutor(new StaffChatHelp());

        for (StaffChatType type : channels) {
            if (config.getBoolean(type.getPrefix() + "-enabled")) {
                getCommand(type.getCommand()).setExecutor(new StaffChatExecutor(type));
                getCommand(type.getToggleCommand()).setExecutor(new StaffChatToggleExecutor(type));
                getCommand(type.getLockCommand()).setExecutor(new StaffChatLockExecutor(type));
            }
        }

        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

        // Load PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            // PlaceholderAPI is alive.
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
                logger.info("No update found for Staff Chat.");
            } else {
                logger.info("There is a new update available for Staff Chat. Download it here: https://www.spigotmc.org/resources/staff-chat.92585/");
            }
        });
    }

    public boolean isChatEnabled(Player player, StaffChatType type) {
        if (!toggleTable.contains(player.getUniqueId(), type.getType())) {
            return true;
        }
        return StaffChat.getInstance().toggleTable.get(player.getUniqueId(), type.getType());
    }

    public AtomicBoolean getPapiEnabled() {
        return papiEnabled;
    }

    public ArrayList<StaffChatType> getChannels() {
        return channels;
    }
}
