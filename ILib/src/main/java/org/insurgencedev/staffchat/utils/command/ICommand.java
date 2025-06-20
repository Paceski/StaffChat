package org.insurgencedev.staffchat.utils.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public abstract class ICommand extends Command {

//    private String label = "";

    protected String[] args;
    @Getter
    protected CommandSender sender;
    @Setter
    private String permission = null;

    @Getter
    private Player player;


    public ICommand(@NotNull String name) {
        super(name);
    }


    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        this.args = args;
        this.sender = sender;
        this.player = sender instanceof Player ? (Player) sender : null;
        if (permission != null && !hasPerm(permission)) {
            sender.sendMessage("§cYou do not have permission to execute this command.");
            return false;
        }
        onCommand();
        return false;
    }

    public abstract void onCommand();

    @Override
    public final @NotNull List<String> tabComplete(final CommandSender sender, final String alias, final String[] args, final Location location) throws IllegalArgumentException {
        return this.tabComplete(sender, alias, args);
    }

    @Override
    public final @NotNull List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        this.sender = sender;
        this.args = args;

        if (this.hasPerm(this.getPermission())) {
            List<String> suggestions = this.tabComplete();

            // Return online player names when suggestions are null - simulate Bukkit behaviour
            if (suggestions == null)
                suggestions = Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();

            return suggestions;
        }

        return new ArrayList<>();
    }

    protected List<String> tabComplete() {
        return null;
    }

    private boolean hasPerm(String perm) {
        return perm == null || sender.hasPermission(perm);
    }
}
