package com.thftgr.discord;

import com.thftgr.osu_Servers.bancho.BeatmapDownloader;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EventThread {

    public static class bancho implements Runnable {
        MessageReceivedEvent e;

        public bancho(MessageReceivedEvent e) {
            this.e = e;

        }

        public void run() {
            new com.thftgr.osu_Servers.bancho.banchoMain().event(e);
        }

    }
}
