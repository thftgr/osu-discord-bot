package com.thftgr.osuPerformance;

import com.google.gson.JsonObject;

public class Taiko {
    int mods = 0;
    double difficultyrating;
    int totalHits;
    int countMiss = 0;
    int beatmapMaxCombo;
    int misses = 0;
    int scoreMaxCombo;
    double od;
    int accuracy = 1;

    public double Calculate(JsonObject map) {
        mods = 0;
        difficultyrating = map.get("difficultyrating").getAsDouble();
        totalHits = map.get("count_normal").getAsInt();
        countMiss = 0;
        beatmapMaxCombo = map.get("count_normal").getAsInt();
        misses = 0;
        scoreMaxCombo = map.get("count_normal").getAsInt();
        od = map.get("diff_overall").getAsDouble();
        accuracy = 1;


        double multiplier = 1.1;

        if ((mods & 1) != 0) multiplier *= 0.90;

        if ((mods & 8) != 0) multiplier *= 1.10;

        double strainValue = computeStrainValue();
        double accuracyValue = computeAccuracyValue();
        double totalValue =
                Math.pow(Math.pow(strainValue, 1.1) + Math.pow(accuracyValue, 1.1), 1.0 / 1.1) * multiplier;


        return totalValue;
    }

    double computeStrainValue() {
        double strainValue = Math.pow(5.0 * Math.max(1.0, difficultyrating / 0.0075) - 4.0, 2.0) / 100000.0;


        double lengthBonus = 1 + 0.1 * Math.min(1.0, totalHits / 1500.0);
        strainValue *= lengthBonus;

        strainValue *= Math.pow(0.985, countMiss);

        if (beatmapMaxCombo > 0)
            strainValue *= Math.min(Math.pow(scoreMaxCombo, 0.5) / Math.pow(beatmapMaxCombo, 0.5), 1.0);

        if ((mods & 8) != 0) strainValue *= 1.025;

        if ((mods & 1024) != 0) strainValue *= 1.05 * lengthBonus;

        return strainValue * accuracy;
    }

    private double computeAccuracyValue() {
        double GreatHitWindow = DifficultyRange(od);
        if (GreatHitWindow <= 0)
            return 0;

        double accValue = Math.pow(150.0 / GreatHitWindow, 1.1) * Math.pow(accuracy, 15) * 22.0;

        return accValue * Math.min(1.15, Math.pow(totalHits / 1500.0, 0.3));
    }

    double DifficultyRange(double difficulty) {
        if (difficulty > 5)
            return 35 + (20 - 35) * (difficulty - 5) / 5;
        if (difficulty < 5)
            return 35 - (35 - 50) * (5 - difficulty) / 5;

        return 35;
    }
}
