package com.mygdx.game;
import java.sql.*;



public class MySQL {
    private static final String host = "localhost";
    private static final String port = "3306";
    private static final String database = "table";
    private static final String username = "root";
    private static final String password = "12345678";
    private static Connection con;



    @SuppressWarnings("deprecation")
    public static void connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/q11_s01&table=table?user=q11_s01&password=12345678");
        }
        catch (SQLException e) {
                e.printStackTrace();
        }
    }

    public static void disconnect() {

        try {
                con.close();
        } catch (SQLException e) {
                e.printStackTrace();
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