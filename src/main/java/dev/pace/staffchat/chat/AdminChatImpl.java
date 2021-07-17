package dev.pace.staffchat.chat;

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
