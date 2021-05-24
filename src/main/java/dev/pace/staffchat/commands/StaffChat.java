package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        Main.getInstance().toggledSC.putIfAbsent(p.getUniqueId(), true);
        if (!p.hasPermission("staff.staffchat")) {
            // :thumb:
            p.sendMessage("§cYou are not allowed to execute this command. Contact a server administrator if you believe this is an error.");
            return true;
        }

        String message = String.join(" ", args);

        if (message.equals("")) {
            p.sendMessage("§cUsage:§7 /staffchat <message>");
            return true;
        }

        if(Main.getInstance().toggledSC.get(p.getUniqueId())) {
            p.sendMessage("Do /sctoggle to talk in staff chat!");
            return true;
        }
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.staffchat")) {

                // Add if absent
                Main.getInstance().toggledSC.putIfAbsent(staff.getUniqueId(), true);
                // if its enabled then there u go.
                if(Main.getInstance().toggledSC.get(staff.getUniqueId())) {
                    staff.sendMessage("§e§lStaffChat§7§l》" + p.getName() + ": " + message);
                }
            }
        }
        return false;

    }
}
