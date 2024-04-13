package OTP;

public class LCM {
    private static long m;
    private static long a;
    private static long c;
    private static long initial;

    public LCM(long m, long a, long c, long initial){
        LCM.m = m;
        LCM.a = a;
        LCM.c = c;
        LCM.initial = initial;
    }

    public long generation(){
        long result;
        result = (a*initial+c)%m;
        initial = result;
        return result;
    }
}
