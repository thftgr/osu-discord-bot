package com.thftgr.osuPerformance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PerfomanceMain {

    public JsonObject ppCalc(JsonArray mapSetJsonArray) {
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
