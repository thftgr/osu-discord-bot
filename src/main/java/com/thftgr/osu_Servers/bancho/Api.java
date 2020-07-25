package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.thftgr.discord.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Api {

    public JsonArray call(String url, String Parameters) {
        JsonArray userInfoJsonArray = (JsonArray) JsonParser.parseString("[]");

        try {
            String Parm = "k=" + Main.settingValue.get("osu!").getAsJsonObject().get("apiKey").getAsString() + Parameters;
//            System.out.println("https://osu.ppy.sh/api/" + url + "?" + Parm);
            System.out.println("https://osu.ppy.sh/api/" + url + "?" + Parm);
            HttpURLConnection httpURLConnection = connectServer("https://osu.ppy.sh/api/" + url + "?" + Parm);
            httpURLConnection.setConnectTimeout(5000);
//            System.out.println(httpURLConnection.getConnectTimeout());
            String tmp = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine();
//            System.out.println(tmp);
            userInfoJsonArray = (JsonArray) JsonParser.parseString(tmp);
            httpURLConnection.disconnect();
        } catch (Exception e) {
            System.out.println("API().call() "+e.getMessage());
        }
        return userInfoJsonArray;
    }

    HttpURLConnection connectServer(String urlAndParm) throws Exception {
        HttpURLConnection req = (HttpURLConnection) new URL(urlAndParm).openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "application/json; utf-8");
        req.setRequestProperty("Accept", "application/json");
        return req;
    }


}
