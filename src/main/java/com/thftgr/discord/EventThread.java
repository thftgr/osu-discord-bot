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

    public static class gatari implements Runnable {
        MessageReceivedEvent e;

        public gatari(MessageReceivedEvent e) {
            this.e = e;
        }

        public void run() {

            new com.thftgr.osu_Servers.gatari.gatariMain().event(e);
        }
    }
    public static class debian implements Runnable {
        MessageReceivedEvent e;

        public debian(MessageReceivedEvent e) {
            this.e = e;
        }

        public void run() {

            new com.thftgr.osu_Servers.Debian.debainMain().event(e);
        }
    }







//    public static class banchoRankedMapWhatcher implements Runnable {
//
//        public void run() {
//            while(true){
//                System.out.println("started Rankmap Watchet");
//                new com.thftgr.osu_Servers.bancho.NewRankedMapWatcher().RankedMapWatcher();
//                System.out.println("stoped Rankmap Watchet");
//            }
//        }
//
//    }


}
