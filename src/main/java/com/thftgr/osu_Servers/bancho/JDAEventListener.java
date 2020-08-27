package com.thftgr.osu_Servers.bancho;

import com.thftgr.discord.Util;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class JDAEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent e) {
        super.onMessageReceived(e);
        if (!new Util().isCommand(e.getMessage().getContentRaw())) return;
        String cmd = e.getMessage().getContentRaw().substring(1);
        String[] array = cmd.split(" ");

        switch (array[0]) {
            case "h":
            case "help":
                String helpMessage = ">>> ";
                helpMessage +="!user, u [username/userID] [mode]\n";
                helpMessage +="> username have space? Between \". ex) !user \"O S U\"\n\n";
                helpMessage +="!mapset, ms [mapSetID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]\n\n";
                helpMessage +="!map, m [mapID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]\n\n";
                helpMessage +="!rank_map_notice, rmn Set this channel to new Ranked map notice\n";
                helpMessage +="> This command is only available to the server owner.\n\n";
                e.getChannel().sendMessage(helpMessage).queue();
                break;


            case "u":
            case "user":
                if (array.length < 2) {
                    e.getChannel().sendMessage(">>> !user, u [username/userID] [mode]\n username have space? Between \". ex) !user \"O S U\"").queue();
                    break;
                }
                new PrintUser().PrintUser(e.getChannel(), cmd.contains("\"") ? parseSpace(cmd) : array[1], ((array.length > 2) ? array[2] : null));
                break;

            case "ms":
            case "mapset":
                if (array.length < 2) {
                    e.getChannel().sendMessage(" >>> !mapset, ms [mapSetID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]").queue();
                    return;
                }
                new printBeatmap().beatMapSet(e.getChannel(), array[1], ((array.length > 2) ? array[2] : null),"[MAPSET]");

                break;
            case "m":
            case "map":
                if (array.length < 2) {
                    e.getChannel().sendMessage(" >>> !map, m [mapID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]").queue();
                    return;
                }
                new printBeatmap().beatmap(e.getChannel(), array[1], ((array.length > 2) ? array[2] : null));
                break;

        }
    }

//    public void event(MessageReceivedEvent e) {
//        String cmd = e.getMessage().getContentRaw().substring(1);
//        String[] array = cmd.split(" ");
//
//        switch (array[0]) {
//            case "h":
//            case "help":
//                String helpMessage = ">>> ";
//                helpMessage +="!user, u [username/userID] [mode]\n";
//                helpMessage +="> username have space? Between \". ex) !user \"O S U\"\n\n";
//                helpMessage +="!mapset, ms [mapSetID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]\n\n";
//                helpMessage +="!map, m [mapID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]\n\n";
//                helpMessage +="!rank_map_notice, rmn Set this channel to new Ranked map notice\n";
//                helpMessage +="> This command is only available to the server owner.\n\n";
//                e.getChannel().sendMessage(helpMessage).queue();
//                break;
//
//
//            case "u":
//            case "user":
//                if (array.length < 2) {
//                    e.getChannel().sendMessage(">>> !user, u [username/userID] [mode]\n username have space? Between \". ex) !user \"O S U\"").queue();
//                    break;
//                }
//                new PrintUser().PrintUser(e.getChannel(), cmd.contains("\"") ? parseSpace(cmd) : array[1], ((array.length > 2) ? array[2] : null));
//                break;
//
//            case "ms":
//            case "mapset":
//                if (array.length < 2) {
//                    e.getChannel().sendMessage(" >>> !mapset, ms [mapSetID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]").queue();
//                    return;
//                }
//                new printBeatmap().beatMapSet(e.getChannel(), array[1], ((array.length > 2) ? array[2] : null),"[MAPSET]");
//
//                break;
//            case "m":
//            case "map":
//                if (array.length < 2) {
//                    e.getChannel().sendMessage(" >>> !map, m [mapID] [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!Mania ]").queue();
//                    return;
//                }
//                new printBeatmap().beatmap(e.getChannel(), array[1], ((array.length > 2) ? array[2] : null));
//                break;
//
//        }
//    }

    String parseSpace(String data) {
        String tmp = data.substring(data.indexOf("\"") + 1);
        return tmp.substring(0, tmp.indexOf("\""));
    }
}
