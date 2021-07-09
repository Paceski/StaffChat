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

public class DeveloperChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        StaffChat staffChat = StaffChat.getInstance();
        if(!staffChat.toggleTable.contains(p.getUniqueId(), "dev"))
            staffChat.toggleTable.put(p.getUniqueId(), "dev", true);
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

        if (!StaffChat.getInstance().toggleTable.get(p.getUniqueId(), "dev")) {
            p.sendMessage("§7Do /devchattoggle to talk in staff chat!");
            return true;
        }
        boolean isPapi = dev.pace.staffchat.StaffChat.getInstance().getPapiEnabled().get();
        String header = staffChat.config.getString("developerchat.header");
        String placeholder = staffChat.config.getString("developerchat.placeholder.name");
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("staff.developerchat")) {
                if(!staffChat.toggleTable.contains(staff.getUniqueId(), "dev"))
                    staffChat.toggleTable.put(staff.getUniqueId(), "dev", true);
                if (StaffChat.getInstance().toggleTable.get(staff.getUniqueId(), "dev")) {
                    String sendMessage = ChatColor.translateAlternateColorCodes('&',  header) +
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
