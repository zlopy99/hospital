package Login;

import DataBase.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModul {
    Connection con;

    public LoginModul() {
        try {
            con = DbConnection.getConnection();
        }catch (Exception e){
            System.out.println("Nešto nije u redu: " + e);
        }

        if(con == null)
            System.exit(1);
    }

    // Provjera jesmo li spojeni sa bazom
    public boolean isConnected(){
        return con != null;
    }

    // Provjera jesmo li logirani
    public boolean isLogin(String usr, String passw, String opt) throws Exception{
        PreparedStatement prepS = null;
        ResultSet rs = null;

        // HASGBYTES('SHA1',lozinka) --> Enkripcija
        String querry = "SELECT * FROM korisnik where Korisnicko_ime = ? and Lozinka = PASSWORD(?) and Uloga = ?";

        try{
            prepS = con.prepareStatement(querry);
            prepS.setString(1,usr);
            prepS.setString(2,passw);
            prepS.setString(3,opt);

            rs = prepS.executeQuery();

            if(rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }

        }catch (Exception e){
            e.getMessage();
            return false;
        }finally {
            prepS.close();
            rs.close();
        }
    }
}
