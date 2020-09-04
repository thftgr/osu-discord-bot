package com.thftgr;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.FileReader;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class test {
    static JsonArray proxyList = new JsonArray();
    static long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        try {
            proxyList = (JsonArray) JsonParser.parseReader(new Gson().newJsonReader(new FileReader("setting/proxy_list.json")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        int dataCount = 0;
        long timetmp = 0;
        while (true) {
            for (int i = 0; i < proxyList.size(); i++) {
                timetmp = System.currentTimeMillis();
                new test().setProxy(proxyList.get(i).getAsJsonObject());
                System.out.println("=====================================");
                System.out.println(System.getProperty("https.proxyHost"));
                try {
                    Thread.sleep(200);
                    if (new test().generator("7e2757b7-badc-4303-8cd0-2eefa9d78e3b")) {
                        dataCount += 1;
                        System.out.println("generated data : " + dataCount + "GB");
                    } else {
                        System.out.println("generate fail. | data : " + dataCount + "GB");
                    }
                } catch (Exception e) {
                    System.out.println("generate fail. | data : " + dataCount + "GB");
                }
                System.out.println((System.currentTimeMillis() - timetmp) + "ms\n");
                System.out.println(((System.currentTimeMillis() - startTime)/dataCount) + "Ms/GB\n");
            }
        }
    }

    String rand(int length, boolean intOnly) {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            int rIndex = rnd.nextInt(3);
            if (intOnly) rIndex = 2;
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();

    }

    String iosdate() {
        String iostime = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        return iostime.substring(0, iostime.length() - 1) + "+09:00";
    }

    boolean generator(String referrer_id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String installId = new test().rand(11, false);

        JsonObject data = new JsonObject();
        data.addProperty("key", (new test().rand(43, false) + "="));
        data.addProperty("install_id", installId);
        data.addProperty("fcm_token", installId + ":APA91b" + new test().rand(134, false));
        data.addProperty("referrer", referrer_id);
        data.addProperty("warp_enabled", false);
        data.addProperty("tos", new test().iosdate());
        data.addProperty("type", "Android");
        data.addProperty("locale", "ko-KR");

        Request request = new Request.Builder()
                .url("https://api.cloudflareclient.com/v0a" + new test().rand(3, true) + "/reg")
                .addHeader("'Content-Type'", " 'application/json; charset=UTF-8',")
                .addHeader("'Host'", " 'api.cloudflareclient.com',")
                .addHeader("'Connection'", " 'Keep-Alive',")
                .addHeader("'Accept-Encoding'", " 'gzip',")
                .addHeader("'User-Agent'", " 'okhttp/3.12.1'")
                .post(RequestBody.create(MediaType.parse("application/json"), data.toString()))
                .build();

        Response response = client.newCall(request).execute();
        JsonObject resp = (JsonObject) JsonParser.parseString(response.body().string());

        client.connectionPool().evictAll();
        System.out.println("status code : " + response.code());
        return !resp.get("referrer").isJsonNull();
    }

    JsonArray updateProxyList(String url) {
        JsonArray list = new JsonArray();
        /*
            call rest api
            parse string data to json array
                [
                    {"ip":"String","port":"int"},
                    {"ip":"String","port":"int"},
                    {"ip":"String","port":"int"},
                ]
         */
        return list;
    }


    void setProxy(JsonObject ip_port) {
        System.setProperty("https.proxyHost", ip_port.get("ip").getAsString());
        System.setProperty("https.proxyPort", ip_port.get("port").getAsString());
    }


}
