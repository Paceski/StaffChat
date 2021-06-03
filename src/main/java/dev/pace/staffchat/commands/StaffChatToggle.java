package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.hasPermission("staff.staffchat")||!player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.config.getString("staffchat.error")));
            return true;
        }

        Main.getInstance().toggledSC.putIfAbsent(player.getUniqueId(), true);

        boolean isEnabled = Main.getInstance().toggledSC.get(player.getUniqueId());
        boolean previousValue = Main.getInstance().toggledSC.put(player.getUniqueId(), !isEnabled);
        player.sendMessage(!previousValue ? ChatColor.translateAlternateColorCodes('&',Main.config.getString("staffchat.toggle-on")): ChatColor.translateAlternateColorCodes('&',Main.config.getString("staffchat.toggle-off")));
        return true;
    }
}
