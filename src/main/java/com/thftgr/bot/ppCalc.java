package com.thftgr.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ppCalc {

    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }

    JsonObject ppCalcLocal(JsonArray mapSetJsonArray, float acc, int mods) {
        JsonObject mapData = new JsonObject();


        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            String version = "";
            float pp = 0;
            switch (mapSetJsonArray.get(i).getAsJsonObject().get("mode").getAsInt()) {
                case 0:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = osuPpCalc(mapSetJsonArray.get(i).getAsJsonObject(), acc, mods);
                    mapData.addProperty(version, String.format("%.2fpp", pp));

                break;
                case 1:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = taikoPpCalc(mapSetJsonArray.get(i).getAsJsonObject());
                    mapData.addProperty(version, String.format("%.2fpp", pp));
                    break;
                case 2:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = catchPpCalc(mapSetJsonArray.get(i).getAsJsonObject());
                    mapData.addProperty(version, String.format("%.2fpp", pp));
                    break;
                case 3:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = maniaPpCalc(mapSetJsonArray.get(i).getAsJsonObject());
                    mapData.addProperty(version, String.format("%.2fpp", pp));
                break;
            }
        }

        return mapData;
    }


    float osuPpCalc(JsonObject map, float accuracy, int mods) {
        OsuPPCalc ppCalc = new OsuPPCalc();

        ppCalc.aimStars = map.get("diff_aim").getAsDouble(); //diff_aim
        ppCalc.speedStars = map.get("diff_speed").getAsDouble(); //diff_speed
        ppCalc.mods = mods; //mods int
        ppCalc.beatmapMaxCombo = map.get("max_combo").getAsInt(); // max_combo
        ppCalc.scoreMaxCombo = map.get("max_combo").getAsInt();

        ppCalc.ar = map.get("diff_approach").getAsDouble(); // diff_approach
        ppCalc.od = map.get("diff_overall").getAsDouble(); // diff_overall

        //count_normal + count_slider + count_spinner
        ppCalc.n300 = map.get("count_normal").getAsInt() + map.get("count_slider").getAsInt() + map.get("count_spinner").getAsInt();
        ppCalc.n100 = 0;
        ppCalc.n50 = 0;
        ppCalc.nMiss = 0;

        ppCalc.countHitCircles = map.get("count_normal").getAsInt(); //count_normal
        ppCalc.accuracy = accuracy / 100; //acc percent

        return (float) ppCalc.osuPPCalculate();
    }

    float maniaPpCalc(JsonObject map) {
        OsuPPCalc ppCalc = new OsuPPCalc();

        ppCalc.mods = 0;
        ppCalc.scaledScore = 1000000;
        ppCalc.difficultyrating = map.get("difficultyrating").getAsDouble();

        //count_normal + count_slider +count_spinner
        ppCalc.countPerfect = map.get("count_normal").getAsInt()+map.get("count_slider").getAsInt();    // (HitResult.Perfect);
        ppCalc.countGreat = 0;      // (HitResult.Great);
        ppCalc.countGood = 0;       // (HitResult.Good);
        ppCalc.countOk = 0;         // (HitResult.Ok);
        ppCalc.countMeh = 0;        // (HitResult.Meh);
        ppCalc.countMiss = 0;       // (HitResult.Miss);

        return (float) ppCalc.maniaPPCalculate();

    }

    float catchPpCalc(JsonObject map){
        OsuPPCalc ppCalc = new OsuPPCalc();

        ppCalc.mods = 0;

        ppCalc.difficultyrating = map.get("difficultyrating").getAsDouble();
        ppCalc.beatmapMaxCombo = map.get("max_combo").getAsInt();
        ppCalc.misses =0;
        ppCalc.scoreMaxCombo = map.get("max_combo").getAsInt();
        ppCalc.ar = map.get("diff_approach").getAsDouble();
        ppCalc.accuracy = 1;


        return (float) ppCalc.catchPPCalculate();
    }
    float taikoPpCalc(JsonObject map){
        OsuPPCalc ppCalc = new OsuPPCalc();

        ppCalc.mods = 0;

        ppCalc.difficultyrating = map.get("difficultyrating").getAsDouble();
        ppCalc.totalHits = map.get("count_normal").getAsInt();
        ppCalc.countMiss = 0;
        ppCalc.beatmapMaxCombo = map.get("count_normal").getAsInt();

        ppCalc.misses = 0;
        ppCalc.scoreMaxCombo =map.get("count_normal").getAsInt();
        ppCalc.od =map.get("diff_overall").getAsDouble();
        ppCalc.accuracy = 1;


        return (float) ppCalc.taikoPPCalculate();
    }


}



