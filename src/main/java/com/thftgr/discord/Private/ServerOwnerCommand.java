package com.thftgr.discord.Private;

import com.google.gson.JsonArray;
import com.thftgr.discord.Main;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.FileWriter;
import java.io.IOException;

public class ServerOwnerCommand {






    void setNewRankedMapnotice(MessageChannel channel) {

        //이미 리스트에 있는지 확인
        // 리스트에 있으면 삭제
        JsonArray rmnList = Main.settingValue.get("rank_map_notice").getAsJsonArray();
        for (int i = 0; i < rmnList.size() ; i++) {
            if(rmnList.get(i).getAsString().equals(channel.getId())){
                Main.settingValue.get("rank_map_notice").getAsJsonArray().remove(i);
                channel.sendMessage("this channel successfully removed New RankedMap notice").queue();
                settingSave();
                return;
            }
        }
        Main.settingValue.get("rank_map_notice").getAsJsonArray().add(channel.getId());
        channel.sendMessage("this channel added New RankedMap notice").queue();
        settingSave();

    }
    void settingSave(){
        try {
            FileWriter fw = new FileWriter("setting/Setting.json", false);
            fw.write(Main.settingValue.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
