package dev.pace.staffchat;

import com.google.common.collect.Maps;
import dev.pace.staffchat.commands.StaffChat;
import dev.pace.staffchat.commands.StaffChatToggle;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static Main instance = null;
    public Map<UUID, Boolean> toggledSC = Maps.newConcurrentMap();

    public synchronized static Main getInstance(){
        if(instance == null) instance = new Main();
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("staffchat").setExecutor(new StaffChat());
        getCommand("sc").setExecutor(new StaffChat());
        getCommand("sctoggle").setExecutor(new StaffChatToggle());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
