package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String DbName = "hospital";
    private static final String User = "root";
    private static final String Password = "";
    private static final String ConnectionUrl = "jdbc:mysql://localhost:3306/"+ DbName;

    public static Connection getConnection() throws SQLException{
        try{
            return DriverManager.getConnection(ConnectionUrl, User, Password);
        }catch (Exception e){
            System.out.println("Nesto je poslo po zlu:\n" + e);
        }
        return null;
    }
}
