package dev.pace.staffchat.chat.listener;

import dev.pace.staffchat.StaffChat;
import dev.pace.staffchat.chat.StaffChatType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class ChatListener implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        for (StaffChatType channel : StaffChat.getInstance().getChannels()) {
            if (StaffChat.getInstance().lockMap.get(player.getUniqueId()).equals(channel.getType())) {
                if (player.hasPermission(channel.getPermission())) {
                    channel.sendChatMessage(player, event.getMessage());
                    event.setCancelled(true);
                    return;
                } else {
                    // If player loses permission while chat toggled, set it back to public
                    StaffChat.getInstance().lockMap.put(player.getUniqueId(), "public");
                }
            }
        }
    }
}
