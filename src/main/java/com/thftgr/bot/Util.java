package com.thftgr.bot;

import com.google.gson.JsonArray;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

public class Util {

    public void download(String sourceUrl, String targetFilename) {
        FileOutputStream fos = null;
        InputStream is = null;
        String pathAndFileName = Main.settingValue.get("downloadPath").getAsString() + targetFilename;
        try {
            fos = new FileOutputStream(pathAndFileName + ".tmp");
            is = new URL(sourceUrl).openConnection().getInputStream();
            byte[] buffer = new byte[8192];
            int readBytes;
            while ((readBytes = is.read(buffer)) != -1) {
                fos.write(buffer, 0, readBytes);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {

            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                File f = new File(pathAndFileName);
                if (f.isFile()) f.delete();
                System.out.println(pathAndFileName);

                renameFile(pathAndFileName + ".tmp", pathAndFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void divisionDownload(MessageChannel channel, int start, int end) {
        if ((end - start) >= 20) {
            int divv = ((end - start) / 4);
            int sets = start;


            while ((end - sets) > divv) {

                new Thread(new ThreadRun.beatmapDownload(channel, Integer.toString(sets), Integer.toString((sets + divv) - 1))).start();

                System.out.println(sets + ":" + ((sets + divv) - 1) + ":" + ((sets + divv) - sets));
                sets += divv;
            }

            System.out.println(sets + ":" + end + ":" + (end - sets));
            new Thread(new ThreadRun.beatmapDownload(channel, Integer.toString(sets), Integer.toString(end))).start();
        } else {
            new Thread(new ThreadRun.beatmapDownload(channel, Integer.toString(start), Integer.toString(end))).start();
            System.out.println(start + ":" + end + ":" + (end - start));
        }


    }

    void renameFile(String filename, String newFilename) {
        File file = new File(filename);
        File fileNew = new File(newFilename);
        if (file.exists()) file.renameTo(fileNew);
    }

    String grabUsername(String str) {

        int lineCnt = 0;
        int fromIndex = -1;
        while ((fromIndex = str.indexOf("\"", fromIndex + 1)) >= 0) {
            lineCnt++;
        }
        System.out.println(lineCnt);
        if (lineCnt == 2) {
            String[] array = str.split("\"");
            System.out.println("1:" + array[1]);
            return array[1];
        } else {
            String[] array = str.split(" ");
            System.out.println("2:" + array[1]);
            try {
                return URLEncoder.encode(array[1], "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return array[1];
            }
        }
    }

    JsonArray sortJsonarray(JsonArray jsonArray) {
        String[] diff = new String[jsonArray.size()];
        JsonArray jaa = new JsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            diff[i] = jsonArray.get(i).getAsJsonObject().get("difficultyrating").getAsString();
        }

        Arrays.sort(diff);
        for (int i = 0; i < jsonArray.size(); i++) {
            for (int j = 0; j < jsonArray.size(); j++) {
                if (diff[i].equals(jsonArray.get(j).getAsJsonObject().get("difficultyrating").getAsString())) {
                    jaa.add(jsonArray.get(j).getAsJsonObject());
                }
            }
        }
        return jaa;


    }

    String spacedString(String str) {

        int lineCnt = 0;
        int fromIndex = -1;
        while ((fromIndex = str.indexOf("\"", fromIndex + 1)) >= 0) {
            lineCnt++;
        }
        System.out.println(lineCnt);
        if (lineCnt == 2) {
            String[] array = str.split("\"");
            System.out.println("1:" + array[1]);
            return array[1];
        } else {
            String[] array = str.split(" ");
            System.out.println("2:" + array[1]);
            return array[1];

        }
    }


}
