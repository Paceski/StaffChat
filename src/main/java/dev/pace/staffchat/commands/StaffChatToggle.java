package dev.pace.staffchat.commands;

import dev.pace.staffchat.StaffChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class StaffChatToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        StaffChat staffChat = StaffChat.getInstance();
        if (!player.hasPermission("staff.staffchat") || !player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("staffchat.error")));
            return true;
        }

        staffChat.toggledSC.putIfAbsent(player.getUniqueId(), true);

        boolean isEnabled = staffChat.toggledSC.get(player.getUniqueId());
        boolean previousValue = staffChat.toggledSC.put(player.getUniqueId(), !isEnabled);
        player.sendMessage(!previousValue ? ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("staffchat.toggle-on")) : ChatColor.translateAlternateColorCodes('&', staffChat.config.getString("staffchat.toggle-off")));
        return true;
    }
}
