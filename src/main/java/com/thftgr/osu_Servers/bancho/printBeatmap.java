package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thftgr.discord.EventListener;
import com.thftgr.discord.Util;
import com.thftgr.webApi.WebApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.Arrays;

public class printBeatmap {
    JDA jda = EventListener.mainJda;

    public void beatMapSet(MessageChannel channel, String setID, String mode, String title) {

        System.out.println("asd");
        String parm = "&s=" + setID;
        parm += mode == null ? "" : "&m=" + mode;
        JsonArray beatMapSetJsonArray = new Api().call("get_beatmaps", parm);


        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(255, 255, 255));
        if (beatMapSetJsonArray == null | beatMapSetJsonArray.isJsonNull()) return;

        beatMapSetJsonArray = sortJsonarray(beatMapSetJsonArray);
        //title setting
        String author = title ==null ? "[BEATMAP SET]\n" : title+"\n";
        author += beatMapSetJsonArray.get(0).getAsJsonObject().get("artist").isJsonNull() ? "" : beatMapSetJsonArray.get(0).getAsJsonObject().get("artist").getAsString();
        author += " - ";
        author += beatMapSetJsonArray.get(0).getAsJsonObject().get("title").isJsonNull() ? "" : beatMapSetJsonArray.get(0).getAsJsonObject().get("title").getAsString();
        author += beatMapSetJsonArray.get(0).getAsJsonObject().get("creator").isJsonNull() ? "" : "by " + beatMapSetJsonArray.get(0).getAsJsonObject().get("creator").getAsString();
        embedBuilder.setAuthor(author, "https://osu.ppy.sh/beatmapsets/" + beatMapSetJsonArray.get(0).getAsJsonObject().get("beatmapset_id").getAsString(), null);


        //Description setting


        String[] modeList = {"", "", "", "", ""};
        int playtime = avgPlaytime(beatMapSetJsonArray);
        String beatMapInfo = "▸PlayTime AVG : " + (playtime / 60) + "m " + (playtime - ((playtime / 60) * 60)) + "s";
        beatMapInfo += beatMapSetJsonArray.get(0).getAsJsonObject().get("bpm").isJsonNull() ? "" : "▸BPM: " + beatMapSetJsonArray.get(0).getAsJsonObject().get("bpm").getAsString() + "\n";

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
        embedBuilder.setImage("https://b.ppy.sh/thumb/" + beatMapSetJsonArray.get(0).getAsJsonObject().get("beatmapset_id").getAsString() + "l.jpg");

        //String beatmap Last updated Data
        String beatMapLastDate;
        if (!beatMapSetJsonArray.get(0).getAsJsonObject().get("approved_date").isJsonNull()) {
            beatMapLastDate = beatMapSetJsonArray.get(0).getAsJsonObject().get("approved_date").getAsString();

        } else if (!beatMapSetJsonArray.get(0).getAsJsonObject().get("last_update").isJsonNull()) {
            beatMapLastDate = beatMapSetJsonArray.get(0).getAsJsonObject().get("last_update").getAsString();

        } else {
            beatMapLastDate = beatMapSetJsonArray.get(0).getAsJsonObject().get("submit_date").getAsString();
        }

        //footer setting
        String footer = "❤  ";
        footer += beatMapSetJsonArray.get(0).getAsJsonObject().get("favourite_count").isJsonNull() ? "" : beatMapSetJsonArray.get(0).getAsJsonObject().get("favourite_count").getAsString();
        footer += "  |  ";
        footer += approvedStatus(beatMapSetJsonArray.get(0).getAsJsonObject().get("approved").isJsonNull() ? 5 : beatMapSetJsonArray.get(0).getAsJsonObject().get("approved").getAsInt());
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
}
