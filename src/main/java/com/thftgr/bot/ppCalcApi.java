package com.thftgr.bot;

import javafx.scene.control.Spinner;

import javax.swing.text.Position;

public class ppCalcApi {
    public double aimStars;
    public double speedStars;
    public int mods;
    public int totalHits;
    public int beatmapMaxCombo;
    public int scoreMaxCombo;
    public double ar, od;
    public int n300, n100, n50, nMiss;
    public int countHitCircles;
    public double accuracy;


    public double osuPPCalculate() {

        totalHits = totalHits();

        double multiplier = 1.12; // This is being adjusted to keep the final pp value scaled around what it used to be when changing things

        if ((mods & 1) == 1) multiplier *= 0.90;

        if ((mods & 4096) == 4096) multiplier *= 0.95;

        double aimValue = computeAimValue();
        double speedValue = computeSpeedValue();
        double accuracyValue = computeAccuracyValue();

        return Math.pow(Math.pow(aimValue, 1.1) + Math.pow(speedValue, 1.1) + Math.pow(accuracyValue, 1.1), 1.0 / 1.1) * multiplier;


    }

    double computeAimValue() {
        //터치스크린
        aimStars = ((mods & 4) != 0) ? Math.pow(aimStars, 0.8) : aimStars;


        double aimValue = Math.pow(5.0 * Math.max(1.0, aimStars / 0.0675) - 4.0, 3.0) / 100000.0;

        // Longer maps are worth more
        double lengthBonus = 0.95 + 0.4 * Math.min(1.0, totalHits / 2000.0) +
                (totalHits > 2000 ? Math.log10(totalHits / 2000.0) * 0.5 : 0.0);


        aimValue *= lengthBonus;

        // Penalize misses exponentially. This mainly fixes tag4 maps and the likes until a per-hitobject solution is available
        aimValue *= Math.pow(0.97, nMiss);
        // Combo scaling
        if (beatmapMaxCombo > 0)
            aimValue *= Math.min((Math.pow(scoreMaxCombo, 0.8) / Math.pow(beatmapMaxCombo, 0.8)), 1.0);

        double approachRateFactor = 1.0;
        if (ar > 10.33) {

            approachRateFactor += 0.3 * (ar - 10.33);

        } else if (ar < 8.0) {

            approachRateFactor += 0.01 * (8.0 - ar);

        }

        aimValue *= approachRateFactor;

        //++ aim: 21.161042290759895
        // We want to give more reward for lower AR when it comes to aim and HD. This nerfs high AR and buffs lower AR.
        if ((mods & 8) != 0)
            aimValue *= 1.0 + 0.04 * (12.0 - ar);

        if ((mods & 1024) != 0) {
            // Apply object-based bonus for flashlight.
            aimValue *= 1.0 + 0.35 * Math.min(1.0, totalHits / 200.0) +
                    (totalHits > 200
                            ? 0.3 * Math.min(1.0, (totalHits - 200) / 300.0) +
                            (totalHits > 500 ? (totalHits - 500) / 1200.0 : 0.0)
                            : 0.0);
        }

        // Scale the aim value with accuracy _slightly_
        aimValue *= 0.5 + accuracy / 2.0;
        // It is important to also consider accuracy difficulty when doing that
        aimValue *= 0.98 + Math.pow(od, 2) / 2500;

        return aimValue;
    }

    double computeSpeedValue() {

        double speedValue = Math.pow(5.0 * Math.max(1.0, speedStars / 0.0675) - 4.0, 3.0) / 100000.0;

        // Longer maps are worth more
        speedValue *= 0.95 + 0.4 * Math.min(1.0, totalHits / 2000.0) +
                (totalHits > 2000 ? Math.log10(totalHits / 2000.0) * 0.5 : 0.0);

        // Penalize misses exponentially. This mainly fixes tag4 maps and the likes until a per-hitobject solution is available
        speedValue *= Math.pow(0.97, nMiss);

        // Combo scaling
        if (beatmapMaxCombo > 0)
            speedValue *= Math.min(Math.pow(scoreMaxCombo, 0.8) / Math.pow(beatmapMaxCombo, 0.8), 1.0);

        double approachRateFactor = 1.0;
        if (ar > 10.33)
            approachRateFactor += 0.3 * (ar - 10.33);

        speedValue *= approachRateFactor;

        if ((mods & 8) == 8)
            speedValue *= 1.0 + 0.04 * (12.0 - ar);

        // Scale the speed value with accuracy _slightly_
        speedValue *= 0.02 + accuracy;
        // It is important to also consider accuracy difficulty when doing that
        speedValue *= 0.96 + Math.pow(od, 2) / 1600;

        return speedValue;
    }

    double computeAccuracyValue() {
        // This percentage only considers HitCircles of any value - in this part of the calculation we focus on hitting the timing hit window
        double betterAccuracyPercentage;
        int amountHitObjectsWithAccuracy = countHitCircles;

        if (amountHitObjectsWithAccuracy > 0)
            betterAccuracyPercentage = ((n300 - (totalHits() - amountHitObjectsWithAccuracy)) * 6 + n100 * 2 + n50) / (double) (amountHitObjectsWithAccuracy * 6);
        else
            betterAccuracyPercentage = 0;

        // It is possible to reach a negative accuracy with this formula. Cap it at zero - zero points
        if (betterAccuracyPercentage < 0)
            betterAccuracyPercentage = 0;

        // Lots of arbitrary values from testing.
        // Considering to use derivation from perfect accuracy in a probabilistic manner - assume normal distribution
        double accuracyValue = Math.pow(1.52163, od) * Math.pow(betterAccuracyPercentage, 24) * 2.83;

        // Bonus for many hitcircles - it's harder to keep good accuracy up for longer
        accuracyValue *= Math.min(1.15, Math.pow(amountHitObjectsWithAccuracy / 1000.0, 0.3));

        if ((mods & 8) != 0)
            accuracyValue *= 1.08;
        if ((mods & 1024) != 0)
            accuracyValue *= 1.02;

        return accuracyValue;
    }

    private int totalHits() {
        return n300 + n100 + n50 + nMiss;
    }





}
