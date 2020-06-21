package com.thftgr.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.jdi.Mirror;

import java.util.Random;

public class ppCalc {
    public static class mods{
        public int None           = 0;
        public int NoFail         = 1;
        public int Easy           = 2;
        public int TouchDevice    = 4;
        public int Hidden         = 8;
        public int HardRock       = 16;
        public int SuddenDeath    = 32;
        public int DoubleTime     = 64;
        public int Relax          = 128;
        public int HalfTime       = 256;
        public int Nightcore      = 512; // Only set along with DoubleTime. i.e: NC only gives 576
        public int Flashlight     = 1024;
        public int Autoplay       = 2048;
        public int SpunOut        = 4096;
        public int Relax2         = 8192;    // Autopilot
        public int Perfect        = 16384; // Only set along with SuddenDeath. i.e: PF only gives 16416
        public int Key4           = 32768;
        public int Key5           = 65536;
        public int Key6           = 131072;
        public int Key7           = 262144;
        public int Key8           = 524288;
        public int FadeIn         = 1048576;
        public int Random         = 2097152;
        public int Cinema         = 4194304;
        public int Target         = 8388608;
        public int Key9           = 16777216;
        public int KeyCoop        = 33554432;
        public int Key1           = 67108864;
        public int Key3           = 134217728;
        public int Key2           = 268435456;
        public int ScoreV2        = 536870912;
        public int Mirror         = 1073741824;
        public int KeyMod = Key1 | Key2 | Key3 | Key4 | Key5 | Key6 | Key7 | Key8 | Key9 | KeyCoop;
        public int FreeModAllowed = NoFail | Easy | Hidden | HardRock | SuddenDeath | Flashlight | FadeIn | Relax | Relax2 | SpunOut | KeyMod;
        public int ScoreIncreaseMods = Hidden | HardRock | DoubleTime | Flashlight | FadeIn;


    }
    public void parseMods(String mods){
        String[] mod = new String[mods.length()/2];
        for (int i = 0; i < (mods.length()/2) ; i++) {
            mod[i] = mods.substring((i*2),((i*2)+2)).toUpperCase();
            System.out.println(mod[i]);
        }
        



    }


    float osuPpCalc(JsonObject map, float accuracy, int mods) {
        com.thftgr.bot.ppCalcApi ppCalcApi = new ppCalcApi();

        ppCalcApi.aimStars = map.get("diff_aim").getAsDouble(); //diff_aim
        ppCalcApi.speedStars = map.get("diff_speed").getAsDouble(); //diff_speed
        ppCalcApi.mods = mods; //mods int
        ppCalcApi.beatmapMaxCombo = map.get("max_combo").getAsInt(); // max_combo
        ppCalcApi.scoreMaxCombo = map.get("max_combo").getAsInt();

        ppCalcApi.ar = map.get("diff_approach").getAsDouble(); // diff_approach
        ppCalcApi.od = map.get("diff_overall").getAsDouble(); // diff_overall

        //count_normal + count_slider + count_spinner
        ppCalcApi.n300 = map.get("count_normal").getAsInt()+map.get("count_slider").getAsInt()+map.get("count_spinner").getAsInt();
        ppCalcApi.n100 = 0;
        ppCalcApi.n50 = 0;
        ppCalcApi.nMiss = 0;

        ppCalcApi.countHitCircles = map.get("count_normal").getAsInt(); //count_normal
        ppCalcApi.accuracy = accuracy / 100; //acc percent

        return (float) ppCalcApi.osuPPCalculate();
    }


    JsonObject ppCalcLocal(JsonArray mapSetJsonArray, float acc, int mods) {
        JsonObject mapData = new JsonObject();

        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            String version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
            float pp = osuPpCalc(mapSetJsonArray.get(i).getAsJsonObject(),acc,mods);
            mapData.addProperty(version,String.format("%.2fpp",pp));

        }

        return mapData;
    }

}



