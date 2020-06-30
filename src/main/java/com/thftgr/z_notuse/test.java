package com.thftgr.z_notuse;


public class test {


    public static void main(String[] args) {
System.out.println(new com.thftgr.discord.Util().GetUTCtime());

        //System.out.println();
    }


    public void parseMods(String mods) {
        String[] mod = new String[mods.length() / 2];
        for (int i = 0; i < (mods.length() / 2); i++) {
            mod[i] = mods.substring((i * 2), ((i * 2) + 2)).toUpperCase();
            System.out.println(mod[i]);
        }
    }






}









