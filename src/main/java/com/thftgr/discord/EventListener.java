package com.thftgr.discord;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


public class EventListener extends ListenerAdapter {
    public static JDA mainJda;


    @Override // 특정 누군가의 온라인 status 이벤트
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        OnlineStatus os = event.getGuild().getMember(event.getUser()).getOnlineStatus();
        if (event.getMember().getId().equals("706888597147615311")) {
            System.out.println(os.getKey());
            if (os.getKey().equals("online")) {
                event.getJDA().getPresence().setActivity(Activity.playing("봇 개발"));
            } else if (os.getKey().equals("offline")) {
                event.getJDA().getPresence().setActivity(Activity.playing("0-0"));
            }
        }
    }

    @Override //봇 시작시
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
        //new serverThread(new ThreadRun.rankWatcher()).start();
        mainJda = event.getJDA();

    }


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        try {
            if (e.getMember().getUser().isBot()) return;
        } catch (Exception ignored) {
        }

        String messageFormChannel = e.getMessage().getContentRaw();
        if (messageFormChannel.toLowerCase().equals("owo")) e.getChannel().sendMessage("What's This?").queue();

        if (!messageFormChannel.startsWith(Main.settingValue.get("commandStartWith").getAsString())) return;
        if (e.getAuthor().getId().equals(Main.settingValue.get("discord.botOwnerID").getAsString())) {
            new com.thftgr.discord.Private.HiddenCommand().event(e);
        }
//        return;




        if (!Main.settingValue.get("discord.channelOption").getAsJsonObject().get(e.getChannel().getId()).isJsonNull()) {

            switch (Main.settingValue.get("discord.channelOption").getAsJsonObject().get(e.getChannel().getId()).getAsString()) {
                case "gatari":
                    new com.thftgr.osu_Servers.gatari.gatariMain().event(e);
                    break;
                case "debian":
                    new com.thftgr.osu_Servers.Debian.debainMain().event(e);
                    break;

            }

        }
        new com.thftgr.osu_Servers.bancho.banchoMain().event(e);

//        String cmd = messageFormChannel.substring(1);
//        String[] array = cmd.split(" ");
        
//        switch (array[0]) {
//            case "h":
//            case "help":
//                new Message().helpMessage(e.getChannel());
//                break;
//
//
//            case "m":
//            case "map":
//                if (array.length < 2) {
//                    new Message().sayMsg(e.getChannel(), "!map, m [mapID]", null);
//                } else if (array.length == 2) {
//                    new serverThread(new ThreadRun.beatmapPrint(e.getChannel(), array[1])).start();
//                }
//                break;
//
//            case "ms":
//            case "mapset":
//                if (array.length < 2) {
//                    new Message().sayMsg(e.getChannel(), "!mapset, ms [mapSetID]", null);
//                } else if (array.length == 2) {
//                    new serverThread(new ThreadRun.beatmapSetPrint(e.getChannel(), array[1], "[MAPSET]\n")).start();
//
//                }
//                break;
//
//            case "u":
//            case "user":
//                if (array.length < 2) {
//                    new Message().sayMsg(e.getChannel(), "!user [username] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]", null);
//                } else if (array.length == 2) {
//                    new serverThread(new ThreadRun.getUserInfo(e.getChannel(), array[1], "")).start();
//                } else {
//                    new serverThread(new ThreadRun.getUserInfo(e.getChannel(), new Util().grabUsername(cmd), array[array.length - 1])).start();
//                }
//                break;
//
//
//            case "d":
//            case "download":
//                if (array.length < 2) {
//                    new Message().sayMsg(e.getChannel(), "!d [mapSetID]", null);
//                } else if (array.length == 2) {
//                    new serverThread(new ThreadRun.beatmapDownload(e.getChannel(), array[1], null)).start();
//                } else if (array.length == 3) {
//                    if (e.getAuthor().getId().equals("368620104365244418"))
//                        new serverThread(new ThreadRun.beatmapDownload(e.getChannel(), array[1], array[2])).start();
//                } else if (array.length == 4) {
//                    if (e.getAuthor().getId().equals("368620104365244418"))
//                        new serverThread(new ThreadRun.beatmapDownload(e.getChannel(), array[1], array[2])).start();
//                    Main.downloadThread = Integer.parseInt(array[3]);
//                }
//
//                break;
//
//            case "rmn":
//            case "rank_map_notice":
//                if (e.getMember().isOwner()) {
//                    new serverThread(new ThreadRun.setNewRankedMapnotice(e.getChannel())).start();
//                } else {
//                    e.getChannel().sendMessage("This Command Can Use Server Owner").queue();
//                }
//                break;
//
//
//            case "s":
//            case "status":
//                if (!e.getAuthor().getId().equals("368620104365244418")) break;
//
//                if (array.length > 2) {
//                    if (array.length > 3) {
//                        for (int i = 3; i < array.length; i++) {
//                            array[2] += " " + array[i];
//                        }
//                    }
//                    new serverThread(new ThreadRun.setBotStatus(array)).start();
//                } else {
//                    new Message().sayMsg(e.getChannel(), "!status, s[P, L, W] [String Status use \"\" ]", null);
//                }
//                break;
//
//            case "t":
//                break;
//
//        }
//        System.gc();

    }


}


