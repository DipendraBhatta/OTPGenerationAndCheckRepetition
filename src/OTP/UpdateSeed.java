package OTP;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdateSeed {

    long number;
    String filename;

    public UpdateSeed(long number, String filename){
        this.number = number;
        this.filename = filename;
    }

    public void updateNumber(long num){
        try{
            FileOutputStream fout = new FileOutputStream(filename + ".txt");
            String op = String.valueOf(num);
            fout.write(op.getBytes());
            fout.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String getNumber(){
        String number = null;
        try{
            FileInputStream fin = new FileInputStream(filename + ".txt");
            int rv = 0;
            StringBuilder otp = new StringBuilder();
            while((rv = fin.read()) != -1){
                otp.append((char)rv);
            }
            updateNumber(Integer.parseInt(otp.toString()));
            number = otp.toString();
            fin.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return number;
    }
}

