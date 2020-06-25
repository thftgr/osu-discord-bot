package com.thftgr.osuPerformance;

import com.google.gson.JsonObject;

public class Catch {

    public double Calculate(JsonObject map) {


        int mods = 0;

        double difficultyrating = map.get("difficultyrating").getAsDouble();
        int beatmapMaxCombo = map.get("max_combo").getAsInt();
        int misses =0;
        int scoreMaxCombo = map.get("max_combo").getAsInt();
        double ar = map.get("diff_approach").getAsDouble();
        int accuracy = 1;


        double value = Math.pow(5.0 * Math.max(1.0, difficultyrating / 0.0049) - 4.0, 2.0) / 100000.0;

        double lengthBonus = 0.95 + 0.3 * Math.min(1.0, beatmapMaxCombo / 2500.0) + (beatmapMaxCombo > 2500 ? Math.log10(beatmapMaxCombo / 2500.0) * 0.475 : 0.0);

        value *= lengthBonus * Math.pow(0.97, misses);

        // Combo scaling
        if (beatmapMaxCombo > 0)
            value *= Math.min(Math.pow(scoreMaxCombo, 0.8) / Math.pow(beatmapMaxCombo, 0.8), 1.0);


        double approachRateFactor = 1.0;
        if (ar > 9.0) approachRateFactor += 0.1 * (ar - 9.0); // 10% for each AR above 9
        if (ar > 10.0) approachRateFactor += 0.1 * (ar - 10.0); // Additional 10% at AR 11, 30% total
        else if (ar < 8.0) approachRateFactor += 0.025 * (8.0 - ar); // 2.5% for each AR below 8

        value *= approachRateFactor;

        if ((mods & 8) != 0) {
            value *= 1.05 + 0.075 * (10.0 - Math.min(10.0, ar)); // 7.5% for each AR below 10
            // Hiddens gives almost nothing on max approach rate, and more the lower it is
            if (ar <= 10.0) value *= 1.05 + 0.075 * (10.0 - ar); // 7.5% for each AR below 10
            else if (ar > 10.0) value *= 1.01 + 0.04 * (11.0 - Math.min(11.0, ar)); // 5% at AR 10, 1% at AR 11
        }

        if ((mods & 1024) != 0)
            // Apply length bonus again if flashlight is on simply because it becomes a lot harder on longer maps.
            value *= 1.35 * lengthBonus;

        // Scale the aim value with accuracy _slightly_
        value *= Math.pow(accuracy, 5.5);

        // Custom multipliers for NoFail. SpunOut is not applicable.
        if ((mods & 1) != 0)
            value *= 0.90;

        return value;
    }

}
