package com.thftgr.bot;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
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
        new Thread(new ThreadRun.rankWatcher()).start();
        mainJda = event.getJDA();
    }

    @Override // 반응 이모지 추가시
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        super.onMessageReactionAdd(event);
        //System.out.println(event.getReaction().getMessageId());
        //JDA jda = event.getJDA();
        //System.out.println(jda.getTextChannelById(event.getChannel().getId()).getHistory().getMessageById(event.getMessageId()).getContentRaw());

    }


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        //System.out.println("permission :"+e.getMember().getPermissions().toString().contains("ADMINISTRATOR"));

        if (e.getMember().getUser().isBot()) return;


        //List<Attachment> att = e.getMessage().getAttachments();
//        if (!e.getMessage().getAttachments().isEmpty() && Main.deBugmode) {
//            System.out.println(e.getMessage().getAttachments().get(0).getUrl());
//            new detectText().getImgText(e.getChannel(), e.getMessage().getAttachments().get(0).getUrl());
//        }

        String msg = e.getMessage().getContentRaw();
        String msg_;
        if (Main.settingValue.get("debug").getAsString().equals("true")) {
            msg_ = "@";
        } else {
            msg_ = "!";
        }

        if (!msg.startsWith(msg_)) return;

        String cmd = msg.substring(1);
        String[] array = cmd.split(" ");

        switch (array[0]) {
            case "h":
            case "help":
                if (array.length < 2) {
                    new Message().helpMessage(e.getChannel());
                }
                break;


            case "m":
            case "map":
                if (array.length < 2) {
                    new Message().sayMsg(e.getChannel(), "!map, m [mapID]", null);
                } else if (array.length == 2) {
                    new Thread(new ThreadRun.beatmapPrint(e.getChannel(), array[1])).start();
                }
                break;

            case "ms":
            case "mapset":
                if (array.length < 2) {
                    new Message().sayMsg(e.getChannel(), "!mapset, ms [mapSetID]", null);
                } else if (array.length == 2) {
                    new Thread(new ThreadRun.beatmapSetPrint(e.getChannel(), array[1], "[MAPSET]\n")).start();

                }
                break;

            case "u":
            case "user":
                if (array.length < 2) {
                    new Message().sayMsg(e.getChannel(), "!user [username] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!mania ]", null);
                } else if (array.length == 2) {
                    new Thread(new ThreadRun.getUserInfo(e.getChannel(), array[1], "")).start();
                } else {
                    new Thread(new ThreadRun.getUserInfo(e.getChannel(), new Util().grabUsername(cmd), array[array.length - 1])).start();
                }
                break;


            case "d":
            case "download":
                if (!e.getAuthor().getId().equals("368620104365244418")) {
                    new Message().sayMsg(e.getChannel(), "이 기능은 임시 비활성 입니다. thftgr#3102 에게 문의하세요.", null);
                    break;
                }
                if (array.length < 2) {
                    new Message().sayMsg(e.getChannel(), "!d [mapSetID]", null);
                } else if (array.length == 2) {
                    new Thread(new ThreadRun.beatmapDownload(e.getChannel(), array[1], null)).start();
                } else {
                    if (e.getAuthor().getId().equals("368620104365244418")) {
                        new Util().divisionDownload(e.getChannel(), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
                    }
                }
                break;

            case "rmn":
            case "rank_map_notice":
                if (e.getMember().isOwner()) {
                    new Thread(new ThreadRun.setNewRankedMapnotice(e.getChannel())).start();
                } else {
                    e.getChannel().sendMessage("This Command Can Use Server Owner").queue();
                }
                break;


            case "s":
            case "status":
                if (!e.getAuthor().getId().equals("368620104365244418")) {
                    new Message().sayMsg(e.getChannel(), "이 기능은 봇 주인만 가능합니다. thftgr#3102 에게 문의하세요.", null);
                    break;
                }
                if (array.length > 2) {
                    if (array.length > 3) {
                        for (int i = 3; i < array.length; i++) {
                            array[2] += " " + array[i];
                        }
                    }
                    new Thread(new ThreadRun.setBotStatus(array)).start();
                } else {
                    new Message().sayMsg(e.getChannel(), "!status, s[P, L, W] [String Status use \"\" ]", null);
                }
                break;

            case "t":
                break;

        }
        System.gc();

    }


}


