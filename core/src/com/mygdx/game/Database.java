package com.mygdx.game;
import java.sql.*;
public class Database {


    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/delftstackDB";
        String username = "q11_s01";
        String password = "12345";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select MAX(Time) from table");
            System.out.println("Connected");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " Time:" + rs.getInt(2));
            }

            con.close();

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

}
