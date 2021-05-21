package dev.pace.staffchat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        if (!p.hasPermission("staff.staffchat")) {
            p.sendMessage("§cYou are not allowed to execute this command. Contact server administrators if you believe this is an error.");
            return true;
        }

        String message = String.join(" ", args);

        if (message.equals("")) {
            p.sendMessage("§cUsage:§7 /staffchat <message>");
            return true;
        }

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.staffchat")) {
                staff.sendMessage("§e§lStaffChat§7§l》" + p.getName() + ": " + message);
            }
        }
        return false;

    }
}
