package com.thftgr.z_notuse;


import com.github.francesco149.koohii.Koohii;
import com.google.gson.JsonObject;
import com.thftgr.bot.ppCalc;
import com.thftgr.bot.ppCalcApi;

import java.io.BufferedReader;
import java.io.FileReader;

public class test {
    public static JsonObject settingValue ;


    public static void main(String[] args){
        System.out.println(new test().calcpp());
    }



    float calcpp(){


        com.thftgr.bot.ppCalcApi ppCalcApi = new ppCalcApi();

        ppCalcApi.aimStars = 3.45455; //diff_aim
        ppCalcApi.speedStars = 2.34301; //diff_speed
        ppCalcApi.mods = 0; //mods int
        ppCalcApi.beatmapMaxCombo = 507; // max_combo

        ppCalcApi.scoreMaxCombo = 507;
        ppCalcApi.ar = 7.8;
        ppCalcApi.od = 6.5;
        ppCalcApi.n300 = 263; //count_normal + count_slider + count_spinner
        ppCalcApi.n100 = 0;
        ppCalcApi.n50 = 0;
        ppCalcApi.nMiss = 0;

        ppCalcApi.countHitCircles = 101; //count_normal
        ppCalcApi.accuracy = 100f / 100; //acc percent

        return (float) ppCalcApi.Calculate();





    }



}






