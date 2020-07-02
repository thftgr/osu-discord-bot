package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class PrintUser {
    void PrintUser(MessageChannel channel,String user,String mode){
        System.out.println("printuser");
        String parm = "&u="+user;
        parm += (mode == null) ? "" : "&m="+mode;
        JsonArray userInfoJsonArray = new Api().call("get_user",parm);
        System.out.println(userInfoJsonArray.size());
        if(userInfoJsonArray.size() == 0){
            channel.sendMessage("Can't find user.").queue();
            return;
        }
        JsonObject userInfoJsonObject = userInfoJsonArray.get(0).getAsJsonObject();

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(255,255,255));

        //author setting
        String author = userInfoJsonObject.get("country").isJsonNull() ? "" : userInfoJsonObject.get("country").getAsString() + " ";
        author += parseMode(mode)+" player ";
        author += userInfoJsonObject.get("username").isJsonNull() ? "" : userInfoJsonObject.get("username").getAsString()+"'s profile\n";
        embedBuilder.setAuthor(author,"https://osu.ppy.sh/users/"+userInfoJsonObject.get("user_id").getAsString());

        //Description setting
        String description = "";

        embedBuilder.setDescription(description);



        channel.sendMessage(embedBuilder.build()).queue();
    }

    String parseMode(String mods) {

        if(mods == null) return "Standard";
        if(mods.equals("1")) return "Taiko";
        if(mods.equals("2")) return "Catch the beat";
        if(mods.equals("3")) return "Mania";
        return "Standard";
    }

}
