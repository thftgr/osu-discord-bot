package com.thftgr.bot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class Message {
    void sayMsg(MessageChannel channel, String msg, String Language) {
        channel.sendMessage("```" + Language + "\n" + msg + "```").queue();
    }

    String NonNull(JsonObject JO, String value) {
        if (JO.get(value) == null) {
            return "-";
        }
        return JO.get(value).getAsString();
    }

    void helpMessage(MessageChannel channel) {
        String hmsg = "Command: {\n" +
                "   [mode: 0 = osu!, 1 = Taiko, 2 = CtB, 3 = osu!mania ]\n\n" +
                "   !help, h\n\n" +

                "   !user, u [username/userID] [mode]\n" +
                "     > username have space? Between \". ex) !user \"O S U\"\n\n" +

                "   !map, m [mapID]\n\n" +

                "   !mapset, ms [mapSetID]\n\n" +

                "   !download, d [mapSetID] download beatmapSet to thftgr's server\n\n" +

                "   !rank_map_notice, rmn\n" +
                "     > osu! new rank map notice this Channel\n\n" +

                "}\n";
        sayMsg(channel, hmsg, null);

    }

    void beatmapPrint(MessageChannel channel, String mapID) {

        MessageBuilder mb = new MessageBuilder();



        EmbedBuilder eb = new EmbedBuilder().setColor(new Color(255, 255, 255));

        mb.mapSetJsonArray = new osuApiCall().call("get_beatmaps", "&b=" + mapID);
        mb.mapJsonObject = mb.mapSetJsonArray.get(0).getAsJsonObject();
        if (mb.mapJsonObject == null) {// 맵이 없는경우
            channel.sendMessage(eb.setDescription("😢 BeatmapDecoder Not Found! Check mapID").build()).queue();
            return;
        }


        String mapSetID = mb.mapJsonObject.get("beatmapset_id").getAsString();

        String sb = NonNull(mb.mapJsonObject, "artist") + " - " + NonNull(mb.mapJsonObject, "title");

        if (!mb.mapJsonObject.get("creator").isJsonNull()) sb += " \nby " + mb.mapJsonObject.get("creator").getAsString();

        eb.setAuthor(sb, "https://osu.ppy.sh/s/" + mapSetID, null);



        eb.setDescription(mb.mapInfo());
        eb.setImage("https://b.ppy.sh/thumb/" + mapSetID + "l.jpg");

        String map_date;
        if (!mb.mapJsonObject.get("approved_date").isJsonNull()) {
            map_date = mb.mapJsonObject.get("approved_date").getAsString();

        } else if (!mb.mapJsonObject.get("last_update").isJsonNull()) {
            map_date = mb.mapJsonObject.get("last_update").getAsString();

        } else {
            map_date = mb.mapJsonObject.get("submit_date").getAsString();
        }

        eb.setFooter("❤  " + NonNull(mb.mapJsonObject, "favourite_count") + "  |  " + approvedStatus(Integer.parseInt(NonNull(mb.mapJsonObject, "approved"))) + "  |  " + map_date + " UTC +0");
        String messageID = channel.sendMessage(eb.build()).complete().getId();
        if (mb.mapJsonObject.get("mode").getAsInt() != 1) editMessage(channel, messageID, eb.setDescription(mb.mapInfoWithPP()));


    }

    void beatmapSetPrint(MessageChannel channel, String mapSetID, String _title) {

        EmbedBuilder eb = new EmbedBuilder().setColor(new Color(255, 255, 255));
        JsonArray mapSetJsonArray = new osuApiCall().call("get_beatmaps", "&s=" + mapSetID);


        if (mapSetJsonArray == null) { //맵 조회
            eb.setDescription("😢 BeatmapSet Not Found! Check mapSetID");
            channel.sendMessage(eb.build()).queue();
            return;
        }

        mapSetJsonArray = new Util().sortJsonarray(mapSetJsonArray);
        JsonObject JO = mapSetJsonArray.get(0).getAsJsonObject();

        String sb = _title + NonNull(JO, "artist") + " - " + NonNull(JO, "title");

        if (NonNull(JO, "creator") != null) {
            sb += " \nby " + (NonNull(JO, "creator"));
        }
        eb.setAuthor(sb, "https://osu.ppy.sh/s/" + mapSetID, null);


        eb.setImage("https://b.ppy.sh/thumb/" + mapSetID + "l.jpg");
        String map_date;

        if (!mapSetJsonArray.get(0).getAsJsonObject().get("approved_date").isJsonNull()) {
            map_date = mapSetJsonArray.get(0).getAsJsonObject().get("approved_date").getAsString();

        } else if (!mapSetJsonArray.get(0).getAsJsonObject().get("last_update").isJsonNull()) {
            map_date = mapSetJsonArray.get(0).getAsJsonObject().get("last_update").getAsString();

        } else {
            map_date = mapSetJsonArray.get(0).getAsJsonObject().get("submit_date").getAsString();
        }

        eb.setFooter("❤  " + NonNull(JO, "favourite_count") + "  |  " + approvedStatus(mapSetJsonArray.get(0).getAsJsonObject().get("approved").getAsInt()) + "  |  " + map_date + " UTC +0");


        MessageBuilder mb = new MessageBuilder();
        mb.mapSetJsonArray = mapSetJsonArray;

        eb.setDescription(mb.mapSetInfo());
        String messageID = channel.sendMessage(eb.build()).complete().getId();

        eb.setDescription(mb.mapSetInfoWithPP());
        editMessage(channel, messageID, eb);


    }

    void UserInfo(MessageChannel channel, String username, String mode) {
        try {
            JsonArray PF = new osuApiCall().call("get_user", "&u=" + username + "&m=" + mode);
            JsonArray BP = new osuApiCall().call("get_user_best", "&u=" + username + "&m=" + mode);
            System.out.println(PF.toString());

            new Message().sayMsg(channel, new MessageBuilder().userInfo(PF, BP, mode), "json");

        } catch (Exception e) {
            System.out.println(e);
            new Message().sayMsg(channel, "Can't find User. try user ID", "json");
        }
    }

    void sayEmbed(MessageChannel channel, String msg) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(255, 255, 255));
        eb.setDescription(msg);
        channel.sendMessage(eb.build()).queue();
    }

    String approvedStatus(int approvedInt) {
        switch (approvedInt) {
            case -2:
                return "Graveyard";
            case -1:
                return "WIP";
            case 0:
                return "Pending";
            case 1:
                return "Ranked";
            case 2:
                return "Approved";
            case 3:
                return "Qualified";
            case 4:
                return "Loved";
            default:
                return "-";
        }

    }

    void addPage(net.dv8tion.jda.api.entities.Message message) {
        message.addReaction("U+23EA").queue();
        message.addReaction("U+23E9").queue();
    }

    void editMessage(MessageChannel channel, String messageID, EmbedBuilder eb) {
        channel.editMessageById(messageID, eb.build()).queue();
    }


    String[] embedVeryLongString(String Description) {


        String[] s = Description.split("\n");
        String[] ret = new String[2];

        for (int i = 0; i < s.length; i++) {

            if (ret[0].length() <= 2048) {
                ret[0] += s[i] + "\n";
            } else if (ret[1].length() <= 2048) {
                ret[1] += s[i] + "\n";
            }
        }
        return ret;
    }


}
