package com.thftgr.z_notuse;


import com.thftgr.bot.Main;
import com.thftgr.bot.ThreadRun;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class test {


    public static void main(String[] args) {
        new test().fileNameRebuild();
    }


    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }

    void fileNameRebuild(){
        String path = "Y:\\_tmp\\";
        File SongsDir = new File(path);
        File[] f = SongsDir.listFiles();


        for (int i = 0; i < f.length; i++) {

            try {
                File tmp = new File(path + f[i].getName());

//                if (tmp.isDirectory() & tmp.getName().contains(" ")) {
                if (tmp.getName().contains(" ")) {
                    String filename = path + f[i].getName().substring(0,f[i].getName().indexOf(" "))+".osz";
                    tmp.renameTo(new File(filename));
                    System.out.println(filename);
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }

                }

            } catch (Exception e) {
                System.out.println("error : " + e.getMessage());
            }
        }
        System.out.println(f.length + ". fin");
        System.gc();

    }


}









