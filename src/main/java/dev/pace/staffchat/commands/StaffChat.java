package dev.pace.staffchat.commands;

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

public class StaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        dev.pace.staffchat.StaffChat staffChat = dev.pace.staffchat.StaffChat.getInstance();

        if (!staffChat.toggleTable.contains(p.getUniqueId(), "staff"))
            staffChat.toggleTable.put(p.getUniqueId(), "staff", true);

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

        if (!dev.pace.staffchat.StaffChat.getInstance().toggleTable.get(p.getUniqueId(), "staff")) {
            p.sendMessage("§7Do /sctoggle to talk in staff chat!");
            return true;
        }
        boolean isPapi = dev.pace.staffchat.StaffChat.getInstance().getPapiEnabled().get();
        String header = staffChat.config.getString("staffchat.header");
        String placeholder = staffChat.config.getString("staffchat.placeholder.name");
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.staffchat")) {
                if (!staffChat.toggleTable.contains(staff.getUniqueId(), "staff"))
                    staffChat.toggleTable.put(staff.getUniqueId(), "staff", true);
                if (dev.pace.staffchat.StaffChat.getInstance().toggleTable.get(staff.getUniqueId(), "staff")) {
                    String willSendMessage =
                            ChatColor.translateAlternateColorCodes('&', header)
                                    +
                                    (isPapi ? placeholder : p.getName())
                                    + ": "
                                    + message;

                    if (isPapi) {
                        staff.sendMessage(PlaceholderAPI.setPlaceholders(p.getPlayer(), willSendMessage));
                    } else {
                        staff.sendMessage(willSendMessage);
                    }
                }
            }
        }
        return false;
    }

}