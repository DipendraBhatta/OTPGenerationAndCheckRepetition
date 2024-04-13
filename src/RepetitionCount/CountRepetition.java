package RepetitionCount;

public class CountRepetition {
    String checkToString;

    public CountRepetition(String checkToString) {
        this.checkToString = checkToString;
    }

    public void ComparingOTP(int[][] otpArray) {
        String aString = this.checkToString;

        int[] digits = new int[aString.length()];

        for (int i = 0; i < aString.length(); i++) {
            digits[i] = Character.getNumericValue(aString.charAt(i));
        }

        for (int l = 0; l < digits.length; l++) {
            System.out.print(digits[l] + " ");
        }
        System.out.println("\n*******************");

        int rows = otpArray.length;
        int cols = otpArray[0].length;
        int[][] multiArray = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                multiArray[i][j] = otpArray[i][j];
            }
        }

        System.out.println("************************");

        int ncount = 0;
        for (int i = 0; i < rows; i++) {
            int count = 0;
            for (int j = 0; j < cols - 3; j++) {
                if (multiArray[i][j] == digits[j] && multiArray[i][cols - 1 - j] == digits[cols - 1 - j]) {
                    count++;
                }
            }

            if (count == 3) ncount++;
        }

        System.out.println("\nRepetition: " + ncount);
    }
}

