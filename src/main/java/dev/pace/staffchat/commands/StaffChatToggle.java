package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffChatToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        // Perm check
        if(!player.hasPermission("staff.staffchat")) {
            player.sendMessage(Main.config.getString("staffchat.error"));
            return true;
        }

        // Main execution
        // Put Player into the map if they don't exist
        Main.getInstance().toggledSC.putIfAbsent(player.getUniqueId(), true);

        boolean isEnabled = Main.getInstance().toggledSC.get(player.getUniqueId());
        boolean previousValue = Main.getInstance().toggledSC.put(player.getUniqueId(), !isEnabled);
        player.sendMessage(previousValue ? Main.config.getString("staffchat.toggle-on"): Main.config.getString("staffchat.toggle-off"));
        return true;
    }
}
