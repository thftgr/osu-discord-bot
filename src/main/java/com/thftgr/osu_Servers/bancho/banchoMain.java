package com.thftgr.osu_Servers.bancho;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class banchoMain {
    public void event(MessageReceivedEvent e) {
        String cmd = e.getMessage().getContentRaw().substring(1);
        String[] array = cmd.split(" ");

        switch (array[0]) {
            case "u":
            case "user":
                if (array.length < 2) {
                    e.getChannel().sendMessage(">>> !user, u [username/userID] [mode]\n username have space? Between \". ex) !user \"O S U\"\n\n").queue();
                    return;
                }
                new PrintUser().PrintUser(e.getChannel(), cmd.contains("\"") ? parseSpace(cmd) : array[1], ((array.length > 2) ? array[2] : null));
                break;

            case "ms":
            case "mapset":
                if (array.length < 2) {
                    e.getChannel().sendMessage(" >>> !mapset, ms [mapSetID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]").queue();
                    return;
                }
                new printBeatmap().beatMapSet(e.getChannel(), array[1], ((array.length > 2) ? array[2] : null));

                break;


        }


    }

    String parseSpace(String data) {
        String tmp = data.substring(data.indexOf("\"") + 1);
        return tmp.substring(0, tmp.indexOf("\""));
    }
}
