package dev.pace.staffchat.commands;

import dev.pace.staffchat.StaffChat;
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

public class DeveloperChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        StaffChat staffChat = StaffChat.getInstance();
        staffChat.toggledDEV.putIfAbsent(p.getUniqueId(), true);
        if (!staffChat.config.getBoolean("developerchat-enabled")) return false;
        if (p.hasPermission("staff.developerchat") || p.isOp()) {
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("developerchat.error")));
            return true;
        }

        String message = String.join(" ", args);

        if (message.equals("")) {
            p.sendMessage("§cUsage:§7 /devchat <message>");
            return true;
        }

        if (!StaffChat.getInstance().toggledDEV.get(p.getUniqueId())) {
            p.sendMessage("§7Do /devchattoggle to talk in staff chat!");
            return true;
        }
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.developerchat")) {
                StaffChat.getInstance().toggledDEV.putIfAbsent(staff.getUniqueId(), true);
                if (StaffChat.getInstance().toggledDEV.get(staff.getUniqueId())) {
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("developerchat.header")) + p.getName() + ": " + message);
                }
            }
        }
        return false;
    }
}
