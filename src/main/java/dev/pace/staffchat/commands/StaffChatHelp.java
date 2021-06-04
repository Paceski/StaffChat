package dev.pace.staffchat.commands;

import dev.pace.staffchat.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffChatHelp implements CommandExecutor {
    private ArrayList<String> about;
    private final Main plugin;

    public StaffChatHelp(final Main instance) {
        this.about = new ArrayList<String>();
        this.plugin = instance;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by In-Game players.");
            return true;
        }
        final Player p = (Player) sender;
        if (!p.hasPermission("staff.staffchat")) {
            this.about.add(p.getName());
            p.sendMessage(ChatColor.RED + "You are not allowed to execute this command. Contact a server administrator if you believe this is an error.");
            return true;
        }
        if (command.getName().equalsIgnoreCase("schelp")) {
            p.sendMessage(this.cc("&eStaff Chat Help - Current Version: 0.8 - by Pace#0001"));
            p.sendMessage(this.cc("&6--------------------------------------"));
            p.sendMessage(this.cc("&6/sc <message> - &eTalk in staff chat."));
            p.sendMessage(this.cc("&6/sctoggle- &eEnable or Disable the Staff Chat"));
            p.sendMessage(this.cc("&6/screload - &eReloads plugin configuration."));
            p.sendMessage(this.cc("&6/adminchat <message> - &eTalk in admin chat."));
            p.sendMessage(this.cc("&6/adminchattoggle- &eEnable or Disable the Admin Chat"));
            p.sendMessage(this.cc("&6/schelp - &eView all staff chat useful commands."));
            return true;
        }
        return false;
    }

    public String cc(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}

