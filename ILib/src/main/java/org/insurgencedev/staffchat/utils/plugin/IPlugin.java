package org.insurgencedev.staffchat.utils.plugin;

import lombok.Getter;
import me.clip.placeholderapi.metrics.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.insurgencedev.staffchat.utils.command.CommandUtils;
import org.insurgencedev.staffchat.utils.command.ICommand;


public abstract class IPlugin extends JavaPlugin {
    @Getter
    private static IPlugin iPluginInstance;


    @Override
    public void onEnable() {
        iPluginInstance = this;
        // Check for PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found! Enabling placeholders.");
        } else {
            getLogger().warning("Could not find PlaceholderAPI! Plugin will still work, but placeholders will not be available.");
        }
//        new Metrics(this, 11633);
        onPluginStart();
        onReloadableStart();
    }

    @Override
    public void onDisable() {
        onPluginStop();
    }

    /***
     * Runs on plugin start
     */
    public void onPluginStart() {
    }

    /***
     * Runs on reload and on start
     */
    public void onReloadableStart() {
    }

    /***
     * Runs on plugin stop
     */
    public void onPluginStop() {
    }

    ;

    /***
     * Runs on plugin reload
     */
    public void onPluginReload() {
    }

    ;

    /***
     * Register an event
     * @param event
     */
    public void registerEvent(Listener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }

    /***
     * Register multiple events
     * @param events
     */
    public void registerEvents(Listener... events) {
        for (Listener event : events) {
            Bukkit.getPluginManager().registerEvents(event, this);
        }
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public void registerCommand(ICommand command) {
        CommandUtils.registerCommand(command);
    }


}
