package dev.pace.staffchat.chat.executor;

import dev.pace.staffchat.StaffChat;
import dev.pace.staffchat.chat.StaffChatType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class StaffChatToggleExecutor implements CommandExecutor {

    private final StaffChatType chatType;

    public StaffChatToggleExecutor(StaffChatType chatType) {
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
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString(chatType.getPrefix() + ".error")));
            return true;
        }

        final UUID uuid = player.getUniqueId();
        if (!staffChat.toggleTable.contains(player.getUniqueId(), chatType.getType())) {
            staffChat.toggleTable.put(player.getUniqueId(), chatType.getType(), true);
        }

        final boolean isEnabled = StaffChat.getInstance().toggleTable.get(player.getUniqueId(), chatType.getType());

        StaffChat.getInstance().toggleTable.put(player.getUniqueId(), chatType.getType(), !isEnabled); // if it was disabled, this is true,
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', staffChat.config.getString(chatType.getPrefix() + ".toggle-" + (!isEnabled ? "on" : "off"))));

        return true;
    }
}
