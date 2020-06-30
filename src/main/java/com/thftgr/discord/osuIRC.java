package com.thftgr.discord;

import com.google.gson.JsonArray;
import com.thftgr.webApi.WebApi;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class osuIRC {

    static void sendString(BufferedWriter bw, String str) {
        try {
            bw.write(str + "\r\n");
            bw.flush();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void rankWatcher() {


        try {



            String server = Main.settingValue.get("irc.url").getAsString();
            int port = Main.settingValue.get("irc.port").getAsInt();
            String nickname = Main.settingValue.get("irc.username").getAsString();
            String channel = Main.settingValue.get("irc.channel").getAsString();
            String passwd = Main.settingValue.get("irc.passwd").getAsString();



            Socket socket = new Socket(server, port);
            System.out.println("IRC connected");
            socket.setSoTimeout(5000);

            BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            sendString(bwriter, "PASS " + passwd);
            sendString(bwriter, "NICK " + nickname);
            sendString(bwriter, "JOIN " + channel);
            bwriter.flush();


            String line = null;
            long tim = System.currentTimeMillis();
            BufferedReader breader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                line = breader.readLine();
                if ((tim + 30000) < System.currentTimeMillis()) {
                    sendString(bwriter, "PING");
                    tim = System.currentTimeMillis();
                }

                if (line.contains("End of /NAMES list.") ||
                        line.contains("JOIN") ||
                        line.contains("QUIT") ||
                        line.contains("PART") ||
                        line.contains("PONG") ||
                        line.contains(":cho.ppy.sh 353 -osu- = #announce :") ||
                        line.contains("MODE")) {
                    continue;
                }


                System.out.println(line);
                if (line.contains(":[https://osu.ppy.sh/beatmapsets/")) { //has just been ranked!

                    JsonArray channelList = Main.settingValue.get("rank_map_notice").getAsJsonArray();
                    MessageChannel[] messageChannelList = new MessageChannel[channelList.size()];
                    for (int i = 0; i < channelList.size(); i++) {
                        messageChannelList[i] = EventListener.mainJda.getTextChannelById(channelList.get(i).getAsString());
                    }


                    String mapSetId = line.substring(line.indexOf(":[https:") + 33);
                    mapSetId = mapSetId.substring(0, mapSetId.indexOf(" "));

                    JsonArray msinfo = new WebApi().call("get_beatmaps", "&s=" + mapSetId);

                    if (msinfo.get(0).getAsJsonObject().get("approved").getAsInt() == 1) {
                        for (int i = 0; i < channelList.size(); i++) {
                            new Thread(new ThreadRun.beatmapSetPrint(messageChannelList[i], mapSetId, "[NEW_RANKED_MAPSET]\n")).start();
                        }
                    }
                }

            }

        } catch (Exception e) {
            System.out.println("catch: " + e);
        }


        try {
            System.out.println(new Date() + "trying reconnect osu! irc wait 5s");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

    }

    void setNewRankedMapnotice(MessageChannel channel) {

        //이미 리스트에 있는지 확인
        // 리스트에 있으면 삭제
        JsonArray rmnList = Main.settingValue.get("rank_map_notice").getAsJsonArray();
        for (int i = 0; i < rmnList.size() ; i++) {
            if(rmnList.get(i).getAsString().equals(channel.getId())){
                Main.settingValue.get("rank_map_notice").getAsJsonArray().remove(i);
                channel.sendMessage("this channel successfully removed New RankedMap notice").queue();
                new fileIO().settingSave();
                return;
            }
        }
        Main.settingValue.get("rank_map_notice").getAsJsonArray().add(channel.getId());
        channel.sendMessage("this channel added New RankedMap notice").queue();
        new fileIO().settingSave();

    }


}