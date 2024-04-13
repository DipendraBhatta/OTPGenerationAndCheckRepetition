package SendingOTPAndStoring;

import OTP.Encryption;
import frontend.WelcomePage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class Receive {
    Connection con;
    Statement stm;
    ResultSet rst;


    public void verification(String email, String otp, JFrame frame) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Store_OTP", "root", "");
            stm = con.createStatement();

            String getOTPQuery = String.format("select otp from otpTable5 where mail= '%s'", email);
            System.out.println(getOTPQuery);
            ResultSet resultSet = stm.executeQuery(getOTPQuery);

            if (resultSet.next()) {
                String otpFromDatabase = resultSet.getString("otp");
                System.out.println(otpFromDatabase);


                System.out.println(Encryption.encryptOTP(otp));


                if(Encryption.encryptOTP(otp).equals(otpFromDatabase)){
                    System.out.println("OTP has been verified");
                    Database.deletionOperation(email);
                    JOptionPane.showMessageDialog(null, "Your OTP has been verified successfully.", "Verification", JOptionPane.INFORMATION_MESSAGE);
                    new WelcomePage();
                    frame.dispose();
                }
                else {
                    System.out.println("You entered wrong OTP");
                    JOptionPane.showMessageDialog(null, "Your OTP is not correct.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                System.out.println("OTP time expired");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}