package com.thftgr.discord;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thftgr.osuPerformance.Catch;
import com.thftgr.osuPerformance.Mania;
import com.thftgr.osuPerformance.STD;
import com.thftgr.osuPerformance.Taiko;
import com.thftgr.webApi.WebApi;

public class MessageBuilder {
    public JsonArray mapSetJsonArray;
    public JsonObject mapJsonObject;

    String NonNull(JsonObject JO, String value) {
        try {
            return JO.get(value).getAsString();
        } catch (UnsupportedOperationException e) {
            return "-";
        }
    }

    String mapInfo() {

        int i = Integer.parseInt(NonNull(mapJsonObject, "total_length"));

        String msg = "";
        String BloodcatLink;
        if ((BloodcatLink = new WebApi().getBloodcatPriviewLink(NonNull(mapJsonObject, "beatmap_id"))) != null)
            msg += "▸[Preview](" + BloodcatLink + ")\n";

        msg += "▸Mode: " + modeParse(NonNull(mapJsonObject, "mode")) + "\n";
        msg += "▸PlayTime: " + (i / 60) + "m " + (i - ((i / 60) * 60)) + "s ▸BPM: " + NonNull(mapJsonObject, "bpm") + "\n";
        msg += "▸Map Version: " + NonNull(mapJsonObject, "version") + "\n";
        msg += "▸Difficulty: " + String.format("%.2f", Float.parseFloat(NonNull(mapJsonObject, "difficultyrating"))) + "★\n";
        msg += "▸Max Combo: x" + NonNull(mapJsonObject, "max_combo") + "\n";
        msg += "▸AR: " + NonNull(mapJsonObject, "diff_approach") + " ";
        msg += "▸OD: " + NonNull(mapJsonObject, "diff_overall") + " ";
        msg += "▸HP: " + NonNull(mapJsonObject, "diff_drain") + " ";
        int mod = mapJsonObject.get("mode").getAsInt();

        if (mod == 3) {
            msg += "▸KEY " + mapJsonObject.get("diff_size").getAsString() + "k";
        }
        msg += "▸CS: " + NonNull(mapJsonObject, "diff_size") + " \n";

        return msg;
    }

    String mapInfoWithPP() {

        //여기에 상세정보를 제작
        int i = Integer.parseInt(NonNull(mapJsonObject, "total_length"));

        String msg = "";
        String BloodcatLink;
        if ((BloodcatLink = new WebApi().getBloodcatPriviewLink(NonNull(mapJsonObject, "beatmap_id"))) != null)
            msg += "▸[Preview](" + BloodcatLink + ")\n";

        msg += "▸Mode: " + modeParse(NonNull(mapJsonObject, "mode")) + "\n";
        msg += "▸PlayTime: " + (i / 60) + "m " + (i - ((i / 60) * 60)) + "s ▸BPM: " + NonNull(mapJsonObject, "bpm") + "\n";
        msg += "▸Map Version: " + NonNull(mapJsonObject, "version") + "\n";
        msg += "▸Difficulty: " + String.format("%.2f", Float.parseFloat(NonNull(mapJsonObject, "difficultyrating"))) + "★\n";
        msg += "▸Max Combo: x" + NonNull(mapJsonObject, "max_combo") + "\n";
        msg += "▸AR: " + NonNull(mapJsonObject, "diff_approach") + " ";
        msg += "▸OD: " + NonNull(mapJsonObject, "diff_overall") + " ";
        msg += "▸HP: " + NonNull(mapJsonObject, "diff_drain") + " ";
        int mod = mapJsonObject.get("mode").getAsInt();

        if (mod == 3) {
            msg += "▸KEY " + mapJsonObject.get("diff_size").getAsString() + "k";
        }

        msg += "▸CS: " + NonNull(mapJsonObject, "diff_size") + " \n";


        JsonObject mapData = ppCalc(mapSetJsonArray);
        msg += "▸PP: " + mapData.get(mapJsonObject.get("version").getAsString()).getAsString();

        return msg;
    }

    String mapSetInfo() {
        String[] modeList = {"", "", "", "", ""};

        int i = avgPlaytime();
        String info = "▸PlayTime AVG: " + (i / 60) + "m " + (i - ((i / 60) * 60)) + "s ▸BPM: " + NonNull(mapSetJsonArray.get(0).getAsJsonObject(), "bpm") + "\n";

        for (int ii = 0; ii < mapSetJsonArray.size(); ii++) {
            JsonObject J = mapSetJsonArray.get(ii).getAsJsonObject();
            String msg = "★" + String.format("%,.2f", Double.parseDouble(J.get("difficultyrating").getAsString())) + " -  " + J.get("version").getAsString();
            int mod = J.get("mode").getAsInt();
            if (mod == 3) {
                msg += " [" + J.get("diff_size").getAsString() + " key]";
            }
            modeList[J.get("mode").getAsInt()] += msg + "\n";

        }

        if (!(modeList[0].equals(""))) {
            modeList[4] += "[osu!]\n" + modeList[0] + "\n";
        }
        if (!(modeList[1].equals(""))) {
            modeList[4] += "[osu!Taiko]\n" + modeList[1] + "\n";
        }
        if (!(modeList[2].equals(""))) {
            modeList[4] += "[osu!catch]\n" + modeList[2] + "\n";
        }
        if (!(modeList[3].equals(""))) {
            modeList[4] += "[osu!Mania]\n" + modeList[3] + "\n";
        }
        return info + "\n" + modeList[4];
    }

    String mapSetInfoWithPP() {
        String[] modeList = {"", "", "", "", ""};

        int playtime = avgPlaytime();
        String info = "▸PlayTime AVG : " + (playtime / 60) + "m " + (playtime - ((playtime / 60) * 60)) + "s ▸BPM: " + NonNull(mapSetJsonArray.get(0).getAsJsonObject(), "bpm") + "\n";
        long nanoTime = System.nanoTime();
        JsonObject mapData = ppCalc(mapSetJsonArray);

        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            JsonObject J = mapSetJsonArray.get(i).getAsJsonObject();
            String msg = "★" + String.format("%,.2f", Double.parseDouble(J.get("difficultyrating").getAsString())) + " -  " + J.get("version").getAsString();
            int mod = J.get("mode").getAsInt();


            if (mod != 3) {
                msg += " " + mapData.get(J.get("version").getAsString()).getAsString();
            }else{
                msg += " [" + J.get("diff_size").getAsString() + " key] " + mapData.get(J.get("version").getAsString()).getAsString();
            }


            modeList[J.get("mode").getAsInt()] += msg + "\n";
        }
        System.out.println(((System.nanoTime() - nanoTime) / 1000000) + "ms");

        if (!(modeList[0].equals(""))) {
            modeList[4] += "[osu!]\n" + modeList[0] + "\n";
        }
        if (!(modeList[1].equals(""))) {
            modeList[4] += "[osu!Taiko]\n" + modeList[1] + "\n";
        }
        if (!(modeList[2].equals(""))) {
            modeList[4] += "[osu!catch]\n" + modeList[2] + "\n";
        }
        if (!(modeList[3].equals(""))) {
            modeList[4] += "[osu!Mania]\n" + modeList[3] + "\n";
        }
        return info + "\n" + modeList[4] + "CT = " + ((System.nanoTime() - nanoTime) / 1000000) + "ms";
    }

    String userInfo(JsonArray user, JsonArray BestMap, String mode) {
        JsonObject PF = user.get(0).getAsJsonObject();

        String Best_pp;
        if (BestMap != null) {
            Best_pp = "    Best_pp          :  " + NonNull(BestMap.get(0).getAsJsonObject(), "pp") + "pp\n";
        } else {
            Best_pp = "    Best_pp          :  -\n";
        }

        String playtime;
        if (!NonNull(PF, "total_seconds_played").equals("-")) {
            int s = Integer.parseInt(PF.get("total_seconds_played").getAsString()), m = s / 60, h = (m / 60), d = (h / 24);
            playtime = "    playtime         :  " + (d + "d " + (h - (d * 24)) + "h " + (m - (h * 60)) + "m " + (s - (m * 60)) + "s") + "\n\n";
        } else {
            playtime = "    playtime         :  -\n\n";
        }

        String acc;
        if (!NonNull(PF, "accuracy").equals("-")) {
            if (NonNull(PF, "accuracy").length() > 5) {
                acc = "    accuracy         :  " + NonNull(PF, "accuracy").substring(0, 5) + "%\n";
            } else {
                acc = "    accuracy         :  " + NonNull(PF, "accuracy") + "%\n";
            }
        } else {
            acc = "    accuracy         :  -\n";
        }

        String pr = NonNull(PF, "country") + " " + NonNull(PF, "username") + "'s profile [" + modeParse(mode) + "]\n" +
                "    user_id          :  " + NonNull(PF, "user_id") + "\n" +
                "    join_date        :  " + NonNull(PF, "join_date") + "\n" +
                playtime +
                "    level            :  " + NonNull(PF, "level") + "\n" +
                "    playcount        :  " + NonNull(PF, "playcount") + "\n" +

                Best_pp +
                "    pp               :  " + NonNull(PF, "pp_raw") + "pp\n" +
                "    pp_rank          :  #" + NonNull(PF, "pp_rank") + "\n" +
                "    country_rank     :  #" + NonNull(PF, "pp_country_rank") + "\n" +

                acc +


                "    ranked_score     :  " + NonNull(PF, "ranked_score") + "\n" +
                "    total_score      :  " + NonNull(PF, "total_score") + "\n";
        System.out.println(pr);
        return pr;
    }

    String modeParse(String modeInt) {
        switch (modeInt) {
            case "0":
                return "Standard";

            case "1":
                return "Taiko";

            case "2":
                return "Catch The Beat";

            case "3":
                return "Mania";

            default:
                return "Standard";

        }

    }

    int avgPlaytime() {
        int avgPlayTime = 0;
        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            //total_length
            if (mapSetJsonArray.get(i).getAsJsonObject().get("total_length").isJsonNull()) continue;
            avgPlayTime += mapSetJsonArray.get(i).getAsJsonObject().get("total_length").getAsInt();
        }
        return avgPlayTime / mapSetJsonArray.size();
    }

    JsonObject ppCalc(JsonArray mapSetJsonArray) {
        JsonObject mapData = new JsonObject();
        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            String version = "";
            float pp = 0;
            switch (mapSetJsonArray.get(i).getAsJsonObject().get("mode").getAsInt()) {
                case 0:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = (float) new STD().Calculate(mapSetJsonArray.get(i).getAsJsonObject());
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
                case 1:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = (float) new Taiko().Calculate(mapSetJsonArray.get(i).getAsJsonObject());
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
                case 2:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = (float) new Catch().Calculate(mapSetJsonArray.get(i).getAsJsonObject());
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
                case 3:
                    version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
                    pp = (float) new Mania().Calculate(mapSetJsonArray.get(i).getAsJsonObject());
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
            }
        }
        return mapData;
    }


}