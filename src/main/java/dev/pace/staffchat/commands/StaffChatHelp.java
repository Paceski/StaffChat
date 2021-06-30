package dev.pace.staffchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class StaffChatHelp implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You cannot do this from console.");
            return true;
        }
        final Player p = (Player) sender;
        if (!p.hasPermission("staff.staffchat")) {
            p.sendMessage(ChatColor.RED + "You are not allowed to execute this command. Contact a server administrator if you believe this is an error.");
            return true;
        }
        if (command.getName().equalsIgnoreCase("schelp")) {
            p.sendMessage(this.cc("&eStaff Chat Help - Current Version: 1.6"));
            p.sendMessage(this.cc("&6--------------------------------------"));
            p.sendMessage(this.cc("&6/sc <message> - &eTalk in staff chat."));
            p.sendMessage(this.cc("&6/sctoggle- &eEnable or Disable the Staff Chat."));
            p.sendMessage(this.cc("&6/screload - &eReloads plugin configuration."));
            p.sendMessage(this.cc("&6/devchat <message> - &eTalk in Developer Chat."));
            p.sendMessage(this.cc("&6/devchattoggle - &eEnable or Disable the Developer Chat."));
            p.sendMessage(this.cc("&6/adminchat <message> - &eTalk in admin chat."));
            p.sendMessage(this.cc("&6/adminchattoggle - &eEnable or Disable the Admin Chat."));
            p.sendMessage(this.cc("&6/schelp - &eView all this plugin's useful commands."));
            return true;
        }
        return false;
    }

    public String cc(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

