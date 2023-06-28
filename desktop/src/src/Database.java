package src;
import java.sql.*;
public class Database{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/delftstackDB";
        String username = "username";
        String password = "password";


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,username,password);

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select MAX(highscore) from table");
            System.out.println("Connected");
            while(rs.next()){
                System.out.println(rs.getInt(1) + " Score:" + rs.getInt(2));
            }

            con.close();

        }

        catch(Exception e) {
            System.out.println(e);
        }
    }
}
