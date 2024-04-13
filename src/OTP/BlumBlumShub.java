package OTP;

public class BlumBlumShub {
    private static long p;
    private static long q;
    private static long n;
    private static long seed;

    public BlumBlumShub(long p, long q, long seed){
        BlumBlumShub.p = p;
        BlumBlumShub.q = q;
        BlumBlumShub.n = p*q;
        BlumBlumShub.seed = seed;
    }

    public long generateNext(){
        long previous = seed % n;
        long next = (previous * previous) % n;
        seed = next;
        return next;
    }
}