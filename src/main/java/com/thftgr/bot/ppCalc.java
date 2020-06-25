package com.thftgr.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thftgr.osuPerformance.Catch;
import com.thftgr.osuPerformance.Mania;
import com.thftgr.osuPerformance.STD;
import com.thftgr.osuPerformance.Taiko;

public class ppCalc {

    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }


}



