package RepetitionCount;
import OTP.*;

import java.io.*;
import java.util.Scanner;

public class GeneratingOTPs {

    static long generate(long s1, long s2, long s3, long s4) {
        return (s1 + s3 * s2 + s4) % 1000000;
    }

    public static void generateOTP(int length) {
        // BBS
        long p = 18913;
        long q = 85729;
        long seed = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("blumblum.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                seed = Long.parseLong(line);
            } else{
                seed = 123456789;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        BlumBlumShub b = new BlumBlumShub(p, q, seed);
        UpdateSeed blum = new UpdateSeed(seed, "blumblum");

        // LCM
        long m = 189913;
        long a = 985729;
        long c = 12345;
        long initial = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("blumblum.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                initial = Long.parseLong(line);
            } else{
                initial = 987654321;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        LCM l = new LCM(m, a, c, initial);
        UpdateSeed lcm = new UpdateSeed(seed, "lcm");

        long updateNewBlum = Integer.parseInt(blum.getNumber());
        long updateNewLCM = Integer.parseInt(lcm.getNumber());
        blum.updateNumber((updateNewBlum + b.generateNext()) % 1000000);
        lcm.updateNumber((updateNewLCM + l.generation()) % 1000000);



// Clear the contents of otp.txt at the beginning of each run

        try {
            FileOutputStream fout = null;
                fout = new FileOutputStream("otp.txt");
                fout.flush();

        } catch (Exception exec) {
            System.out.println(exec.getMessage());
        }

        for (int i = 0; i < length; i++) {
            String balancedOTP = String.format("%06d", generate(b.generateNext() + updateNewBlum, l.generation() + updateNewLCM, updateNewBlum, updateNewLCM));

            //System.out.println(b.generateNext() * updateNewBlum + " " +  l.generation() + " " + updateNewBlum + " " + updateNewLCM);

            String finalOTP = String.valueOf(balancedOTP);
            System.out.println(finalOTP);

            try {
                //Append mode
                FileOutputStream fout = new FileOutputStream("otp.txt", true);
                fout.write(finalOTP.getBytes());
                fout.write("\n".getBytes()); // Add a newline for each OTP
                fout.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("**********");
            System.out.println("Enter an OTP that you want to count Repetition (or type 'exit' to exit):");

            String userInput = sc.next();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the program.");
                break; // Exit the loop if the user enters "exit"
            }

            try {
                int check = Integer.parseInt(userInput);
                String checkToString = Integer.toString(check);
                CountRepetition count = new CountRepetition(checkToString);
                ReadOTP readOTP = new ReadOTP();
                int[][] otpArray = readOTP.readOTPFromFile("otp.txt");
                count.ComparingOTP(otpArray);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid OTP or 'exit' to exit.");
            }
        }
        sc.close();
    }
}