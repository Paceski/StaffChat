package dev.pace.staffchat;

import dev.pace.staffchat.commands.StaffChat;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("staffchat").setExecutor(new StaffChat());
        getCommand("sc").setExecutor(new StaffChat());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
