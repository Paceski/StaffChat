package dev.pace.staffchat.commands;

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

public class StaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        dev.pace.staffchat.StaffChat staffChat = dev.pace.staffchat.StaffChat.getInstance();
        staffChat.toggledSC.putIfAbsent(p.getUniqueId(), true);
        if (p.hasPermission("staff.staffchat") || p.isOp()) {
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("staffchat.error")));
            return true;
        }

        String message = String.join(" ", args);

        if (message.equals("")) {
            p.sendMessage("§cUsage:§7 /staffchat <message>");
            return true;
        }

        if (!dev.pace.staffchat.StaffChat.getInstance().toggledSC.get(p.getUniqueId())) {
            p.sendMessage("Do /sctoggle to talk in staff chat!");
            return true;
        }
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.staffchat")) {
                dev.pace.staffchat.StaffChat.getInstance().toggledSC.putIfAbsent(staff.getUniqueId(), true);
                if (dev.pace.staffchat.StaffChat.getInstance().toggledSC.get(staff.getUniqueId())) {
                    staff.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("staffchat.header")) + p.getName() + ": " + message);
                }
            }
        }
        return false;
    }

}