package com.thftgr.osuApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thftgr.discord.Main;
import com.thftgr.discord.ThreadRun;
import com.thftgr.webApi.WebApi;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class BeatmapDownloader {

    public void downloadBeatmap(MessageChannel channel, int mapStart, int maplast) {


        if (maplast == 0) {
            download(channel, mapStart);
        } else if (maplast - mapStart < 24) {
            downloads(channel, mapStart, maplast);
        } else {
            downloadMultiThread(channel, mapStart, maplast);
        }
    }


    void download(MessageChannel channel, int mapSetId) {
        JsonArray beatmapSetJsonArray;
        String beatmapDownloadUrl;
        if ((beatmapSetJsonArray = new WebApi().call("get_beatmaps", "&s=" + mapSetId)).isJsonNull()) return;
        if ((beatmapDownloadUrl = getBeatmapSetDownloadLink(beatmapSetJsonArray)) == null) return;
        new WebApi().download(beatmapDownloadUrl, getBeatmapFilename(beatmapSetJsonArray));
        channel.sendMessage("download Finished. : " + mapSetId).queue();
    }

    public void downloads(MessageChannel channel, int mapStart, int maplast) {
        for (int i = mapStart - 1; i < maplast; i++) {
            JsonArray beatmapSetJsonArray = new WebApi().call("get_beatmaps", "&s=" + i);
            String beatmapDownloadUrl;

            //비트맵 존재여부

            if (beatmapSetJsonArray == null) continue;

            //비트맵 다운로드 가능여부
            if (mapIsGraveyard(beatmapSetJsonArray)) continue;

            if ((beatmapDownloadUrl = getBeatmapSetDownloadLink(beatmapSetJsonArray)) == null) continue;

            //System.out.println(i + " : " + beatmapDownloadUrl);

            new WebApi().download(beatmapDownloadUrl, getBeatmapFilename(beatmapSetJsonArray));
        }
        channel.sendMessage("download Finished. " + mapStart + ":" + maplast + ":" + (maplast - mapStart)).queue();
        //System.out.println(mapStart + ":" + maplast + ":" + (maplast - mapStart));
    }

    void downloadMultiThread(MessageChannel channel, int mapStart, int maplast) {

        int divv = ((maplast - mapStart) / Main.downloadThread);
        int sets = mapStart;

        while ((maplast - sets) > divv) {
            new Thread(new ThreadRun.beatmapDownloadRun(channel, sets, ((sets + divv) - 1))).start();
            channel.sendMessage("download started : " +sets + " ~ " + ((sets + divv) - 1)).queue();

            sets += divv;
        }
        if ((maplast - sets) != 0) {
            new Thread(new ThreadRun.beatmapDownloadRun(channel, sets, maplast)).start();
            channel.sendMessage("download started : " +sets + " ~ " + maplast).queue();
            System.out.println(sets + " : " + maplast + ":" + (maplast - sets));
        }


    }


    boolean mapIsGraveyard(JsonArray beatmapSetJsonArray) {
        if(beatmapSetJsonArray.get(0).getAsJsonObject().get("approved").isJsonNull()) return false;
        //System.out.println(  beatmapSetJsonArray.get(0).getAsJsonObject().get("beatmapset_id").getAsInt()+ " : "+beatmapSetJsonArray.get(0).getAsJsonObject().get("approved").getAsInt());
        return beatmapSetJsonArray.get(0).getAsJsonObject().get("approved").getAsInt() == -2;
    }


    String getBeatmapSetDownloadLink(JsonArray mapSetJsonArray) {
        String h = Main.settingValue.get("token.osu!direct").getAsString();
        try {
            //System.out.println(mapSetJsonArray.toString());
            String parmeters = mapSetJsonArray.get(0).getAsJsonObject().get("beatmapset_id").getAsString() + "?u=" + Main.settingValue.get("irc.username").getAsString() + "&h=" + h;

            URLConnection con = new URL("https://osu.ppy.sh/d/" + parmeters).openConnection();
            Map<String, List<String>> map = con.getHeaderFields();
            List<String> contentLength = map.get("Location");

            if (contentLength != null) {
                return contentLength.toString().substring(1, contentLength.toString().length() - 1);
            }
        } catch (Exception e) {
            System.out.println("getBeatmapSetDownloadLink : "+e.getMessage());
            return null;
        }
        return null;

    }

    String getBeatmapFilename(JsonArray mapSetJsonArray) {
        JsonObject beatMap = mapSetJsonArray.get(0).getAsJsonObject();
        String str = beatMap.get("beatmapset_id").getAsString() + " " + beatMap.get("artist").getAsString() + " - " + beatMap.get("title").getAsString() + ".osz";
        str = str
                .replaceAll("\\\\", "")
                .replaceAll("/", "")
                .replaceAll(":", "")
                .replaceAll("[*]", "")
                .replaceAll("[?]", "")
                .replaceAll("\"", "")
                .replaceAll("<", "")
                .replaceAll(">", "")
                .replaceAll("[|]", "");

        return str;

    }


}


