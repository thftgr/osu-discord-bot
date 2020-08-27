package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.thftgr.discord.Main;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    public JsonArray call(String url, String Parameters) {
        JsonArray userInfoJsonArray = (JsonArray) JsonParser.parseString("[]");

        try {
            String Parm = "k=" + Main.settingValue.get("osu!").getAsJsonObject().get("apiKey").getAsString() + Parameters;
            System.out.println("https://osu.ppy.sh/api/" + url + "?" + Parameters);
            callOkhttp("https://osu.ppy.sh/api/" + url + "?" + Parm);
            HttpURLConnection httpURLConnection = connectServer("https://osu.ppy.sh/api/" + url + "?" + Parm);
            httpURLConnection.setConnectTimeout(5000);
            String tmp = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine();
            userInfoJsonArray = (JsonArray) JsonParser.parseString(tmp);
            httpURLConnection.disconnect();
        } catch (Exception e) {
            System.out.println("API().call() " + e.getMessage());
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

    public void callOkhttp(String url) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            System.out.println(response.body().string());

        } catch (Exception e) {

        }

    }


}
