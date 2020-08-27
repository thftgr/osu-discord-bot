package com.thftgr.discord;

import com.thftgr.osu_Servers.bancho.JDAEventListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EventThread {

//    public static class bancho implements Runnable {
//        MessageReceivedEvent e;
//
//        public bancho(MessageReceivedEvent e) {
//            this.e = e;
//        }
//
//        public void run() {
//
//            new s().event(e);
//        }
//    }

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
}
