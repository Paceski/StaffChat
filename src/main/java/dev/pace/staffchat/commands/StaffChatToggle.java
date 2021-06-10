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

public class StaffChatToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        Main main = Main.getInstance();
        if (!player.hasPermission("staff.staffchat") || !player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.config.getString("staffchat.error")));
            return true;
        }

        main.toggledSC.putIfAbsent(player.getUniqueId(), true);

        boolean isEnabled = main.toggledSC.get(player.getUniqueId());
        boolean previousValue = main.toggledSC.put(player.getUniqueId(), !isEnabled);
        player.sendMessage(!previousValue ? ChatColor.translateAlternateColorCodes('&', main.config.getString("staffchat.toggle-on")) : ChatColor.translateAlternateColorCodes('&', main.config.getString("staffchat.toggle-off")));
        return true;
    }
}
