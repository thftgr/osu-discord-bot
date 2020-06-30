package com.thftgr.discord;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.JDABuilder;

import java.io.FileReader;
//rel
//https://discordapp.com/oauth2/authorize?client_id=673103762373476382&scope=bot
//debug
//https://discordapp.com/oauth2/authorize?client_id=706888597147615311&scope=bot


public class Main{
    public static JsonObject settingValue ;
    public static int downloadThread = 4;




    public static void main(String[] args){

        try {
            settingValue = (JsonObject) JsonParser.parseReader(new Gson().newJsonReader(new FileReader("setting/Setting.json")));

            JDABuilder jb = new JDABuilder();

            jb.setToken(settingValue.get("token.discord").getAsString());
            jb.addEventListeners(new EventListener());
            jb.setMaxReconnectDelay(32);
            jb.build();

        } catch (Exception e) {
            System.out.println(e);
        }
    }





}
