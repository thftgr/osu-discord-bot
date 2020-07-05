package com.thftgr.z_notuse;


import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.thftgr.discord.Main;
import com.thftgr.osu_Servers.bancho.Api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.Random;

public class test {


    public static void main(String[] arg) throws Exception {
        Connection conn;
        Statement stmt;
        ResultSet rs;

        Class.forName("com.mysql.cj.jdbc.Driver");  // JDBC driver를 메모리에 로드
        conn = DriverManager.getConnection("jdbc:mysql://direct.debian.moe:3306/Ainu?serverTimezone=UTC", "id", "passwd");
        stmt = conn.createStatement();
        //SELECT [칼럼명] FROM [테이블명] WHERE [칼럼명] LIKE 특정문자열;
        rs = stmt.executeQuery("SELECT * FROM beatmaps WHERE id LIKE 33");  // 원하는 쿼리문 실행

        while (rs.next()) {
//            System.out.print(rs.getString("song_name") + "\n");
            System.out.print(rs.getString("pp_100") + "\n");
//            System.out.print(rs.getString("") + "\n");
//            System.out.print(rs.getString("") + "\n");
//            System.out.print(rs.getString("") + "\n");
//            System.out.print(rs.getString("") + "\n");

        }

        rs.close();
        stmt.close();
        conn.close();
    }


}









