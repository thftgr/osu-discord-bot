package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.thftgr.discord.EventListener;
import com.thftgr.discord.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.TimeZone;
import java.util.TimerTask;

import static com.thftgr.discord.Main.jda;

public class NewRankedMapWatcher extends TimerTask {

    @Override
    public void run() {

        if(jda.getStatus() != JDA.Status.CONNECTED) return;

        if(Main.settingValue.get("osu!").getAsJsonObject().isJsonNull()){
            Main.settingValue.get("osu!").getAsJsonObject().addProperty("lastRankedMapCheck", GetUTCtime());
            settingSave();
        }
        try {
            String parm = "&since=" + Main.settingValue.get("osu!").getAsJsonObject().get("lastRankedMapCheck").getAsString();
            JsonArray newRankedBeatmaps = new Api().call("get_beatmaps", parm);
            System.out.println("time = "+new Date().toString() +" : count = "+ newRankedBeatmaps.size());


            if (newRankedBeatmaps.size() == 0) return;
            JsonArray rnakedMapList = new JsonArray();
            boolean check = false;
            for (int i = 0; i < newRankedBeatmaps.size(); i++) {
                for (int j = 0; j < rnakedMapList.size(); j++) {
                    check = rnakedMapList.get(j).getAsInt() == newRankedBeatmaps.get(i).getAsJsonObject().get("beatmapset_id").getAsInt();
                }
                if ((!check) & (newRankedBeatmaps.get(i).getAsJsonObject().get("approved").getAsInt() == 1)) {
                    rnakedMapList.add(newRankedBeatmaps.get(i).getAsJsonObject().get("beatmapset_id").getAsString());
                }
            }

            for (int i = 0; i < rnakedMapList.size(); i++) {
                for (int j = 0; j < Main.settingValue.get("osu!").getAsJsonObject().get("rank_map_notice").getAsJsonArray().size(); j++) {
                    jda.awaitStatus(JDA.Status.CONNECTED);
                    MessageChannel messageChannel = EventListener.mainJda.getTextChannelById(Main.settingValue.get("osu!").getAsJsonObject().get("rank_map_notice").getAsJsonArray().get(j).getAsString());
                    new printBeatmap().beatMapSet(messageChannel, rnakedMapList.get(i).getAsString(), null, "[NEW_RANKED_MAPSET]");
                }
            }

            Main.settingValue.get("osu!").getAsJsonObject().addProperty("lastRankedMapCheck", GetUTCtime());
            settingSave();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    String GetUTCtime() {
        TimeZone def = TimeZone.getDefault();

        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        String dateTime = new java.sql.Date(new Date().getTime()).toString() + " " + new Time(new Date().getTime()).toString();
        TimeZone.setDefault(def);
        return dateTime;
    }

    void settingSave() {
        try {
            FileWriter fw = new FileWriter("setting/Setting.json", false);
            fw.write(Main.settingValue.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
