package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeveloperChatToggle implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
            if (!(commandSender instanceof Player)) return false;
            Player player = (Player) commandSender;
            Main main = Main.getInstance();
            if (main == null) return true;
            if (!player.hasPermission("staff.developerchat") || !player.isOp()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.config.getString("developerchat.error")));
                return true;
            }
            UUID uuid = player.getUniqueId();
            boolean isEnabled = Main.getInstance().toggledDEV.get(player.getUniqueId());
            Main.getInstance().toggledDEV.put(player.getUniqueId(), !isEnabled); // if it was disabled, this is true,
            player.sendMessage(!isEnabled ? ChatColor.translateAlternateColorCodes('&', main.config.getString("developerchat.toggle-on")) : ChatColor.translateAlternateColorCodes('&', main.config.getString("developerchat.toggle-off")));
            return true;
        }
}
