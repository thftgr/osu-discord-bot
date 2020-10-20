package com.thftgr.discord.Private;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonWriter;
import com.thftgr.discord.Main;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class ServerOwnerCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        if(!Objects.requireNonNull(e.getMember()).isOwner()) return;



        String cmd = e.getMessage().getContentRaw().substring(1);
        String[] array = cmd.split(" ");

        switch (array[0]) {

            case "rmn":
            case "rank_map_notice":
                if (!e.getMember().isOwner()) {
                    e.getChannel().sendMessage("This command allow for server admin\n call the server admin.").queue();
                    return;
                }

                setNewRankedMapnotice(e.getChannel());
                break;

        }
    }


    void setNewRankedMapnotice(MessageChannel channel) {

        //이미 리스트에 있는지 확인
        // 리스트에 있으면 삭제
        JsonArray rmnList = Main.settingValue.get("osu!").getAsJsonObject().get("rank_map_notice").getAsJsonArray();
        for (int i = 0; i < rmnList.size(); i++) {
            if (rmnList.get(i).getAsString().equals(channel.getId())) {
                Main.settingValue.get("osu!").getAsJsonObject().get("rank_map_notice").getAsJsonArray().remove(i);
                channel.sendMessage("this channel successfully removed New RankedMap notice").queue();
                settingSave();
                return;
            }
        }
        Main.settingValue.get("osu!").getAsJsonObject().get("rank_map_notice").getAsJsonArray().add(channel.getId());
        channel.sendMessage("this channel added New RankedMap notice").queue();
        settingSave();

    }

    void settingSave() {
        try {
            JsonWriter jsonWriter = new JsonWriter(new FileWriter("setting/Setting.json"));
            jsonWriter.setIndent("    ");
            Gson gson = new GsonBuilder().serializeNulls().create();
            gson.toJson(Main.settingValue,jsonWriter);
            jsonWriter.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

//        try {
//            FileWriter fw = new FileWriter("setting/Setting.json", false);
//            fw.write(Main.settingValue.toString());
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
