package com.thftgr.z_notuse;


import com.thftgr.bot.OsuPPCalc;
import com.thftgr.bot.ppCalc;

public class test {


    public static void main(String[] args) {
        //new ppCalc().parseMods("hdhrdt");

        OsuPPCalc ppCalc = new OsuPPCalc();

        ppCalc.mods = 0;
        ppCalc.scaledScore = 1000000;
        ppCalc.difficultyrating = 2.17828;


        ppCalc.countPerfect = 596;    // (HitResult.Perfect);
        ppCalc.countGreat = 0;      // (HitResult.Great);
        ppCalc.countGood = 0;       // (HitResult.Good);
        ppCalc.countOk = 0;         // (HitResult.Ok);
        ppCalc.countMeh = 0;        // (HitResult.Meh);
        ppCalc.countMiss = 0;       // (HitResult.Miss);




        System.out.println(ppCalc.maniaPPCalculate());
    }


}






