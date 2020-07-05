package com.thftgr.osu_Servers.Debian;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class debainMain {
    public void event(MessageReceivedEvent e) {
        if(!e.getMessage().getContentRaw().startsWith("!") ) return;
    }


}
