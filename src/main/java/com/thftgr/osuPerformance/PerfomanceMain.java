package com.thftgr.osuPerformance;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PerfomanceMain {

    public JsonObject ppCalc(JsonArray mapSetJsonArray) {
        JsonObject mapData = new JsonObject();
        for (int i = 0; i < mapSetJsonArray.size(); i++) {
            String version;
            float pp;

            version = mapSetJsonArray.get(i).getAsJsonObject().get("version").getAsString();
            JsonObject mapSetJsonObject = mapSetJsonArray.get(i).getAsJsonObject();
            switch (mapSetJsonArray.get(i).getAsJsonObject().get("mode").getAsInt()) {

                case 0:
                    pp = (float) new STD().Calculate(mapSetJsonObject);
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
                case 1:
                    pp = (float) new Taiko().Calculate(mapSetJsonObject);
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
                case 2:
                    pp = (float) new Catch().Calculate(mapSetJsonObject);
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
                case 3:
                    pp = (float) new Mania().Calculate(mapSetJsonObject);
                    mapData.addProperty(version, (int)pp+"pp");
                    break;
            }
        }
        return mapData;
    }
}
