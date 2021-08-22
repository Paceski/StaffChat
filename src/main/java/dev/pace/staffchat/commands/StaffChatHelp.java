package dev.pace.staffchat.commands;

import dev.pace.staffchat.StaffChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class StaffChatHelp implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
            // Add /schelp ingame command for people to see list of all commands.
            p.sendMessage(cc("&6Running&b StaffChat " + StaffChat.getInstance().getVersion()));
            p.sendMessage(cc("&fAll useful commands:"));
            p.sendMessage(cc("&e/sc <message> - &fTalk in staff chat."));
            p.sendMessage(cc("&e/sctoggle - &fEnable or Disable the Staff Chat."));
            p.sendMessage(cc("&e/sclock - &fEnable or Disable sending messages to Staff Chat."));
            p.sendMessage(cc("&e/devchat <message> - &fTalk in Developer Chat."));
            p.sendMessage(cc("&e/devchattoggle - &fEnable or Disable the Developer Chat."));
            p.sendMessage(cc("&e/devchatlock - &fEnable or Disable sending messages to Developer Chat."));
            p.sendMessage(cc("&e/adminchat <message> - &fTalk in admin chat."));
            p.sendMessage(cc("&e/adminchattoggle - &fEnable or Disable the Admin Chat."));
            p.sendMessage(cc("&e/adminlock - &fEnable or Disable sending messages to Admin Chat."));
            p.sendMessage(cc("&e/screload - &fReloads plugin configuration."));
            p.sendMessage(cc("&e/schelp - &fView all this plugin's useful commands."));
            return true;
        }
        return false;
    }

    public String cc(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
