package dev.pace.staffchat.chat;

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
