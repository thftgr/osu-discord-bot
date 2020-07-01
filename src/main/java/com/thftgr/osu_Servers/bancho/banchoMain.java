package com.thftgr.osu_Servers.bancho;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class banchoMain {
    public void event(MessageReceivedEvent e) {
        String cmd = e.getMessage().getContentRaw().substring(1);
        String[] array = cmd.split(" ");

        switch (array[0]) {

            case "u":
            case "user":

                break;

        }


    }
}
