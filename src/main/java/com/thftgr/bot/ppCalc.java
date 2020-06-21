package com.thftgr.bot;

import com.github.francesco149.koohii.Koohii;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ppCalc {


//    float ppCalc(File beatMapFile, float accuracy, int modesInt) {
//        try {
//
//
//            Koohii.Map beatmap = new Koohii.Parser().map(new BufferedReader(new FileReader(beatMapFile)));
//            Koohii.DiffCalc stars = new Koohii.DiffCalc().calc(beatmap);
//            Koohii.Accuracy acc = new Koohii.Accuracy(accuracy, beatmap.objects.size(), 0);
//            Koohii.PPv2Parameters params = new Koohii.PPv2Parameters();
//
//
//            params.beatmap = beatmap;
//            params.aim_stars = stars.aim;
//            params.speed_stars = stars.speed;
//
//
//            params.mods = modesInt;
//            params.n300 = acc.n300;
//            params.n100 = acc.n100;
//            params.n50 = acc.n50;
//            params.nmiss = 0;
//            params.score_version = 1;
//            params.base_ar = beatmap.ar;
//            params.base_od = beatmap.od;
//
//            Koohii.PPv2 pp = new Koohii.PPv2(params);
//            return (float) pp.total;
//        } catch (Exception ignored) {
//        }
//
//        return -1f;
//
//    }

    float ppCalc(JsonObject map, float accuracy, int mods) {
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

        return (float) ppCalcApi.Calculate();
    }


    JsonObject ppCalcLocal(JsonArray mapSetJsonArray, float acc, int mods) {
        JsonObject mapData = new JsonObject();

        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            String version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
            float pp = ppCalc(mapSetJsonArray.get(i).getAsJsonObject(),acc,mods);
            mapData.addProperty(version,String.format("%.2fpp",pp));

        }

        return mapData;
    }

}



