package dev.pace.staffchat.chat;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
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
        return "devchatlock";
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
