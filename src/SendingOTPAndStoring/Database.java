package SendingOTPAndStoring;

import OTP.Encryption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    static Connection con;
    static Statement stm;
    ResultSet rst;


    public Database(String mail, String otp) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Store_OTP", "root", "");
            stm = con.createStatement();
            String query = "CREATE database IF NOT EXISTS Store_OTP";
            stm.executeUpdate(query);

            String create_table =
                    "CREATE TABLE IF NOT EXISTS otpTable5(mail varchar(111) primary key, otp varchar(12), curtime time, expirytime time)";
            stm.executeUpdate(create_table);


            String checkQuery = String.format("SELECT mail FROM otpTable5 WHERE mail = '%s'", mail);
            ResultSet resultSet = stm.executeQuery(checkQuery);

            if (resultSet.next()) {
                String deleteQuery = String.format("DELETE FROM otpTable5 WHERE mail = '%s'", mail);
                stm.executeUpdate(deleteQuery);
            }

            String insertQuery = String.format("INSERT INTO otpTable5 VALUES ('%s', '%s', curtime(), DATE_ADD(curtime(), INTERVAL 1 MINUTE))", mail, Encryption.encryptOTP(otp));
            stm.executeUpdate(insertQuery);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deletionOperation(String email){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Store_OTP", "root", "");
            stm = con.createStatement();
            String deleteQuery = String.format("DELETE FROM otpTable5 WHERE mail = '%s'", email);
            stm.executeUpdate(deleteQuery);
            System.out.println("the otp has been deleted");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
