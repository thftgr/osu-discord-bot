package com.thftgr.z_notuse;


import com.thftgr.bot.OsuPPCalc;

public class test {


    public static void main(String[] args) {
        //new ppCalc().parseMods("hdhrdt");
        OsuPPCalc ppCalc = new OsuPPCalc();

        ppCalc.mods = 0;

        ppCalc.difficultyrating =2.28166;
        ppCalc.totalHits = 194;
        ppCalc.countMiss = 0;
        ppCalc.beatmapMaxCombo = 194;

        ppCalc.misses = 0;
        ppCalc.scoreMaxCombo =194;
        ppCalc.od =6;
        ppCalc.accuracy = 1;

        System.out.println(ppCalc.taikoPPCalculate());


    }

    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }


}






