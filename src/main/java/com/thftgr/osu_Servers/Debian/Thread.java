package com.thftgr.osu_Servers.Debian;

import com.thftgr.osu_Servers.bancho.BeatmapDownloader;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Thread {

    public static class task1 implements Runnable {

        MessageChannel channel;
        int mapStart;
        int maplast;

        public task1(MessageChannel channel, String mapStart, String maplast) {
            this.channel = channel;
            this.mapStart = Integer.parseInt(mapStart);
            this.maplast = (maplast == null) ? 0 : Integer.parseInt(maplast);
        }

        public void run() {
            new BeatmapDownloader().downloadBeatmap(channel, mapStart, maplast);
        }

    }
}
