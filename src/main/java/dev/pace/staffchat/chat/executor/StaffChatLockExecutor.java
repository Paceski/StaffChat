package dev.pace.staffchat.chat.executor;

import dev.pace.staffchat.StaffChat;
import dev.pace.staffchat.chat.StaffChatType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatLockExecutor implements CommandExecutor {

    private final StaffChatType chatType;

    public StaffChatLockExecutor(StaffChatType chatType) {
        this.chatType = chatType;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        final Player player = (Player) sender;
        final StaffChat staffChat = StaffChat.getInstance();

        if (!player.hasPermission(chatType.getPermission()) && !player.isOp()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.getConfig().getString(chatType.getPrefix() + ".error")));
            return true;
        }

        if (!staffChat.lockMap.containsKey(player.getUniqueId())) {
            staffChat.lockMap.put(player.getUniqueId(), "public");
        }

        // If using the same chat lock command twice, switch to public chat. Otherwise lock to new chat type.
        final boolean isChannel = staffChat.lockMap.get(player.getUniqueId()).equals(chatType.getType());
        StaffChat.getInstance().lockMap.put(player.getUniqueId(), isChannel ? "public" : chatType.getType());

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.getConfig().getString(chatType.getPrefix() + ".lock-" + (!isChannel ? "on" : "off"))));
        return true;
    }
}
