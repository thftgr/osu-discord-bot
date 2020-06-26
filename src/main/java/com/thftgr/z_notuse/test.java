package com.thftgr.z_notuse;


import java.io.File;

public class test {


    public static void main(String[] args) {
        String path = "Y:\\home\\guest1241\\beatmaps\\";
        File SongsDir = new File(path);
        File[] f = SongsDir.listFiles();


        for (int i = 0; i < f.length; i++) {
            //System.out.println(f[i].getName());
            try {
                File tmp = new File(path + f[i].getName());

                if (tmp.isDirectory() & tmp.getName().contains(" ")) {
                    String filename = path + f[i].getName().substring(0, f[i].getName().indexOf(" "));
                    tmp.renameTo(new File(filename));
                    System.out.println(filename);


                }

            } catch (Exception e) {
                System.out.println("error : " + e.getMessage());
            }
        }
        System.out.println(f.length + ". fin");
        System.gc();

    }


    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }


}









