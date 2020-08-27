package com.thftgr.discord;


import com.thftgr.osu_Servers.bancho.NewRankedMapWatcher;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Timer;


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
        mainJda = event.getJDA();
        new Timer().schedule(new NewRankedMapWatcher(),0,60000);

    }

    @Override
    public void onReconnect(@Nonnull ReconnectedEvent event) {
        super.onReconnect(event);
        mainJda = event.getJDA();
        System.out.println("[ JDA ] Reconnected");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        try {
            if (Objects.requireNonNull(e.getMember()).getUser().isBot()) return;
        } catch (Exception ignored) {
        }

        //메세지가 커맨드가 맞는가.
        if (!e.getMessage().getContentRaw().startsWith(Main.settingValue.get("commandStartWith").getAsString())) return;

        //서버 주인인가.
        if(Objects.requireNonNull(e.getMember()).isOwner()) new com.thftgr.discord.Private.ServerOwnerCommand().event(e);

        //봇 주인인가.
        if (e.getAuthor().getId().equals(Main.settingValue.get("discord").getAsJsonObject().get("botOwnerID").getAsString())) {
            new com.thftgr.discord.Private.HiddenCommand().event(e);
        }

        if(e.getMessage().getContentRaw().contains(" -d") | e.getMessage().getContentRaw().contains(" --debian")){
            new Thread(new EventThread.debian(e)).start();

        }else if(e.getMessage().getContentRaw().contains(" -g") | e.getMessage().getContentRaw().contains(" --gatari")){
            new Thread(new EventThread.gatari(e)).start();
        }
//        else{
//            new Thread(new EventThread.bancho(e)).start();
//        }


    }


}


