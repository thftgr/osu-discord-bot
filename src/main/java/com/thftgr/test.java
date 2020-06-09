package com.thftgr;


import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class test {
    public static JsonObject settingValue ;


    public static void main(String[] args) {

        try {
            BufferedReader bf = new BufferedReader(new FileReader("setting/Setting.json"));
            System.out.println(bf.toString());





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }


}
