package dev.pace.staffchat.chat;

/**
 * Created by Pace
 * https://www.spigotmc.org/resources/1-7-1-17-staff-chat.92585/
 */

public class AdminChatImpl implements StaffChatType {

    @Override
    public String getCommand() {
        return "adminchat";
    }

    @Override
    public String getToggleCommand() {
        return "actoggle";
    }

    @Override
    public String getLockCommand() {
        return "acdisable";
    }

    @Override
    public String getPrefix() {
        return "adminchat";
    }

    @Override
    public String getPermission() {
        return "staff.adminchat";
    }

    @Override
    public String getType() {
        return "admin";
    }
}
