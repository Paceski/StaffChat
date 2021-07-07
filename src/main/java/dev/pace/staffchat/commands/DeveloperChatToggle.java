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

public class DeveloperChatToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        StaffChat staffChat = StaffChat.getInstance();
        if (staffChat == null) return true;
        if (player.hasPermission("staff.developerchat") || player.isOp()) {
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("developerchat.error")));
            return true;
        }
        UUID uuid = player.getUniqueId();
        if(!staffChat.toggleTable.contains(player.getUniqueId(), "dev"))
            staffChat.toggleTable.put(player.getUniqueId(), "dev", true);
        boolean isEnabled = StaffChat.getInstance().toggleTable.get(player.getUniqueId(), "dev");
        StaffChat.getInstance().toggleTable.put(player.getUniqueId(),"dev",!isEnabled); // if it was disabled, this is true,
        player.sendMessage(!isEnabled ? ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("developerchat.toggle-on")) : ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("developerchat.toggle-off")));
        return true;
    }
}
