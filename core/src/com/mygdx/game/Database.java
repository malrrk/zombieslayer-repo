package com.mygdx.game;
import java.sql.*;



public class Database {
    private static final String host = "localhost";
    private static final String port = "3306";
    private static final String database = "table";
    private static final String username = "root";
    private static final String password = "12345678";
    private static Connection con;

    public static boolean isConnected() {
        return (con == null ? false : true);
    }

    public static void connect() throws ClassNotFoundException {
        if (!isConnected()) {

            Class.forName("com.mysql.jdbc.Driver");
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        public static void update(String q){
            try {
                PreparedStatement ps = con.prepareStatement(q);
                ps.execute();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }






}