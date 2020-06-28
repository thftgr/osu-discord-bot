package com.thftgr.Debian;

import com.thftgr.bot.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class debainMain {
    public void eventListener(MessageReceivedEvent e){
        try{
            if (e.getMember().getUser().isBot()) return;
        } catch (Exception ignored){}


        String msg = e.getMessage().getContentRaw();
        String msg_;

        if (Main.settingValue.get("debug").getAsString().equals("true")) {
            msg_ = "@";
        } else {
            msg_ = "!";
        }

        if (msg.toLowerCase().equals("owo")) e.getChannel().sendMessage("What's This?").queue();
        if (!msg.startsWith(msg_)) return;


    }


}
