package dev.pace.staffchat.chat;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
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
