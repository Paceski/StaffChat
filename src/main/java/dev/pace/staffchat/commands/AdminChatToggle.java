package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class AdminChatToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.hasPermission("staff.adminchat")||!player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.config.getString("adminchat.error")));
            return true;
        }

        Main.getInstance().toggledAC.putIfAbsent(player.getUniqueId(), true);

        boolean isEnabled = Main.getInstance().toggledAC.get(player.getUniqueId());
        boolean previousValue = Main.getInstance().toggledAC.put(player.getUniqueId(), !isEnabled);
        player.sendMessage(!previousValue ? ChatColor.translateAlternateColorCodes('&',Main.config.getString("adminchat.toggle-on")): ChatColor.translateAlternateColorCodes('&',Main.config.getString("adminchat.toggle-off")));
        return true;
    }
}