package com.thftgr.z_notuse;


import com.github.francesco149.koohii.Koohii;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;

public class test {
    public static JsonObject settingValue ;


    public static void main(String[] args) throws Exception {
        System.out.println(new test().calcpp());


    }

//    float calcpp(float percent, float aim,float speed,float diff)throws Exception{
//        Koohii.Map beatmap = new Koohii.Parser().map(new BufferedReader(new FileReader("nano - No pain, No game (TV Size) (browiec) [Hard].osu")));
//        Koohii.DiffCalc stars = new Koohii.DiffCalc().calc(beatmap);
//        Koohii.Accuracy acc = new Koohii.Accuracy(percent, beatmap.objects.size(), 0);
//        Koohii.PPv2Parameters params = new Koohii.PPv2Parameters();
//        stars.aim_difficulty = aim;
//        stars.speed_difficulty = speed;
//        stars.total = diff;
//        params.
//
//
//    }

    float calcpp()throws Exception{
        Koohii.Map beatmap = new Koohii.Parser().map(new BufferedReader(new FileReader("nano - No pain, No game (TV Size) (browiec) [Hard].osu")));
//        Koohii.Map beatmap = new Koohii.Parser().map(new BufferedReader(new FileReader(beatMapFile)));
        Koohii.DiffCalc stars = new Koohii.DiffCalc().calc(beatmap);
        Koohii.Accuracy acc = new Koohii.Accuracy(100f, beatmap.objects.size(), 0);
        Koohii.PPv2Parameters params = new Koohii.PPv2Parameters();


        params.beatmap = beatmap;
//        params.aim_stars = stars.aim;
//        params.speed_stars = stars.speed;
        params.aim_stars = 1.78458;
        params.speed_stars = 1.7929;
        stars.total =3.58163;
        params.score_version = 2;



        params.mods = 0;
        params.n300 = acc.n300;
        params.n100 = acc.n100;
        params.n50 = acc.n50;
        params.nmiss = 0;
        params.score_version = 1;
        params.base_ar = beatmap.ar;
        params.base_od = beatmap.od;

        Koohii.PPv2 pp = new Koohii.PPv2(params);
        System.out.println(pp.total);
        return (float) pp.total;





    }






}





