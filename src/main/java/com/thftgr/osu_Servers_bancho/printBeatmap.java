package com.thftgr.osu_Servers_bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thftgr.webApi.WebApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.Arrays;

public class printBeatmap {


    public void beatmap(MessageChannel channel, String mapID, String mode) {

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(255, 255, 255));
        String parm = "&b=" + mapID;
        parm += mode == null ? "" : "&m=" + mode;
        JsonArray mapJsonArray = new Api().call("get_beatmaps", parm);

        if (mapJsonArray.size() == 0) {// 맵이 없는경우
            channel.sendMessage(embedBuilder.setDescription("😢 Beatmap Not Found! Check mapID").build()).queue();
            return;
        }

        JsonObject mapJsonObject = mapJsonArray.get(0).getAsJsonObject();

        //title setting
        String author = "[BEATMAP]\n";
        author += mapJsonObject.get("artist").isJsonNull() ? "" : mapJsonObject.get("artist").getAsString();
        author += " - ";
        author += mapJsonObject.get("title").isJsonNull() ? "" : mapJsonObject.get("title").getAsString();
        author += mapJsonObject.get("creator").isJsonNull() ? "" : "\nby " + mapJsonObject.get("creator").getAsString();
        embedBuilder.setAuthor(author, "https://osu.ppy.sh/beatmapsets/" + mapJsonObject.get("beatmapset_id").getAsString(), null);

        int totaltime = mapJsonObject.get("total_length").isJsonNull() ? 0 : mapJsonObject.get("total_length").getAsInt();

        String BloodcatLink = new WebApi().getBloodcatPriviewLink(mapJsonObject.get("beatmap_id").getAsString());
        JsonObject mapData = new com.thftgr.osuPerformance.PerfomanceMain().ppCalc(mapJsonArray);

        String msg = "";
        msg += "▸Download: ";
        msg += "[ [Bancho] ](https://osu.ppy.sh/beatmapsets/"+mapJsonObject.get("beatmapset_id").getAsString()+"/download)";
        msg += "  |  ";
        msg += "[ [BloodCat] ](https://bloodcat.com/osu/s/"+mapJsonObject.get("beatmapset_id").getAsString()+")";
        msg += "  |  ";
        msg += "[ [Nerina] ](https://nerina.pw/d/"+mapJsonObject.get("beatmapset_id").getAsString()+")\n";

        msg += BloodcatLink == null ? "" : "▸[Preview](" + BloodcatLink + ")\n";
        msg += mapJsonObject.get("mode").isJsonNull() ? "" : "▸Mode: " + modeParse(mapJsonObject.get("mode").getAsInt()) + "\n";
        msg += "▸PlayTime: " + (totaltime / 60) + "m " + (totaltime - ((totaltime / 60) * 60)) + "s ";
        msg += mapJsonObject.get("bpm").isJsonNull() ? "" : "▸BPM: " + mapJsonObject.get("bpm").getAsString() + "\n";
        msg += mapJsonObject.get("version").isJsonNull() ? "" : "▸Map Version: " + mapJsonObject.get("version").getAsString() + "\n";
        msg += mapJsonObject.get("difficultyrating").isJsonNull() ? "" : "▸Difficulty: " + String.format("%.2f", mapJsonObject.get("difficultyrating").getAsFloat()) + "★\n";
        msg += mapJsonObject.get("max_combo").isJsonNull() ? "" : "▸Max Combo: x" + mapJsonObject.get("max_combo").getAsString() + "\n";
        msg += mapJsonObject.get("diff_approach").isJsonNull() ? "" : "▸AR: " + mapJsonObject.get("diff_approach").getAsString() + " ";
        msg += mapJsonObject.get("diff_overall").isJsonNull() ? "" : "▸OD: " + mapJsonObject.get("diff_overall").getAsString() + " ";
        msg += mapJsonObject.get("diff_drain").isJsonNull() ? "" : "▸HP: " + mapJsonObject.get("diff_drain").getAsString() + " ";
        msg += mapJsonObject.get("diff_size").isJsonNull() ? "" : "▸CS: " + mapJsonObject.get("diff_size").getAsString() + " \n";
        msg += mapJsonObject.get("mode").getAsInt() == 3 ? "▸KEY " + mapJsonObject.get("diff_size").getAsString() + "k" : "";


        msg += "▸PP: " + mapData.get(mapJsonObject.get("version").getAsString()).getAsString();


        embedBuilder.setDescription(msg);
        embedBuilder.setImage("https://b.ppy.sh/thumb/" + mapJsonObject.get("beatmapset_id").getAsString() + "l.jpg");

        //String beatmap Last updated Data
        String beatMapLastDate;
        if (!mapJsonObject.get("approved_date").isJsonNull()) {
            beatMapLastDate = mapJsonObject.get("approved_date").getAsString();

        } else if (!mapJsonObject.get("last_update").isJsonNull()) {
            beatMapLastDate = mapJsonObject.get("last_update").getAsString();

        } else {
            beatMapLastDate = mapJsonObject.get("submit_date").getAsString();
        }

        //footer map info setting
        String footer = "❤  ";
        footer += mapJsonObject.get("favourite_count").isJsonNull() ? "" : mapJsonObject.get("favourite_count").getAsString();
        footer += "  |  ";
        footer += approvedStatus(mapJsonObject.get("approved").isJsonNull() ? 5 : mapJsonObject.get("approved").getAsInt());
        footer += "  |  ";
        footer += beatMapLastDate;
        footer += " UTC +0";
        embedBuilder.setFooter(footer);

        channel.sendMessage(embedBuilder.build()).queue();


    }

    public void beatMapSet(MessageChannel channel, String setID, String mode, String title) {

        String parm = "&s=" + setID;
        parm += mode == null ? "" : "&m=" + mode;
        JsonArray beatMapSetJsonArray = new Api().call("get_beatmaps", parm);


        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(255, 255, 255));
        if (beatMapSetJsonArray.size() == 0) {
            channel.sendMessage(embedBuilder.setDescription("😢 BeatmapSet Not Found! Check mapSetID").build()).queue();
            return;
        }
        JsonObject mapJsonObject = beatMapSetJsonArray.get(0).getAsJsonObject();

        beatMapSetJsonArray = sortJsonarray(beatMapSetJsonArray);

        //title setting
        String author = title == null ? "[BEATMAP SET]\n" : title + "\n";
        author += mapJsonObject.get("artist").isJsonNull() ? "" : mapJsonObject.get("artist").getAsString();
        author += " - ";
        author += mapJsonObject.get("title").isJsonNull() ? "" : mapJsonObject.get("title").getAsString();
        author += mapJsonObject.get("creator").isJsonNull() ? "" : "\nby " + mapJsonObject.get("creator").getAsString();
        embedBuilder.setAuthor(author, "https://osu.ppy.sh/beatmapsets/" + mapJsonObject.get("beatmapset_id").getAsString(), null);


        //Description setting
        String[] modeList = {"", "", "", "", ""};
        int playtime = avgPlaytime(beatMapSetJsonArray);
        String beatMapInfo = "";
        beatMapInfo += "▸Download: ";
        beatMapInfo += "[ [Bancho] ](https://osu.ppy.sh/beatmapsets/"+mapJsonObject.get("beatmapset_id").getAsString()+"/download)";
        beatMapInfo += "  |  ";
        beatMapInfo += "[ [BloodCat] ](https://bloodcat.com/osu/s/"+mapJsonObject.get("beatmapset_id").getAsString()+")";
        beatMapInfo += "  |  ";
        beatMapInfo += "[ [Nerina] ](https://nerina.pw/d/"+mapJsonObject.get("beatmapset_id").getAsString()+")\n";



        beatMapInfo += "▸PlayTime AVG : " + (playtime / 60) + "m " + (playtime - ((playtime / 60) * 60)) + "s";
        beatMapInfo += mapJsonObject.get("bpm").isJsonNull() ? "" : "▸BPM: " + mapJsonObject.get("bpm").getAsString() + "\n";

        JsonObject performance = new com.thftgr.osuPerformance.PerfomanceMain().ppCalc(beatMapSetJsonArray);

        for (int i = 0; i < performance.size(); i++) {
            JsonObject beatMapJsonObject = beatMapSetJsonArray.get(i).getAsJsonObject();
            String msg = "★" + String.format("%,.2f", Double.parseDouble(beatMapJsonObject.get("difficultyrating").getAsString())) + " -  " + beatMapJsonObject.get("version").getAsString();
            int mod = beatMapJsonObject.get("mode").getAsInt();

            msg += (mod == 3) ? " [" + beatMapJsonObject.get("diff_size").getAsString() + " key]" : "";
            msg += " " + performance.get(beatMapJsonObject.get("version").getAsString()).getAsString();


            modeList[beatMapJsonObject.get("mode").getAsInt()] += msg + "\n";
        }


        if (!(modeList[0].equals(""))) modeList[4] += "[osu!]\n" + modeList[0] + "\n";
        if (!(modeList[1].equals(""))) modeList[4] += "[osu!Taiko]\n" + modeList[1] + "\n";
        if (!(modeList[2].equals(""))) modeList[4] += "[osu!catch]\n" + modeList[2] + "\n";
        if (!(modeList[3].equals(""))) modeList[4] += "[osu!Mania]\n" + modeList[3] + "\n";
        String beatMapSetInfo = beatMapInfo + "\n" + modeList[4];

        embedBuilder.setDescription(beatMapSetInfo);

        //beatmap image setting
        embedBuilder.setImage("https://b.ppy.sh/thumb/" + mapJsonObject.get("beatmapset_id").getAsString() + "l.jpg");

        //String beatmap Last updated Data
        String beatMapLastDate;
        if (!mapJsonObject.get("approved_date").isJsonNull()) {
            beatMapLastDate = mapJsonObject.get("approved_date").getAsString();

        } else if (!mapJsonObject.get("last_update").isJsonNull()) {
            beatMapLastDate = mapJsonObject.get("last_update").getAsString();

        } else {
            beatMapLastDate = mapJsonObject.get("submit_date").getAsString();
        }

        //footer setting
        String footer = "❤  ";
        footer += mapJsonObject.get("favourite_count").isJsonNull() ? "" : mapJsonObject.get("favourite_count").getAsString();
        footer += "  |  ";
        footer += approvedStatus(mapJsonObject.get("approved").isJsonNull() ? 5 : mapJsonObject.get("approved").getAsInt());
        footer += "  |  ";
        footer += beatMapLastDate;
        footer += " UTC +0";
        embedBuilder.setFooter(footer);
        channel.sendMessage(embedBuilder.build()).queue();


    }

    int avgPlaytime(JsonArray mapSetJsonArray) {
        int avgPlayTime = 0;
        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            //total_length
            if (mapSetJsonArray.get(i).getAsJsonObject().get("total_length").isJsonNull()) continue;
            avgPlayTime += mapSetJsonArray.get(i).getAsJsonObject().get("total_length").getAsInt();
        }
        return avgPlayTime / mapSetJsonArray.size();
    }

    String approvedStatus(int approvedInt) {
        switch (approvedInt) {
            case -2:
                return "Graveyard";
            case -1:
                return "WIP";
            case 0:
                return "Pending";
            case 1:
                return "Ranked";
            case 2:
                return "Approved";
            case 3:
                return "Qualified";
            case 4:
                return "Loved";
            default:
                return "";
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

    String modeParse(int modeInt) {
        switch (modeInt) {
            case 0:
                return "Standard";

            case 1:
                return "Taiko";

            case 2:
                return "Catch The Beat";

            case 3:
                return "Mania";

            default:
                return "Standard";
        }
    }
}
