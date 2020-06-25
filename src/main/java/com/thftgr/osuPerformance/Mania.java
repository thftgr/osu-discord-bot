package com.thftgr.osuPerformance;

import com.google.gson.JsonObject;

public class Mania {
    int mods = 0;
    int scaledScore = 1000000;
    double difficultyrating;
    int countPerfect;
    int countGreat = 0;      // (HitResult.Great);
    int countGood = 0;       // (HitResult.Good);
    int countOk = 0;         // (HitResult.Ok);
    int countMeh = 0;        // (HitResult.Meh);
    int countMiss = 0;       // (HitResult.Miss);
    int totalHits;


    public double Calculate(JsonObject map) {
        mods = 0;
        scaledScore = 1000000;
        difficultyrating = map.get("difficultyrating").getAsDouble();

        countPerfect = map.get("count_normal").getAsInt()+map.get("count_slider").getAsInt();    // (HitResult.Perfect);
        countGreat = 0;      // (HitResult.Great);
        countGood = 0;       // (HitResult.Good);
        countOk = 0;         // (HitResult.Ok);
        countMeh = 0;        // (HitResult.Meh);
        countMiss = 0;       // (HitResult.Miss);




        totalHits = totalHits();


        double scoreMultiplier = 1.0;
        //for 문과 동일 .length()로 길이 보고 출력하는것으로 구현 가능
        //모드가 여러개 있을수 있으니 여러번 돌리는것 같음
        //모드별 배율인데 논모드만 할거라 아직은 필요없음
//            foreach(var m in mods.Where(m = > !scoreIncreaseMods.Contains(m)))
//            scoreMultiplier *= m.ScoreMultiplier;

        // Scale score up, so it's comparable to other keymods
        scaledScore *= 1.0 / scoreMultiplier;

        // Arbitrary initial value for scaling pp in order to standardize distributions across game modes.
        // The specific number has no intrinsic meaning and can be adjusted as needed.
        double multiplier = 0.8;

        if ((mods & 1) != 0) multiplier *= 0.9;
        if ((mods & 2) != 0) multiplier *= 0.5;

        double strainValue = computeStrainValue();
        double accValue = computeAccuracyValue(strainValue);
        return Math.pow(Math.pow(strainValue, 1.1) + Math.pow(accValue, 1.1), 1.0 / 1.1) * multiplier;

    }

    private double computeStrainValue() {
        // Obtain strain difficulty
        double strainValue = Math.pow(5 * Math.max(1, difficultyrating / 0.2) - 4.0, 2.2) / 135.0;

        // Longer maps are worth more
        strainValue *= 1.0 + 0.1 * Math.min(1.0, totalHits / 1500.0);

        if (scaledScore <= 500000)
            strainValue = 0;
        else if (scaledScore <= 600000)
            strainValue *= (scaledScore - 500000) / 100000 * 0.3;
        else if (scaledScore <= 700000)
            strainValue *= 0.3 + (scaledScore - 600000) / 100000 * 0.25;
        else if (scaledScore <= 800000)
            strainValue *= 0.55 + (scaledScore - 700000) / 100000 * 0.20;
        else if (scaledScore <= 900000)
            strainValue *= 0.75 + (scaledScore - 800000) / 100000 * 0.15;
        else
            strainValue *= 0.90 + (scaledScore - 900000) / 100000 * 0.1;

        return strainValue;
    }

    private double computeAccuracyValue(double strainValue) {

        // Lots of arbitrary values from testing.
        // Considering to use derivation from perfect accuracy in a probabilistic manner - assume normal distribution
        double accuracyValue = Math.max(0.0, 0.2 - (40 - 34) * 0.006667)
                * strainValue
                * Math.pow(Math.max(0.0, scaledScore - 960000) / 40000, 1.1);

        // Bonus for many hitcircles - it's harder to keep good accuracy up for longer
        // accuracyValue *= Math.min(1.15, Math.pow(totalHits / 1500.0, 0.3));

        return accuracyValue;
    }

    int totalHits() {
        return countPerfect + countOk + countGreat + countGood + countMeh + countMiss;
    }

}
