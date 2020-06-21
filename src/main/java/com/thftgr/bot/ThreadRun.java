package com.thftgr.bot;

import net.dv8tion.jda.api.entities.MessageChannel;

public class ThreadRun {
    public static class beatmapDownload implements Runnable {

        MessageChannel channel;
        String mapStart;
        String maplast;

        public beatmapDownload(MessageChannel channel, String mapStart, String maplast) {
            this.channel = channel;
            this.mapStart = mapStart;
            this.maplast = maplast;
        }

        public void run() {

            new ApiUtil().getBeatmapDownload(channel, mapStart, maplast);

        }

    }

    public static class getUserInfo implements Runnable {
        MessageChannel channel;
        String username;
        String mode;


        public getUserInfo(MessageChannel channel, String username, String mode) {
            this.channel = channel;
            this.username = username;
            this.mode = mode;

        }

        public void run() {
            new Message().UserInfo(channel, username, mode);
        }
    }

    public static class beatmapPrint implements Runnable {

        MessageChannel channel;
        String ID;

        //java -jar asda.jar
        public beatmapPrint(MessageChannel channel, String ID) {
            this.channel = channel;
            this.ID = ID;
        }

        public void run() {
            new Message().beatmapPrint(channel, ID);
        }

    }

    public static class beatmapSetPrint implements Runnable {

        MessageChannel channel;
        String ID;
        String _title;


        public beatmapSetPrint(MessageChannel channel, String ID, String _title) {
            this.channel = channel;
            this.ID = ID;
            this._title = _title;

        }

        public void run() {
            new Message().beatmapSetPrint(channel, ID, _title);

        }

    }

    public static class rankWatcher implements Runnable {

        public rankWatcher() {
        }

        public void run() {
            while (true) new osuIRC().rankWatcher();
        }
    }

    public static class setNewRankedMapnotice implements Runnable {

        MessageChannel channel;


        public setNewRankedMapnotice(MessageChannel channel) {
            this.channel = channel;

        }

        public void run() {

            new osuIRC().setNewRankedMapnotice(this.channel);

        }

    }

    public static class setBotStatus implements Runnable {
        String[] array;

        public setBotStatus(String[] array) {
            this.array = array;
        }

        public void run() {
            new JDAEvent().status(array[1], array[2]);
        }
    }


}