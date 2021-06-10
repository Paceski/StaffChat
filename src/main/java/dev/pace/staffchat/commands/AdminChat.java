package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class AdminChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        Main main = Main.getInstance();
        main.toggledAC.putIfAbsent(p.getUniqueId(), true);
        if (!main.config.getBoolean("adminchat-enabled")) return false;
        if (p.hasPermission("staff.adminchat") || p.isOp()) {
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.config.getString("adminchat.error")));
            return true;
        }

        String message = String.join(" ", args);

        if (message.equals("")) {
            p.sendMessage("§cUsage:§7 /adminchat <message>");
            return true;
        }

        if (!Main.getInstance().toggledAC.get(p.getUniqueId())) {
            p.sendMessage("Do /adminchattoggle to talk in staff chat!");
            return true;
        }
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.adminchat")) {
                Main.getInstance().toggledAC.putIfAbsent(staff.getUniqueId(), true);
                if (Main.getInstance().toggledAC.get(staff.getUniqueId())) {
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', main.config.getString("adminchat.header")) + p.getName() + ": " + message);
                }
            }
        }
        return false;
    }
}
