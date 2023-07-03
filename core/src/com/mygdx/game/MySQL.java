package com.mygdx.game;
import java.sql.*;



public class MySQL {
    private final String host = "localhost";
    private final String port = "3306";
    private final String database = "table";
    private final String username = "root";
    private final String password = "12345678";
    private static Connection con;




    public static void connect() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/q11_s01&table=table?user=q11_s01&password=12345678");
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    public static void disconnect() {

        try {
                con.close();
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    public static void update(String q){
        try {
            PreparedStatement ps = con.prepareStatement(q);
            ps.execute();

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