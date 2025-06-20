package org.insurgencedev.staffchat.commands;

import org.insurgencedev.staffchat.StaffChat;
import org.insurgencedev.staffchat.utils.Color;
import org.insurgencedev.staffchat.utils.command.ICommand;
import org.insurgencedev.staffchat.utils.plugin.IPlugin;

import java.util.List;

public class TestCommand extends ICommand {

    public TestCommand() {
        super("testc");
        setPermission("staffchat.test");
        setDescription("Test command");
    }

    @Override
    public void onCommand() {
        String miniMessage = StaffChat.getInstance().settings.get("miniMessage1").tojstring();
        String mixedFormatMessage = StaffChat.getInstance().settings.get("miniMessage2").tojstring();
        sender.sendMessage(Color.colorize(miniMessage));
        sender.sendMessage(Color.colorize(mixedFormatMessage));
    }

    @Override
    protected List<String> tabComplete() {
        return switch (args.length) {
            case 1 -> List.of("test1", "test2");
            case 2 -> List.of("test", "test2");
            default -> null;
        };
    }
}
