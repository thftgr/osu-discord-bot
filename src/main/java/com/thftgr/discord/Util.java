package com.thftgr.discord;

public class Util {

    public Boolean isCommand(String s) {
        return s.startsWith(Main.settingValue.get("commandStartWith").getAsString());
    }



}
