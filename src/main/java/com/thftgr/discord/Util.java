package com.thftgr.discord;

import com.google.gson.JsonArray;

import java.io.*;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class Util {

    String grabUsername(String str) {

        int lineCnt = 0;
        int fromIndex = -1;
        while ((fromIndex = str.indexOf("\"", fromIndex + 1)) >= 0) {
            lineCnt++;
        }
        System.out.println(lineCnt);
        if (lineCnt == 2) {
            String[] array = str.split("\"");
            System.out.println("1:" + array[1]);
            return array[1];
        } else {
            String[] array = str.split(" ");
            System.out.println("2:" + array[1]);
            try {
                return URLEncoder.encode(array[1], "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return array[1];
            }
        }
    }

    JsonArray sortJsonarray(JsonArray jsonArray) {
        String[] diff = new String[jsonArray.size()];
        JsonArray jaa = new JsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            diff[i] = jsonArray.get(i).getAsJsonObject().get("difficultyrating").getAsString();
        }

        Arrays.sort(diff);
        for (int i = 0; i < jsonArray.size(); i++) {
            for (int j = 0; j < jsonArray.size(); j++) {
                if (diff[i].equals(jsonArray.get(j).getAsJsonObject().get("difficultyrating").getAsString())) {
                    jaa.add(jsonArray.get(j).getAsJsonObject());
                }
            }
        }
        return jaa;


    }

    String spacedString(String str) {

        int lineCnt = 0;
        int fromIndex = -1;
        while ((fromIndex = str.indexOf("\"", fromIndex + 1)) >= 0) {
            lineCnt++;
        }
        System.out.println(lineCnt);
        if (lineCnt == 2) {
            String[] array = str.split("\"");
            System.out.println("1:" + array[1]);
            return array[1];
        } else {
            String[] array = str.split(" ");
            System.out.println("2:" + array[1]);
            return array[1];

        }
    }

    public Boolean isCommand(String s){
        if(s.startsWith(Main.settingValue.get("commandStartWith").getAsString())) return true;
        else return false;

    }



}
