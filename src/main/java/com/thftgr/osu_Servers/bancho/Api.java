package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.thftgr.discord.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public JsonArray call(String url, String Parameters) {

        String Parm = "k=" + Main.settingValue.get("token.osu!").getAsString() + Parameters;
        HttpURLConnection httpURLConnection = connectServer("https://osu.ppy.sh/api/" + url + "?" + Parm);

        try {
            String s = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine();

            if (!s.equals("[]")) return (JsonArray) JsonParser.parseString(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
