import RepetitionCount.GeneratingOTPs;
import RepetitionCount.MaxRepetition;
import frontend.*;

public class Main {
    public static void main(String[] args)
    {

        /********** OTP Generation and Validation through gmail using GUI **********/

//
     //new SendOTP();


         /********** bulk OTP generation in order to observe the pattern and check the number of repetition **********/


        int NUMBER_OF_OTP_TO_GENERATE = 100000;
  // GeneratingOTPs.generateOTP(NUMBER_OF_OTP_TO_GENERATE);


      /********** Sort the generated OTPS and check the repetition **********/
        int NUMBER_OF_COUNT = 10000;
       MaxRepetition.sortAndCheckRepetition(NUMBER_OF_COUNT);

    }

}

