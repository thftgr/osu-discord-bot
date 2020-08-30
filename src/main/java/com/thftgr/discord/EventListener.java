package com.thftgr.discord;


import com.thftgr.discord.Private.ServerOwnerCommand;
import com.thftgr.osu_Servers_bancho.NewRankedMapWatcher;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Timer;


public class EventListener extends ListenerAdapter {
    public static JDA mainJda;


    @Override // 특정 누군가의 온라인 status 이벤트
    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {
        super.onUserUpdateOnlineStatus(event);
        new Thread(() -> {
            OnlineStatus os = Objects.requireNonNull(event.getGuild().getMember(event.getUser())).getOnlineStatus();
            if (event.getMember().getId().equals("706888597147615311")) {
                System.out.println(os.getKey());
                if (os.getKey().equals("online")) {
                    event.getJDA().getPresence().setActivity(Activity.playing("봇 개발"));
                } else if (os.getKey().equals("offline")) {
                    event.getJDA().getPresence().setActivity(Activity.playing("0-0"));
                }
            }
        }).start();

    }

    @Override //봇 시작시
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);

        mainJda = event.getJDA();
        new Timer().schedule(new NewRankedMapWatcher(), 0, 60000);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);


        new Thread(() -> {
            try {
                if (event.getAuthor().isBot()) return;
            } catch (Exception ignored) {
                return;
            }


            if (event.getMessage().getContentRaw().toLowerCase().contains("owo")) {
                event.getChannel().sendMessage("What's This?").queue();
            }else {
                if(event.getMessage().getContentRaw().toLowerCase().contains("what's this?")){
                    event.getChannel().sendMessage("OWO").queue();
                }
            }
            if(event.getMessage().getContentRaw().contains("홍진호")){
                event.getChannel().sendMessage(event.getMessage().getContentRaw()).queue();
            }

        }).start();



    }
}


