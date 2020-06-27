package com.thftgr.bot;

import com.google.gson.JsonArray;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class fileIO {

//    Boolean localOszIsNew(JsonArray mapInfo) {
//        //업데이트 리스트 줄줄이 비교후 하나라도 업데이트되었으면 false 반환
//        MessageBuilder mb =new MessageBuilder();
//        mb.mapJsonObject = mapInfo.get(0).getAsJsonObject();
//        File fileTmp = new File(Main.settingValue.get("downloadPath").getAsString() + mb.oszFileNameBuilder());
//        if (fileTmp.exists()) {
//            try {
//
//                for (int i = 0; i < mapInfo.size(); i++) {
//
//                    String last_update = new MessageBuilder().NonNull(mapInfo.get(0).getAsJsonObject(), "last_update");
//                    long epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(last_update).getTime();
//                    if (((epoch+32400) / 1000) < (fileTmp.lastModified() / 1000)) {
//                        return true;
//                    }
//                }
//
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//
//            }
//            return false;
//        } else {
//            return false;
//        }
//
//    }
//void divisionDownload(MessageChannel channel, int start, int end) {
//    if ((end - start) >= 20) {
//        int divv = ((end - start) / 4);
//        int sets = start;
//
//
//        while ((end - sets) > divv) {
//
//            new Thread(new ThreadRun.beatmapDownload(channel, Integer.toString(sets), Integer.toString((sets + divv) - 1))).start();
//
//            System.out.println(sets + ":" + ((sets + divv) - 1) + ":" + ((sets + divv) - sets));
//            sets += divv;
//        }
//
//        System.out.println(sets + ":" + end + ":" + (end - sets));
//        new Thread(new ThreadRun.beatmapDownload(channel, Integer.toString(sets), Integer.toString(end))).start();
//    } else {
//        new Thread(new ThreadRun.beatmapDownload(channel, Integer.toString(start), Integer.toString(end))).start();
//        System.out.println(start + ":" + end + ":" + (end - start));
//    }
//
//
//}

//    void textLineRemover(String fileName, String removeText) throws IOException { //텍스트 파일 읽어서 해당하는 라인 삭제
//
//        String msg;
//        String tmp = "";
//
//        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
//        while ((msg = br.readLine()) != null) {
//
//            if (!msg.equals(removeText)) {
//                tmp += (msg + "\n");
//            }
//        }
//        br.close();
//
//        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
//        PrintWriter pw = new PrintWriter(bw, true);
//        pw.write(tmp);
//        pw.flush();
//        pw.close();
//    }
//
//    String[] textFileReader(String Filename) {
//        try {
//            BufferedReader in;
//            String[] text;
//
//            int lineCnt = 0;
//
//            File file = new File(Filename);
//
//            in = new BufferedReader(new FileReader(file));
//
//            while ((in.readLine()) != null) {
//                lineCnt++;
//            }
//            text = new String[lineCnt];
//
//            in = new BufferedReader(new FileReader(file));
//            for (int i = 0; i < lineCnt; i++) {
//                text[i] = in.readLine();
//            }
//            return text;
//
//
//        } catch (IOException e) {
//            return null;
//        }
//
//    }

//    Boolean unzipOsz(String path, String filename) {
//        if (path == null || filename == null) return false;
//        String source = path + filename;
//        try {
//            new ZipFile(source).extractAll(source.substring(0, source.indexOf(".osz")));
//            return true;
//        } catch (ZipException e) {
//            return false;
//        }
//    }

//    String getOszName(String mapID) {
//        String path = Main.settingValue.get("downloadPath").getAsString();
//        File[] matchingFiles = new File(path).listFiles(new FilenameFilter() {
//            public boolean accept(File dir, String name) {
//                return name.startsWith(mapID + " ") && (name.endsWith(".osz"));
//            }
//        });
//
//        if (matchingFiles.length == 0) return null;
//        return matchingFiles[0].getName();
//
//    }

//    String getUnzipPath(String mapID) {
//        String oszname;
//        if ((oszname = getOszName(mapID)) == null) return null;
//        return oszname.substring(0, oszname.indexOf(".osz")) + "/";
//    }

//    String[] getMapFilename(String mapSetID) {
//        String path = Main.settingValue.get("downloadPath").getAsString() + "/" + getUnzipPath(mapSetID);
//        File[] matchingFiles = new File(path).listFiles(new FilenameFilter() {
//            public boolean accept(File dir, String name) {
//                return name.endsWith(".osu");
//            }
//        });
//
//        String[] list = new String[matchingFiles.length];
//        for (int i = 0; i < matchingFiles.length; i++) {
//            list[i] = matchingFiles[i].getName();
//        }
//        return list;
//    }
//
//    String getmapfilepath(String mapSetID) {
//        return Main.settingValue.get("downloadPath").getAsString() + "/" + getUnzipPath(mapSetID);
//    }
//
//    void removeDir(String dirName) {
//
//        System.gc();
//        File deleteFolder = new File(dirName);
//
//        if (deleteFolder.exists()) {
//            File[] deleteFolderList = deleteFolder.listFiles();
//
//            for (int i = 0; i < deleteFolderList.length; i++) {
//                if (deleteFolderList[i].isFile()) {
//                    deleteFolderList[i].delete();
//                } else {
//                    removeDir(deleteFolderList[i].getPath());
//                }
//                deleteFolderList[i].delete();
//            }
//            deleteFolder.delete();
//        }
//    }

    void settingSave(){
        try {
            FileWriter fw = new FileWriter("setting/Setting.json", false);
            fw.write(Main.settingValue.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
