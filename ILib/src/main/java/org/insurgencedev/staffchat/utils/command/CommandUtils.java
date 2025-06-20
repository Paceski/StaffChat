package org.insurgencedev.staffchat.utils.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.insurgencedev.staffchat.utils.ReflectionUtil;

public class CommandUtils {


    /**
     * Injects an existing command into the command map
     *
     * @param command
     */
    public static void registerCommand(final Command command) {
        final CommandMap commandMap = getCommandMap();
        commandMap.register(command.getLabel(), command);

        if(!command.isRegistered()){
            throw new RuntimeException("Failed to register command " + command.getLabel());
        }
    }


    /**
     * Return the server's command map
     *
     * @return
     */
    public static SimpleCommandMap getCommandMap() {
        final Class<?> craftServer = ReflectionUtil.getOBCClass("CraftServer");

        try {
            return (SimpleCommandMap) craftServer.getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());

        } catch (final ReflectiveOperationException ex) {

            try {
                return ReflectionUtil.getFieldContent(Bukkit.getServer(), "commandMap");

            } catch (final Throwable ex2) {
                throw new RuntimeException("Failed to get command map", ex2);
            }
        }
    }

}
