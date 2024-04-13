package RepetitionCount;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MaxRepetition {

    public static int[][] readOTPFromFile(String filePath, int numRows, int numCols) {
        int[][] otpArray = new int[numRows][numCols];

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < numRows && scanner.hasNextLine(); i++) {
                String otp = scanner.nextLine().trim();

                if (otp.length() == numCols && otp.matches("\\d+")) {
                    for (int j = 0; j < numCols; j++) {
                        otpArray[i][j] = Character.getNumericValue(otp.charAt(j));
                    }
                } else {
                    System.out.println("Invalid OTP at line " + (i + 1));
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return otpArray;
    }

    public static String concatenateRow(int[] row) {
        StringBuilder result = new StringBuilder();
        for (int digit : row) {
            result.append(digit);
        }
        return result.toString();
    }

    public static void sortRows(int[][] otpArray) {
        for (int i = 0; i < otpArray.length - 1; i++) {
            for (int j = i + 1; j < otpArray.length; j++) {
                String row1 = concatenateRow(otpArray[i]);
                String row2 = concatenateRow(otpArray[j]);
                if (row1.compareTo(row2) > 0) {
                    int[] temp = otpArray[i];
                    otpArray[i] = otpArray[j];
                    otpArray[j] = temp;
                }
            }
        }
    }

    public static void findDuplicateOTPs(int[][] otpArray) {
        boolean[] visited = new boolean[otpArray.length];

        System.out.println("\nDuplicate OTPs:");
        for (int i = 0; i < otpArray.length; i++) {
            if (visited[i]) {
                continue;
            }

            int count = 1;
            for (int j = i + 1; j < otpArray.length; j++) {
                String row1 = concatenateRow(otpArray[i]);
                String row2 = concatenateRow(otpArray[j]);
                if (row1.equals(row2)) {
                    count++;
                    visited[j] = true;
                }
            }

            if (count > 1) {
                System.out.println(concatenateRow(otpArray[i]) + " occurs " + count + " times.");
            }
        }
    }

    public static void sortAndCheckRepetition(int count) {
        String filePath = "otp.txt";
        int numRows = count; // Replace with the actual number of rows
        int numCols = 6; // Replace with the actual number of columns

        int[][] otpArray = readOTPFromFile(filePath, numRows, numCols);

        if (otpArray.length > 0) {
            System.out.println("Original OTP Array:");
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    System.out.print(otpArray[i][j] + " ");
                }
                System.out.println();
            }

            sortRows(otpArray);

            System.out.println("\nSorted OTP Array:");
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    System.out.print(otpArray[i][j] + " ");
                }
                System.out.println();
            }

            findDuplicateOTPs(otpArray);
        }
    }


    /** just testing **/
}
