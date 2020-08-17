package com.thftgr.osu_Servers.Debian;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class debainMain {
    public void event(MessageReceivedEvent e) {
        String message = e.getMessage().getContentRaw().substring(1);
        message = message.replaceAll(" -d ", " ");
        message = message.replaceAll(" --debian "," ");


        String[] array = message.split(" ");


        switch (array[0]) {
            case "h":
            case "help":
                String helpMessage = ">>> ";
                helpMessage += "!user, u [username/userID] [mode]\n";
                helpMessage += "> username have space? Between \". ex) !user \"O S U\"\n\n";
                helpMessage += "!mapset, ms [mapSetID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]\n\n";
                helpMessage += "!map, m [mapID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]\n\n";
                e.getChannel().sendMessage(helpMessage).queue();
                break;
        }




    }


}
