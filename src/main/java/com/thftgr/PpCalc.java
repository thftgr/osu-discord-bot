package com.thftgr;

import com.github.francesco149.koohii.Koohii;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PpCalc {

    float ppCalc(File beatMapFile, float accuracy, int modesInt) {
//        JsonObject jo = new JsonObject();
        try {


            Koohii.Map beatmap = new Koohii.Parser().map(new BufferedReader(new FileReader(beatMapFile)));
            Koohii.DiffCalc stars = new Koohii.DiffCalc().calc(beatmap);
            Koohii.Accuracy acc = new Koohii.Accuracy(accuracy, beatmap.objects.size(), 0);
            Koohii.PPv2Parameters params = new Koohii.PPv2Parameters();

            params.beatmap = beatmap;
            params.aim_stars = stars.aim;
            params.speed_stars = stars.speed;
            params.mods = modesInt;
            params.n300 = acc.n300;
            params.n100 = acc.n100;
            params.n50 = acc.n50;
            params.nmiss = 0;
            params.score_version = 1;
            params.base_ar = beatmap.ar;
            params.base_od = beatmap.od;

            Koohii.PPv2 pp = new Koohii.PPv2(params);
//            jo.addProperty(beatmap.version, String.format("%.2f", pp.total));
            return (float) pp.total;
        } catch (Exception ignored) {
        }


        return -1f;

    }

    JsonObject ppCalcLocal(String mapSetid, float acc, int mods) {
        String downloadpath = Main.settingValue.get("downloadPath").getAsString();


        String oszname = new fileIO().getOszName(mapSetid);

        if (oszname == null) {
            if (!new ApiUtil().getBeatmapDownload(mapSetid)) return null;
            oszname = new fileIO().getOszName(mapSetid);
        }

        if(!new File(downloadpath+(oszname.substring(0,oszname.indexOf(".osz")))).isDirectory()){
            if (!new fileIO().unzipOsz(downloadpath, oszname)) return null;
        }



        String mapPath = new fileIO().getmapfilepath(mapSetid);
        String[] s = new fileIO().getMapFilename(mapSetid);
        JsonObject mapData = new JsonObject();
        for (int i = 0; i < s.length; i++) {
            File f = new File(mapPath + s[i]);
            JsonObject VandM = getMapData(f,acc,mods);

            //mapData.addProperty(VandM.get("version").getAsString(), VandM.getAsJsonObject());
            mapData.add(VandM.get("version").getAsString(),VandM);

        }
        System.gc();

        return mapData;
    }

    JsonObject getMapData(File mapFile,float acc,int mods) {

        try {
            BufferedReader bf = new BufferedReader(new FileReader(mapFile));
            String line = "";
            String versionString = "";
            String mode = "";
            while ((line = bf.readLine()) != null) {
                if(line.startsWith("//")) continue;

                if (line.startsWith("Mode:")) {
                    mode = line.substring(line.indexOf(":") + 1);
                    mode = mode.replaceAll(" ", "");
                }

                if (line.startsWith("Version:")) {
                    versionString = line.substring(line.indexOf(":") + 1);
                }

                if (line.contains("[TimingPoints]")) {
                    bf.close();
                    break;
                }
            }
            String ppValue = "-1";
            if (mode.equals("0")) {
                ppValue = String.format("%.2fpp", new PpCalc().ppCalc(mapFile, acc, mods));
            }


            JsonObject mapData = new JsonObject();
            mapData.addProperty("version", versionString);
            mapData.addProperty("mode", mode);
            mapData.addProperty("pp", ppValue);

            return mapData;

        } catch (Exception e){
            return null;
        }

    }


}
