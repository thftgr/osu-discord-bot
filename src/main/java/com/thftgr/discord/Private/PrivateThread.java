package com.thftgr.discord.Private;

import com.thftgr.discord.EventListener;
import net.dv8tion.jda.api.entities.Activity;

public class PrivateThread {
    public static class setBotStatus implements Runnable {
        String[] array;

        public setBotStatus(String[] array) {
            this.array = array;
        }

        public void run() {

                switch (array[1].toLowerCase().substring(0,1)){
                    case "p" :
                        EventListener.mainJda.getPresence().setActivity(Activity.playing(array[2]));
                        break;
                    case "w" :
                        EventListener.mainJda.getPresence().setActivity(Activity.watching(array[2]));
                        break;
                    case "l" :
                        EventListener.mainJda.getPresence().setActivity(Activity.listening(array[2]));
                        break;

                }

        }
    }
}
