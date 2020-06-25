package com.thftgr.z_notuse;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class test {


    public static void main(String[] args) {
        String path = "Y:\\home\\guest1241\\beatmaps\\";
        File SongsDir = new File(path);
        File[] f = SongsDir.listFiles();


        while (true){
            for (int i = 0; i < f.length ; i++) {
                //System.out.println(f[i].getName());
                try {
                    File tmp = new File(path + f[i].getName());

                    if (tmp.isDirectory() & tmp.getName().contains(" ")) {
                        tmp.renameTo(new File(path + f[i].getName().substring(0, f[i].getName().indexOf(" "))));
                        System.out.println(tmp.getName());
                    }

                } catch (Exception e) {
                    System.out.println("error : "+e.getMessage());
                }


            }
            System.out.println(f.length + ". fin");
        }



    }



    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }


}









