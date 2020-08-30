package com.thftgr.discord;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thftgr.osu_Servers_bancho.JDAEventListener_bancho;
import com.thftgr.osu_Servers_debian.JDAEventListener_debian;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.io.FileReader;
//rel
//https://discordapp.com/oauth2/authorize?client_id=673103762373476382&scope=bot
//debug
//https://discordapp.com/oauth2/authorize?client_id=706888597147615311&scope=bot


public class Main {
    public static JsonObject settingValue;
    public static JDA jda;

    public static void main(String[] args) {
        new Main().setJDA();
    }

    void setJDA() {
        try {
            settingValue = (JsonObject) JsonParser.parseReader(new Gson().newJsonReader(new FileReader("setting/Setting.json")));
            jda = JDABuilder.createDefault(settingValue.get("discord").getAsJsonObject().get("token").getAsString())
                    .addEventListeners(new EventListener())
                    .addEventListeners(new JDAEventListener_bancho())
                    .addEventListeners(new JDAEventListener_debian())
                    .setMaxReconnectDelay(32)
                    .build();
            jda.setAutoReconnect(true);
            jda.setRequestTimeoutRetry(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
