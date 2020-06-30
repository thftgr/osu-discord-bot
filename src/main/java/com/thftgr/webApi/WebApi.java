package com.thftgr.webApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.thftgr.discord.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class WebApi {
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


    public void download(String sourceUrl, String targetFilename) {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String pathAndFileName = Main.settingValue.get("downloadPath").getAsString() + targetFilename;


        try {

            fileOutputStream = new FileOutputStream(pathAndFileName + ".tmp");

            inputStream = new URL(sourceUrl).openConnection().getInputStream();
            //인풋스트림을 전달
            byte[] buffer = new byte[8192];
            int readBytes;
            while ((readBytes = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, readBytes);

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {

            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }


                if (inputStream != null) {
                    inputStream.close();
                }

                File f = new File(pathAndFileName);

                if (f.isFile()) f.delete();

                File file = new File(pathAndFileName + ".tmp");
                if (file.exists()) file.renameTo(new File(pathAndFileName));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBloodcatPriviewLink(String ID) {
        try {
            URLConnection con = new URL("https://bloodcat.com/osu/b/" + ID).openConnection();

            Map<String, List<String>> map = con.getHeaderFields();
            List<String> contentLength = map.get("Content-Disposition");
            if (contentLength != null) {
                return "https://bloodcat.com/osu/preview.html#" + ID;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
