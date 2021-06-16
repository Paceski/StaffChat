package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeveloperChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        Main main = Main.getInstance();
        main.toggledDEV.putIfAbsent(p.getUniqueId(), true);
        if (!main.config.getBoolean("developerchat-enabled")) return false;
        if (p.hasPermission("staff.developerchat") || p.isOp()) {
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.config.getString("developerchat.error")));
            return true;
        }

        String message = String.join(" ", args);

        if (message.equals("")) {
            p.sendMessage("§cUsage:§7 /devchat <message>");
            return true;
        }

        if (!Main.getInstance().toggledDEV.get(p.getUniqueId())) {
            p.sendMessage("Do /developerchattoggle to talk in staff chat!");
            return true;
        }
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.developerchat")) {
                Main.getInstance().toggledDEV.putIfAbsent(staff.getUniqueId(), true);
                if (Main.getInstance().toggledDEV.get(staff.getUniqueId())) {
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', main.config.getString("developerchat.header")) + p.getName() + ": " + message);
                }
            }
        }
        return false;
    }
}
