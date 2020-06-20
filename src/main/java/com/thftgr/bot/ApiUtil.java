package com.thftgr.bot;

import com.google.gson.JsonArray;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

public class ApiUtil {

    String getBeatmapDownloadLink(String ID) {
        String h = Main.settingValue.get("token.osu!direct").getAsString();
        try {
            JsonArray JA = new osuApiCall().call("get_beatmaps", "&s=" + ID);
            if (JA == null) {
                return null;
            }

            String parmeters = JA.get(0).getAsJsonObject().get("beatmapset_id").getAsString() + "?u=-osu-" + "&h=" + h;
            URLConnection con = new URL("https://osu.ppy.sh/d/" + parmeters).openConnection();

            Map<String, List<String>> map = con.getHeaderFields();
            List<String> contentLength = map.get("Location");

            if (contentLength != null) {
                return contentLength.toString().substring(1, contentLength.toString().length() - 1);
            }

        } catch (Exception e) {
            System.out.println(e);

        }
        return null;

    }

    String getBloodcatPriviewLink(String ID) {
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

    String getBeatmapFilename(String downloadLink, boolean enc) {
        String str;
        if (enc) {

            try {
                str = URLDecoder.decode(downloadLink, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.toString());
                return null;
            }
            int target_num = str.indexOf("?") + 4;
            str = str.substring(target_num, (str.substring(target_num).indexOf("&fd") + target_num));

        } else {
            int target_num = downloadLink.indexOf("?") + 4;
            str = downloadLink.substring(target_num, (downloadLink.substring(target_num).indexOf("&fd") + target_num));
        }
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

    Boolean getBeatmapDownload(String map) {

        String md = getBeatmapDownloadLink(map);

        if (md == null) return false;

        new Util().download(md, getBeatmapFilename(md, true));

        return true;

    }

    void getBeatmapDownload(MessageChannel channel, String mapStart, String maplast) {
        if (maplast == null) {
            String md = getBeatmapDownloadLink(mapStart);
            if (md != null) {

                String fn = getBeatmapFilename(md, true);
                channel.sendMessage("downloading....").queue();
                new Util().download(md, fn);
                new Message().sayEmbed(channel, "download finished : \n" + fn + "\n[thftgr](https://www.xiiov.com/d/" + getBeatmapFilename(md, false) + ")");

            } else {
                channel.sendMessage("> 😢 BeatmapDecoder Not Found!" + mapStart).queue();
            }
        } else {
            int start = Integer.parseInt(mapStart);
            int end = Integer.parseInt(maplast);

            new Message().sayEmbed(channel, "start " + mapStart + " ~ " + maplast);
            for (int i = 0; (end) >= (start + i); i++) {
                System.out.println("map : " + (start + i));
                String md = getBeatmapDownloadLink(Integer.toString(start + i));


                //맵이 최신인가?
                // 무덤맵 다운로드 수량이 남아있는가.


                if (md != null) {
                    String fn = getBeatmapFilename(md, true);
                    new Util().download(md, fn);
                }
            }

            new Message().sayEmbed(channel, "finished " + mapStart + " ~ " + maplast);
        }
    }
}
