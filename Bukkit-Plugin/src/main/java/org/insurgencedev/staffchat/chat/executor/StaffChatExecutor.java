package org.insurgencedev.staffchat.chat.executor;

import org.insurgencedev.staffchat.chat.StaffChatType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pace
 * https://www.spigotmc.org/resources/1-7-1-17-staff-chat.92585/
 */

public class StaffChatExecutor implements CommandExecutor {

    private final StaffChatType chatType;

    public StaffChatExecutor(StaffChatType chatType) {
        this.chatType = chatType;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        chatType.sendChatMessage(((Player) sender), String.join(" ", args));
        return true;
    }
}
