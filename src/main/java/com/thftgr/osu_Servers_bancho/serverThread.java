package com.thftgr.osu_Servers_bancho;

import net.dv8tion.jda.api.entities.MessageChannel;

public class serverThread {



    public static class beatmapDownloadRun implements Runnable {
        MessageChannel channel;
        int mapStart;
        int maplast;

        public beatmapDownloadRun(MessageChannel channel, int mapStart, int maplast) {
            this.channel = channel;
            this.mapStart = mapStart;
            this.maplast =  maplast;
        }

        public void run() {
            new BeatmapDownloader().downloads(channel, mapStart, maplast);
        }

    }
}
