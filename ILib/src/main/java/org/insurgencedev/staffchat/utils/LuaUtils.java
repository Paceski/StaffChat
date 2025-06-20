package org.insurgencedev.staffchat.utils;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaUtils {

    public static Globals getGlobals() {
        return JsePlatform.standardGlobals();
    }


}
