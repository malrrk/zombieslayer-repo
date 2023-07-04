package com.mygdx.game;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{
    int i;
    public Database(){

        i = 0;
    }



    public void out(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Q11_S01?user=root&password=12345678");
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM Q11_S01.newtable ORDER BY time DESC");

            while(res.next()){
                System.out.print("Username: " + res.getString("username") + "   ");
                System.out.print("Time: " + res.getInt("time") + "   ");
                System.out.println("Kills: " + res.getInt("kills") + "   ");
            }

            res.close();
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception ex){
            System.out.println(ex + "shit");
        }
    }

    public void in(String u, int t, int k){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Q11_S01?user=root&password=12345678");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Q11_S01.newtable(`id`,`username`,`time`,`kills`) VALUES(" + i + "," + "'" +u +"'" +"," + t + "," + k + ")");
            pstmt.execute();
            conn.close();
            i++;
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception ex){
            System.out.println(ex);
        }

    }


}