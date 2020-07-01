package com.thftgr.osu_Servers.bancho;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class PrintUser {
    void PrintUser(MessageChannel channel,String user,String mode){
        String parm = "&u="+user;
        parm += (mode == null) ? "" : "&m="+mode;

        JsonObject userInfoJsonObject = new Api().call("get_user",parm).get(0).getAsJsonObject();

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(new Color(255,255,255));

        //title setting
        String title = userInfoJsonObject.get("country").isJsonNull() ? "" : userInfoJsonObject.get("country").getAsString() + " ";
        title += userInfoJsonObject.get("username").isJsonNull() ? "" : userInfoJsonObject.get("username").getAsString()+"'s profile\n";
        title += userInfoJsonObject.get("country").isJsonNull() ? "" : userInfoJsonObject.get("country").getAsString();
        title += userInfoJsonObject.get("country").isJsonNull() ? "" : userInfoJsonObject.get("country").getAsString();
    }

}
