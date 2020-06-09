package com.thftgr;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
//rel
//https://discordapp.com/oauth2/authorize?client_id=673103762373476382&scope=bot
//bebug
//https://discordapp.com/oauth2/authorize?client_id=706888597147615311&scope=bot


public class Main{
    public static JsonObject settingValue ;


    public static void main(String[] args){

        try {
            settingValue = (JsonObject) JsonParser.parseReader(new Gson().newJsonReader(new FileReader("setting/Setting.json")));


            JDABuilder jb = new JDABuilder();
            jb.setToken(settingValue.get("token.discord").getAsString());
            jb.addEventListeners(new EventListener());

            jb.setActivity(Activity.playing("0-0"));
            jb.build();



        } catch (Exception e) {
            System.out.println(e);
        }
    }





}
