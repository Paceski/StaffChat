package org.insurgencedev.staffchat;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import org.insurgencedev.staffchat.chat.AdminChatImpl;
import org.insurgencedev.staffchat.chat.DeveloperChatImpl;
import org.insurgencedev.staffchat.chat.StaffChatImpl;
import org.insurgencedev.staffchat.chat.StaffChatType;
import org.insurgencedev.staffchat.chat.executor.StaffChatExecutor;
import org.insurgencedev.staffchat.chat.executor.StaffChatToggleExecutor;
import org.insurgencedev.staffchat.chat.executor.StaffChatLockExecutor;
import org.insurgencedev.staffchat.chat.listener.ChatListener;
import org.insurgencedev.staffchat.commands.StaffChatHelp;
import org.insurgencedev.staffchat.commands.StaffChatReload;
import org.insurgencedev.staffchat.commands.TestCommand;
import org.insurgencedev.staffchat.settings.Localization;
import org.insurgencedev.staffchat.settings.Settings;
import org.insurgencedev.staffchat.utils.plugin.IPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Pace and Dragon
 * https://www.spigotmc.org/resources/1-7-1-17-staff-chat.92585/
 */

public final class StaffChat extends IPlugin {

    public static StaffChat instance;
    public FileConfiguration config;
    public Table<UUID, String, Boolean> toggleTable = HashBasedTable.create();
    public HashMap<UUID, String> lockMap = new HashMap<>();
    @Getter
    public AtomicBoolean papiEnabled = new AtomicBoolean(false);
    @Getter
    public ArrayList<StaffChatType> channels = new ArrayList<>();

    public static StaffChat getInstance() {
        return instance;
    }
    public Settings settings;
    public Localization localization;


    @Override
    public void onPluginStart() {
        instance = this;
        config = this.getConfig();
        config.options().copyDefaults(true);
        config.addDefault("staffchat-enabled", true);
        config.addDefault("developerchat-enabled", true);
        config.addDefault("adminchat-enabled", true);
        config.addDefault("update-checker", true); // Update checker is now enabled by default.
        this.saveConfig();
        channels.add(new AdminChatImpl());
        channels.add(new DeveloperChatImpl());
        channels.add(new StaffChatImpl());
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
        registerEvent(new ChatListener());
        registerCommand(new TestCommand());
        settings = new Settings();
        localization = new Localization();
    }

    @Override
    public void onReloadableStart() {
        this.reloadConfig();
        config = this.getConfig();
        papiEnabled.set(config.getBoolean("enable-placeholders"));
    }

    public boolean isChatEnabled(Player player, StaffChatType type) {
        if (!toggleTable.contains(player.getUniqueId(), type.getType())) {
            return true;
        }
        return StaffChat.getInstance().toggleTable.get(player.getUniqueId(), type.getType());
    }

}
