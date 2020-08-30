package com.thftgr.osu_Servers_debian;

import com.thftgr.discord.Util;
import com.thftgr.osu_Servers_bancho.PrintUser;
import com.thftgr.osu_Servers_bancho.printBeatmap;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class JDAEventListener_debian extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        super.onMessageReceived(e);
        try{
            if(e.getAuthor().isBot()) return;
        } catch (Exception exception) {
            System.out.println("bot check error: " + exception.getMessage());
        }




    }


}
