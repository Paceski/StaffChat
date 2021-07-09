package dev.pace.staffchat.commands;

import dev.pace.staffchat.StaffChat;
import me.clip.placeholderapi.PlaceholderAPI;
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
        StaffChat staffChat = StaffChat.getInstance();

        // If this does not contain staff bool, add it.
        if (!staffChat.toggleTable.contains(p.getUniqueId(), "admin"))
            staffChat.toggleTable.put(p.getUniqueId(), "admin", true);

        if (!staffChat.config.getBoolean("adminchat-enabled")) return false;
        if (p.hasPermission("staff.adminchat") || p.isOp()) {
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("adminchat.error")));
            return true;
        }

        String message = String.join(" ", args);

        if (message.equals("")) {
            p.sendMessage("§cUsage:§7 /adminchat <message>");
            return true;
        }

        if (!StaffChat.getInstance().toggleTable.get(p.getUniqueId(), "admin")) {
            p.sendMessage("§7Do /adminchattoggle to talk in admin chat!");
            return true;
        }
        boolean isPapi = dev.pace.staffchat.StaffChat.getInstance().getPapiEnabled().get();
        String header = staffChat.config.getString("adminchat.header");
        String placeholder = staffChat.config.getString("adminchat.placeholder.name");
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.adminchat")) {
                if (!staffChat.toggleTable.contains(staff.getUniqueId(), "admin"))
                    staffChat.toggleTable.put(staff.getUniqueId(), "admin", true);
                if (StaffChat.getInstance().toggleTable.get(staff.getUniqueId(), "admin")) {
                    String sendMessage = ChatColor.translateAlternateColorCodes('&', header) +
                            (isPapi ? placeholder : p.getName())
                            + ": "
                            + message;

                    if (isPapi) {
                        staff.sendMessage(PlaceholderAPI.setPlaceholders(p.getPlayer(), sendMessage));
                    } else {
                        staff.sendMessage(sendMessage);
                    }
                }
            }
        }
        return false;
    }
}
