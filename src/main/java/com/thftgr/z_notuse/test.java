package com.thftgr.z_notuse;


import com.google.gson.JsonArray;
import com.thftgr.osu_Servers.bancho.Api;

import java.sql.Time;
import java.util.Date;

public class test {


    public static void main(String[] args) {
        System.out.println("statred");

        long delaytime = System.currentTimeMillis() + 5000;
        while (true) {
            if (!(delaytime + 5000 < System.currentTimeMillis())) continue;
            System.out.println("check");


            delaytime = System.currentTimeMillis();
        }
        //System.out.println();
    }


    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }






}









