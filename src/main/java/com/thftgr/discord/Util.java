package com.thftgr.discord;

import com.google.gson.JsonElement;

public class Util {

    public Boolean isCommand(String s) {
        return s.startsWith(Main.settingValue.get("commandStartWith").getAsString());
    }







}
