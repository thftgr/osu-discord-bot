package com.thftgr.z_notuse;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class test {
    public static JsonObject settingValue;

    public static void main(String[] arg) throws Exception {
        settingValue = (JsonObject) JsonParser.parseReader(new Gson().newJsonReader(new FileReader("setting/deb.json")));

        System.out.println(new test().beatMap("-","beatmap_md5","bfa88bdd7604d3fdc5e862175281b9df",0).toString());
    }

    JsonArray beatMap(String from, String where, String like, int limit) {
        // connection setting

        String user = settingValue.get("databaseUser").getAsString();
        String passwd = settingValue.get("databaseUserPassWord").getAsString();
        String databaseUrl = settingValue.get("databaseUrl").getAsString();
        String databasePort = settingValue.get("databasePort").getAsString();
        String databasePath = settingValue.get("databasePath").getAsString();
        JsonArray beatMapJsonArray = new JsonArray();
        JsonObject beatMapJsonObject = new JsonObject();

        try {
            //query database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn;
            Statement stmt;
            ResultSet rs;

            conn = DriverManager.getConnection("jdbc:mysql://" + databaseUrl + ":" + databasePort  + "?serverTimezone=UTC", user, passwd);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + from + " WHERE " + where + " LIKE '" + like + "'");  // 원하는 쿼리문 실행
            for (int i = 0; (limit == 0) ? rs.next() : (i < limit & rs.next()) ; i++) {
                for (int j = 1; j < rs.getMetaData().getColumnCount() + 1; j++) {
                    beatMapJsonObject.addProperty(rs.getMetaData().getColumnName(j), rs.getString(rs.getMetaData().getColumnName(j)));

                }
                beatMapJsonArray.add(beatMapJsonObject);
                beatMapJsonObject = new JsonObject();
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            beatMapJsonArray.add(e.getMessage());
        }
        return beatMapJsonArray;

    }




}









