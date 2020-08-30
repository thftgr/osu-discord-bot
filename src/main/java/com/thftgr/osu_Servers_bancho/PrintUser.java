package com.thftgr.osu_Servers_bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class PrintUser {
    void PrintUser(MessageChannel channel, String user, String mode) {
        String parm = "&u=" + user;
        parm += (mode == null) ? "" : "&m=" + mode;
        JsonArray userInfoJsonArray = new Api().call("get_user", parm);

        if (userInfoJsonArray.size() == 0) {
            channel.sendMessage("Can't find user.").queue();
            return;
        }

        parm = "&u=" + user;
        parm += "&limit=1";
        parm += mode == null ? "" : "&m=" + mode;

        JsonArray userBestBeatmap = new Api().call("get_user_best", parm);

        //get user Info
        JsonObject userInfoJsonObject = userInfoJsonArray.get(0).getAsJsonObject();
        JsonObject userBestBeatmapJsonObject = userBestBeatmap.get(0).getAsJsonObject();

        //create embedbuilder
        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(255, 255, 255));

        //country image setting
        String countryImage = userInfoJsonObject.get("country").isJsonNull() ? "" : "https://osu.ppy.sh/images/flags/" + userInfoJsonObject.get("country").getAsString() + ".png";

        //author setting
        String author = userInfoJsonObject.get("country").isJsonNull() ? "" : userInfoJsonObject.get("country").getAsString() + " ";
        author += userInfoJsonObject.get("username").isJsonNull() ? "" : userInfoJsonObject.get("username").getAsString() + "'s profile " + parseMode(mode);

        embedBuilder.setAuthor(author, "https://osu.ppy.sh/users/" + userInfoJsonObject.get("user_id").getAsString(), countryImage);

        //thumbnail setting
        embedBuilder.setThumbnail(userInfoJsonObject.get("user_id").isJsonNull() ? "" : "http://s.ppy.sh/a/" + userInfoJsonObject.get("user_id").getAsString());

        //Description setting
        String description = "";
        description += userInfoJsonObject.get("user_id").isJsonNull() ? "" : "▸User ID : " + userInfoJsonObject.get("user_id").getAsString() + "\n";
        description += userInfoJsonObject.get("level").isJsonNull() ? "" : "▸Level : " + String.format("%.2f", userInfoJsonObject.get("level").getAsDouble()) + "\n";
        description += userInfoJsonObject.get("pp_raw").isJsonNull() ? "" : "▸Total PP : " + String.format("%.2f", userInfoJsonObject.get("pp_raw").getAsDouble()) + "\n";
        description += userBestBeatmapJsonObject.get("pp").isJsonNull() | userBestBeatmapJsonObject.get("pp") == null ? "" : "▸Best_pp : " + String.format("%.2f", userBestBeatmapJsonObject.get("pp").getAsDouble()) + "\n";
        description += userInfoJsonObject.get("accuracy").isJsonNull() ? "" : "▸Accuracy : " + String.format("%.2f", userInfoJsonObject.get("accuracy").getAsDouble()) + "%\n";
        description += userInfoJsonObject.get("pp_rank").isJsonNull() ? "" : "▸Rank : #" + userInfoJsonObject.get("pp_rank").getAsString() + "\n";
        description += userInfoJsonObject.get("pp_country_rank").isJsonNull() ? "" : "▸CountryRank : #" + userInfoJsonObject.get("pp_country_rank").getAsString() + "\n";
        description += userInfoJsonObject.get("playcount").isJsonNull() ? "" : "▸Playcount : " + userInfoJsonObject.get("playcount").getAsString() + "\n";
        description += userInfoJsonObject.get("join_date").isJsonNull() ? "" : "▸Join Date : " + userInfoJsonObject.get("join_date").getAsString() + "\n";
        if (!(userBestBeatmap.size() == 0)) {
            int playtime_s = Integer.parseInt(userInfoJsonObject.get("total_seconds_played").getAsString()), playtime_m = playtime_s / 60, playtime_h = (playtime_m / 60), playtime_d = (playtime_h / 24);
            description += "▸Playtime : " + (playtime_d + "d " + (playtime_h - (playtime_d * 24)) + "h " + (playtime_m - (playtime_h * 60)) + "m " + (playtime_s - (playtime_m * 60)) + "s") + "\n";
        }

        embedBuilder.setDescription(description);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    String parseMode(String mods) {

        if (mods == null) return "[Standard]";
        if (mods.equals("1")) return "[Taiko]";
        if (mods.equals("2")) return "[Catch the beat]";
        if (mods.equals("3")) return "[Mania]";
        return "[Standard]";
    }


}
