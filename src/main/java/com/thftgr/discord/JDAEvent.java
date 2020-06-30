package com.thftgr.discord;

import net.dv8tion.jda.api.entities.Activity;

public class JDAEvent {

    void status(String mod, String status){
        switch (mod.toLowerCase().substring(0,1)){
            case "p" :
                EventListener.mainJda.getPresence().setActivity(Activity.playing(status));
                break;
            case "w" :
                EventListener.mainJda.getPresence().setActivity(Activity.watching(status));
                break;
            case "l" :
                EventListener.mainJda.getPresence().setActivity(Activity.listening(status));
                break;

        }
    }


}
