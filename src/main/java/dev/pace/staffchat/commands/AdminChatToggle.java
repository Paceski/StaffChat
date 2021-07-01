package dev.pace.staffchat.commands;

import dev.pace.staffchat.StaffChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class AdminChatToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        StaffChat staffChat = StaffChat.getInstance();
        if (staffChat == null) return true;
        if (!player.hasPermission("staff.adminchat") || !player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("adminchat.error")));
            return true;
        }
        UUID uuid = player.getUniqueId();
        if(!staffChat.toggleTable.contains(player.getUniqueId(), "admin"))
            staffChat.toggleTable.put(player.getUniqueId(), "admin", true);

        boolean isEnabled = StaffChat.getInstance().toggleTable.get(player.getUniqueId(), "admin");
        StaffChat.getInstance().toggleTable.put(player.getUniqueId(),"admin", !isEnabled); // if it was disabled, this is true,
        player.sendMessage(!isEnabled ? ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("adminchat.toggle-on")) : ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("adminchat.toggle-off")));
        return true;
    }
}
