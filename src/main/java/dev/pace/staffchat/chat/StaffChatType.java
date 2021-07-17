package dev.pace.staffchat.chat;

import dev.pace.staffchat.StaffChat;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */


public interface StaffChatType {

    String getCommand();

    String getToggleCommand();

    String getPrefix();

    String getPermission();

    String getType();

    default boolean sendChatMessage(final Player player, final String message) {
        if (!player.hasPermission(getPermission()) && !player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', StaffChat.instance.config.getString(getType() + ".error")));
            return false;
        }

        if (message.equals("")) {
            player.sendMessage("§cUsage:§7 /" + getPrefix() + " <message>");
            return false;
        }

        if (!StaffChat.getInstance().toggleTable.get(player.getUniqueId(), getType())) {
            player.sendMessage("§7Do /" + getType() + "chattoggle to talk in " + getType() + " chat!");
            return true;
        }

        final boolean isPapi = dev.pace.staffchat.StaffChat.getInstance().getPapiEnabled().get();
        final String header = StaffChat.instance.config.getString(getPrefix() + ".header");
        final String placeholder = StaffChat.instance.config.getString(getPrefix() + ".placeholder.name");

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission(getPermission())) {
                if (!StaffChat.instance.toggleTable.contains(staff.getUniqueId(), getType()))
                    StaffChat.instance.toggleTable.put(staff.getUniqueId(), getType(), true);
                if (StaffChat.getInstance().toggleTable.get(staff.getUniqueId(), getType())) {
                    String sendMessage = ChatColor.translateAlternateColorCodes('&', header) +
                            (isPapi ? placeholder : player.getName())
                            + ": "
                            + message;

                    if (isPapi) {
                        staff.sendMessage(PlaceholderAPI.setPlaceholders(player, sendMessage));
                    } else {
                        staff.sendMessage(sendMessage);
                    }
                }
            }
        }

        return true;

    }

}
