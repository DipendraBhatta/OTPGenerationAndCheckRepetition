package RepetitionCount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadOTP {

    public void createFle() {
        String filePath = "otp.txt"; // Replace with your actual file path

        int[][] otpArray = readOTPFromFile(filePath);

        // Print out the OTPs
        for (int i = 0; i < otpArray.length; i++) {
            for (int j = 0; j < otpArray[i].length; j++) {
                System.out.print(otpArray[i][j]);
            }
            System.out.println(); // Move to the next line for the next OTP
        }


    }


    public static int[][] readOTPFromFile(String filePath) {
        int[][] otpArray = new int[1000000][6]; // Assuming 10000000 OTPs of 6 digits each

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < 1000000 && scanner.hasNextLine(); i++) {
                String otp = scanner.nextLine().trim();

                // Validate if the OTP is exactly 6 digits
                if (otp.length() == 6 && otp.matches("\\d+")) {
                    for (int j = 0; j < 6; j++) {
                        otpArray[i][j] = Character.getNumericValue(otp.charAt(j));
                    }
//                } else {
//                    System.out.println("Invalid OTP at line " + (i + 1));
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return otpArray;
    }
}




