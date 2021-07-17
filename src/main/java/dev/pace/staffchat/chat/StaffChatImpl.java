package dev.pace.staffchat.chat;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
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
