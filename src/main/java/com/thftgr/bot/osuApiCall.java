package com.thftgr.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class osuApiCall {
    JsonArray call(String url, String Parameters) {
        String Parm = "k=" + Main.settingValue.get("token.osu!").getAsString() + Parameters;
        Parm += "&a=0";
        HttpURLConnection httpURLConnection = connectServer("https://osu.ppy.sh/api/" + url + "?" + Parm);
        try {
            String s = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine();
            if (!s.equals("[]")) {
                return (JsonArray) JsonParser.parseString(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    HttpURLConnection connectServer(String urlAndParm) {

        try {
            HttpURLConnection h = (HttpURLConnection) new URL(urlAndParm).openConnection();
            h.setRequestMethod("GET");
            h.setRequestProperty("Content-Type", "application/json; utf-8");
            h.setRequestProperty("Accept", "application/json");
            return h;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



}
