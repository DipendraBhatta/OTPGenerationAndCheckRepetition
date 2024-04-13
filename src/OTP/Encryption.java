package OTP;

public class Encryption {
    public static String encryptOTP(String otp) {
        StringBuilder encryptedResult = new StringBuilder();

        for (int i = 0; i < otp.length(); i++) {
            char currentChar = otp.charAt(i);
            int currentCharToInt = Integer.parseInt(String.valueOf(currentChar));
            if (currentCharToInt % 2 == 0){
                encryptedResult.append(currentCharToInt + 11);
            } else if (currentCharToInt % 3 == 0) {
                encryptedResult.append(currentCharToInt + 15);
            }else{
                encryptedResult.append(currentCharToInt + 6);
            }
        }

        return encryptedResult.toString();
    }
}
