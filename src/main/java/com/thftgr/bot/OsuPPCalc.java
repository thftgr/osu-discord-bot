package com.thftgr.bot;

public class OsuPPCalc {
    //all
    public int mods;
    public int beatmapMaxCombo;
    public int scoreMaxCombo;
    public double ar;
    //std
    public double od;
    public int n300, n100, n50, nMiss;
    public int countHitCircles;
    public double accuracy;
    public double aimStars;
    public double speedStars;
    public int totalHits;
    //catch
    public double difficultyrating;     //stars
    public int tinyTicksHit;            //SmallTickHit
    public int ticksHit;                //LargeTickHit
    public int fruitsHit;               //Perfect
    public int misses;                  //Miss
    public int tinyTicksMissed;         //SmallTickMiss
    //mania
    public double scaledScore;  // 10000000
    public int countPerfect;    // (HitResult.Perfect);
    public int countGreat;      // (HitResult.Great);
    public int countGood;       // (HitResult.Good);
    public int countOk;         // (HitResult.Ok);
    public int countMeh;        // (HitResult.Meh);
    public int countMiss;       // (HitResult.Miss);

    public double osuPPCalculate() {
        return new STD().Calculate();

    }

    public double catchPPCalculate() {
        return new Catch().Calculate();
    }

    public double maniaPPCalculate(){
        return new Mania().Calculate();
    }

//    public static class mod {
//        public int None = 0;
//        public int NoFail = 1;
//        public int Easy = 2;
//        public int TouchDevice = 4;
//        public int Hidden = 8;
//        public int HardRock = 16;
//        public int SuddenDeath = 32;
//        public int DoubleTime = 64;
//        public int Relax = 128;
//        public int HalfTime = 256;
//        public int Nightcore = 512; // Only set along with DoubleTime. i.e: NC only gives 576
//        public int Flashlight = 1024;
//        public int Autoplay = 2048;
//        public int SpunOut = 4096;
//        public int Relax2 = 8192;    // Autopilot
//        public int Perfect = 16384; // Only set along with SuddenDeath. i.e: PF only gives 16416
//        public int Key4 = 32768;
//        public int Key5 = 65536;
//        public int Key6 = 131072;
//        public int Key7 = 262144;
//        public int Key8 = 524288;
//        public int FadeIn = 1048576;
//        public int Random = 2097152;
//        public int Cinema = 4194304;
//        public int Target = 8388608;
//        public int Key9 = 16777216;
//        public int KeyCoop = 33554432;
//        public int Key1 = 67108864;
//        public int Key3 = 134217728;
//        public int Key2 = 268435456;
//        public int ScoreV2 = 536870912;
//        public int Mirror = 1073741824;
//        public int KeyMod = Key1 | Key2 | Key3 | Key4 | Key5 | Key6 | Key7 | Key8 | Key9 | KeyCoop;
//        public int FreeModAllowed = NoFail | Easy | Hidden | HardRock | SuddenDeath | Flashlight | FadeIn | Relax | Relax2 | SpunOut | KeyMod;
//        public int ScoreIncreaseMods = Hidden | HardRock | DoubleTime | Flashlight | FadeIn;
//
//
//    }

    class STD {
        double Calculate(){
            totalHits = new STD().totalHits();

            double multiplier = 1.12; // This is being adjusted to keep the final pp value scaled around what it used to be when changing things

            if ((mods & 1) != 0) multiplier *= 0.90;
            if ((mods & 4096) != 0) multiplier *= 0.95;

            double aimValue = new STD().computeAimValue();
            double speedValue = new STD().computeSpeedValue();
            double accuracyValue = new STD().computeAccuracyValue();

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

        int totalHits() {
            return n300 + n100 + n50 + nMiss;
        }
    }

    class Catch {
        double Calculate() {


            // We are heavily relying on aim in catch the beat
            double value = Math.pow(5.0 * Math.max(1.0, difficultyrating / 0.0049) - 4.0, 2.0) / 100000.0;

            // Longer maps are worth more. "Longer" means how many hits there are which can contribute to combo
            int numTotalHits = totalComboHits();

            // Longer maps are worth more
            double lengthBonus =
                    0.95 + 0.3 * Math.min(1.0, numTotalHits / 2500.0) +
                            (numTotalHits > 2500 ? Math.log10(numTotalHits / 2500.0) * 0.475 : 0.0);

            // Longer maps are worth more
            value *= lengthBonus;

            // Penalize misses exponentially. This mainly fixes tag4 maps and the likes until a per-hitobject solution is available
            value *= Math.pow(0.97, misses);

            // Combo scaling
            if (beatmapMaxCombo > 0)
                value *= Math.min(Math.pow(scoreMaxCombo, 0.8) / Math.pow(beatmapMaxCombo, 0.8), 1.0);

            double approachRate = ar;
            double approachRateFactor = 1.0;
            if (approachRate > 9.0)
                approachRateFactor += 0.1 * (approachRate - 9.0); // 10% for each AR above 9
            if (approachRate > 10.0)
                approachRateFactor += 0.1 * (approachRate - 10.0); // Additional 10% at AR 11, 30% total
            else if (approachRate < 8.0)
                approachRateFactor += 0.025 * (8.0 - approachRate); // 2.5% for each AR below 8

            value *= approachRateFactor;

            if ((mods & 8) != 0) {
                value *= 1.05 + 0.075 * (10.0 - Math.min(10.0, ar)); // 7.5% for each AR below 10
                // Hiddens gives almost nothing on max approach rate, and more the lower it is
                if (approachRate <= 10.0)
                    value *= 1.05 + 0.075 * (10.0 - approachRate); // 7.5% for each AR below 10
                else if (approachRate > 10.0)
                    value *= 1.01 + 0.04 * (11.0 - Math.min(11.0, approachRate)); // 5% at AR 10, 1% at AR 11
            }

            if ((mods & 1024) != 0)
                // Apply length bonus again if flashlight is on simply because it becomes a lot harder on longer maps.
                value *= 1.35 * lengthBonus;

            // Scale the aim value with accuracy _slightly_
            value *= Math.pow(accuracy(), 5.5);

            // Custom multipliers for NoFail. SpunOut is not applicable.
            if ((mods & 1) != 0)
                value *= 0.90;

            return value;
        }


        double accuracy() {
            return totalHits() == 0 ? 0 : Clamp((double) totalSuccessfulHits() / totalHits(), 0, 1);
        }

        int totalHits() {
            return tinyTicksHit + ticksHit + fruitsHit + misses + tinyTicksMissed;
        }

        int totalSuccessfulHits() {
            return tinyTicksHit + ticksHit + fruitsHit;
        }

        int totalComboHits() {
            return misses + ticksHit + fruitsHit;
        }

        double Clamp(double value, double min, double max) {
            return Math.max(max, Math.min(min, value));
        }

    }

    class Mania {
        double Calculate() {
            totalHits = new Mania().totalHits();

            int scoreIncreaseMods = 8 | 16 | 64 | 1024 | 1048576;

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
            // accuracyValue *= Math.Min(1.15, Math.Pow(totalHits / 1500.0, 0.3));

            return accuracyValue;
        }

        int totalHits() {
            return countPerfect + countOk + countGreat + countGood + countMeh + countMiss;
        }
    }


}




