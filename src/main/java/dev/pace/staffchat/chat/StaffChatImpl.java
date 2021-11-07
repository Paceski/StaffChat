package dev.pace.staffchat.chat;

/**
 * Created by Pace
 * https://www.spigotmc.org/resources/1-7-1-17-staff-chat.92585/
 */

public class StaffChatImpl implements StaffChatType {

    @Override
    public String getCommand() {
        return "staffchat";
    }

    @Override
    public String getToggleCommand() {
        return "sctoggle";
    }

    @Override
    public String getLockCommand() {
        return "scdisable";
    }

    @Override
    public String getPrefix() {
        return "staffchat";
    }

    @Override
    public String getPermission() {
        return "staff.staffchat";
    }

    @Override
    public String getType() {
        return "staff";
    }
}
