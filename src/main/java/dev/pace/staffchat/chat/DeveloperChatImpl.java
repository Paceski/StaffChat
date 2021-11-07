package dev.pace.staffchat.chat;

/**
 * Created by Pace
 * https://www.spigotmc.org/resources/1-7-1-17-staff-chat.92585/
 */

public class DeveloperChatImpl implements StaffChatType {

    @Override
    public String getCommand() {
        return "devchat";
    }

    @Override
    public String getToggleCommand() {
        return "devchattoggle";
    }

    @Override
    public String getLockCommand() {
        return "devchatdisable";
    }

    @Override
    public String getPrefix() {
        return "developerchat";
    }

    @Override
    public String getPermission() {
        return "staff.developerchat";
    }

    @Override
    public String getType() {
        return "dev";
    }
}
