package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.thftgr.discord.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.TimeZone;

public class Api {

    public JsonArray call(String url, String Parameters) {

        String Parm = "k=" + Main.settingValue.get("osu!").getAsJsonObject().get("apiKey").getAsString() + Parameters;
        HttpURLConnection httpURLConnection = connectServer("https://osu.ppy.sh/api/" + url + "?" + Parm);
        JsonArray userInfoJsonArray = (JsonArray) JsonParser.parseString("[]");
        try {
            userInfoJsonArray = (JsonArray) JsonParser.parseString(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return userInfoJsonArray;
    }

    HttpURLConnection connectServer(String urlAndParm) {

        try {
            HttpURLConnection req = (HttpURLConnection) new URL(urlAndParm).openConnection();
            req.setRequestMethod("GET");
            req.setRequestProperty("Content-Type", "application/json; utf-8");
            req.setRequestProperty("Accept", "application/json");
            return req;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }





}
