package com.thftgr.z_notuse;


import com.thftgr.bot.OsuPPCalc;
import com.thftgr.bot.ppCalc;

public class test {


    public static void main(String[] args) {
        //new ppCalc().parseMods("hdhrdt");
        System.out.println(DifficultyRange(3));
    }
    static double DifficultyRange(double difficulty)
    {
        if (difficulty > 5)
            return 35 + (20 - 35) * (difficulty - 5) / 5;
        if (difficulty < 5)
            return 35 - (35 - 50) * (5 - difficulty) / 5;

        return 35;
    }


}






