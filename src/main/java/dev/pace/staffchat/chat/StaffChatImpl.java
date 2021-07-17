package dev.pace.staffchat.chat;

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
    public String getPrefix() {
        return "staffchat";
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
