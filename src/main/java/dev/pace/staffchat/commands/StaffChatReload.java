package dev.pace.staffchat.commands;

import dev.pace.staffchat.StaffChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class StaffChatReload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            // Be able to do /screload from  console too.
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            StaffChat.getInstance().reloadConfiguration();
            console.sendMessage(ChatColor.GREEN + "Config has been reloaded!");
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("staff.reload") || !p.isOp()) {
            p.sendMessage(ChatColor.RED + "You are not allowed to execute this command. Contact a server administrator if you believe this is an error.");
            return true;
        }
        try {
            StaffChat.getInstance().reloadConfiguration();
            sender.sendMessage(ChatColor.GREEN + "Config has been reloaded!");
        } catch (NullPointerException e) {
            sender.sendMessage(ChatColor.DARK_RED + "Error! Check console for more details. Having issues? Ask for support from plugin developer.");
        }
        return true;
    }
}
