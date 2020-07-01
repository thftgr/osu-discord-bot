package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;

import java.sql.Time;
import java.util.Date;
import java.util.TimeZone;

public class NewRankedMapWatcher {

    public void NewRankedMapWatcher() {
        long delaytime = System.currentTimeMillis() + 60000;
        while (true) {
            if (!(delaytime + 60000 < System.currentTimeMillis())) continue;
            //60초에 1번씩 api 불러서 추가된 맵 print
            String parm = "&since=" + GetUTCtime(60000);
            JsonArray newRankedBeatmaps = new Api().call("get_beatmaps", parm);
            if(!newRankedBeatmaps.equals("[]")) {

            }

            delaytime = System.currentTimeMillis();
        }
    }

    String GetUTCtime(long offset) {
        TimeZone def = TimeZone.getDefault();

        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        String dateTime = new java.sql.Date(new Date().getTime()).toString() + " " + new Time(new Date().getTime() - offset).toString();
        TimeZone.setDefault(def);
        return dateTime;
    }













}
